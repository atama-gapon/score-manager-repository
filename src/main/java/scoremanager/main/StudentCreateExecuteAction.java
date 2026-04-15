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

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 入力値取得
        String entYearStr = req.getParameter("entYear");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");

        // ログイン中の先生の学校を取得
        School school = (School) req.getSession().getAttribute("school");

        // ログイン機能がないときの対策
        if (school == null) {
            school = new School();
            school.setCd("oom");
        }

        // エラー時に毎回必要な共通処理（年度リスト & クラスリスト）
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

        // ① 未入力チェック
        if (no == null || no.isEmpty() || name == null || name.isEmpty()) {
            req.setAttribute("message", "このフィールドに入力してください");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // ② 入学年度 未選択
        if (entYearStr == null || entYearStr.isEmpty()) {
            req.setAttribute("message", "入学年度を選択してください");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // Student オブジェクトに詰める
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(Integer.parseInt(entYearStr));
        s.setClassNum(classNum);
        s.setSchool(school);

        // DAO
        StudentDao dao = new StudentDao();

        // ③ 学生番号重複チェック
        if (dao.get(no) != null) {
            req.setAttribute("message", "学生番号が重複しています");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // 保存
        boolean result = dao.save(s);

        if (!result) {
            req.setAttribute("message", "登録に失敗しました");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // 完了画面へ
        req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
    }
}
