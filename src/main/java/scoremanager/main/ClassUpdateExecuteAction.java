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

public class ClassUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String newClassNum = req.getParameter("class_num");
		String oldClassNum = req.getParameter("old_class_num");
		Map<String, String> errors = new HashMap<>();

// 【クラス番号と学校コードに合致するデータを取得する】
		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum GetclassNum = classNumDao.get(newClassNum, school);

// 【DBへの書き込みを辞め、「クラス番号が重複しています」と表示する】
		if (GetclassNum != null) {
			errors.put("class_num_duplication", "クラス番号が重複しています");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", newClassNum);
			req.setAttribute("old_class_num", oldClassNum);
			req.getRequestDispatcher("class_update.jsp").forward(req, res);
			return;
		}

// 【DBにクラスを保存する】
		ClassNum classNum = new ClassNum();
		classNum.setClass_num(oldClassNum);
		classNum.setSchool(school);
		classNumDao.save(classNum, newClassNum);
		req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
	}
}