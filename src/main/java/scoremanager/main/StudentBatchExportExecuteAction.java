package scoremanager.main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		int entYear = Integer.parseInt(entYearStr);

		boolean isAttend = false;
		if (isAttendStr != null && isAttendStr.equals("t")) {
			isAttend = true;
		}

		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumList = classNumDao.filter(school);

		StudentDao studentDao = new StudentDao();
		List<Integer> entYearList = studentDao.getEntYearList(school);

		List<Student> student_list = studentDao.filter(school, entYear, classNum, isAttend);

		Map<String, String> errors = new HashMap<>();

		if (student_list.size() <= 0) {
			errors.put("student_list_size", "学生情報が存在しませんでした。");
			req.setAttribute("ent_year_list", entYearList);
			req.setAttribute("class_num_list", classNumList);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_batchexport.jsp").forward(req, res);
			return;
		}

		req.removeAttribute("error");
		req.removeAttribute("message");

		res.setContentType("text/csv; charset=MS932");
		res.setHeader("Content-Disposition", "attachment; filename=\"student_list.csv\"");

		try (PrintWriter out = res.getWriter()) {

			if (student_list != null) {
				for (Student s : student_list) {
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
}
