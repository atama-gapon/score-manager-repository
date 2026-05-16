package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestSubjectList;

public class TestSubjectListDao extends Dao {
	// 学校コードを条件とするベースの結合SQL
	private String baseSql = "SELECT s.ent_year, s.name, s.no, s.class_num, t.no AS test_no, t.point FROM student s JOIN test t ON s.no = t.student_no AND s.school_cd = t.school_cd WHERE s.school_cd = ?";

	// 検索結果を走査し、学生番号をキーとして試験回数ごとの得点を集計したリストに変換
	private List<TestSubjectList> postFilter(ResultSet rSet) throws Exception {
		// 学生番号(no)をキーにして、重複を防ぐためのMapを用意
		Map<String, TestSubjectList> map = new HashMap<>();

		try {
			while (rSet.next()) {
				String studentNo = rSet.getString("no");

				// すでにその学生のインスタンスがMapにあるか確認
				TestSubjectList subject = map.get(studentNo);

				if (subject == null) {
					// 初めて出てきた学生なら、新しく作ってMapに入れる
					subject = new TestSubjectList();
					subject.setEntYear(rSet.getInt("ent_year"));
					subject.setStudentName(rSet.getString("name"));
					subject.setStudentNo(studentNo);
					subject.setClassNum(rSet.getString("class_num"));
					map.put(studentNo, subject);
				}

				// 点数情報を追加（既存の学生でも新しい学生でもここでMapにputPointされる）
				subject.putPoint(rSet.getInt("test_no"), rSet.getInt("point"));
			}
		} catch (SQLException | NullPointerException e) {
			// 呼び出し元への影響を抑えるため、例外発生時はそこまでの集計マップを処理に回す
		}

		// Mapにまとめた結果をListに変換して返す
		return new ArrayList<>(map.values());
	}

	// 入学年度、クラス番号、科目、学校情報を条件に、該当クラス全員の試験結果一覧を取得
	public List<TestSubjectList> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestSubjectList> list = new ArrayList<>();
		String condition = " AND s.ent_year = ? AND s.class_num = ? AND t.subject_cd = ? AND t.no > 0";
		String order = " ORDER BY s.no ASC, t.no ASC";

		// try-with-resources を適用して Connection と PreparedStatement を自動クローズ
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(baseSql + condition + order)) {

			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				list = postFilter(resultSet);
			}
		}
		return list;
	}
}