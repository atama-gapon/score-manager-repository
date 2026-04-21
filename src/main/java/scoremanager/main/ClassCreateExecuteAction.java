package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassCreateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String class_num = req.getParameter("class_num");
		Map<String, String> errors = new HashMap<>();
		
// 【クラス番号と学校コードに合致するデータを取得する】
		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum classNum = classNumDao.get(class_num, school);
		
// 【DBへの書き込みを辞め、「クラス番号が重複しています」と表示する】
		if (classNum != null) {
			errors.put("class_num_duplication", "クラス番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", class_num);
			req.getRequestDispatcher("class_create.jsp").forward(req, res);
			return;
		}
		
// 【DBにクラスを保存する】
		ClassNum classNum2 = new ClassNum();
		classNum2.setClass_num(class_num);
		classNum2.setSchool(school);
		classNumDao.save(classNum2);
		req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
	}
}