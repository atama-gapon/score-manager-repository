package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff)req.getAttribute("staff");
		School school = staff.getSchool();
		
		// 入学年度の候補をざっと作る
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		req.setAttribute("ent_year_set", entYearSet);
		
		// クラス一覧を学校から取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classNumSet = cNumDao.filter(school);
		req.setAttribute("class_num_set", classNumSet);
		
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_create.jsp").forward(req, res);
	}
}
