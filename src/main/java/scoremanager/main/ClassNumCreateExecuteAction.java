package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumCreateExecuteAction extends Action {

	// 入力されたクラス番号のバリデーションを行い、重複がなければデータベースに登録する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		String num = req.getParameter("num");

		// インスタンス化
		ClassNumDao classNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();

		// バリデーション
		if (classNumDao.get(num, school) != null) {
			errors.put("num", "クラス番号が重複しています");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("num", num);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("ClassNumCreate.action").forward(req, res);
			return;
		}

		// DBへ反映
		ClassNum classNum = new ClassNum();
		classNum.setClassNum(num);
		classNum.setSchool(school);
		classNumDao.save(classNum);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/ClassNumCreateDone.action");
	}
}