package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;



public class TestListStudentDao extends Dao {
	private String baseSql = "select s.name, t.subject_cd, t.no as test_no, t.point from test t join subject s on t.subject_cd = s.cd where t.school_cd = ?";

	//SQLの結果をList<Test>に変換する
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		try {
			while(rSet.next()) {
				TestListStudent student = new TestListStudent();

				student.setSubjectName(rSet.getString("name"));
				student.setSubjectCd(rSet.getString("subject_cd"));
				student.setNum(rSet.getInt("test_no"));
				student.setPoint(rSet.getInt("point"));
				
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	//学籍番号でデータを取得
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String condition = " and t.student_no = ?";
		String order = " order by t.subject_cd";
		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, student.getSchool().getCd());
			statement.setString(2, student.getNo());
			
			resultSet = statement.executeQuery();
			list = postFilter(resultSet);
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
