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

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");

        // 入力値戻し用
        req.setAttribute("no", no);
        req.setAttribute("name", name);
        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classNum", classNum);

        // 未入力チェック
        if (name == null || name.isEmpty()) {
            req.setAttribute("message", "このフィールドを入力してください");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        if (entYearStr == null || entYearStr.isEmpty()) {
            req.setAttribute("message", "入学年度を選択してください");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);

        // school が null の場合の保険
        School school = (School) req.getSession().getAttribute("school");
        if (school == null) {
            school = new School();
            school.setCd("oom");
        }
     // エラー時
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


        // Student 作成
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(entYear);   
        s.setClassNum(classNum);
        s.setAttend(true);
        s.setSchool(school);

        StudentDao dao = new StudentDao();
        dao.save(s);

        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}
