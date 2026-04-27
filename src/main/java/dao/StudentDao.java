package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

// 役割：学生テーブルに対して処理を行う
public class StudentDao extends Dao {
	private String baseSql = "select * from student where school_cd=?";
	
	// 学生番号を指定して学生インスタンスを1件取得。
	// 学生変更の処理内で学生の詳細データを取得する際に利用。
	public Student get(String no) throws Exception {
		Student student = new Student();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from student where no=?");
			statement.setString(1, no);
			ResultSet resultSet = statement.executeQuery();
			SchoolDao schoolDao = new SchoolDao();
			if (resultSet.next()) {
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				student = null;
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
		
		return student;
	}
	
	// 指定したクラス番号の中に学生がいるか
	public boolean hasStudentInClass(String classNum) throws Exception {
		boolean isFound = false;
		
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from student where class_num = ?");
			statement.setString(1, classNum);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				isFound = true;
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
		
		return isFound;
	}
	
	// 処理内容：フィルター後のリストへの格納
	private List<Student> postFilter(ResultSet resultSet, School school) {
		List<Student> list = new ArrayList<>();
		
		try {
			while(resultSet.next()) {
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
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String condition = " and ent_year=? and class_num=?";
		String order = " order by no asc";
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
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
	
	// 学校、入学年度、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String condition = " and ent_year=?";
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		String order = " order by no asc";
		
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
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
	
	// 学校、在学フラグを指定して学生の一覧を取得
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}
		String order = " order by no asc";
		
		try {
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			statement.setString(1, school.getCd());
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
	
	// 学生インスタンスをデータベースに保存する
	public boolean save(Student student) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;
		
		try {
			// データベースから学生を取得
			Student old = get(student.getNo());
			if (old == null) {
				// 学生が存在しなかった場合
				statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				// 学生が存在した場合
				statement = connection.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
			}

			count = statement.executeUpdate();

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
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}
