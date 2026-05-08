package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ログイン中の職員を取得
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		// DAO から学校ごとのステータス一覧を取得
		StatusDao dao = new StatusDao();
		List<Status> list = dao.filter(school);

		req.setAttribute("statusList", list);
		
		req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_list.jsp").forward(req, res);
	}
}