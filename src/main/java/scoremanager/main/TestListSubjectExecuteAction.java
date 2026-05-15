package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// 入力内容のチェック
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

		if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
			req.setAttribute("message", "入学年度とクラスと科目を選択してください");
			ClassNumDao cDao = new ClassNumDao();
			req.setAttribute("class_num_list", cDao.filter(school));

			SubjectDao sDao = new SubjectDao();
			req.setAttribute("subjects", sDao.filter(school));

			StudentDao studentDao = new StudentDao();
			List<Integer> entYearList = studentDao.getEntYearList(school);
			req.setAttribute("ent_year_list", entYearList);
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list.jsp").forward(req, res);
			return;
		}

		int entYear = Integer.parseInt(entYearStr);
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(subjectCd, school);

		// ユーザーデータからユーザーが所属している学校のクラスデータを取得
		ClassNumDao cDao = new ClassNumDao();
		// 学校コードに合致するデータを取得
		List<String> cNumSet = cDao.filter(school);
		// ユーザーデータからユーザーが所属している学校の科目データを取得
		SubjectDao subjectDao = new SubjectDao();
		// 科目コードに合致するデータを取得
		List<Subject> subjects = subjectDao.filter(school);
		// 入学年度リストを生成
		StudentDao studentDao = new StudentDao();
		List<Integer> entYearList = studentDao.getEntYearList(school);

		// 収集したデータをリクエストに設定
		req.setAttribute("class_num_list", cNumSet);
		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_list", entYearList);

		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);

		// 入学年度、クラス、科目に合致するデータを取得
		TestListSubjectDao tDao = new TestListSubjectDao();
		List<TestListSubject> testListSubjects = tDao.filter(entYear, classNum, subject, school);

		// 入力欄に取得したデータを初期値としてセットし、一覧で表示する
		req.setAttribute("subject", subject);
		req.setAttribute("testListSubjects", testListSubjects);
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_list_subject.jsp").forward(req, res);
		return;
	}
}
