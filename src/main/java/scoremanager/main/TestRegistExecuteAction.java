package scoremanager.main;
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
//成績登録・検索を実行するactionクラス
public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        
        //セッションからログインユーザーの情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        School school = teacher.getSchool();
        
        String entYearStr = request.getParameter("f1"); 
        String classNum = request.getParameter("f2");  
        String subjectcd = request.getParameter("f3");
        String numStr = request.getParameter("f4");
        
        int entYear = Integer.parseInt(entYearStr);
        int num = Integer.parseInt(numStr);
        
        //科目コードから科目オブジェクトを取得
        SubjectDao subDao = new SubjectDao();
        Subject subject = subDao.get(subjectcd, school);
        
        TestDao tDao = new TestDao();
        String Regist = request.getParameter("regist");
        
         
        if(Regist != null) {
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
                    try {
                        int p = Integer.parseInt(pStr);
                        testList.get(i).setPoint(p);

                        if (p < 0 || p > 100) {
                            over = true;
                        } else {
                            // 正常な値のみ保存用リストへ
                            saveList.add(testList.get(i));
                        }
                    } catch (NumberFormatException e) {
                        // 数字以外が入力された場合
                        over = true;
                    }
                }
            }
            //範囲外が一つでもあった場合、警告文を出す
	        if (over) {
	            request.setAttribute("message_over", "0〜100の範囲で入力してください");
	            java.time.LocalDate todaysDate = java.time.LocalDate.now();
	            int year = todaysDate.getYear();
	            List<Integer> entYearSet = new ArrayList<>();
	            for (int i = year - 10; i <= year + 1; i++) {
	                entYearSet.add(i);
	            request.setAttribute("ent_year_set", entYearSet);
	            ClassNumDao cNumDao = new ClassNumDao();
                request.setAttribute("class_num_set", cNumDao.filter(school));
                request.setAttribute("subject_set", subDao.filter(school));
	            request.setAttribute("tests", testList);
	            
	            request.setAttribute("f1", Integer.parseInt(entYearStr));
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
}