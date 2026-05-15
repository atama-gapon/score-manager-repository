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
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// セレクトボックス用のクラスデータを取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classNumList = cNumDao.filter(school);

		// 入学年度の選択肢を作成
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		List<Integer> entYearList = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearList.add(i);
		}

		req.setAttribute("ent_year_list", entYearList);
		req.setAttribute("class_num_list", classNumList);

		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_create.jsp").forward(req, res);
	}
}
