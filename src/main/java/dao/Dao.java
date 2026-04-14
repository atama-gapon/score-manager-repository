package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

// 役割：DBに接続するための共通部分
public class Dao {
	static DataSource ds;
	
	public Connection  getConnection() throws Exception {
		if (ds==null) {
			InitialContext ic = new InitialContext();
			ds = (DataSource)ic.lookup("java:/comp/env/jdbc/score_management");
		}
		return ds.getConnection();
	}
}