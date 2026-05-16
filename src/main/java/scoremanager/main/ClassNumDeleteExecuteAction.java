package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassNumDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		ClassNumDao classNumDao = new ClassNumDao();
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();

		// リクエストパラメータの取得
		String num = req.getParameter("num");

		// バリデーション
		if (classNumDao.get(num, school) == null) {
			errors.put("num", "クラスが存在していません");
		}

		if (studentDao.hasStudentInClass(num)) {
			errors.put("num", "クラスのなかに生徒が存在しているため削除できません");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("ClassNumDelete.action").forward(req, res);
			return;
		}

		// DBへ反映
		ClassNum classNum = new ClassNum();
		classNum.setClassNum(num);
		classNum.setSchool(school);
		classNumDao.delete(classNum);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/ClassNumDeleteDone.action");
	}
}
