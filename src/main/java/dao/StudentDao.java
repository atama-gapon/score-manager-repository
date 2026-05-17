package dao;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
	// 学校コードを条件とするベースSQL
	private String baseSql = "SELECT * FROM student WHERE school_cd = ?";

	// 学生番号を指定して、該当する学生の詳細情報を1件取得
	public Student get(String no) throws Exception {
		Student student = null;
		String sql = "SELECT * FROM student WHERE no = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, no);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					student = new Student();
					student.setNo(resultSet.getString("no"));
					student.setName(resultSet.getString("name"));
					student.setEntYear(resultSet.getInt("ent_year"));
					student.setClassNum(resultSet.getString("class_num"));
					student.setAttend(resultSet.getBoolean("is_attend"));

					SchoolDao schoolDao = new SchoolDao();
					student.setSchool(schoolDao.get(resultSet.getString("school_cd")));
				}
			}
		}
		return student;
	}

	// 指定されたクラス番号に所属する学生が存在するかチェック
	public boolean hasStudentInClass(String classNum) throws Exception {
		String sql = "SELECT * FROM student WHERE class_num = ?";
		boolean isFound = false;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, classNum);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					isFound = true;
				}
			}
		}
		return isFound;
	}

	// 指定された学校に所属する学生の全入学年度を降順（重複なし）で取得
	public List<Integer> getEntYearList(School school) throws Exception {
		List<Integer> list = new ArrayList<>();
		String sql = "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year DESC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					list.add(resultSet.getInt("ent_year"));
				}
			}
		}
		return list;
	}

	// 検索結果を走査し、学生エンティティのリストに変換
	private List<Student> postFilter(ResultSet resultSet, School school) {
		List<Student> list = new ArrayList<>();

		try {
			while (resultSet.next()) {
				Student student = new Student();
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(school);
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			// 呼び出し元への影響を抑えるため、例外発生時は空のリストを返す
		}
		return list;
	}

	// 学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String condition = " AND ent_year = ? AND class_num = ?";
		String conditionIsAttend = isAttend ? " AND is_attend = true" : "";
		String order = " ORDER BY no ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet, school);
			}
		}
		return list;
	}

	// 学校、入学年度、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String condition = " AND ent_year = ?";
		String conditionIsAttend = isAttend ? " AND is_attend = true" : "";
		String order = " ORDER BY no ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet, school);
			}
		}
		return list;
	}

	// 学校、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		String conditionIsAttend = isAttend ? " AND is_attend = true" : "";
		String order = " ORDER BY no ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(baseSql + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet, school);
			}
		}
		return list;
	}

	// 学生情報をデータベースに新規登録、または既存の情報を更新
	public boolean save(Student student) throws Exception {
		int count = 0;
		Student old = get(student.getNo());

		try (Connection connection = getConnection()) {
			if (old == null) {
				// 学生が存在しなかった場合は新規登録
				String insertSql = "INSERT INTO student (no, name, ent_year, class_num, is_attend, school_cd) VALUES (?, ?, ?, ?, ?, ?)";
				try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
					statement.setString(1, student.getNo());
					statement.setString(2, student.getName());
					statement.setInt(3, student.getEntYear());
					statement.setString(4, student.getClassNum());
					statement.setBoolean(5, student.isAttend());
					statement.setString(6, student.getSchool().getCd());
					count = statement.executeUpdate();
				}
			} else {
				// 学生が存在した場合は更新
				String updateSql = "UPDATE student SET name = ?, ent_year = ?, class_num = ?, is_attend = ? WHERE no = ?";
				try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
					statement.setString(1, student.getName());
					statement.setInt(2, student.getEntYear());
					statement.setString(3, student.getClassNum());
					statement.setBoolean(4, student.isAttend());
					statement.setString(5, student.getNo());
					count = statement.executeUpdate();
				}
			}
		}
		return count > 0;
	}

	// CSVファイルから読み込んだ複数の学生情報と不足しているクラス情報を一括登録（トランザクション制御）
	public boolean Bulk(BufferedReader br, String schoolCd) throws Exception {
		int count = 0;
		String classSql = "INSERT INTO class_num (school_cd, class_num) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM class_num WHERE school_cd = ? AND class_num = ?)";
		String studentSql = "INSERT INTO student (school_cd, no, name, ent_year, class_num, is_attend) VALUES (?, ?, ?, ?, ?, ?)";

		// オートコミットの変更やロールバック制御を行うため、Connection自体はtry-with-resourcesの外部で定義
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false); // トランザクション開始

			try (PreparedStatement classSt = connection.prepareStatement(classSql);
					PreparedStatement statement = connection.prepareStatement(studentSql)) {

				String line;
				while ((line = br.readLine()) != null) {
					if (line.trim().isEmpty()) {
						continue;
					}

					String[] data = line.split(",", -1);

					if (data.length < 5) {
						throw new Exception("CSVの形式が正しくありません");
					}

					String attend = data[4].trim().toLowerCase();
					if (!attend.equals("true") && !attend.equals("false")) {
						throw new Exception("在学フラグを正しい形で入力してください(true/false)");
					}

					// クラスの重複チェック挿入を実行
					classSt.setString(1, schoolCd);
					classSt.setString(2, data[3].trim());
					classSt.setString(3, schoolCd);
					classSt.setString(4, data[3].trim());
					classSt.executeUpdate();

					// 学生情報をバッチに追加
					statement.setString(1, schoolCd);
					statement.setString(2, data[0].trim());
					statement.setString(3, data[1].trim());
					statement.setInt(4, Integer.parseInt(data[2].trim()));
					statement.setString(5, data[3].trim());
					statement.setBoolean(6, Boolean.parseBoolean(data[4].trim()));

					statement.addBatch();
					count++;
				}

				statement.executeBatch(); // 一括実行
				connection.commit(); // コミット
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback(); // ロールバック
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			String errorMsg = e.getMessage();
			if (errorMsg != null) {
				if (errorMsg.contains("Duplicate") || errorMsg.contains("PRIMARY")) {
					errorMsg = "既に登録されている学生が含まれています";
				}
			} else if (e instanceof NumberFormatException) {
				errorMsg = "入学年度に数値以外の値が含まれています";
			}
			throw new Exception(errorMsg);
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // オートコミットを元に戻す
					connection.close();
				} catch (SQLException e) {
					// クローズ時の例外は握りつぶすかログ出力
				}
			}
		}

		return count > 0;
	}
}