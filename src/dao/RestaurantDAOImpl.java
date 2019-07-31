package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import sql.Sql;

public class RestaurantDAOImpl extends BaseDAO implements RestaurantDAO {

	@Override
	public List<Restaurant> selectAll() {

		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(Sql.RESTAURANT_SELECT_ALL_SQL);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Restaurant restaurant = new Restaurant();

				restaurant.setrNum(resultSet.getInt("rNum"));
				restaurant.setrName(resultSet.getString("rName"));
				restaurant.setrPhoneNum(resultSet.getString("rPhoneNum"));
				restaurant.setcNum(resultSet.getInt("cNum"));
				restaurant.setTownNum(resultSet.getInt("townNum"));
				restaurant.setStarAvg(resultSet.getInt("starAvg"));

				restaurants.add(restaurant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}

		return restaurants;
	}

	@Override
	public List<Restaurant> selectByName(String name) {

		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(Sql.RESTAURANT_SELECT_BY_NAME_SQL);
			preparedStatement.setString(1, "%" + name + "%");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				Restaurant restaurant = new Restaurant();

				restaurant.setrName(resultSet.getString("rName"));
				restaurant.setrPhoneNum(resultSet.getString("rPhoneNum"));
				restaurant.setStarAvg(resultSet.getInt("starAvg"));

				restaurants.add(restaurant);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(resultSet, preparedStatement, connection);
		}

		return restaurants;
	}

	@Override
	public boolean insertRestaurant(Restaurant restaurant) {

		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(Sql.RESTAURANT_INSERT_RESTAURANT_SQL);

			preparedStatement.setInt(1, restaurant.getrNum());
			preparedStatement.setString(2, restaurant.getrName());
			preparedStatement.setString(3, restaurant.getrPhoneNum());
			preparedStatement.setInt(4, restaurant.getcNum());
			preparedStatement.setInt(5, restaurant.getTownNum());
			preparedStatement.setInt(6, restaurant.getStarAvg());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, preparedStatement, connection);
		}
		return result;
	}

	@Override
	public boolean updateRestaurant(Restaurant restaurant) {

		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(Sql.RESTAURANT_UPDATE_RESTAURANT_SQL);

			preparedStatement.setString(1, restaurant.getrName());
			preparedStatement.setString(2, restaurant.getrPhoneNum());
			preparedStatement.setInt(3, restaurant.getcNum());
			preparedStatement.setInt(4, restaurant.getTownNum());
			preparedStatement.setInt(5, restaurant.getStarAvg());
			preparedStatement.setInt(6, restaurant.getrNum());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, preparedStatement, connection);
		}

		return result;
	}

	@Override
	public boolean deleteRestaurant(int rNum) {
		
		boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(Sql.RESTAURANT_DELETE_RESTAURANT_SQL);

			preparedStatement.setInt(1, rNum);

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDBObjects(null, preparedStatement, connection);
		}

		return result;
	}

}