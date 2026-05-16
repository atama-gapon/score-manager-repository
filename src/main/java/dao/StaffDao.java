package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Position;
import bean.School;
import bean.Staff;
import bean.Status;

public class StaffDao extends Dao {

	// 職員番号と学校情報を条件に、一意に指定された職員の詳細情報を取得
	public Staff get(String no, School school) throws Exception {
		Staff staff = null;
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
					staff
				LEFT JOIN
					position ON staff.position_id = position.id
				LEFT JOIN
					status ON staff.status_id = status.id
				LEFT JOIN
					school ON staff.school_cd = school.cd
				WHERE
					staff.no = ? AND staff.school_cd = ?
				""";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, no);
			statement.setString(2, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					staff = new Staff();
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
				}
			}
		}
		return staff;
	}

	// 指定された状態を持つ職員が存在するかチェック
	public boolean hasStaffInStatus(int id) throws Exception {
		String sql = "SELECT * FROM staff WHERE status_id = ?";
		boolean isFound = false;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					isFound = true;
				}
			}
		}
		return isFound;
	}

	// 指定された役職を持つ職員が存在するかチェック
	public boolean hasStaffInPosition(int id) throws Exception {
		String sql = "SELECT * FROM staff WHERE position_id = ?";
		boolean isFound = false;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					isFound = true;
				}
			}
		}
		return isFound;
	}

	// 職員番号と学校コードをもとに、該当する職員のパスワードハッシュを取得
	public String findPasswordHashByStaffNo(String no, String schoolCd) throws Exception {
		String sql = "SELECT password_hash FROM staff WHERE no = ? AND school_cd = ?";
		String passwordHash = "";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, no);
			statement.setString(2, schoolCd);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					passwordHash = resultSet.getString("password_hash");
				}
			}
		}
		return passwordHash;
	}

	// 学校コード、職員番号、パスワードハッシュをもとにログイン認証を行う
	public Staff login(String schoolCd, String no, String password) throws Exception {
		Staff staff = null;
		String sql = "SELECT * FROM staff WHERE school_cd = ? AND no = ? AND password_hash = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, schoolCd);
			statement.setString(2, no);
			statement.setString(3, password);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					staff = new Staff();
					staff.setNo(resultSet.getString("no"));
					staff.setPassword(resultSet.getString("password_hash"));
					staff.setLastName(resultSet.getString("last_name"));
					staff.setFirstName(resultSet.getString("first_name"));
					staff.setLastNameKana(resultSet.getString("last_name_kana"));
					staff.setFirstNameKana(resultSet.getString("first_name_kana"));

					School school = new School();
					school.setCd(resultSet.getString("school_cd"));

					SchoolDao schoolDao = new SchoolDao();
					school.setName(schoolDao.get(school.getCd()).getName());
					staff.setSchool(school);
				}
			}
		}
		return staff;
	}

	// 指定された学校に所属する全職員の一覧を取得
	public List<Staff> filter(School school) throws Exception {
		List<Staff> list = new ArrayList<>();
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
					staff
				LEFT JOIN
					position ON staff.position_id = position.id
				LEFT JOIN
					status ON staff.status_id = status.id
				LEFT JOIN
					school ON staff.school_cd = school.cd
				WHERE
					staff.school_cd = ?
				""";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
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
			}
		}
		return list;
	}

	// 職員情報をデータベースに新規登録、または既存の情報を更新
	public boolean save(Staff staff) throws Exception {
		int count = 0;

		// 既存の職員が存在するか確認
		Staff old = get(staff.getNo(), staff.getSchool());

		try (Connection connection = getConnection()) {
			if (old == null) {
				// 職員が存在しなかった場合は新規登録
				String insertSql = "INSERT INTO staff(no, last_name, first_name, last_name_kana, first_name_kana, password_hash, position_id, status_id, school_cd) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
					statement.setString(1, staff.getNo());
					statement.setString(2, staff.getLastName());
					statement.setString(3, staff.getFirstName());
					statement.setString(4, staff.getLastNameKana());
					statement.setString(5, staff.getFirstNameKana());
					statement.setString(6, staff.getPassword());
					statement.setInt(7, staff.getPosition().getId());
					statement.setInt(8, staff.getStatus().getId());
					statement.setString(9, staff.getSchool().getCd());
					count = statement.executeUpdate();
				}
			} else {
				// 職員が存在した場合は更新
				String updateSql = "UPDATE staff SET last_name = ?, first_name = ?, last_name_kana = ?, first_name_kana = ?, password_hash = ?, position_id = ?, status_id = ? WHERE no = ? AND school_cd = ?";
				try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
					statement.setString(1, staff.getLastName());
					statement.setString(2, staff.getFirstName());
					statement.setString(3, staff.getLastNameKana());
					statement.setString(4, staff.getFirstNameKana());
					statement.setString(5, staff.getPassword());
					statement.setInt(6, staff.getPosition().getId());
					statement.setInt(7, staff.getStatus().getId());
					statement.setString(8, staff.getNo());
					statement.setString(9, staff.getSchool().getCd());
					count = statement.executeUpdate();
				}
			}
		}
		return count > 0;
	}

	// 既存の職員情報を更新（パスワード以外）
	public boolean update(Staff staff) throws Exception {
		String sql = """
				UPDATE staff
				SET
					last_name = ?,
					first_name = ?,
					last_name_kana = ?,
					first_name_kana = ?,
					position_id = ?,
					status_id = ?
				WHERE
					no = ? AND school_cd = ?
				""";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, staff.getLastName());
			statement.setString(2, staff.getFirstName());
			statement.setString(3, staff.getLastNameKana());
			statement.setString(4, staff.getFirstNameKana());
			statement.setInt(5, staff.getPosition().getId());
			statement.setInt(6, staff.getStatus().getId());
			statement.setString(7, staff.getNo());
			statement.setString(8, staff.getSchool().getCd());

			count = statement.executeUpdate();
		}
		return count > 0;
	}
}