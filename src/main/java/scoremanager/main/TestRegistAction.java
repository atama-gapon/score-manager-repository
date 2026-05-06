package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Staff;
import bean.Subject;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//成績登録actionクラス
public class TestRegistAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		//現在の年度を取得し、入学年度の選択肢を追加
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		//入学年度リストをセット
		req.setAttribute("ent_year_set", entYearSet);

		//データベースからクラス一覧と科目一覧取得
		ClassNumDao cNumDao = new ClassNumDao();
		List<String> classNumSet = cNumDao.filter(school);
		SubjectDao sDao = new SubjectDao();
		List<Subject> subjectSet = sDao.filter(school);

		//	クラスと科目リストをセット
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);

		//検索ボタンか登録ボタンが押されたかチェック
		if (req.getParameter("search") != null || req.getParameter("regist") != null) {
			req.setAttribute("message_over", null);

			String entYearStr = req.getParameter("f1");
			String classNum = req.getParameter("f2");
			String subjectcd = req.getParameter("f3");
			String numStr = req.getParameter("f4");

			//入力チェック
			if (entYearStr == null || entYearStr.isEmpty() ||
					classNum == null || classNum.isEmpty() ||
					subjectcd == null || subjectcd.equals("0") ||
					numStr == null || numStr.isEmpty()) {
				req.setAttribute("message", "入学年度・クラス・科目・回数を入力してください");
				req.setAttribute("f1", entYearStr);
				req.setAttribute("f2", classNum);
				req.setAttribute("f3", subjectcd);
				req.setAttribute("f4", numStr);
				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
				return;
			}

			int entYear = Integer.parseInt(entYearStr);
			int num = Integer.parseInt(numStr);
			Subject subject = sDao.get(subjectcd, school);

			TestDao tDao = new TestDao();
			//条件に合致する成績リストを取得
			List<Test> test = tDao.filter(entYear, classNum, subject, num, school);

			//jspに値をセット
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectcd);
			req.setAttribute("f4", numStr);
			req.setAttribute("tests", test);

			req.setAttribute("num", num);
			req.setAttribute("subject", subject);

			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
		} else {
			//初回アクセス時は、入力画面表示
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
		}
	}
}