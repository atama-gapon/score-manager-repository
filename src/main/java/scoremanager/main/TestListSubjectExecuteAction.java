package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションのユーザーデータを取得
        // 【テスト環境の処理】
        // HttpSession session = req.getSession();
        // Teacher teacher = (Teacher)session.getAttribute("user");
        // School school = teacher.getSchool()
        School school = new School();
        school.setCd("oom");
        school.setName("テスト：oom");
        // 【/テスト環境の処理】
        
// 入力内容のチェック
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        
        if (entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
        	req.setAttribute("message", "入学年度とクラスと科目を選択してください");
            req.getRequestDispatcher("TestList.action").forward(req, res);
            return;
		}
        
        int entYear = Integer.parseInt(entYearStr);
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, school);
        
		// ユーザーデータからユーザーが所属している学校のクラスデータを取得
		ClassNumDao cDao = new ClassNumDao();
// 学校コードに合致するデータを取得
		List<String> cNumSet = cDao.filter(school);
// ユーザーデータからユーザーが所属している学校の科目データを取得
		SubjectDao subjectDao = new SubjectDao();
// 科目コードに合致するデータを取得
		List<Subject> subjects =  subjectDao.filter(school);
// 入学年度リストを生成
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		// 10年前から1年後までをリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<=year+1; i++) {
			entYearSet.add(i);
		}
		
// 収集したデータをリクエストに設定
		req.setAttribute("class_num_set", cNumSet);
		req.setAttribute("subjects", subjects);
		req.setAttribute("ent_year_set", entYearSet);
        
// 入学年度、クラス、科目に合致するデータを取得
        TestListSubjectDao tDao = new TestListSubjectDao();
        List<TestListSubject> testListSubjects = tDao.filter(entYear, classNum, subject, school);
		
// 入力欄に取得したデータを初期値としてセットし、一覧で表示する
		req.setAttribute("subject", subject);
		req.setAttribute("testListSubjects", testListSubjects);
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}
