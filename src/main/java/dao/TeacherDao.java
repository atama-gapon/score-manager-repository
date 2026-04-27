package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public Teacher login(String id, String password) throws Exception {
		// 一致しているか確認？
		// 一致していなかったらnull入りのTeacher
		// 一致していたらいろんな情報を詰め込んだTeacher
		Teacher teacher = new Teacher();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from teacher where id = ? and password = ?");
			statement.setString(1, id);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			
			if (resultSet.next()) {
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				School school = new School();
				school.setCd(resultSet.getString("school_cd"));
				school.setName(schoolDao.get(school.getCd()).getName());
				
				teacher.setSchool(school);
			}else {
				teacher = null;
			}
			
		}catch (Exception e) {
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
	
	
	// 学校コードに合致する教師の一覧を取得
	public List<Teacher> filter(School school) throws Exception {
		List<Teacher> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from teacher where school_cd = ?");
			statement.setString(1, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setId(resultSet.getString("id"));
				teacher.setName(resultSet.getString("name"));
				teacher.setSchool(school);
				list.add(teacher);
			}
		} catch (Exception e) {
			// 例外の再スロー
			e.printStackTrace();
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
			
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		
		return list;
	}
}
