package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String no = req.getParameter("no");

        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);

        School school = (School) req.getSession().getAttribute("school");
        if (school == null) {
            school = new School();
            school.setCd("oom");
        }

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        req.setAttribute("class_num_set", classNumSet);

        req.setAttribute("student", student);

        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
