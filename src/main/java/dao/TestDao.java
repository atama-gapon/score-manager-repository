package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao {
	private String baseSql = "select s.no as student_no, s.name, s.ent_year, s.class_num, t.subject_cd, t.no as test_no, t.point from student s left join test t on s.no = t.student_no where s.school_cd=?";
	//取得した学生番号、科目、学校情報、回数を元に得点情報を取得する
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select point from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				test.setPoint(resultSet.getInt("point"));
				
				
			} else {
				test = null;
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
		
		return test;
	}	
	//SQLの結果をList<Test>に変換する
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		 List<Test> list = new ArrayList<>();

		    while (rSet.next()) {
		        Test test = new Test();
		        Student student = new Student();
		        student.setEntYear(rSet.getInt("ent_year"));
		        student.setNo(rSet.getString("student_no"));
		        test.setStudent(student);
		        
		        test.setSubject(rSet.getString("subject_cd"));
		        test.setNo(rSet.getInt("test_no"));
		        test.setPoint(rSet.getInt("point"));
		        test.setClassNum(rSet.getString("class_num"));
		        test.setSchool(school);
		        list.add(test);
		    }

		    return list;
		}
	
	//入学年度、クラス番号、科目、回数、学校情報を指定して、得点の一覧を取得
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String condition = " and s.ent_year = ? and s.class_num = ? and t.subject_cd = ? and t.no = ? ";
		String order = " order by s.no ";
		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());
			statement.setInt(5, num);
			resultSet = statement.executeQuery();
			list = postFilter(resultSet, school);
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
    // 得点更新
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);

            for (Test test : list) {
                
                save(test, connection);
            }

            
            connection.commit();
		}catch (Exception e) {
			// 例外の再スロー
			connection.rollback();
			e.printStackTrace();
			throw e;
		} finally {
            if (connection != null) {
                connection.close();
            }
        }
        return true;
	}
	
	// 得点追加
	private boolean save(Test test, Connection connection) throws Exception {
		
	}
	
	// 成績削除
//	public boolean delete(Test test) throws Exception {
//		Connection connection = getConnection();
//		PreparedStatement statement = null;
//		int count = 0;
//		
//		try {
//			statement = connection.prepareStatement("");
//			statement.setString(1, subject.getSchool().getCd());
//			statement.setString(2, subject.getCd());
//
//			count = statement.executeUpdate();
//
//		} catch (Exception e) {
//			// 例外の再スロー
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if (statement != null) {
//				try {
//					statement.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//					throw e;
//				}
//			}
//			
//			if (connection != null) {
//				try {
//					connection.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//					throw e;
//				}
//			}
//		}
//		
//		if (count > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
