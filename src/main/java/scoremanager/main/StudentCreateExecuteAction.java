package scoremanager.main;

import bean.School;
import bean.Student;
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

        // 入力チェック
        if (entYearStr == null || entYearStr.isEmpty()) {
            req.setAttribute("message", "入学年度を入力してください");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        // ログイン中の先生の学校を取得
        School school = (School) req.getSession().getAttribute("school");
        

        // Student オブジェクトに詰める
        Student s = new Student();
        s.setNo(no);
        s.setName(name);
        s.setEntYear(Integer.parseInt(entYearStr));
        s.setClassNum(classNum);
   
        s.setSchool(school);
//////////////////ログイン機能ないときの対策//////////////////////////////
        if (school == null) {
            school = new School();
            school.setCd("oom"); // ← DB の SCHOOL_CD に合わせる
        }
        
        s.setSchool(school);
//////////////////////////////////////
        // DAOで保存
        StudentDao dao = new StudentDao();
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
