package controller;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MenuDAOImpl;
import model.City;
import model.Comments;
import model.Menu;
import model.Restaurant;
import model.Selected_menu;
import model.Town;
import model.UserOrderList;
import model.UserOrderListSub;
import model.Users;

@WebServlet(name = "MainController", urlPatterns = { "/login_link", "/join_link", "/qa_board_link", "/event_board_link",
		"/home_link", "/search_link", "/addr_search", "/logout_link", "/admin_home_link", "/restaurant_detail",
		"/order_confirm", "/ordermenu_add", "/return_detail", "/order_final", "/confirm_orders", "/myPage_link",
		"/order_end", "/login_index_link", "/test", "/test2", "/user_orderlist" })

public class MainController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);

	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		MenuDAOImpl Mimpl = null;
		String uri = req.getRequestURI();
		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);

		if (action.equals("login_index_link")) {

			HttpSession session = req.getSession();
			session.removeAttribute("caller");
			RequestDispatcher rd = req.getRequestDispatcher("join/login.jsp");
			rd.forward(req, resp);

		}

		else if (action.equals("login_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("join/login.jsp");
			rd.forward(req, resp);

		} else if (action.equals("logout_link")) {
			HttpSession session = req.getSession();
			session.removeAttribute("caller");
			RequestDispatcher rd = req.getRequestDispatcher("user_logout");
			rd.forward(req, resp);

		} else if (action.equals("join_link")) {
			
			HttpSession session = req.getSession();
			session.setAttribute("chkid", 0);
			
			RequestDispatcher rd = req.getRequestDispatcher("join/join.jsp");
			rd.forward(req, resp);

		} else if (action.equals("qa_board_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("board/qaboard.jsp");
			rd.forward(req, resp);

		} else if (action.equals("event_board_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("board/eventboard.jsp");
			rd.forward(req, resp);

		} else if (action.equals("home_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, resp);

		} else if (action.equals("admin_home_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("admin/adminScreen.jsp");
			rd.forward(req, resp);

		}

		else if (action.equals("search_link")) {
			Mimpl = new MenuDAOImpl();

			HttpSession session = req.getSession();
			int category = Integer.parseInt(req.getParameter("category"));
			req.setAttribute("categ", category);

			try {
				Users user = (Users) session.getAttribute("users");
				List<String> tokens = Mimpl.useridToAddr(user.getUserId());

				List<Restaurant> lists = Mimpl.selectByTownnumAndCnum(category, tokens.get(0), tokens.get(1));
				req.setAttribute("lists", lists);

				req.setAttribute("userCity", tokens.get(0));
				req.setAttribute("userTown", tokens.get(1));
			} catch (Exception e) {
				List<Restaurant> lists = Mimpl.selectByCategory(category);
				req.setAttribute("lists", lists);
				req.setAttribute("userCity", "전체");
				req.setAttribute("userTown", "전체");
			}

			List<City> citylists = Mimpl.selectAllCity();
			req.setAttribute("citylist", citylists);

			List<Town> townlists = Mimpl.selectAllTown();
			req.setAttribute("townlist", townlists);

			session.setAttribute("caller", "/search_link?category=" + category);

			RequestDispatcher rd = req.getRequestDispatcher("main/search.jsp");
			rd.forward(req, resp);

		} else if (action.equals("addr_search")) {
			Mimpl = new MenuDAOImpl();
			String city = req.getParameter("selectCity");
			String town = req.getParameter("selectTown");
			int selctedCate = Integer.parseInt(req.getParameter("catego"));


			req.setAttribute("userCity", city);
			req.setAttribute("userTown", town);
			
			List<Restaurant> lists = Mimpl.selectByTownnumAndCnum(selctedCate, city, town);
			req.setAttribute("lists", lists);
			req.setAttribute("categ", selctedCate);
			
			RequestDispatcher rd = req.getRequestDispatcher("main/search.jsp");
			rd.forward(req, resp);

		}

		else if (action.equals("restaurant_detail")) {
			Mimpl = new MenuDAOImpl();
			int rno = Integer.parseInt(req.getParameter("rno"));
			Restaurant r = new Restaurant();

			List<Menu> lists = Mimpl.menuDetailSelectByRnum(rno);
			List<Comments> comments_list = Mimpl.selectByRnumComments(rno);
			r = Mimpl.selectByrNum(rno);

			HttpSession session = req.getSession();

			session.setAttribute("detailR", r);
			session.setAttribute("lists", lists);
			session.setAttribute("comments_list", comments_list);

			RequestDispatcher rd = req.getRequestDispatcher("main/restaurant_detail.jsp");
			rd.forward(req, resp);
		}

		else if (action.equals("order_confirm")) {

			int mno = Integer.parseInt(req.getParameter("mnum"));
			req.setAttribute("mno", mno);

			RequestDispatcher rd = req.getRequestDispatcher("test2.jsp");
			rd.forward(req, resp);
		}

		else if (action.equals("ordermenu_add")) {
			System.out.println("check");
			int menuNum = Integer.parseInt(req.getParameter("num"));
			int menuCnt = Integer.parseInt(req.getParameter("cnt"));
			boolean cnt_plus_chk = false;
			HttpSession session = req.getSession();

			Restaurant r = (Restaurant) session.getAttribute("detailR");
			if ((List<Selected_menu>) session.getAttribute("order_lists") == null) {
				List<Selected_menu> order_lists = new ArrayList<Selected_menu>();
				session.setAttribute("order_restaurant", r.getrName());
				session.setAttribute("order_lists", order_lists);
			} else {
				if (r.getrName() != (String) session.getAttribute("order_restaurant")) {
					session.removeAttribute("order_lists");
					session.removeAttribute("order_restaurant");
					List<Selected_menu> order_lists = new ArrayList<Selected_menu>();
					session.setAttribute("order_restaurant", r.getrName());
					session.setAttribute("order_lists", order_lists);
				}
			}

			Selected_menu m = new Selected_menu();

			ArrayList<Selected_menu> order_lists = (ArrayList<Selected_menu>) session.getAttribute("order_lists");
			int price = 0;
			MenuDAOImpl impl = new MenuDAOImpl();
			for (int i = 0; i < order_lists.size(); i++) {
				if (menuNum == order_lists.get(i).getmNum()) {
					order_lists.get(i).setCount(order_lists.get(i).getCount() + menuCnt);
					order_lists.get(i).setPrice(order_lists.get(i).getCount() * impl.priceOfMenu(menuNum));
					cnt_plus_chk = true;
					break;
				}
			}
			if (cnt_plus_chk)
				;
			else {

				m.setPrice(menuCnt * impl.priceOfMenu(menuNum));
				m.setmName(impl.MnumToMname(menuNum));
				m.setmNum(menuNum);
				m.setCount(menuCnt);
				order_lists.add(m);
			}

			for (Selected_menu mi : order_lists)
				price += mi.getPrice();

			System.out.println(menuNum + " " + menuCnt);
			session.setAttribute("caller", "index.jsp");
			session.setAttribute("total_price", price);
			session.setAttribute("order_lists", order_lists);

		} else if (action.equals("return_detail")) {

			RequestDispatcher rd = req.getRequestDispatcher("main/restaurant_detail.jsp");
			rd.forward(req, resp);

		} else if (action.equals("order_final")) {

			RequestDispatcher rd = req.getRequestDispatcher("order/order_confirm.jsp");
			rd.forward(req, resp);

		} else if (action.equals("confirm_orders")) {
			HttpSession session = req.getSession();

			int chk = Integer.parseInt(req.getParameter("delichk"));
			session.setAttribute("delivery_check", chk);

		} else if (action.equals("myPage_link")) {

			RequestDispatcher rd = req.getRequestDispatcher("join/myPage.jsp");
			rd.forward(req, resp);

		} else if (action.equals("order_end")) {
			
			Mimpl = new MenuDAOImpl();
			HttpSession session = req.getSession();
			try {
				Users user = (Users) session.getAttribute("users");
				int chk = (int) session.getAttribute("delivery_check");
				ArrayList<Selected_menu> order_lists = (ArrayList<Selected_menu>) session.getAttribute("order_lists");

				Mimpl.insertUserOrder(user.getUserId(), chk);
				int orderNumber = Mimpl.nowOrderOnum();
				
				for (int i = 0; i < order_lists.size(); i++) {
					int sales = Mimpl.getMsales(order_lists.get(i).getmNum());
					sales += order_lists.get(i).getCount();
					Mimpl.insertOrderMenu(order_lists.get(i).getmNum(), orderNumber, order_lists.get(i).getCount());
					Mimpl.menuSalesUpdate(sales, order_lists.get(i).getmNum());
				}
				session.removeAttribute("total_price");
				session.removeAttribute("order_lists");
				RequestDispatcher rd = req.getRequestDispatcher("order/finish.jsp");
				rd.forward(req, resp);
			}
			catch(Exception e){
				RequestDispatcher rd = req.getRequestDispatcher("login_link");
				rd.forward(req, resp);;
			}
			

		} else if (action.equals("test")) {

			RequestDispatcher rd = req.getRequestDispatcher("imageUpload/imgup.jsp");
			rd.forward(req, resp);

		} else if (action.equals("test2")) {

			RequestDispatcher rd = req.getRequestDispatcher("imageUpload/upload.jsp");
			rd.forward(req, resp);

		}
		else if(action.equals("user_orderlist")) {
			MenuDAOImpl impl = new MenuDAOImpl();
			
			HttpSession session = req.getSession();
			
			try {
			Users user = (Users) session.getAttribute("users");
			List<UserOrderList> list = impl.userOrderList(user.getUserId());
			for(int index = 0; index < list.size(); index++) {
				List<UserOrderListSub> subList = impl.orderInfoSub(list.get(index).getoNum());
				for(UserOrderListSub a : subList) {
					System.out.println(a.toString());
				}
				if(subList.size() != 0) {
					list.get(index).setOrderRName(subList.get(0).getrName());
					list.get(index).setOrderRNum(subList.get(0).getrNum());
				}
				String orderFullList = "";
				int total = 0;
				for(int index_sub = 0; index_sub < subList.size(); index_sub++) {
					orderFullList = orderFullList + subList.get(index_sub).getmName() + ":" + subList.get(index_sub).getCount() + "개 ";
					total += subList.get(index_sub).getCount() * subList.get(index_sub).getmPrice();
				}
				list.get(index).setOrderListName(orderFullList);
				list.get(index).setTotalPrice(total);
			}
			req.setAttribute("order_list", list);
			
			RequestDispatcher rd = req.getRequestDispatcher("userOrderList.jsp");
			rd.forward(req, resp);
			}
			catch(Exception e) {
				RequestDispatcher rd = req.getRequestDispatcher("login_link");
				rd.forward(req, resp);
			}
			
		}
	}
}