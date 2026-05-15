package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumList = classNumDao.filter(school);

		StudentDao studentDao = new StudentDao();
		List<Integer> entYearList = studentDao.getEntYearList(school);

		req.setAttribute("ent_year_list", entYearList);
		req.setAttribute("class_num_list", classNumList);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_batchexport.jsp").forward(req, res);
	}
}
