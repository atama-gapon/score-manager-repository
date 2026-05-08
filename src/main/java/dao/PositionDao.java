package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Position;
import bean.School;

public class PositionDao extends Dao {

	public List<Position> filter(School school) throws Exception {
		List<Position> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement("select * from position where school_cd = ? order by sort_order");
			statement.setString(1, school.getCd());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Position position = new Position();

				position.setId(resultSet.getInt("ID"));
				position.setName(resultSet.getString("NAME"));
				position.setSortOrder(resultSet.getInt("SORT_ORDER"));
				position.setSchool(school);

				list.add(position);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw e;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw e;
				}
			}
		}
		return list;
	}

	public boolean save(Position p) throws Exception {
		Connection connection = getConnection();

		// 次のIDを取得
		String idSql = "SELECT COALESCE(MAX(ID), 0) + 1 AS NEXT_ID FROM POSITION";

		PreparedStatement idSt = connection.prepareStatement(idSql);

		ResultSet resultSet = idSt.executeQuery();

		int nextId = 1;

		if (resultSet.next()) {
			nextId = resultSet.getInt("NEXT_ID");
		}

		String sql = "INSERT INTO POSITION(SCHOOL_CD, ID, NAME, SORT_ORDER) VALUES(?, ?, ?, ?)";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, p.getSchool().getCd());
		statement.setInt(2, nextId);
		statement.setString(3, p.getName());
		statement.setInt(4, p.getSortOrder());

		int count = statement.executeUpdate();

		resultSet.close();
		idSt.close();
		statement.close();
		connection.close();

		return count > 0;
	}

	public Position get(int id) throws Exception {

		Connection connection = getConnection();

		String sql = "SELECT * FROM POSITION WHERE ID = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, id);

		ResultSet resultSet = statement.executeQuery();

		Position p = null;

		if (resultSet.next()) {

			p = new Position();
			p.setId(
					resultSet.getInt("ID"));

			p.setName(
					resultSet.getString("NAME"));

			p.setSortOrder(
					resultSet.getInt("SORT_ORDER"));
		}

		resultSet.close();
		statement.close();
		connection.close();

		return p;
	}

	public boolean update(Position p)
			throws Exception {

		Connection connection = getConnection();

		String sql = "UPDATE POSITION "
				+ "SET NAME = ?, SORT_ORDER = ? "
				+ "WHERE ID = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, p.getName());

		statement.setInt(2, p.getSortOrder());

		statement.setInt(3, p.getId());

		int count = statement.executeUpdate();

		statement.close();
		connection.close();

		return count > 0;
	}

	public boolean delete(Position p)
			throws Exception {

		Connection connection = getConnection();

		String sql = "DELETE FROM POSITION WHERE ID = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setInt(1, p.getId());

		int count = statement.executeUpdate();

		statement.close();
		connection.close();

		return count > 0;
	}
}