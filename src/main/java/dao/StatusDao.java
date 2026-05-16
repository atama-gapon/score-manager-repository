package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Status;

public class StatusDao extends Dao {

	// 指定された学校に所属する在籍状態の一覧をソート順で取得
	public List<Status> filter(School school) throws Exception {
		List<Status> list = new ArrayList<>();
		String sql = "SELECT id, name, sort_order FROM status WHERE school_cd = ? ORDER BY sort_order ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Status s = new Status();
					s.setId(resultSet.getInt("id"));
					s.setName(resultSet.getString("name"));
					s.setSortOrder(resultSet.getInt("sort_order"));
					s.setSchool(school);
					list.add(s);
				}
			}
		}
		return list;
	}

	// 同じ学校内に同名の在籍状態が既に存在するかチェック
	public boolean existsByName(String name, School school) throws Exception {
		String sql = "SELECT COUNT(*) FROM status WHERE name = ? AND school_cd = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setString(2, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					count = resultSet.getInt(1);
				}
			}
		}
		return count > 0;
	}

	// 指定されたIDに一致する在籍状態の詳細情報を取得
	public Status get(int id) throws Exception {
		String sql = "SELECT id, name, sort_order FROM status WHERE id = ?";
		Status s = null;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					s = new Status();
					s.setId(resultSet.getInt("id"));
					s.setName(resultSet.getString("name"));
					s.setSortOrder(resultSet.getInt("sort_order"));
				}
			}
		}
		return s;
	}

	// 新しい在籍状態の情報を登録
	public boolean save(Status s) throws Exception {
		String sql = "INSERT INTO status (name, sort_order, school_cd) VALUES (?, ?, ?)";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, s.getName());
			statement.setInt(2, s.getSortOrder());
			statement.setString(3, s.getSchool().getCd());

			count = statement.executeUpdate();
		}
		return count == 1;
	}

	// 既存の在籍状態の名前やソート順を更新
	public boolean update(Status s) throws Exception {
		String sql = "UPDATE status SET name = ?, sort_order = ? WHERE id = ? AND school_cd = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, s.getName());
			statement.setInt(2, s.getSortOrder());
			statement.setInt(3, s.getId());
			statement.setString(4, s.getSchool().getCd());

			count = statement.executeUpdate();
		}
		return count == 1;
	}

	// 指定されたIDの在籍状態情報を削除
	public boolean delete(int id) throws Exception {
		String sql = "DELETE FROM status WHERE id = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);
			count = statement.executeUpdate();
		}
		return count > 0;
	}
}