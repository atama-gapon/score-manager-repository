package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Status;

public class StatusDao extends Dao {

	// 一覧取得（学校ごと）
	public List<Status> filter(School school) throws Exception {
		List<Status> list = new ArrayList<>();

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"SELECT id, name, sort_order FROM status WHERE school_cd=? ORDER BY sort_order");
		st.setString(1, school.getCd());

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Status s = new Status();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSortOrder(rs.getInt("sort_order"));
			s.setSchool(school);
			list.add(s);
		}

		st.close();
		con.close();
		return list;
	}

	//同名チェック
	public boolean existsByName(String name, School school) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM status WHERE name=? AND school_cd=?");
		st.setString(1, name);
		st.setString(2, school.getCd());

		ResultSet rs = st.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		st.close();
		con.close();

		return count > 0;
	}

	// 1件取得
	public Status get(int id) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("SELECT id, name, sort_order FROM status WHERE id=?");
		st.setInt(1, id);

		ResultSet rs = st.executeQuery();

		Status s = null;
		if (rs.next()) {
			s = new Status();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSortOrder(rs.getInt("sort_order"));
		}

		st.close();
		con.close();

		return s;
	}

	// 新規登録
	public boolean save(Status s) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"INSERT INTO status(name, sort_order, school_cd) VALUES(?, ?, ?)");
		st.setString(1, s.getName());
		st.setInt(2, s.getSortOrder());
		st.setString(3, s.getSchool().getCd());

		int line = st.executeUpdate();

		st.close();
		con.close();

		return line == 1;
	}

	// 更新
	public boolean update(Status s) throws Exception {

		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement(
				"UPDATE status SET name=?, sort_order=? WHERE id=? AND school_cd=?");
		st.setString(1, s.getName());
		st.setInt(2, s.getSortOrder());
		st.setInt(3, s.getId());
		st.setString(4, s.getSchool().getCd());

		int line = st.executeUpdate();

		st.close();
		con.close();

		return line==1;
	}

	public boolean delete(Status status) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement(
                "delete from status where id=? and school_cd=?"
            );
            statement.setInt(1, status.getId());
            statement.setString(2, status.getSchool().getCd());  
            count = statement.executeUpdate();

        } catch (Exception e) {
            throw e;

        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException e) { throw e; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException e) { throw e; }
            }
        }

        return count > 0;
    }
}
