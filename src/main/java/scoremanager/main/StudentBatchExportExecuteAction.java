package scoremanager.main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();
		List<Student> studentList = new ArrayList<>();

		// 入力値を取得
		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		int entYear = 0;
		try {
			entYear = Integer.parseInt(entYearStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean isEntYearSelected = !entYearStr.isEmpty();
		boolean isClassNumSelected = !classNum.isEmpty();
		boolean isAttend = isAttendStr != null;

		// データの準備
		req.setAttribute("ent_year", entYearStr);
		req.setAttribute("class_num", classNum);
		req.setAttribute("is_attend", isAttendStr);

		if (entYearStr.isEmpty() && classNum.isEmpty()) {
			studentList = studentDao.filter(school, isAttend);
		} else {
			// バリデーション
			if (isClassNumSelected && !isEntYearSelected) {
				errors.put("exist", "クラスを指定する場合は入学年度も指定してください");
			}

			if (errors.isEmpty()) {
				studentList = findStudents(school, entYear, classNum, isEntYearSelected, isClassNumSelected, isAttend, studentDao);
			} else {
				studentList = studentDao.filter(school, isAttend);
				req.setAttribute("errors", errors);
				req.setAttribute("student_list", studentList);
				req.getRequestDispatcher("StudentBatchExport.action").forward(req, res);
				return;
			}
		}
		
		if (studentList.isEmpty()) {
			errors.put("exist", "学生情報が存在しません");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StudentBatchExport.action").forward(req, res);
			return;
		}

		res.setContentType("text/csv; charset=MS932");
		res.setHeader("Content-Disposition", "attachment; filename=\"student_list.csv\"");

		try (PrintWriter out = res.getWriter()) {

			if (studentList != null) {
				for (Student s : studentList) {
					out.print(s.getNo() + ",");
					out.print(s.getName() + ",");
					out.print(s.getEntYear() + ",");
					out.print(s.getClassNum() + ",");
					out.print(s.isAttend() ? "true" : "false");
					out.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Student> findStudents(School school, Integer entYear, String classNum, boolean isEntYearSelected, boolean isClassNumSelected, boolean isAttend, StudentDao studentDao) throws Exception {
		if (isClassNumSelected) {
			return studentDao.filter(school, entYear, classNum, isAttend);
		}

		return studentDao.filter(school, entYear, isAttend);
	}
}
