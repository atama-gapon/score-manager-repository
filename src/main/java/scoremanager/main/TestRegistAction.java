package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
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
        	request.setAttribute("message_over", null);
        	
        	String entYearStr = request.getParameter("f1"); 
            String classNum = request.getParameter("f2");  
            String subjectcd = request.getParameter("f3");
            String numStr = request.getParameter("f4");
            

            
            //入力チェック
            if (entYearStr == null || entYearStr.isEmpty() ||
            	    classNum == null || classNum.isEmpty() ||
            	    subjectcd == null || subjectcd.equals("0") ||
            	    numStr == null || numStr.isEmpty()) {

            	    request.setAttribute("message", "入学年度・クラス・科目・回数を入力してください");
            	    request.setAttribute("f1", entYearStr);
                    request.setAttribute("f2", classNum);
                    request.setAttribute("f3", subjectcd);
                    request.setAttribute("f4", numStr);
            	    request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            	    return;
            }
            int entYear = Integer.parseInt(entYearStr);
            int num = Integer.parseInt(numStr);        
            Subject subject = sDao.get(subjectcd, school);
            
            TestDao tDao = new TestDao();
        	//条件に合致する成績リストを取得
        	List<Test> test = tDao.filter(entYear, classNum, subject, num, school);
        	
        	//jspに値をセット
        	request.setAttribute("f1", entYearStr); 
        	request.setAttribute("f2", classNum);     
        	request.setAttribute("f3", subjectcd);    
        	request.setAttribute("f4", numStr);
        	request.setAttribute("tests", test); 
            
            
            request.setAttribute("num", num);
            request.setAttribute("subject", subject);
            
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        } else {
            //初回アクセス時は、入力画面表示
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        }
    }
}