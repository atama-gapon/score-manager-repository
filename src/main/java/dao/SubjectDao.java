package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {
	public Subject get(String cd, School school) throws Exception {
		Subject subject = new Subject();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			if (resultSet.next()) {
				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				subject = null;
			}
		} catch (Exception e) {
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
		
		return subject;
	}
	
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.prepareStatement("select * from SUBJECT where school_cd = ? order by cd asc");
			statement.setString(1, school.getCd());
			resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Subject subject = new Subject();

				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchool(school);

				list.add(subject);
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
	
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		
		try {
			statement = connection.prepareStatement("insert into subject(school_cd, cd, name) values(?, ?, ?);");
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			statement.setString(3, subject.getName());

			count = statement.executeUpdate();

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
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(Subject subject) {
		return false;
		
	}
}
