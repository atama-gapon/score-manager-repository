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
    	// 入力値の取得
    	String entYearStr = req.getParameter("ent_year");
    	String no = req.getParameter("no");
    	String name = req.getParameter("name");
    	String classNum = req.getParameter("class_num");

    	// ログイン中の先生の学校情報を確認
    	School school = (School) req.getSession().getAttribute("school");
    	if (school == null) {
    	    res.sendRedirect("../login.jsp");
    	    return;
    	}

    		// 入学年度の選択肢を作成
    		LocalDate todaysDate = LocalDate.now();
    		int year = todaysDate.getYear();

    	 List<Integer> entYearSet = new ArrayList<>();
    		for (int i = year - 10; i <= year + 1; i++) {
    			entYearSet.add(i);
    	}	
    			req.setAttribute("ent_year_set", entYearSet);

    	// クラスの選択肢を取得
    		ClassNumDao cNumDao = new ClassNumDao();
    	List<String> classNumSet = cNumDao.filter(school);
   			req.setAttribute("class_num_set", classNumSet);

   		// 入力値を戻す
   			req.setAttribute("ent_year", entYearStr);
   			req.setAttribute("no", no);
   			req.setAttribute("name", name);
   			req.setAttribute("class_num", classNum);

   			// バリデーション
   			Map<String, String> errors = new HashMap<>();
   			StudentDao dao = new StudentDao();

   			if (entYearStr == null || entYearStr.isEmpty()) {
   			    errors.put("ent_year", "入学年度を選択してください");
   			}

   			if (no != null && !no.isEmpty() && dao.get(no) != null) {
   			    errors.put("no", "学生番号が重複しています");
   			}

   			if (!errors.isEmpty()) {
   			    req.setAttribute("errors", errors);
   			    req.getRequestDispatcher("student_create.jsp").forward(req, res);
   			    return;
   			}

    	// 登録処理
    	Student s = new Student();
    	
    	s.setNo(no);   	
    s.setName(name);
    	s.setEntYear(Integer.parseInt(entYearStr));
    		s.setClassNum(classNum);
    	s.setSchool(school);

    	// DBへ保存
    	boolean result = dao.save(s);

    	// 保存に失敗した場合
    	if (!result) {
    	    req.setAttribute("message", "登録に失敗しました");
    	    req.getRequestDispatcher("student_create.jsp").forward(req, res);
    	    return;
    	}

    	// 完了画面へ
    	req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
}}