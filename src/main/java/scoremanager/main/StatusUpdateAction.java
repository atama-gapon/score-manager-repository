package scoremanager.main;

import bean.School;
import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Staff staff = (Staff) req.getAttribute("staff");
        School school = staff.getSchool();
        String schoolCd = school.getCd();

        // パラメータ取得
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        // DAO から取得（schoolCd を渡す）
        StatusDao dao = new StatusDao();
        Status status = dao.get(id, schoolCd);

        // JSP に渡す
        req.setAttribute("status", status);

        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_update.jsp")
           .forward(req, res);
    }
}
