package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Position;
import bean.School;

public class PositionDao extends Dao {

	public List<Position> filter(School school) throws Exception {

		List<Position> list = new ArrayList<>();

		Connection con = getConnection();

		String sql =
			"SELECT * FROM POSITION WHERE SCHOOL_CD = ? ORDER BY SORT_ORDER";

		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, school.getCd());

		ResultSet rs = st.executeQuery();

		while (rs.next()) {

			Position p = new Position();

			p.setSchoolCd(rs.getString("SCHOOL_CD"));
			p.setId(rs.getInt("ID"));
			p.setName(rs.getString("NAME"));
			p.setSortOrder(rs.getInt("SORT_ORDER"));

			list.add(p);
		}

		st.close();
		con.close();

		return list;
	}
	public boolean save(Position p) throws Exception {

		Connection con = getConnection();

		// 次のIDを取得
		String idSql =
			"SELECT COALESCE(MAX(ID), 0) + 1 AS NEXT_ID FROM POSITION";

		PreparedStatement idSt = con.prepareStatement(idSql);

		ResultSet rs = idSt.executeQuery();

		int nextId = 1;

		if (rs.next()) {
			nextId = rs.getInt("NEXT_ID");
		}

		// INSERT
		String sql =
			"INSERT INTO POSITION(SCHOOL_CD, ID, NAME, SORT_ORDER) VALUES(?, ?, ?, ?)";

		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, p.getSchoolCd());

		// ← ここ重要
		st.setInt(2, nextId);

		st.setString(3, p.getName());

		st.setInt(4, p.getSortOrder());

		int count = st.executeUpdate();

		rs.close();
		idSt.close();
		st.close();
		con.close();

		return count > 0;
	}
	public Position get(int id) throws Exception {

		Connection con = getConnection();

		String sql =
			"SELECT * FROM POSITION WHERE ID = ?";

		PreparedStatement st =
			con.prepareStatement(sql);

		st.setInt(1, id);

		ResultSet rs = st.executeQuery();

		Position p = null;

		if (rs.next()) {

			p = new Position();

			p.setSchoolCd(
				rs.getString("SCHOOL_CD"));

			p.setId(
				rs.getInt("ID"));

			p.setName(
				rs.getString("NAME"));

			p.setSortOrder(
				rs.getInt("SORT_ORDER"));
		}

		rs.close();
		st.close();
		con.close();

		return p;
	}
	public boolean update(Position p)
			throws Exception {

		Connection con = getConnection();

		String sql =
			"UPDATE POSITION "
			+ "SET NAME = ?, SORT_ORDER = ? "
			+ "WHERE ID = ?";

		PreparedStatement st =
			con.prepareStatement(sql);

		st.setString(1, p.getName());

		st.setInt(2, p.getSortOrder());

		st.setInt(3, p.getId());

		int count = st.executeUpdate();

		st.close();
		con.close();

		return count > 0;
	}
	public boolean delete(Position p)
			throws Exception {

		Connection con = getConnection();

		String sql =
			"DELETE FROM POSITION WHERE ID = ?";

		PreparedStatement st =
			con.prepareStatement(sql);

		st.setInt(1, p.getId());

		int count = st.executeUpdate();

		st.close();
		con.close();

		return count > 0;
	}
}


