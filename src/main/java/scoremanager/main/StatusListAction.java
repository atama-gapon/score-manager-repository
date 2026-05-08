package scoremanager.main;

import java.util.List;

import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ログイン中の職員を取得
        Staff staff = (Staff) req.getAttribute("staff");
        String schoolCd = staff.getSchool().getCd();

        // DAO から学校ごとのステータス一覧を取得
        StatusDao dao = new StatusDao();
        List<Status> list = dao.filter(schoolCd);

        // JSP に渡す
        req.setAttribute("statusList", list);

        // 画面遷移
        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_list.jsp")
           .forward(req, res);
    }
}
