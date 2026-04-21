package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 HttpSession session = request.getSession();
    	 Teacher teacher = (Teacher)session.getAttribute("user");
         School school = teacher.getSchool();

        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        request.setAttribute("ent_year_set", entYearSet);

        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        SubjectDao sDao = new SubjectDao();
        List<Subject> subjectSet = sDao.filter(school);

        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjectSet);

        
        if (request.getParameter("search") != null || request.getParameter("regist") != null) {
            
            TestRegistExecuteAction executeAction = new TestRegistExecuteAction();
            executeAction.execute(request, response);
        } else {
            
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        }
    }
}