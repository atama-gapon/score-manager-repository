package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // ログイン中の先生の学校
        School school = (School) req.getSession().getAttribute("school");
        if (school == null) {
            res.sendRedirect("../login.jsp");
            return;
        }

        // 年度リスト
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        // クラスリスト
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        req.setAttribute("class_num_set", classNumSet);

        // DAO
        StudentDao dao = new StudentDao();

        // エラーを入れる箱
        Map<String, String> errors = new HashMap<>();

        // 入学年度
        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("entYear", "入学年度を選択してください");
        }

        // 学生番号
        if (no == null || no.isEmpty()) {
            errors.put("no", "学生番号を入力してください");
        }

        // 氏名
        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください");
        }

        // 重複チェック（DAO に合わせて get(no) を使う）
        if (no != null && !no.isEmpty() && dao.get(no) != null) {
            errors.put("no", "学生番号が重複しています");
        }

        // エラーがあれば戻す
        if (!errors.isEmpty()) {

            // 入力値保持
            req.setAttribute("entYear", entYearStr);
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("classNum", classNum);

            req.setAttribute("errors", errors);
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // Student オブジェクト
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(Integer.parseInt(entYearStr));
        s.setClassNum(classNum);
        s.setSchool(school); // ← DAO の save() が school_cd を使うので必須

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
