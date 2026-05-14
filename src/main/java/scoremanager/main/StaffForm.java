package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import tool.PasswordUtil;
import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import tool.Validator;

public class StaffForm {
	private String no;
	private String lastName;
	private String firstName;
	private String lastNameKana;
	private String firstNameKana;
	private String password;
	private String password2;
	private String positionId;
	private String statusId;

	// コンストラクタでリクエストから値を一気に抜き出す
	public StaffForm(HttpServletRequest req) {
		this.no = req.getParameter("no");
		this.lastName = req.getParameter("last_name");
		this.firstName = req.getParameter("first_name");
		this.lastNameKana = req.getParameter("last_name_kana");
		this.firstNameKana = req.getParameter("first_name_kana");
		this.password = req.getParameter("password");
		this.password2 = req.getParameter("password2");
		this.positionId = req.getParameter("position_id");
		this.statusId = req.getParameter("status_id");
	}

	// バリデーション
	public Map<String, String> validate(School school) throws Exception {
		Map<String, String> errors = new HashMap<>();
		StaffDao staffDao = new StaffDao();
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		Validator.required(no, "no", "職員番号を選択してください", errors);
		if (!errors.containsKey("no")) {
			if (staffDao.get(no, school) != null) {
				errors.put("no", "職員番号が重複しています");
			}
		}
		Validator.required(lastName, "last_name", "姓を入力してください", errors);
		Validator.required(firstName, "first_name", "名を入力してください", errors);
		Validator.required(lastNameKana, "last_name_kana", "姓（カタカナ）を入力してください", errors);
		Validator.required(firstNameKana, "first_name_kana", "名（カタカナ）を入力してください", errors);
		Validator.required(password, "password", "パスワードを入力してください", errors);
		Validator.required(password2, "password2", "パスワード（確認用）を入力してください", errors);

		if (!errors.containsKey("password") && !errors.containsKey("password2")) {
			if (!password.equals(password2)) {
				errors.put("password2", "同じパスワードを入力してください");
			}
		}

		Validator.required(positionId, "position_id", "役職を選択してください", errors);

		if (!errors.containsKey("position_id")) {
			Validator.integer(positionId, "position_id", "役職を選択してください", errors);
		}

		if (!errors.containsKey("position_id")) {
			if (positionDao.get(Integer.parseInt(positionId)) == null) {
				errors.put("position_id", "選択された役職が存在しません");
			}
		}

		Validator.required(statusId, "status_id", "状態を選択してください", errors);

		if (!errors.containsKey("status_id")) {
			Validator.integer(statusId, "status_id", "状態を選択してください", errors);
		}

		if (!errors.containsKey("status_id")) {
			if (statusDao.get(Integer.parseInt(statusId)) == null) {
				errors.put("status_id", "選択された状態が存在しません");
			}
		}

		return errors;
	}

	// 正常な場合、保存用の型に変換する
	public Staff toEntity(School school) throws Exception {
		Staff staff = new Staff();
		staff.setNo(no);
		staff.setLastName(lastName);
		staff.setFirstName(firstName);
		staff.setLastNameKana(lastNameKana);
		staff.setFirstNameKana(firstNameKana);
		staff.setPassword(PasswordUtil.hashPassword(password));
		staff.setSchool(school);

		// IDからインスタンスを取得してセット
		PositionDao positionDao = new PositionDao();
		staff.setPosition(positionDao.get(Integer.parseInt(positionId)));

		StatusDao statusDao = new StatusDao();
		staff.setStatus(statusDao.get(Integer.parseInt(statusId)));

		return staff;
	}

	// エラー時に値を画面に戻すための補助メソッド
	public void setAttributes(HttpServletRequest req) {
		req.setAttribute("no", no);
		req.setAttribute("last_name", lastName);
		req.setAttribute("first_name", firstName);
		req.setAttribute("last_name_kana", lastNameKana);
		req.setAttribute("first_name_kana", firstNameKana);
		req.setAttribute("position_id", positionId);
		req.setAttribute("status_id", statusId);
	}
}
