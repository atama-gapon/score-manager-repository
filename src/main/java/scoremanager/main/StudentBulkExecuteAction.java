package scoremanager.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import bean.School;
import bean.Staff;
import dao.StudentDao;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import tool.Action;

@MultipartConfig(maxFileSize = 10000000, maxRequestSize = 10000000, fileSizeThreshold = 10000000)
public class StudentBulkExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Staff staff = (Staff) req.getAttribute("staff");
		School school = staff.getSchool();

		Part csv = req.getPart("csv");
		BufferedReader br = null;

		try {

			if (csv != null && csv.getSize() > 0) {
				InputStream is = csv.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				br = new BufferedReader(isr);

				StudentDao dao = new StudentDao();
				dao.Bulk(br, school.getCd());

				res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentBulkDone.action");
			} else {

				req.setAttribute("error", "ファイルを選択してください。");
				req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_bulk.jsp").forward(req, res);
			}

		} catch (Exception e) {

			req.setAttribute("error", "登録中にエラーが発生しました：" + e.getMessage());
			req.getRequestDispatcher("/WEB-INF/jsp/scoremanager/main/student_bulk.jsp").forward(req, res);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {

				}
			}
		}
	}
}