package scoremanager.main;

import bean.Staff;
import bean.Status;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Staff staff = (Staff) req.getAttribute("staff");
        String schoolCd = staff.getSchool().getCd();

        int id = Integer.parseInt(req.getParameter("id"));

        StatusDao dao = new StatusDao();
        Status status = dao.get(id, schoolCd);

        req.setAttribute("id", status.getId());
        req.setAttribute("name", status.getName());
        req.setAttribute("sortOrder", status.getSortOrder());

        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_delete.jsp")
           .forward(req, res);
    }
}
