package scoremanager.main;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
//成績登録・検索を実行するactionクラス
public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //パラメータの取得
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
        	    request.getRequestDispatcher("test_regist.jsp").forward(request, response);
        	    return;
        	}
        
        //セッションからログインユーザーの情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        School school = teacher.getSchool();
        
        
        int entYear = Integer.parseInt(entYearStr);
        int num = Integer.parseInt(numStr);
        
        //科目コードから科目オブジェクトを取得
        SubjectDao subDao = new SubjectDao();
        Subject subject = subDao.get(subjectcd, school);
        
        TestDao tDao = new TestDao();
        String Regist = request.getParameter("regist");
        
        //検索が押された場合の処理
        if (request.getParameter("search") != null) {
        	//エラーメッセージをリセット
        	request.setAttribute("message_over", null);
        	
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
        //登録ボタンが押された場合の処理    
        }else if(Regist != null) {
        	//エラーメッセージをリセット
        	request.setAttribute("message_over", null);
        	
        	
        	String[] studentList = request.getParameterValues("student_no_list");
            
            //現在の表示リストを再取得して、入力値を反映させる
            List<Test> testList = tDao.filter(entYear, classNum, subject, num, school);
            //保存
            List<Test> saveList = new ArrayList<>();
            //範囲外の値を入力した場合
            boolean over = false;

            if ( studentList != null) {
                for (int i = 0; i < studentList.length; i++) {
                	String stNo = studentList[i];
                	
                	String pStr = request.getParameter("point_" + stNo);
                	
                    //点数が未入力の場合
                    if (pStr == null || pStr.isEmpty()) {
                        over = true;
                        
                        testList.get(i).setPoint(-1); 
                        continue;
                    }
                    int p = Integer.parseInt(pStr);
                    
                    
                    testList.get(i).setPoint(p);
                    //点数が0～100以内かを判定
                    if (p < 0 || p > 100) {
                        over = true;
                    } else {
                    	//正常な値であったら保存
                        saveList.add(testList.get(i));
                    }
                }
            }
            //範囲外が一つでもあった場合、警告文を出す
	        if (over) {
	            request.setAttribute("message_over", "0〜100の範囲で入力してください");
	            
	            request.setAttribute("tests", testList);
	            
	            request.setAttribute("f1", entYearStr);
	            request.setAttribute("f2", classNum);
	            request.setAttribute("f3", subjectcd);
	            request.setAttribute("f4", numStr);
	            request.setAttribute("tests", testList);
	            request.setAttribute("num", num);
	            request.setAttribute("subject", subject);
	
	            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
	            return;
	        }
	    
            
            //データーベースに一括で保存
            tDao.save(saveList);
            request.setAttribute("f1", entYearStr);
            request.setAttribute("f2", classNum);
            request.setAttribute("f3", subjectcd);
            request.setAttribute("f4", numStr);
            request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
        }
        
    }
}