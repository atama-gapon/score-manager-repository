package scoremanager.main;

import java.io.PrintWriter;
import java.util.List;

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

		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String isAttendStr = req.getParameter("f3");

		int entYear = Integer.parseInt(entYearStr);

		boolean isAttend = false;
		if (isAttendStr != null && isAttendStr.equals("t")) {
			isAttend = true;
		}

		StudentDao sDao = new StudentDao();
		List<Student> students = sDao.filter(school, entYear, classNum, isAttend);

		req.removeAttribute("error");
		req.removeAttribute("message");

		res.setContentType("text/csv; charset=MS932");
		res.setHeader("Content-Disposition", "attachment; filename=\"student_list.csv\"");

		try (PrintWriter out = res.getWriter()) {

			if (students != null) {
				for (Student s : students) {
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
