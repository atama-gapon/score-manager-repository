package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {
	// 教師情報を取得
	public Teacher get(String id) throws Exception {
		Teacher teacher = new Teacher();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from teacher where id=?");
			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				teacher.setSchool((School)resultSet.getObject("school"));
			} else {
				teacher = null;
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
		
		return teacher;
	}
	
	public Teacher login(String id, String password) {
		return null;
		// 一致しているか確認？
		// 一致していなかったらnull入りのTeacher
		// 一致していたらいろんな情報を詰め込んだTeacher
		
		
	}
}
