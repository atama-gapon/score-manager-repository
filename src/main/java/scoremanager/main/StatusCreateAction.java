package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StatusCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 何もせず JSP を表示するだけ
        req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/status_create.jsp")
           .forward(req, res);
    }
}
