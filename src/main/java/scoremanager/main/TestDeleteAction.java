package scoremanager.main;

import bean.School;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 【セッションからユーザーデータ（教員データ）を取得】
//		【テスト環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
//		【/テスト環境の処理】
		
		// 【本番環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
		// 【/本番環境の処理】
		
		String studentNo = req.getParameter("studentNo");
		String subjectCd = req.getParameter("subjectCd");
		String schoolCd = req.getParameter("schoolCd");
		String num = req.getParameter("num");
		
		System.out.println(studentNo);
		System.out.println(subjectCd);
		System.out.println(schoolCd);
		System.out.println(num);
	}
}
