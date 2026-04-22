package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateAction extends Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();

        // user が入ってない時の保険
        Teacher teacher = (Teacher)session.getAttribute("user");
            if (teacher == null) {
        teacher = (Teacher)session.getAttribute("loginUser");
                session.setAttribute("user", teacher);
            }

        // 認証済み扱いにしておく
            teacher.setAuthenticated(true);

        School school = teacher.getSchool();
        session.setAttribute("school", school);

        // 入学年度の候補をざっと作る
                LocalDate today = LocalDate.now();
        int year = today.getYear();

        List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i <= year + 1; i++) {
        entYearSet.add(i);
            }
        req.setAttribute("ent_year_set", entYearSet);

        // クラス一覧を学校から取得
        ClassNumDao cNumDao = new ClassNumDao();
                List<String> classNumSet = cNumDao.filter(school);
        req.setAttribute("class_num_set", classNumSet);

        // 画面へ
                req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
