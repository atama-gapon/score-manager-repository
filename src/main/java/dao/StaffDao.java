package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;

public class StaffDao extends Dao {
	// 役割：一意に指定された職員情報を取得
	public Staff get(String no) throws Exception {
		Staff staff = new Staff();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from staff where no=?");
			statement.setString(1, no);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				staff.setNo(resultSet.getString("no"));
				staff.setPassword(resultSet.getString("password"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name_kana(resultSet.getString("last_name_kana"));
				staff.setFirst_name_kana(resultSet.getString("first_name_kana"));
				staff.setSchool((School) resultSet.getObject("school"));
			} else {
				staff = null;
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
		return staff;
	}

	// 役割：職員番号とパスワードで認証を行う
	public Staff login(String no, String password) throws Exception {
		Staff staff = new Staff();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from staff where no = ? and password = ?");
			statement.setString(1, no);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				staff.setNo(resultSet.getString("no"));
				staff.setPassword(resultSet.getString("password"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name_kana(resultSet.getString("last_name_kana"));
				staff.setFirst_name_kana(resultSet.getString("first_name_kana"));
				School school = new School();
				school.setCd(resultSet.getString("school_cd"));
				school.setName(schoolDao.get(school.getCd()).getName());
				staff.setSchool(school);
			} else {
				staff = null;
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
		return staff;
	}

	// 学校コードに合致する職員の一覧を取得
	public List<Staff> filter(School school) throws Exception {
		List<Staff> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from staff where school_cd = ?");
			statement.setString(1, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Staff staff = new Staff();
				staff.setNo(resultSet.getString("no"));
				staff.setLast_name(resultSet.getString("last_name"));
				staff.setFirst_name(resultSet.getString("first_name"));
				staff.setLast_name_kana(resultSet.getString("last_name_kana"));
				staff.setFirst_name_kana(resultSet.getString("first_name_kana"));
				staff.setSchool(school);
				list.add(staff);
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
}