package scoremanager.main;

import java.util.Map;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusUpdateExecuteAction extends Action {

	// 入力された在籍状態変更情報のバリデーションを行い、問題がなければデータベースを更新する
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 必要なリクエスト情報の取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// Formオブジェクトの生成と値の抽出
		StatusForm form = new StatusForm(req);
		StatusDao statusDao = new StatusDao();

		// バリデーション（変更処理であることを示すために第2引数に true を指定）
		// ※現状のFormの簡易仕様に合わせつつ、名前変更なしの考慮が必要な場合はDAO側等でハンドリング
		Map<String, String> errors = form.validate(school, true);

		// エラーがある場合は入力画面へ戻す
		if (!errors.isEmpty()) {
			// 画面表示用のデータを準備
			form.setAttributes(req);
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("StatusUpdate.action").forward(req, res);
			return;
		}

		// DBへ反映
		Status status = form.toEntity(school);
		statusDao.update(status);

		res.sendRedirect(req.getContextPath() + "/scoremanager/main/StatusUpdateDone.action");
	}
}