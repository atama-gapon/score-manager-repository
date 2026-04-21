package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;


public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 変更したい学生の番号を取る
        String no = req.getParameter("no");

        // 学生情報を1件取ってくる
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no);
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

        session.setAttribute("school", school);

    	
    	//         セッションのユーザーデータを取得
//         【テスト環境の処理】
//    	HttpSession session = req.getSession();
//        Teacher teacher = (Teacher)session.getAttribute("user");
//        School school = teacher.getSchool();


        // 入学年度のリストを作る（プルダウン用）
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        // クラス一覧を取ってセット
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        req.setAttribute("class_num_set", classNumSet);

        // 画面に表示する学生データ
        req.setAttribute("student", student);

        // 更新画面へ
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
