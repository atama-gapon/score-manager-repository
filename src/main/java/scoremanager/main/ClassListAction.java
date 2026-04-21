package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 【セッションからユーザーデータを取得】
		 HttpSession session = req.getSession();
		 Teacher teacher = (Teacher)session.getAttribute("user");
		 School school = teacher.getSchool();

		// 【学校コードに合致する科目の一覧を取得】
		 ClassNumDao classNumDao = new ClassNumDao();
		 List<String> classNumSet = classNumDao.filter(school);
		req.setAttribute("classNumSet", classNumSet);
		req.getRequestDispatcher("class_list.jsp").forward(req, res);
	}
}