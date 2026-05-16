package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import tool.PasswordHasher;
import tool.Validator;

public class StaffForm {

	// フィールド
	private String no;
	private String lastName;
	private String firstName;
	private String lastNameKana;
	private String firstNameKana;
	private String password;
	private String password2;
	private String positionId;
	private String statusId;

	// コンストラクタ：リクエストから値を取り出す
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

	// ゲッター
	public String getNo() {
		return no;
	}

	// 新規登録用のバリデーションチェック
	public Map<String, String> validate(School school) throws Exception {
		StaffDao staffDao = new StaffDao();
		Map<String, String> errors = new HashMap<>();

		// 職員番号のチェック
		Validator.required(no, "no", "職員番号を選択してください", errors);
		if (!errors.containsKey("no")) {
			if (staffDao.get(no, school) != null) {
				errors.put("no", "職員番号が重複しています");
			}
		}

		// 氏名のチェック
		Validator.required(lastName, "last_name", "姓を入力してください", errors);
		Validator.required(firstName, "first_name", "名を入力してください", errors);
		Validator.required(lastNameKana, "last_name_kana", "姓（カタカナ）を入力してください", errors);
		Validator.required(firstNameKana, "first_name_kana", "名（カタカナ）を入力してください", errors);

		// パスワードのチェック
		Validator.required(password, "password", "パスワードを入力してください", errors);
		Validator.required(password2, "password2", "パスワード（確認用）を入力してください", errors);

		if (!errors.containsKey("password") && !errors.containsKey("password2")) {
			if (!password.equals(password2)) {
				errors.put("password2", "同じパスワードを入力してください");
			}
		}

		// 役職と状態のチェック
		Validator.required(positionId, "position_id", "役職を選択してください", errors);
		Validator.required(statusId, "status_id", "状態を選択してください", errors);

		return errors;
	}

	// 更新用のバリデーションチェック
	public Map<String, String> validateForUpdate(School school) throws Exception {
		Map<String, String> errors = new HashMap<>();

		// 氏名のチェック
		Validator.required(lastName, "last_name", "姓を入力してください", errors);
		Validator.required(firstName, "first_name", "名を入力してください", errors);
		Validator.required(lastNameKana, "last_name_kana", "姓（カタカナ）を入力してください", errors);
		Validator.required(firstNameKana, "first_name_kana", "名（カタカナ）を入力してください", errors);

		// 役職と状態のチェック
		Validator.required(positionId, "position_id", "役職を選択してください", errors);
		Validator.required(statusId, "status_id", "状態を選択してください", errors);

		return errors;
	}

	// 新規登録用のエンティティ変換
	public Staff toEntity(School school) throws Exception {
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();
		Staff staff = new Staff();

		// 基本情報をセット
		staff.setNo(no);
		staff.setLastName(lastName);
		staff.setFirstName(firstName);
		staff.setLastNameKana(lastNameKana);
		staff.setFirstNameKana(firstNameKana);
		staff.setSchool(school);

		// パスワードをハッシュ化してセット
		if (password != null && !password.isEmpty()) {
			staff.setPassword(PasswordHasher.hash(password));
		}

		// 役職と状態を数値変換してセット
		if (positionId != null && !positionId.isEmpty()) {
			staff.setPosition(positionDao.get(Integer.parseInt(positionId)));
		}
		if (statusId != null && !statusId.isEmpty()) {
			staff.setStatus(statusDao.get(Integer.parseInt(statusId)));
		}

		return staff;
	}

	// 更新用のエンティティ変換
	public Staff toEntityForUpdate(Staff original, School school) throws Exception {
		PositionDao positionDao = new PositionDao();
		StatusDao statusDao = new StatusDao();

		// 基本情報を上書き
		original.setLastName(lastName);
		original.setFirstName(firstName);
		original.setLastNameKana(lastNameKana);
		original.setFirstNameKana(firstNameKana);

		// パスワードは入力があったときだけハッシュ化して上書き
		if (password != null && !password.isEmpty()) {
			original.setPassword(PasswordHasher.hash(password));
		}

		// 役職と状態を数値変換して上書き
		if (positionId != null && !positionId.isEmpty()) {
			original.setPosition(positionDao.get(Integer.parseInt(positionId)));
		}
		if (statusId != null && !statusId.isEmpty()) {
			original.setStatus(statusDao.get(Integer.parseInt(statusId)));
		}

		return original;
	}

	// 画面表示用のデータを準備（エラー発生時の入力値の復元）
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