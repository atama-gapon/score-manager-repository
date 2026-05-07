package scoremanager.main;

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

public class TestRegistExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectcd = req.getParameter("f3");
		String numStr = req.getParameter("f4");

		int entYear = Integer.parseInt(entYearStr);
		int num = Integer.parseInt(numStr);
		
		//科目コードから科目オブジェクトを取得
		SubjectDao subjectDao = new SubjectDao();
		Subject subject = subjectDao.get(subjectcd, school);

		TestDao testDao = new TestDao();
		String Regist = req.getParameter("regist");

		if (Regist != null) {
			// エラーメッセージをリセット
			req.setAttribute("message_over", null);
			String[] studentList = req.getParameterValues("student_no_list");

			// 現在の表示リストを再取得して、入力値を反映させる
			List<Test> testList = testDao.filter(entYear, classNum, subject, num, school);
			List<Test> saveList = new ArrayList<>();
			
			// 範囲外の値を入力した場合
			boolean over = false;

			if (studentList != null) {
				for (int i = 0; i < studentList.length; i++) {
					String stNo = studentList[i];
					String pStr = req.getParameter("point_" + stNo);

					// 点数が未入力の場合
					if (pStr == null || pStr.isEmpty()) {
						over = true;
						testList.get(i).setPoint(-1);
						continue;
					}

					try {
						int p = Integer.parseInt(pStr);
						testList.get(i).setPoint(p);
						if (p < 0 || p > 100) {
							over = true;
						} else {
							saveList.add(testList.get(i));
						}
					} catch (NumberFormatException e) {
						over = true;
					}
				}
			}

			// --- 判定後の処理 ---
			if (over) {
				// エラーがある場合：入力画面に戻す
				req.setAttribute("message_over", "0〜100の範囲で入力してください");
				
				// セットアップ処理（ループの外に出す！）
				java.time.LocalDate todaysDate = java.time.LocalDate.now();
				int year = todaysDate.getYear();
				List<Integer> entYearSet = new ArrayList<>();
				for (int i = year - 10; i <= year + 1; i++) {
					entYearSet.add(i);
				}
				req.setAttribute("ent_year_set", entYearSet);
				ClassNumDao classNumDao = new ClassNumDao();
				req.setAttribute("class_num_set", classNumDao.filter(school));
				req.setAttribute("subject_set", subjectDao.filter(school));
				req.setAttribute("tests", testList);
				req.setAttribute("f1", entYear);
				req.setAttribute("f2", classNum);
				req.setAttribute("f3", subjectcd);
				req.setAttribute("f4", numStr);
				req.setAttribute("num", num);
				req.setAttribute("subject", subject);
				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);

			} else {
				// エラーがない場合：保存して完了画面へ
				testDao.save(saveList);
				
				req.setAttribute("f1", entYearStr);
				req.setAttribute("f2", classNum);
				req.setAttribute("f3", subjectcd);
				req.setAttribute("f4", numStr);
				req.setAttribute("tests", testList);
				req.setAttribute("num", num);
				req.setAttribute("subject", subject);

				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist.jsp").forward(req, res);
				return;
			}
				
			//データーベースに一括で保存
			testDao.save(saveList);
			req.setAttribute("f1", entYearStr);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectcd);
			req.setAttribute("f4", numStr);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/test_regist_done.jsp").forward(req, res);
		}
	}
}