package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.TestSubjectList;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestSubjectListDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestSubjectListExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();
		TestSubjectListDao testSubjectListDao = new TestSubjectListDao();
		List<TestSubjectList> testSubjectList = new ArrayList<>();

		// 入力値を取得
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("is_attend");

		if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
			req.setAttribute("message", "入学年度とクラスと科目を選択してください");
			req.setAttribute("class_num_list", classNumDao.filter(school));

			req.setAttribute("subject_list", subjectDao.filter(school));

			List<Integer> entYearList = studentDao.getEntYearList(school);
			req.setAttribute("ent_year_list", entYearList);
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("class_num", classNum);
			req.setAttribute("is_attend", subjectCd);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list.jsp").forward(req, res);
			return;
		}

		int entYear = Integer.parseInt(entYearStr);
		Subject subject = subjectDao.get(subjectCd, school);

		// データの準備
		prepareViewData(req, school, studentDao, subjectDao, classNumDao);
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("class_num", classNum);
		req.setAttribute("is_attend", subjectCd);

		// 入学年度、クラス、科目に合致するデータを取得
		testSubjectList = testSubjectListDao.filter(entYear, classNum, subject, school);

		// 入力欄に取得したデータを初期値としてセットし、一覧で表示する
		req.setAttribute("subject", subject);
		req.setAttribute("test_subject_list", testSubjectList);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list_subject.jsp").forward(req, res);
		return;
	}

	private void prepareViewData(HttpServletRequest req, School school, StudentDao studentDao, SubjectDao subjectDao, ClassNumDao classNumDao) throws Exception {
		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}
