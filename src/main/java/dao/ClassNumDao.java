package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {
	
	// 取得したクラス番号、学校情報をもとにクラス情報を返却するメソッド
	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select * from class_num where class_num = ? and school_cd = ?");
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			SchoolDao sDao = new SchoolDao();
			if (resultSet.next()) {
				classNum.setClass_num(resultSet.getString("class_num"));
				classNum.setSchool(sDao.get(resultSet.getString("school_cd")));
			} else {
				classNum = null;
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
		
		return classNum;
	}
	
	// 引数で指定された学校に所属している、クラス一覧を取得
	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select class_num from class_num where school_cd=? order by class_num");
			statement.setString(1, school.getCd());;
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				list.add(resultSet.getString("class_num"));
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
	
	public boolean save(ClassNum classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		
		try {
			statement = connection.prepareStatement("insert into class_num(class_num, school_cd) values(?, ?);");
			statement.setString(1, classNum.getClass_num());
			statement.setString(2, classNum.getSchool().getCd());
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

	public boolean save(ClassNum classNum, String newClassNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		
		try {
			statement = connection.prepareStatement("update class_num set class_num=? where class_num=? and school_cd=?");
			statement.setString(1, newClassNum);
			statement.setString(2, classNum.getClass_num());
			statement.setString(3, classNum.getSchool().getCd());
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
	
	public boolean delete(ClassNum classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		
		try {
			statement = connection.prepareStatement("delete from class_num where school_cd=? and class_num=?;");
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClass_num());

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
}