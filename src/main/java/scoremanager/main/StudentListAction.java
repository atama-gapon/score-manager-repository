package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

// 役割：学生の一覧を取得する処理を行うアクションクラス
public class StudentListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// 【セッションからユーザーデータ（教員データ）を取得】
//		【テスト環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
//		School school = new School();
//		school.setCd("oom");
//		school.setName("テスト：oom");
//		【/テスト環境の処理】
		
		// 【本番環境の処理】
		HttpSession session = req.getSession();
		
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		// 【/本番環境の処理】


// 【セッションのユーザーデータから、ユーザーが所属している学校のクラス一覧用データを取得】
		
		ClassNumDao cNumDao = new ClassNumDao();
//		【テスト環境の処理】
		List<String> classNumSet = cNumDao.filter(school);
//		【/テスト環境の処理】

		// 【本番環境の処理】
		// ログインユーザーの学校コードをもとにクラスの一覧を取得
//		List<String> list = cNumDao.filter(school);
		// 【/本番環境の処理】


// 【セッションのユーザーデータから、ユーザーが所属している学校の生徒一覧用データを取得】
// &【選択された情報をもとに学校の生徒データを取得】
		StudentDao sDao = new StudentDao();
		// リクエストパラメータを受け取る
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String isAttendStr = req.getParameter("f3");
		
			int entYear = 0;
		// エラー対策
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		
		boolean isAttend = false;
		// 在学フラグが送信されていた場合
		if (isAttendStr != null) {
				isAttend = true;
		}

		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		// 10年前から1年後までをリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<=year+1; i++) {
			entYearSet.add(i);
		}
		
			List<Student> students = new ArrayList<>();
		Map<String, String> errors = new HashMap<>();
		
		// クラスのみ入力 → エラー
        if ((classNum != null && !classNum.isEmpty()) && entYear == 0) {
            req.setAttribute("message", "クラスを指定する場合は入学年度も指定してください");
            req.getRequestDispatcher("student_list.jsp").forward(req, res);
            return;
        }

        // クラス + 在学中 で入学年度なし → エラー
        if ((classNum != null && !classNum.isEmpty()) && entYear == 0 && isAttend) {
            req.setAttribute("message", "クラスを指定する場合は入学年度も指定してください");
            req.getRequestDispatcher("student_list.jsp").forward(req, res);
            return;
        }
		
		
		 if (entYear != 0 && !classNum.equals("0")) {
			// 入学年度とクラス番号を指定された場合
			students = sDao.filter(school, entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
			// 入学年度のみを指定された場合
			students = sDao.filter(school, entYear, isAttend);
		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			// 指定なしの場合
					students = sDao.filter(school, isAttend);
			} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
				students = sDao.filter(school, isAttend);
		}

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
	 	req.setAttribute("f3", isAttend);
		
	 	req.setAttribute("students", students);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("ent_year_set", entYearSet);

		req.getRequestDispatcher("student_list.jsp").forward(req, res);
	}
}