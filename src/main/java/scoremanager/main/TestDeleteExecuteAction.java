package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//セッションからログインユーザーの情報を取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		//
		String studentNo = req.getParameter("student_no");
		String subjectCd = req.getParameter("subject_cd");
		//回数を取得して数値型に変換
		int num = Integer.parseInt(req.getParameter("num"));
		

		//学生番号から学生情報を取得
		StudentDao sDao = new StudentDao();
		Student student = sDao.get(studentNo);
		//科目オブジェクトを作成
		Subject subject = new Subject();
		subject.setCd(subjectCd);

		//テストdaoを使って削除対象が存在するかチェック
		TestDao tDao = new TestDao();
		Test test = tDao.get(student, subject, school, num);
		
		
		if (test != null) {
			//データが存在した場合、削除を実行
	        tDao.delete(test);

		}
		//学生番号をセット
		req.setAttribute("student_no", studentNo);
		
		//削除完了画面へ遷移
		req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
	}
}
