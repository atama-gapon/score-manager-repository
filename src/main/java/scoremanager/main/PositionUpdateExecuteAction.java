package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Position;
import bean.School;
import bean.Staff;
import dao.PositionDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class PositionUpdateExecuteAction extends Action {

	// 入力された役職変更情報のバリデーションを行い、問題がなければデータベースを更新する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String idStr = req.getParameter("id");
		String name = req.getParameter("name");
		String sortOrderStr = req.getParameter("sort_order");

		// インスタンス化
		PositionDao positionDao = new PositionDao();
		Map<String, String> errors = new HashMap<>();

		int id = 0;
		int sortOrder = 0;

		// バリデーション
		try {
			if (idStr != null && !idStr.isEmpty()) {
				id = Integer.parseInt(idStr);
			} else {
				errors.put("id", "不正なリクエストです");
			}
		} catch (NumberFormatException e) {
			errors.put("id", "不正なリクエストです");
		}


		try {
			if (sortOrderStr != null && !sortOrderStr.isEmpty()) {
				sortOrder = Integer.parseInt(sortOrderStr);
				if (sortOrder < 0) {
					errors.put("sort_order", "並び順は 0 以上の整数で入力してください");
				}
			} else {
				errors.put("sort_order", "並び順を入力してください");
			}
		} catch (NumberFormatException e) {
			errors.put("sort_order", "並び順は半角数字で入力してください");
		}

		Position currentPosition = positionDao.get(id);
		if (currentPosition != null) {
			// 入力された名前が、現在の名前とは違う場合のみ重複チェックを行う
			if (!currentPosition.getName().equals(name)) {
				if (positionDao.existsByName(name, school)) {
					errors.put("name", "同じ名前の役職がすでに存在します");
				}
			}
		} else {
			errors.put("id", "変更対象の役職が存在しません");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("id", idStr);
			req.setAttribute("name", name);
			req.setAttribute("sort_order", sortOrderStr);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("PositionUpdate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Position position = new Position();
		position.setId(id);
		position.setSchool(school);
		position.setName(name);
		position.setSortOrder(sortOrder);
		positionDao.update(position);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionUpdateDone.action");
	}
}