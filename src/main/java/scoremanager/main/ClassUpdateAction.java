package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassUpdateAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String oldClassNum = req.getParameter("class_num");
		req.setAttribute("old_class_num", oldClassNum);
		req.getRequestDispatcher("class_update.jsp").forward(req, res);
	}
}