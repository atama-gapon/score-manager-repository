package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	// 科目コードと学校情報を条件に、該当する科目の詳細情報を1件取得
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null;
		String sql = "SELECT * FROM subject WHERE cd = ? AND school_cd = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, cd);
			statement.setString(2, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					subject = new Subject();
					subject.setCd(resultSet.getString("cd"));
					subject.setName(resultSet.getString("name"));

					SchoolDao schoolDao = new SchoolDao();
					subject.setSchool(schoolDao.get(resultSet.getString("school_cd")));
				}
			}
		}
		return subject;
	}

	// 指定された学校に所属する科目の一覧をコード順で取得
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		String sql = "SELECT * FROM subject WHERE school_cd = ? ORDER BY cd ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Subject subject = new Subject();
					subject.setCd(resultSet.getString("cd"));
					subject.setName(resultSet.getString("name"));
					subject.setSchool(school);
					list.add(subject);
				}
			}
		}
		return list;
	}

	// 科目情報をデータベースに新規登録、または既存の情報を更新
	public boolean save(Subject subject) throws Exception {
		int count = 0;
		Subject old = get(subject.getCd(), subject.getSchool());

		try (Connection connection = getConnection()) {
			if (old == null) {
				// 科目が存在しなかった場合は新規登録
				String insertSql = "INSERT INTO subject (school_cd, cd, name) VALUES (?, ?, ?)";
				try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
					statement.setString(1, subject.getSchool().getCd());
					statement.setString(2, subject.getCd());
					statement.setString(3, subject.getName());
					count = statement.executeUpdate();
				}
			} else {
				// 科目が存在した場合は更新
				String updateSql = "UPDATE subject SET name = ? WHERE school_cd = ? AND cd = ?";
				try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
					statement.setString(1, subject.getName());
					statement.setString(2, subject.getSchool().getCd());
					statement.setString(3, subject.getCd());
					count = statement.executeUpdate();
				}
			}
		}
		return count > 0;
	}

	// 指定された科目情報を削除
	public boolean delete(Subject subject) throws Exception {
		String sql = "DELETE FROM subject WHERE school_cd = ? AND cd = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			count = statement.executeUpdate();
		}
		return count > 0;
	}
}