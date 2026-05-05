package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 Staff staff = (Staff)req.getAttribute("staff");
		 School school = staff.getSchool();

		// 【学校コードに合致する科目の一覧を取得】
		 ClassNumDao classNumDao = new ClassNumDao();
		 List<String> classNumSet = classNumDao.filter(school);
		req.setAttribute("classNumSet", classNumSet);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_list.jsp").forward(req, res);
	}
}