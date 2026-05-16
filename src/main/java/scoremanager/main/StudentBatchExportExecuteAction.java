package scoremanager.main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentBatchExportExecuteAction extends Action {

	// 入力された検索条件のバリデーションを行い、対象の学生情報をCSVファイルとしてダウンロード出力する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		String entYearStr = req.getParameter("ent_year");
		String classNum = req.getParameter("class_num");
		String isAttendStr = req.getParameter("is_attend");

		// インスタンス化
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>();
		List<Student> studentList = new ArrayList<>();

		int entYear = 0;
		boolean isEntYearSelected = entYearStr != null && !entYearStr.isEmpty();
		boolean isClassNumSelected = classNum != null && !classNum.isEmpty();
		boolean isAttend = isAttendStr != null;

		// バリデーション：数値変換チェック
		if (isEntYearSelected) {
			try {
				entYear = Integer.parseInt(entYearStr);
			} catch (NumberFormatException e) {
				errors.put("ent_year", "入学年度は半角数字で入力してください");
			}
		}

		// バリデーション：相関チェック
		if (isClassNumSelected && !isEntYearSelected) {
			errors.put("exist", "クラスを指定する場合は入学年度も指定してください");
		}

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("class_num", classNum);
			req.setAttribute("is_attend", isAttendStr);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StudentBatchExport.action").forward(req, res);
			return;
		}

		// データの準備（対象データの抽出）
		if (!isEntYearSelected && !isClassNumSelected) {
			studentList = studentDao.filter(school, isAttend);
		} else {
			studentList = findStudents(school, entYear, classNum, isClassNumSelected, isAttend, studentDao);
		}

		// 対象データが存在しない場合はエラーとして戻す
		if (studentList == null || studentList.isEmpty()) {
			errors.put("exist", "学生情報が存在しません");
			// 画面表示用のデータを準備
			req.setAttribute("ent_year", entYearStr);
			req.setAttribute("class_num", classNum);
			req.setAttribute("is_attend", isAttendStr);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StudentBatchExport.action").forward(req, res);
			return;
		}

		// CSVファイルとしてレスポンスを設定し出力
		res.setContentType("text/csv; charset=MS932");
		res.setHeader("Content-Disposition", "attachment; filename=\"student_list.csv\"");

		try (PrintWriter out = res.getWriter()) {
			for (Student s : studentList) {
				out.print(s.getNo() + ",");
				out.print(s.getName() + ",");
				out.print(s.getEntYear() + ",");
				out.print(s.getClassNum() + ",");
				out.println(s.isAttend() ? "true" : "false");
			}
		}
	}

	private List<Student> findStudents(School school, int entYear, String classNum, boolean isClassNumSelected, boolean isAttend, StudentDao studentDao) throws Exception {
		if (isClassNumSelected) {
			return studentDao.filter(school, entYear, classNum, isAttend);
		}
		return studentDao.filter(school, entYear, isAttend);
	}
}