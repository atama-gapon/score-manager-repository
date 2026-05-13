package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.School;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;

public class StatusForm {
	private String id;
	private String name;
	private String sortOrder;

	// コンストラクタでリクエストから値を一気に抜き出す
	public StatusForm(HttpServletRequest req) {
		this.id = req.getParameter(id);
		this.name = req.getParameter("name");
		this.sortOrder = req.getParameter("sort_order");
	}

	// バリデーション
	public Map<String, String> validate(School school) throws Exception {
		Map<String, String> errors = new HashMap<>();
		StatusDao statusDao = new StatusDao();
		
		if(id == null || id.isEmpty()) {
			errors.put("id", "idを入力してください");
		}

		if (name == null || name.isEmpty()) {
			errors.put("name", "状態名を入力してください");
		}

		int sortOrderInt = 0;
		try {
			sortOrderInt = Integer.parseInt(sortOrder);

			if (sortOrderInt < 0) {
				errors.put("sortOrder", "並び順は 0 以上の整数で入力してください");
			}

		} catch (NumberFormatException e) {
			errors.put("sortOrder", "並び順は数字で入力してください");
		}

		// 重複チェック
		if (statusDao.existsByName(name, school)) {
			errors.put("name", "同じ名前の状態がすでに存在します");
		}

		return errors;
	}

	// 正常な場合、保存用の型に変換する
	public Status toEntity(School school) throws Exception {
		Status status = new Status();
		status.setId(Integer.parseInt(id));
		status.setName(name);
		status.setSortOrder(Integer.parseInt(sortOrder));
		status.setSchool(school);

		return status;
	}

	// エラー時に値を画面に戻すための補助メソッド
	public void setAttributes(HttpServletRequest req) {
		Status status = new Status();
		status.setId(Integer.parseInt(id));
		status.setName(name);
		status.setSortOrder(Integer.parseInt(sortOrder));
		req.setAttribute("status", status);
	}
}
