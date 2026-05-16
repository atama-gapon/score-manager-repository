package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;

public class SchoolDao extends Dao {

	// 学校コード（cd）を条件に、該当する学校の詳細情報を取得
	public School get(String cd) throws Exception {
		School school = null;
		String sql = "SELECT * FROM school WHERE cd = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, cd);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					school = new School();
					school.setCd(resultSet.getString("cd"));
					school.setName(resultSet.getString("name"));
				}
			}
		}

		return school;
	}
}