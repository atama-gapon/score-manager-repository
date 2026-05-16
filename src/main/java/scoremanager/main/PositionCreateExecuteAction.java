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

public class PositionCreateExecuteAction extends Action {

	// 入力された役職情報のバリデーションを行い、問題がなければデータベースに登録する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String name = req.getParameter("name");
		String sortOrderStr = req.getParameter("sort_order");

		// インスタンス化
		PositionDao positionDao = new PositionDao();
		Map<String, String> errors = new HashMap<>();

		int sortOrder = 0;

		// バリデーション
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

		if (positionDao.existsByName(name, school)) {
			errors.put("name", "同じ名前の役職がすでに存在します");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("name", name);
			req.setAttribute("sort_order", sortOrderStr);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("PositionCreate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Position position = new Position();
		position.setSchool(school);
		position.setName(name);
		position.setSortOrder(sortOrder);
		positionDao.save(position);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/PositionCreateDone.action");
	}
}