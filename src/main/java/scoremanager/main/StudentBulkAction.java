package scoremanager.main;

import bean.School;
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentBulkAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
         HttpSession session = req.getSession();
         Teacher teacher = (Teacher)session.getAttribute("user");
         School school = teacher.getSchool();

        req.getRequestDispatcher("student_bulk.jsp").forward(req, res);
    }
}
