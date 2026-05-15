package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StaffUpdateDoneAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/staff_update_done.jsp")
           .forward(req, res);
    }
}
