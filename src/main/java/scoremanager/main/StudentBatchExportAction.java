package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		
		
		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumSet = classNumDao.filter(school);
		
		java.time.LocalDate todaysDate = java.time.LocalDate.now();
		int year = todaysDate.getYear();
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		
		
		
		req.setAttribute( "ent_year_set",entYearSet);
		req.setAttribute("class_num_set",classNumSet);
		
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_batchexport.jsp").forward(req, res);
	}
}
