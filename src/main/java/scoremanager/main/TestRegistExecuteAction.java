package scoremanager.main;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
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
        String entYearStr = request.getParameter("entyear"); 
        String classNum = request.getParameter("classnum");  
        String subjectcd = request.getParameter("subject_cd");
        String numStr = request.getParameter("num");
        
        String Search = request.getParameter("search");
        String Regist = request.getParameter("regist");
        
        if (entYearStr == null || entYearStr.isEmpty() ||
        	    classNum == null || classNum.isEmpty() ||
        	    subjectcd == null || subjectcd.equals("0") ||
        	    numStr == null || numStr.isEmpty()) {

        	    request.setAttribute("message", "入学年度・クラス・科目・回数を入力してください");
        	    request.getRequestDispatcher("student_create.jsp").forward(request, response);
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
        
        if (Search != null) {
        	List<Test> test = tDao.filter(entYear, classNum, subject, num, school);
        	
        	request.setAttribute("entyear", entYear);
            request.setAttribute("classnum", classNum);
            request.setAttribute("subject_cd", subjectcd);
            request.setAttribute("num", num);
            request.setAttribute("subject", subject); 
            request.setAttribute("tests", test);
            
            request.getRequestDispatcher("test_regist.jsp").forward(request, response);
            
        }else if(Regist != null) {
        	String[] points = request.getParameterValues("point");
            String[] students = request.getParameterValues("student_no");
            
            List<Test> save = new ArrayList<>();
            
            if (points != null && students != null) {
                for (int i = 0; i < students.length; i++) {
                    String pStr = points[i];
                    
                    
                    if (pStr == null || pStr.isEmpty()) {
                        continue;
                    }
                    
                    int point = Integer.parseInt(pStr);
                    
                    
                    if (point < 0 || point > 100) {
                        request.setAttribute("message", "0〜100の範囲で入力してください");
                        
                        this.execute(request, response);
                        return;
                    }
                    
                   
                    Test test = new Test();
                    Student student = new Student();
                    student.setNo(students[i]);
                    test.setStudent(student);
                    test.setClassNum(classNum);
                    test.setSubject(subjectcd);
                    test.setSchool(school);
                    test.setNo(num);
                    test.setPoint(point);
                    
                    save.add(test);
                }
            }
            tDao.save(save);
            request.getRequestDispatcher("test_regist_done.jsp").forward(request, response);
        }
        
    }
}