package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.StudentUtil;

public class StudentUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String targetNo = req.getParameter("no");
		String submitted = req.getParameter("submitted");

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		if (!"true".equals(submitted)) {
			req.setAttribute("student", studentDao.get(targetNo));
		}

		// データの準備
		prepareViewData(req, school, classNumDao);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_update.jsp").forward(req, res);
	}

	private void prepareViewData(HttpServletRequest req, School school, ClassNumDao classNumDao) throws Exception {
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("ent_year_list", StudentUtil.createEntYearList(req));
	}
}
