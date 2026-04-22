package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class ClassDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String ClassNum = req.getParameter("class_num");
		req.setAttribute("class_num", ClassNum);
		req.getRequestDispatcher("class_delete.jsp").forward(req, res);
	}
}