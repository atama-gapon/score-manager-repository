package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ClassDeleteExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		School school = teacher.getSchool();
		
		String classNum = req.getParameter("class_num");
		Map<String, String> errors = new HashMap<>();

// 【クラス番号と学校コードに合致するデータを取得する】
		ClassNumDao classNumDao = new ClassNumDao();
		ClassNum GetclassNum = classNumDao.get(classNum, school);

// 【DBへの書き込みを辞め、「クラスが存在していません」と表示する】
		if (GetclassNum == null) {
			errors.put("invalid", "クラスが存在していません");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", classNum);
			req.getRequestDispatcher("class_delete.jsp").forward(req, res);
			return;
		}
		
		StudentDao studentDao = new StudentDao();
		boolean isFound = studentDao.hasStudentInClass(classNum);
	
		// クラスに生徒が存在していた場合
		if (isFound) {
			errors.put("has_student", "クラスのなかに生徒が存在しているため削除できません");
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", classNum);
			req.getRequestDispatcher("class_delete.jsp").forward(req, res);
			return;
		}

// 【DBにクラスを保存する】
		ClassNum classNum2 = new ClassNum();
		classNum2.setClass_num(classNum);
		classNum2.setSchool(school);
		classNumDao.delete(classNum2);
		req.getRequestDispatcher("class_delete_done.jsp").forward(req, res);
	}
}