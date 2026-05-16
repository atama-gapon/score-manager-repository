package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

	// クラス番号と学校情報を条件に、該当するクラスの詳細情報を取得
	public ClassNum get(String classNumStr, School school) throws Exception {
		ClassNum classNum = null;
		String sql = "SELECT * FROM class_num WHERE class_num = ? AND school_cd = ?";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, classNumStr);
			statement.setString(2, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					classNum = new ClassNum();
					classNum.setClassNum(resultSet.getString("class_num"));

					// 実際に使用するタイミングでDaoのインスタンスを生成
					SchoolDao sDao = new SchoolDao();
					classNum.setSchool(sDao.get(resultSet.getString("school_cd")));
				}
			}
		}

		return classNum;
	}

	// 指定された学校に所属しているクラス番号の一覧を昇順で取得
	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		String sql = "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num ASC";

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					list.add(resultSet.getString("class_num"));
				}
			}
		}

		return list;
	}

	// 新しいクラス情報を登録
	public boolean save(ClassNum classNum) throws Exception {
		String sql = "INSERT INTO class_num (class_num, school_cd) VALUES (?, ?)";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, classNum.getClassNum());
			statement.setString(2, classNum.getSchool().getCd());
			count = statement.executeUpdate();
		}

		return count > 0;
	}

	// 既存のクラス番号を新しいクラス番号へ更新
	public boolean save(ClassNum classNum, String newClassNum) throws Exception {
		String sql = "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newClassNum);
			statement.setString(2, classNum.getClassNum());
			statement.setString(3, classNum.getSchool().getCd());
			count = statement.executeUpdate();
		}

		return count > 0;
	}

	// 指定されたクラス情報を削除
	public boolean delete(ClassNum classNum) throws Exception {
		String sql = "DELETE FROM class_num WHERE school_cd = ? AND class_num = ?";
		int count = 0;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClassNum());
			count = statement.executeUpdate();
		}

		return count > 0;
	}
}