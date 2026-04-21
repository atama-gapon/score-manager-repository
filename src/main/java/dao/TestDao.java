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
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    while (rSet.next()) {
	        Test test = new Test();
	        Student student = new Student();
	        student.setEntYear(rSet.getInt("ent_year"));
	        student.setNo(rSet.getString("student_no"));
	        student.setName(rSet.getString("name"));
	        test.setStudent(student);

	        // テストデータがDBにない場合、SQLで一緒に取得した「検索に使った値」をセットする
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

	        test.setPoint(rSet.getInt("point"));
	        test.setClassNum(rSet.getString("class_num"));
	        test.setSchool(school);
	        list.add(test);
	    }
	    return list;
	}
	
	//入学年度、クラス番号、科目、回数、学校情報を指定して、得点の一覧を取得
	// 入学年度、クラス番号、科目、回数、学校情報を指定して、得点の一覧を取得
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    // ポイント：SELECT句に検索条件（科目コードと回数）を定数として含める
	    // これにより postFilter の引数を増やさずに、中身を補完できます
	    String sql = "select s.no as student_no, s.name, s.ent_year, s.class_num, "
	            + "t.subject_cd, t.no as test_no, t.point, "
	            + "? as filter_sub, ? as filter_num " 
	            + "from student s "
	            + "left join test t on s.no = t.student_no and t.subject_cd = ? and t.no = ? "
	            + "where s.school_cd = ? and s.ent_year = ? and s.class_num = ? "
	            + "and s.is_attend = true " // ★これ（在学中のみ）を追加
	            + "order by s.no asc";

	    try {
	        statement = connection.prepareStatement(sql);
	        // パラメータセット（順番を間違えないように！）
	        statement.setString(1, subject.getCd()); // filter_sub
	        statement.setInt(2, num);                // filter_num
	        statement.setString(3, subject.getCd()); // 結合用 subject_cd
	        statement.setInt(4, num);                // 結合用 no
	        statement.setString(5, school.getCd());  // school_cd
	        statement.setInt(6, entYear);            // ent_year
	        statement.setString(7, classNum);        // class_num

	        resultSet = statement.executeQuery();
	        resultSet = statement.executeQuery();
	        
	        // 設計書通りの引数
	        list = postFilter(resultSet, school);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	    return list;
	}

	
    // 一括で保存するためのメソッド
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
	
	// 一括保存
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(
	            "update test set point = ? where student_no = ? and subject_cd = ? and no = ? and school_cd = ?"
	        );
	        statement.setInt(1, test.getPoint());
	        statement.setString(2, test.getStudent().getNo());
	        statement.setString(3, test.getSubject());
	        statement.setInt(4, test.getNo());
	        statement.setString(5, test.getSchool().getCd());
	        
	        int rowCount = statement.executeUpdate();
	        if (rowCount == 0) {
	        	statement.close();
				statement = connection.prepareStatement("insert into test (student_no, subject_cd, school_cd, no, point, class_num) values(?,?,?,?,?,?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
				statement.executeUpdate();
	        }
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
		}
        return true;
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
