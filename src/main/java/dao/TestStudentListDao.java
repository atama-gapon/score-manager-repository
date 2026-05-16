package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestStudentList;

public class TestStudentListDao extends Dao {
	// 学校コードを条件とするベースの結合SQL
	private String baseSql = "SELECT s.name, t.subject_cd, t.no AS test_no, t.point FROM test t JOIN subject s ON t.school_cd = s.school_cd AND t.subject_cd = s.cd WHERE t.school_cd = ?";

	// 検索結果を走査し、学生用テスト結果（TestStudentList）のリストに変換
	private List<TestStudentList> postFilter(ResultSet rSet) throws Exception {
		List<TestStudentList> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				TestStudentList student = new TestStudentList();
				student.setSubjectName(rSet.getString("name"));
				student.setSubjectCd(rSet.getString("subject_cd"));
				student.setNum(rSet.getInt("test_no"));
				student.setPoint(rSet.getInt("point"));
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			// 呼び出し元への影響を抑えるため、例外発生時はそこまでのリスト（または空リスト）を返す
		}
		return list;
	}

	// 学籍番号を条件に、該当する学生のテスト結果一覧を科目コード順で取得
	public List<TestStudentList> filter(Student student) throws Exception {
		List<TestStudentList> list = new ArrayList<>();
		String condition = " AND t.student_no = ? AND t.no >= 0";
		String order = " ORDER BY t.subject_cd ASC";

		// try-with-resources を適用して Connection と PreparedStatement を自動クローズ
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(baseSql + condition + order)) {

			statement.setString(1, student.getSchool().getCd());
			statement.setString(2, student.getNo());

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet);
			}
		}
		return list;
	}
}