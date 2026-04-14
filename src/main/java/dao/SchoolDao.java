package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao {
	// 学校情報を取得
	public School get(String cd) throws Exception {
		School school = new School();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from school where cd=?");
			statement.setString(1, cd);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				school.setCd(resultSet.getString("cd"));
				school.setName(resultSet.getString("name"));
			} else {
				school = null;
			}
		} catch (Exception e) {
			// 例外の再スロー
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
		
		return school;
	}
}
