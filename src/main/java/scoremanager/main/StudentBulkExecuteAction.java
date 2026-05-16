package scoremanager.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import bean.School;
import bean.Staff;
import dao.StudentDao;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import tool.Action;

@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 10000000, fileSizeThreshold = 10000000)
public class StudentBulkExecuteAction extends Action {

	// アップロードされたCSVファイルをバリデーションし、問題がなければ学生情報の一括登録・更新処理を実行する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();
		Part csv = req.getPart("csv");

		// バリデーション
		if (csv == null || csv.getSize() <= 0) {
			req.setAttribute("error", "ファイルを選択してください。");
			req.getRequestDispatcher("StudentBulk.action").forward(req, res);
			return;
		}

		// DBへ反映（ストリームを開いて一括処理を実行）
		try (BufferedReader br = new BufferedReader(new InputStreamReader(csv.getInputStream(), "UTF-8"))) {
			StudentDao studentDao = new StudentDao();

			// DAOの一括登録・更新ロジックを呼び出し
			studentDao.Bulk(br, school.getCd());

			res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentBulkDone.action");

		} catch (Exception e) {
			// 例外発生時はエラーメッセージを設定して入力画面へ戻す
			req.setAttribute("error", "一括処理中にエラーが発生しました: " + e.getMessage());
			req.getRequestDispatcher("StudentBulk.action").forward(req, res);
		}
	}
}