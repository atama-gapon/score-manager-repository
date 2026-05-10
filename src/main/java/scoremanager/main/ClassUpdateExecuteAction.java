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

public class ClassUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String newClassNum = req.getParameter("class_num");
		String oldClassNum = req.getParameter("old_class_num");
		Map<String, String> errors = new HashMap<>();

		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum GetclassNum = classNumDao.get(newClassNum, school);

		if (GetclassNum != null) {
			errors.put("class_num_duplication", "クラス番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", newClassNum);
			req.setAttribute("old_class_num", oldClassNum);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_update.jsp").forward(req, res);
			return;
		}

		ClassNum classNum = new ClassNum();
		classNum.setClassNum(oldClassNum);
		classNum.setSchool(school);
		classNumDao.save(classNum, newClassNum);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/ClassUpdateDone.action");
	}
}