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

		String classNum = req.getParameter("class_num");
		Map<String, String> errors = new HashMap<>();

		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum GetclassNum = classNumDao.get(classNum, school);

		if (GetclassNum == null) {
			errors.put("invalid", "クラスが存在していません");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", classNum);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_delete.jsp").forward(req, res);
			return;
		}

		StudentDao studentDao = new StudentDao();
		boolean isFound = studentDao.hasStudentInClass(classNum);

		if (isFound) {
			errors.put("has_student", "クラスのなかに生徒が存在しているため削除できません");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", classNum);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_num_delete.jsp").forward(req, res);
			return;
		}

		ClassNum classNum2 = new ClassNum();
		classNum2.setClassNum(classNum);
		classNum2.setSchool(school);
		classNumDao.delete(classNum2);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/ClassNumDeleteDone.action");
	}
}
