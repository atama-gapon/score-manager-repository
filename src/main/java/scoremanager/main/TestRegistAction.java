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

//成績登録actionクラス
public class TestRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//セッションからログインユーザーの情報を取得
    	HttpSession session = request.getSession();
    	Teacher teacher = (Teacher)session.getAttribute("user");
        School school = teacher.getSchool();
         
        //現在の年度を取得し、入学年度の選択肢を追加
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year + 1; i++) {
            entYearSet.add(i);
        }
        //入学年度リストをセット
        request.setAttribute("ent_year_set", entYearSet);

        //データベースからクラス一覧と科目一覧取得
        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(school);
        SubjectDao sDao = new SubjectDao();
        List<Subject> subjectSet = sDao.filter(school);
        
        //	クラスと科目リストをセット
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("subject_set", subjectSet);

        //検索ボタンか登録ボタンが押されたかチェック
        if (request.getParameter("search") != null || request.getParameter("regist") != null) {
            //TestRegistExecuteActionへ処理を渡す
            TestRegistExecuteAction executeAction = new TestRegistExecuteAction();
            executeAction.execute(request, response);
        } else {
            //初回アクセス時は、入力画面表示
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        }
    }
}