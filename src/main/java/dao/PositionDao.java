package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Position;
import bean.School;

public class PositionDao extends Dao {
	// 学校ごとの全件取得
	public List<Position> filter(School school) throws Exception {
		List<Position> list = new ArrayList<>();
		String sql = "SELECT * FROM position WHERE school_cd = ? ORDER BY sort_order";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Position position = new Position();
					position.setId(resultSet.getInt("id"));
					position.setName(resultSet.getString("name"));
					position.setSortOrder(resultSet.getInt("sort_order"));
					position.setSchool(school);
					list.add(position);
				}
			}
		}
		return list;
	}

	public boolean save(Position p) throws Exception {
		String idSql = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM position";
		String sql = "INSERT INTO position(school_cd, id, name, sort_order) VALUES(?, ?, ?, ?)";

		try (Connection connection = getConnection()) {
			int nextId = 1;

			try (PreparedStatement idSt = connection.prepareStatement(idSql);
					ResultSet resultSet = idSt.executeQuery()) {
				if (resultSet.next()) {
					nextId = resultSet.getInt("next_id");
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, p.getSchool().getCd());
				statement.setInt(2, nextId);
				statement.setString(3, p.getName());
				statement.setInt(4, p.getSortOrder());

				return statement.executeUpdate() > 0;
			}
		}
	}

	public Position get(int id) throws Exception {
		String sql = "SELECT * FROM position WHERE id = ?";
		Position p = null;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					p = new Position();
					p.setId(resultSet.getInt("id"));
					p.setName(resultSet.getString("name"));
					p.setSortOrder(resultSet.getInt("sort_order"));
				}
			}
		}
		return p;
	}

	public boolean update(Position p) throws Exception {
		String sql = "UPDATE position SET name = ?, sort_order = ? WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, p.getName());
			statement.setInt(2, p.getSortOrder());
			statement.setInt(3, p.getId());

			return statement.executeUpdate() > 0;
		}
	}

	public boolean delete(int id) throws Exception {
		String sql = "DELETE FROM position WHERE id = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			return statement.executeUpdate() > 0;
		}
	}
}