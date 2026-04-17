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
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	private String baseSql = "select s.ent_year, s.name, s.no, s.class_num, t.no as test_no, t.point from student s join test t on s.no = t.student_no where s.school_cd = ?";
	
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
	    // 学生番号(no)をキーにして、重複を防ぐためのMapを用意
	    Map<String, TestListSubject> map = new HashMap<>();
	    
	    try {
	        while(rSet.next()) {
	            String studentNo = rSet.getString("no");
	            
	            // すでにその学生のインスタンスがMapにあるか確認
	            TestListSubject subject = map.get(studentNo);
	            
	            if (subject == null) {
	                // 初めて出てきた学生なら、新しく作ってMapに入れる
	                subject = new TestListSubject();
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
	        e.printStackTrace();
	    }
	    
	    // Mapにまとめた結果をListに変換して返す
	    return new ArrayList<>(map.values());
	}
	
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String condition = " and s.ent_year = ? and s.class_num = ? and t.subject_cd = ?";
		String order = " order by s.no,t.no";
		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());
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