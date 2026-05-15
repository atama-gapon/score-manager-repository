package scoremanager.main;

import bean.School;
import bean.Staff;
import dao.PositionDao;
import dao.StaffDao;
import dao.StatusDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Staff loginStaff = (Staff) req.getAttribute("staff");
        School school = loginStaff.getSchool();

        String no = req.getParameter("no");

        Staff target = new StaffDao().get(no, school);

        req.setAttribute("staff", target);
        req.setAttribute("position_list", new PositionDao().filter(school));
        req.setAttribute("status_list", new StatusDao().filter(school));

        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_update.jsp")
           .forward(req, res);
    }
}
