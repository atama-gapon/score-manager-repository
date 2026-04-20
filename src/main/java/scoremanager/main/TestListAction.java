package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 【セッションからユーザーデータ（教員データ）を取得】
//		【テスト環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
		School school = new School();
		school.setCd("oom");
		school.setName("テスト：oom");
//		【/テスト環境の処理】
		
		// 【本番環境の処理】
//		HttpSession session = req.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		School school = teacher.getSchool()
		// 【/本番環境の処理】
		
// ユーザーデータからユーザーが所属している学校のクラスデータを取得
		ClassNumDao cDao = new ClassNumDao();
// 学校コードに合致するデータを取得
		List<String> cNumSet = cDao.filter(school);
// ユーザーデータからユーザーが所属している学校の科目データを取得
		SubjectDao sDao = new SubjectDao();
// 科目コードに合致するデータを取得
		List<Subject> subjects =  sDao.filter(school);
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
		req.getRequestDispatcher("test_list.jsp").forward(req, res);;
	}
}
