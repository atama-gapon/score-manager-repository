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
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classSet = classNumDao.filter(school);

		req.setAttribute("class_set", classSet);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/class_list.jsp").forward(req, res);
	}
}