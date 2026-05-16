package scoremanager.main;

import java.util.ArrayList;
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

public class TestRegistExecuteAction extends Action {

	// 入力された複数生徒のテスト成績情報をバリデーションし、変更のあったデータのみをデータベースに一括保存する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String subjectCd = req.getParameter("subject_cd");
		String numStr = req.getParameter("num");

		// 基本パラメータの存在チェック（ガード句による500エラー防止）
		if (entYearStr == null || entYearStr.isEmpty() ||
				classNum == null || classNum.isEmpty() ||
				subjectCd == null || subjectCd.isEmpty() ||
				numStr == null || numStr.isEmpty()) {
			req.setAttribute("error", "登録に必要なパラメータが不足しています。");
			req.getRequestDispatcher("TestList.action").forward(req, res);
			return;
		}

		// 安全対策：数値変換チェック
		int entYear = 0;
		int num = 0;
		try {
			entYear = Integer.parseInt(entYearStr);
			num = Integer.parseInt(numStr);
		} catch (NumberFormatException e) {
			req.setAttribute("error", "パラメータの数値変換に失敗しました。");
			req.getRequestDispatcher("TestList.action").forward(req, res);
			return;
		}

		SubjectDao subjectDao = new SubjectDao();
		Subject subject = subjectDao.get(subjectCd, school);
		TestDao testDao = new TestDao();

		// 登録ボタンが押下された場合のみ実行
		if (req.getParameter("regist") != null) {
			req.setAttribute("message_over", null);
			String[] studentList = req.getParameterValues("student_no_list");

			// 現在の登録データを基準として取得（入力値をマッピングするベース）
			List<Test> testList = testDao.filter(entYear, classNum, subject, num, school);
			List<Test> saveList = new ArrayList<>();
			boolean over = false;

			if (studentList != null) {
				for (int i = 0; i < studentList.length; i++) {
					String stNo = studentList[i];
					String pStr = req.getParameter("point_" + stNo);

					// 点数が未入力の場合
					if (pStr == null || pStr.isEmpty()) {
						over = true;
						if (i < testList.size()) {
							testList.get(i).setPoint(-1);
						}
						continue;
					}

					try {
						int p = Integer.parseInt(pStr);
						if (i < testList.size()) {
							int oldPoint = testList.get(i).getPoint();

							if (p < 0 || p > 100) {
								over = true;
								testList.get(i).setPoint(p);
							} else {
								// 値に変更があった差分データのみを更新対象リスト（saveList）に格納
								if (p != oldPoint) {
									testList.get(i).setPoint(p);
									testList.get(i).setMarkerStaff(staff); // 採点者をセット
									saveList.add(testList.get(i));
								}
							}
						}
					} catch (NumberFormatException e) {
						over = true;
						if (i < testList.size()) {
							testList.get(i).setPoint(-1); // 不正文字入力時は暫定値をセット
						}
					}
				}
			}

			// バリデーションエラーがある場合は入力画面（Action）へ復帰
			if (over) {
				// 画面表示用のデータを準備
				prepareViewData(req, school);
				req.setAttribute("message_over", "0〜100の範囲で入力してください");
				req.setAttribute("tests", testList);
				req.setAttribute("ent_year", entYearStr);
				req.setAttribute("class_num", classNum);
				req.setAttribute("subject_cd", subjectCd);
				req.setAttribute("num", num);
				req.setAttribute("subject", subject);

				// 登録画面（TestRegistAction）の再表示処理へフォワード
				req.getRequestDispatcher("TestRegist.action").forward(req, res);
				return;
			}

			// DBへ反映
			if (!saveList.isEmpty()) {
				testDao.save(saveList);
			}

			res.sendRedirect(req.getContextPath() + "/scoremanager/main/TestRegistDone.action");
		}
	}

	// 学校情報を条件に検索条件用ドロップダウンデータを抽出し、リクエスト属性に設定する
	private void prepareViewData(HttpServletRequest req, School school) throws Exception {
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		req.setAttribute("class_num_list", classNumDao.filter(school));
		req.setAttribute("subject_list", subjectDao.filter(school));
		req.setAttribute("ent_year_list", studentDao.getEntYearList(school));
	}
}