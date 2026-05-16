package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// ユーザーが所属している学校のクラスデータを取得
		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumList = classNumDao.filter(school);

		// ユーザーが所属している学校の科目データを取得
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(school);

		// 入学年度
		StudentDao studentDao = new StudentDao();
		List<Integer> entYearList = studentDao.getEntYearList(school);

		req.setAttribute("class_num_list", classNumList);
		req.setAttribute("subject_list", subjectList);
		req.setAttribute("ent_year_list", entYearList);

		//　検索ボタンか登録ボタンが押されたかチェック
		if (req.getParameter("search") != null || req.getParameter("regist") != null) {
			req.setAttribute("message_over", null);

			String entYearStr = req.getParameter("ent_year");
			String classNum = req.getParameter("class_num");
			String subjectCd = req.getParameter("is_attend");
			String numStr = req.getParameter("f4");

			// 入学年度、クラス、科目、回数のいずれかが未入力の場合
			if (entYearStr == null || entYearStr.isEmpty() || classNum == null || classNum.isEmpty()
					|| subjectCd == null || subjectCd.equals("0") || numStr == null || numStr.equals("0")) {
				req.setAttribute("message", "入学年度・クラス・科目・回数を入力してください");
				req.setAttribute("ent_year", entYearStr);
				req.setAttribute("class_num", classNum);
				req.setAttribute("is_attend", subjectCd);
				req.setAttribute("f4", numStr);
				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
				return;
			}

			int entYear = Integer.parseInt(entYearStr);
			int num = Integer.parseInt(numStr);
			Subject subject = subjectDao.get(subjectCd, school);

			TestDao testDao = new TestDao();
			//条件に合致する成績リストを取得
			List<Test> test = testDao.filter(entYear, classNum, subject, num, school);

			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("class_num", classNum);
			req.setAttribute("is_attend", subjectCd);
			req.setAttribute("f4", numStr);
			req.setAttribute("tests", test);
			req.setAttribute("num", num);
			req.setAttribute("subject", subject);
			req.setAttribute("school", school);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
		} else {
			//初回アクセス時は、入力画面表示
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
		}
	}
}
