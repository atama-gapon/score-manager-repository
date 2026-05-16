package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	// 学生、科目、学校、テスト回数を条件に、該当する特定の得点情報を1件取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = null;
		String sql = "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ? AND no >= 0";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					test = new Test();
					test.setStudent(student);
					test.setNo(no);
					test.setPoint(resultSet.getInt("point"));
					test.setSubject(subject.getCd());
					test.setSchool(school);
				}
			}
		}
		return test;
	}

	// 検索結果を走査し、学生情報と得点情報を組み合わせたテストリストに変換
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		// ループ内での無駄なインスタンス生成を防ぐため、Daoを外側で定義
		StaffDao staffDao = new StaffDao();

		while (rSet.next()) {
			Test test = new Test();
			Student student = new Student();
			student.setEntYear(rSet.getInt("ent_year"));
			student.setNo(rSet.getString("student_no"));
			student.setName(rSet.getString("name"));
			test.setStudent(student);

			// テストデータがDBにない場合、SQLで一緒に取得した「検索に使った値」を補填
			String subCd = rSet.getString("subject_cd");
			if (subCd == null) {
				subCd = rSet.getString("filter_sub");
			}
			test.setSubject(subCd);

			int tNo = rSet.getInt("test_no");
			if (tNo == 0) {
				tNo = rSet.getInt("filter_num");
			}
			test.setNo(tNo);

			test.setMarkerStaff(staffDao.get(rSet.getString("marker_staff_no"), school));
			test.setPoint(rSet.getInt("point"));
			test.setClassNum(rSet.getString("class_num"));
			test.setSchool(school);
			list.add(test);
		}
		return list;
	}

	// 入学年度、クラス番号、科目、回数、学校情報を条件に、学生情報と得点（成績）の一覧を取得
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		String sql = "SELECT s.no AS student_no, s.name, s.ent_year, s.class_num, t.subject_cd, t.no AS test_no, t.point, t.marker_staff_no, ? AS filter_sub, ? AS filter_num "
				+ "FROM student s LEFT JOIN test t ON s.no = t.student_no AND s.school_cd = t.school_cd "
				+ "AND t.subject_cd = ? AND t.no = ? AND t.no >= 0 "
				+ "WHERE s.school_cd = ? AND s.ent_year = ? AND s.class_num = ? AND s.is_attend = true ORDER BY s.no ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, subject.getCd());
			statement.setInt(2, num);
			statement.setString(3, subject.getCd());
			statement.setInt(4, num);
			statement.setString(5, school.getCd());
			statement.setInt(6, entYear);
			statement.setString(7, classNum);

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet, school);
			}
		}
		return list;
	}

	// 複数の成績情報を一括でデータベースに保存（トランザクション制御）
	public boolean save(List<Test> list) throws Exception {
		// オートコミットの制御を行うため、Connectionはtry-with-resourcesの外部で宣言
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false); // トランザクション開始

			for (Test test : list) {
				save(test, connection);
			}

			connection.commit(); // すべて成功したらコミット
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback(); // エラー時はロールバック
			}
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.setAutoCommit(true); // オートコミットを元に戻す
					connection.close();
				} catch (SQLException e) {
					// クローズ時の例外
				}
			}
		}
		return true;
	}

	// 1件の成績情報を保存（既存データがあれば更新、なければ新規登録）
	private boolean save(Test test, Connection connection) throws Exception {
		String updateSql = "UPDATE test SET point = ?, marker_staff_no = ? WHERE student_no = ? AND subject_cd = ? AND no = ? AND school_cd = ?";
		String insertSql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, marker_staff_no) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
			statement.setInt(1, test.getPoint());
			statement.setString(2, test.getMarkerStaff().getNo());
			statement.setString(3, test.getStudent().getNo());
			statement.setString(4, test.getSubject());
			statement.setInt(5, test.getNo());
			statement.setString(6, test.getSchool().getCd());

			int rowCount = statement.executeUpdate();

			// アップデート対象が無かった（0行更新だった）場合はインサートに切り替え
			if (rowCount == 0) {
				try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
					insertStmt.setString(1, test.getStudent().getNo());
					insertStmt.setString(2, test.getSubject());
					insertStmt.setString(3, test.getSchool().getCd());
					insertStmt.setInt(4, test.getNo());
					insertStmt.setInt(5, test.getPoint());
					insertStmt.setString(6, test.getMarkerStaff().getNo());
					insertStmt.executeUpdate();
				}
			}
		}
		return true;
	}

	// 成績データを論理削除（テスト回数の値を現在時刻ベースの負の数に変更）
	public boolean delete(Test test) throws Exception {
		String sql = "UPDATE test SET no = ? WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?";
		int count = 0;

		// 現在時刻を取得して負の数値に落とし込む
		LocalDateTime now = LocalDateTime.now();
		int num = (now.getMonthValue() * 10000000
				+ now.getDayOfMonth() * 100000
				+ now.getHour() * 1000
				+ now.getMinute() * 10
				+ now.getSecond() / 6) * -1;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, num);
			statement.setString(2, test.getStudent().getNo());
			statement.setString(3, test.getSubject());
			statement.setString(4, test.getSchool().getCd());
			statement.setInt(5, test.getNo());

			count = statement.executeUpdate();
		}
		return count > 0;
	}
}