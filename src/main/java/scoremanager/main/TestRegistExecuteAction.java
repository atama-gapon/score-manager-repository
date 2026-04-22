package scoremanager.main;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. パラメータの取得
        String entYearStr = request.getParameter("f1"); 
        String classNum = request.getParameter("f2");  
        String subjectcd = request.getParameter("f3");
        String numStr = request.getParameter("f4");
        

        
        
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
        
        School school = (School) request.getSession().getAttribute("school");

        if (school == null) {
            school = new School();
            school.setCd("oom");
        }
        
        int entYear = Integer.parseInt(entYearStr);
        int num = Integer.parseInt(numStr);
        
        SubjectDao subDao = new SubjectDao();
        Subject subject = subDao.get(subjectcd, school);
        
        TestDao tDao = new TestDao();
        String Regist = request.getParameter("regist");
        if (request.getParameter("search") != null) {
        	request.setAttribute("message_over", null);
        	
        	List<Test> test = tDao.filter(entYear, classNum, subject, num, school);
        	request.setAttribute("f1", entYearStr); 
        	request.setAttribute("f2", classNum);     
        	request.setAttribute("f3", subjectcd);    
        	request.setAttribute("f4", numStr);
        	request.setAttribute("tests", test); 
            
            
            request.setAttribute("num", num);
            request.setAttribute("subject", subject);
            
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            
        }else if(Regist != null) {
        	request.setAttribute("message_over", null);
        	
        	String[] points = request.getParameterValues("point");
            String[] students = request.getParameterValues("student_no_list");
            
            List<Test> testList = tDao.filter(entYear, classNum, subject, num, school);
            List<Test> saveList = new ArrayList<>();
            boolean over = false;

            if (points != null) {
                for (int i = 0; i < testList.size(); i++) {
                	String pStr = points[i];
                    
                    
                    if (pStr == null || pStr.isEmpty()) {
                        over = true;
                        
                        testList.get(i).setPoint(-1); 
                        continue;
                    }
                    int p = Integer.parseInt(points[i]);
                    
                    
                    testList.get(i).setPoint(p);

                    if (p < 0 || p > 100) {
                        over = true;
                    } else {
                        saveList.add(testList.get(i));
                    }
                }
            }
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
	    
            
            
            tDao.save(saveList);
            request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
        }
        
    }
}