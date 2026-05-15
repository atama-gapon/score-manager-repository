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

public class ClassCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String class_num = req.getParameter("class_num");
		Map<String, String> errors = new HashMap<>();

		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum classNum = classNumDao.get(class_num, school);

		if (classNum != null) {
			errors.put("class_num_duplication", "クラス番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", class_num);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_create.jsp").forward(req, res);
			return;
		}

		ClassNum classNum2 = new ClassNum();
		classNum2.setClassNum(class_num);
		classNum2.setSchool(school);
		classNumDao.save(classNum2);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/ClassCreateDone.action");
	}
}
