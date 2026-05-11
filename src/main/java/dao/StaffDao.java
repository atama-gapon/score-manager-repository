package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Position;
import bean.School;
import bean.Staff;
import bean.Status;

public class StaffDao extends Dao {
	// 役割：一意に指定された職員情報を取得
	public Staff get(String no, School school) throws Exception {
		Staff staff = new Staff();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String sql = """
				SELECT
					staff.no,
					staff.last_name,
					staff.first_name,
					staff.last_name_kana,
					staff.first_name_kana,
					staff.position_id,
					position.name AS position_name,
					staff.status_id,
					status.name AS status_name,
					staff.school_cd,
					school.name AS school_name
				FROM
					STAFF
				LEFT JOIN
					POSITION ON staff.position_id = position.id
				LEFT JOIN
					STATUS ON staff.status_id = status.id
				LEFT JOIN
					SCHOOL ON staff.school_cd = school.cd
				WHERE
					staff.no = ? AND staff.school_cd = ?
				""";

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, no);
			statement.setString(2, school.getCd());
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				staff.setNo(resultSet.getString("no"));
				staff.setLastName(resultSet.getString("last_name"));
				staff.setFirstName(resultSet.getString("first_name"));
				staff.setLastNameKana(resultSet.getString("last_name_kana"));
				staff.setFirstNameKana(resultSet.getString("first_name_kana"));

				Position position = new Position();
				position.setId(resultSet.getInt("position_id"));
				position.setName(resultSet.getString("position_name"));
				staff.setPosition(position);

				Status status = new Status();
				status.setId(resultSet.getInt("status_id"));
				status.setName(resultSet.getString("status_name"));
				staff.setStatus(status);

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

	// 役割：ログイン認証を行う
	public Staff login(String schoolCd, String no, String password) throws Exception {
		Staff staff = new Staff();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from staff where school_cd=? and no=? and password=?");
			statement.setString(1, schoolCd);
			statement.setString(2, no);
			statement.setString(3, password);
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				staff.setNo(resultSet.getString("no"));
				staff.setPassword(resultSet.getString("password"));
				staff.setLastName(resultSet.getString("last_name"));
				staff.setFirstName(resultSet.getString("first_name"));
				staff.setLastNameKana(resultSet.getString("last_name_kana"));
				staff.setFirstNameKana(resultSet.getString("first_name_kana"));
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
		String sql = """
				SELECT
					staff.no,
					staff.last_name,
					staff.first_name,
					staff.last_name_kana,
					staff.first_name_kana,
					staff.position_id,
					position.name AS position_name,
					staff.status_id,
					status.name AS status_name,
					staff.school_cd
				FROM
					STAFF
				LEFT JOIN
					POSITION ON staff.position_id = position.id
				LEFT JOIN
					STATUS ON staff.status_id = status.id
				LEFT JOIN
					SCHOOL ON staff.school_cd = school.cd
				WHERE
					staff.school_cd = ?
				""";

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, school.getCd());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Staff staff = new Staff();
				staff.setNo(resultSet.getString("no"));
				staff.setLastName(resultSet.getString("last_name"));
				staff.setFirstName(resultSet.getString("first_name"));
				staff.setLastNameKana(resultSet.getString("last_name_kana"));
				staff.setFirstNameKana(resultSet.getString("first_name_kana"));

				Position position = new Position();
				position.setId(resultSet.getInt("position_id"));
				position.setName(resultSet.getString("position_name"));
				staff.setPosition(position);

				Status status = new Status();
				status.setId(resultSet.getInt("status_id"));
				status.setName(resultSet.getString("status_name"));
				staff.setStatus(status);

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

	// 職員をデータベースに保存する
	public boolean save(Staff staff) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// データベースから職員を取得
			Staff old = get(staff.getNo(), staff.getSchool());
			if (old == null) {
				// 学生が存在しなかった場合
				statement = connection.prepareStatement(
						"insert into staff(no, last_name, first_name, last_name_kana, first_name_kana, password, position_id, status_id, school_cd) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				statement.setString(1, staff.getNo());
				statement.setString(2, staff.getLastName());
				statement.setString(3, staff.getFirstName());
				statement.setString(4, staff.getLastNameKana());
				statement.setString(5, staff.getFirstNameKana());
				statement.setString(6, staff.getPassword());
				statement.setInt(7, staff.getPosition().getId());
				statement.setInt(8, staff.getStatus().getId());
				statement.setString(9, staff.getSchool().getCd());
			} else {
				// 学生が存在した場合
				statement = connection
						.prepareStatement("update staff set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				statement.setString(1, staff.getNo());
				statement.setString(2, staff.getLastName());
				statement.setString(3, staff.getFirstName());
				statement.setString(4, staff.getLastNameKana());
				statement.setString(5, staff.getFirstNameKana());
				statement.setString(6, staff.getPassword());
				statement.setInt(7, staff.getPosition().getId());
				statement.setInt(8, staff.getStatus().getId());
				statement.setString(9, staff.getSchool().getCd());
			}

			count = statement.executeUpdate();

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
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}