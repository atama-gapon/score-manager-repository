package scoremanager.main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
public class TestRegistAction extends Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		School school = new School();
        school.setCd("oom");
        school.setName("テスト：oom");
        
     // HttpSession session = req.getSession();
     // Teacher teacher = (Teacher)session.getAttribute("user");
     // School school = teacher.getSchool()
        
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

        
        request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        
	}
}
