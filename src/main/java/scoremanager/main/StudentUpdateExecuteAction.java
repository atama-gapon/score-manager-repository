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

        // フォームから送られてきた値を取る
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        String entYearStr = req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        //チェックボックスから受け取った値を受け取れるように追加(修正)
        String isAttendStr = req.getParameter("is_attend");

        // 入力エラー時に値を戻すためにセット
        req.setAttribute("no", no);
        req.setAttribute("name", name);
        req.setAttribute("entYear", entYearStr);
        req.setAttribute("classNum", classNum);
        
        //チェックボックスがチェック/未チェックなのか判定(修正)
        boolean isAttend = false;
		if (isAttendStr != null) {
		    isAttend = true;
		}
        
        // 氏名が空ならエラー
        if (name == null || name.isEmpty()) {
            req.setAttribute("message", "このフィールドを入力してください");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // 入学年度が空ならエラー
        if (entYearStr == null || entYearStr.isEmpty()) {
            req.setAttribute("message", "入学年度を選択してください");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // 数値に変換
        int entYear = Integer.parseInt(entYearStr);

        // school がセッションに無いときの保険
        School school = (School) req.getSession().getAttribute("school");
        if (school == null) {
            school = new School();
            school.setCd("oom");
        }

        // エラー時に必要な入学年度リストを作る
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        req.setAttribute("ent_year_set", entYearSet);

        // クラス一覧をセット（エラー時用）
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        req.setAttribute("class_num_set", classNumSet);

        // Student オブジェクトに値を入れる
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(entYear);
        s.setClassNum(classNum);
        //常時「true」が送られていたので受け取った値をセットできるように変更(修正)
        s.setAttend(isAttend); // 在学中はtrue
        s.setSchool(school);

        // DB に保存（更新）
        StudentDao dao = new StudentDao();
        dao.save(s);

        // 完了画面へ
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}
