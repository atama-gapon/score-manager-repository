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

        // ★ user を session に確実に入れる
        Teacher teacher = (Teacher)session.getAttribute("user");
        if (teacher == null) {
            teacher = (Teacher)session.getAttribute("loginUser"); // ← ログイン時の名前に合わせる
            session.setAttribute("user", teacher);
        }

        // ★ 認証済みフラグ
        teacher.setAuthenticated(true);

        School school = teacher.getSchool();

    	
    	
    	//         セッションのユーザーデータを取得
//         【テスト環境の処理】
//    	HttpSession session = req.getSession();
//        Teacher teacher = (Teacher)session.getAttribute("user");
//        School school = teacher.getSchool();

        session.setAttribute("school", school);
        // 入学年度リスト作成
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);


        // 【セッションのユーザーデータから、ユーザーが所属している学校のクラス一覧用データを取得】
        ClassNumDao cNumDao = new ClassNumDao();
//        【テスト環境の処理】
//        List<String> classNumSet = cNumDao.filter(school);
        // 【/テスト環境の処理】

        // 【本番環境の処理】
         List<String> classNumSet = cNumDao.filter(school);
        // 【/本番環境の処理】

        req.setAttribute("class_num_set", classNumSet);

        // ★ ここが重要：student_create.jsp に遷移する
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}
