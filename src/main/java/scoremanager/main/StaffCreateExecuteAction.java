package scoremanager.main;

import java.util.Map;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// パラメータの取得とFormへの詰め替え
		StaffForm form = new StaffForm(req);

		// バリデーション
		Map<String, String> errors = form.validate(school);

		if (!errors.isEmpty()) {
			prepareViewData(req, school);
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_create.jsp").forward(req, res);
			return;
		}

		// 保存処理
		Staff newStaff = form.toEntity(school);
		new StaffDao().save(newStaff);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffCreateDone.action");
	}

	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		req.setAttribute("position_list", new PositionDao().filter(school));
		req.setAttribute("status_list", new StatusDao().filter(school));
	}
}

//	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		Staff staff = (Staff) req.getAttribute("staff");
//		School school = staff.getSchool();
//
//		// リクエストパラメータを取得
//		String no = req.getParameter("no");
//		String lastName = req.getParameter("last_name");
//		String firstName = req.getParameter("first_name");
//		String lastNameKana = req.getParameter("last_name_kana");
//		String firstNameKana = req.getParameter("first_name_kana");
//		String password = req.getParameter("password");
//		String password2 = req.getParameter("password2");
//		String positionId = req.getParameter("position_id");
//		String statusId = req.getParameter("status_id");
//
//		// 初期化
//		StaffDao staffDao = new StaffDao();
//		PositionDao positionDao = new PositionDao();
//		StatusDao statusDao = new StatusDao();
//		Map<String, String> errors = new HashMap<>();
//
//		// バリデーションチェック
//		Validator.required(no, "no", "職員番号を選択してください", errors);
//		if (!errors.containsKey("no")) {
//			if (staffDao.get(no, school) != null) {
//				errors.put("no", "職員番号が重複しています");
//			}
//		}
//		Validator.required(lastName, "last_name", "姓を入力してください", errors);
//		Validator.required(firstName, "first_name", "名を入力してください", errors);
//		Validator.required(lastNameKana, "last_name_kana", "姓（カタカナ）を入力してください", errors);
//		Validator.required(firstNameKana, "first_name_kana", "名（カタカナ）を入力してください", errors);
//		Validator.required(password, "password", "パスワードを入力してください", errors);
//		Validator.required(password2, "password2", "パスワード（確認用）を入力してください", errors);
//
//		if (!errors.containsKey("password") && !errors.containsKey("password2")) {
//			if (!password.equals(password2)) {
//				errors.put("password2", "同じパスワードを入力してください");
//			}
//		}
//
//		Validator.required(positionId, "position_id", "役職を選択してください", errors);
//
//		if (!errors.containsKey("position_id")) {
//			Validator.integer(positionId, "position_id", "役職を選択してください", errors);
//		}
//
//		if (!errors.containsKey("position_id")) {
//			if (positionDao.get(Integer.parseInt(positionId)) == null) {
//				errors.put("position_id", "選択された役職が存在しません");
//			}
//		}
//
//		Validator.required(statusId, "status_id", "状態を選択してください", errors);
//
//		if (!errors.containsKey("status_id")) {
//			Validator.integer(statusId, "status_id", "状態を選択してください", errors);
//		}
//
//		if (!errors.containsKey("status_id")) {
//			if (statusDao.get(Integer.parseInt(statusId)) == null) {
//				errors.put("status_id", "選択された状態が存在しません");
//			}
//		}
//
//		// エラーがある場合は入力画面へ戻す
//		if (!errors.isEmpty()) {
//			List<Position> positionList = positionDao.filter(school);
//			List<Status> statusSet = statusDao.filter(school);
//
//			req.setAttribute("no", no);
//			req.setAttribute("last_name", lastName);
//			req.setAttribute("first_name", firstName);
//			req.setAttribute("last_name_kana", lastNameKana);
//			req.setAttribute("first_name_kana", firstNameKana);
//			req.setAttribute("position_id", positionId);
//			req.setAttribute("status_id", statusId);
//			req.setAttribute("position_list", positionList);
//			req.setAttribute("status_list", statusSet);
//			req.setAttribute("errors", errors);
//
//			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_create.jsp").forward(req, res);
//			return;
//		}
//
//		// 職員情報を登録
//		staff = new Staff();
//
//		staff.setNo(no);
//		staff.setLastName(lastName);
//		staff.setFirstName(firstName);
//		staff.setLastNameKana(lastNameKana);
//		staff.setFirstNameKana(firstNameKana);
//		staff.setPassword(password);
//
//		Position position = positionDao.get(Integer.parseInt(positionId));
//		staff.setPosition(position);
//
//		Status status = statusDao.get(Integer.parseInt(statusId));
//		staff.setStatus(status);
//
//		staff.setSchool(school);
//
//		staffDao.save(staff);
//
//		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StaffCreateDone.action");
//	}
