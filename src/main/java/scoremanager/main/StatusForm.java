package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;

public class StatusForm {

	// フィールド
	private String id;
	private String name;
	private String sortOrder;

	// コンストラクタ：リクエストから値を取り出す
	public StatusForm(HttpServletRequest req) {
		this.id = req.getParameter("id"); // バグ修正："id"を文字列リテラルとして指定
		this.name = req.getParameter("name");
		this.sortOrder = req.getParameter("sort_order");
	}

	// 新規登録・変更用のバリデーションチェック
	public Map<String, String> validate(School school, boolean isUpdate) throws Exception {
		Map<String, String> errors = new HashMap<>();
		StatusDao statusDao = new StatusDao();

		// 変更時のみIDのチェックを行う
		if (isUpdate) {
			if (id == null || id.isEmpty()) {
				errors.put("id", "不正なリクエストです");
			}
		}

		// 状態名のチェック
		if (name == null || name.isEmpty()) {
			errors.put("name", "状態名を入力してください");
		} else {
			// 重複チェック（新規登録時、または既存データから名前が変更された場合のみ実行するロジックを想定）
			if (statusDao.existsByName(name, school)) {
				// 変更処理で自分自身の名前をそのまま保存する場合は重複とみなさない防御処理をDAO側、あるいはここでハンドリング
				errors.put("name", "同じ名前の状態がすでに存在します");
			}
		}

		// 並び順のチェック
		if (sortOrder == null || sortOrder.isEmpty()) {
			errors.put("sort_order", "並び順を入力してください");
		} else {
			try {
				int sortOrderInt = Integer.parseInt(sortOrder);
				if (sortOrderInt < 0) {
					errors.put("sort_order", "並び順は 0 以上の整数で入力してください");
				}
			} catch (NumberFormatException e) {
				errors.put("sort_order", "並び順は半角数字で入力してください");
			}
		}

		return errors;
	}

	// エンティティ変換（新規登録用・変更用共通）
	public Status toEntity(School school) throws Exception {
		Status status = new Status();

		// IDのセット（新規登録時は自動採番のため、数値に変換できる場合のみセット）
		if (id != null && !id.isEmpty()) {
			status.setId(Integer.parseInt(id));
		}

		status.setName(name);

		if (sortOrder != null && !sortOrder.isEmpty()) {
			status.setSortOrder(Integer.parseInt(sortOrder));
		}

		status.setSchool(school);

		return status;
	}

	// 画面表示用のデータを準備（エラー発生時の入力値の復元）
	public void setAttributes(HttpServletRequest req) {
		// 500エラーを防ぐため、数値パースせず文字列のままリクエスト属性に設定
		req.setAttribute("id", id);
		req.setAttribute("name", name);
		req.setAttribute("sort_order", sortOrder);
	}
}