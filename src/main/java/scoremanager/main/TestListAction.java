package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// ユーザーデータからユーザーが所属している学校のクラスデータを取得
		ClassNumDao cDao = new ClassNumDao();
		// 学校コードに合致するデータを取得
		List<String> cNumSet = cDao.filter(school);
		// ユーザーデータからユーザーが所属している学校の科目データを取得
		SubjectDao sDao = new SubjectDao();
		// 科目コードに合致するデータを取得
		List<Subject> subjectList = sDao.filter(school);
		// 入学年度リストを生成
		StudentDao studentDao = new StudentDao();
		List<Integer> entYearList = studentDao.getEntYearList(school);

		// 収集したデータをリクエストに設定
		req.setAttribute("class_num_list", cNumSet);
		req.setAttribute("subject_list", subjectList);
		req.setAttribute("ent_year_list", entYearList);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list.jsp").forward(req, res);
		;
	}
}
