package scoremanager.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import bean.School;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import tool.Action;

@MultipartConfig(
	    maxFileSize=10000000,
	    maxRequestSize=10000000,
	    fileSizeThreshold=10000000
	)
	public class StudentBulkExecuteAction extends Action {
	    @Override
	    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        HttpSession session = request.getSession();
	        Teacher teacher = (Teacher)session.getAttribute("user");
	        School school = teacher.getSchool();
	         
	        Part csv = request.getPart("csv");
	        BufferedReader br = null;
	        
	        try {
	            
	            if (csv != null && csv.getSize() > 0) {
	                InputStream is = csv.getInputStream();
	                InputStreamReader isr = new InputStreamReader(is, "MS932");
	                br = new BufferedReader(isr);
	                
	                StudentDao dao = new StudentDao();
	                dao.Bulk(br, school.getCd());
	                
	                
	                request.getRequestDispatcher("student_bulk_done.jsp").forward(request, response);
	            } else {
	                
	                request.setAttribute("error", "ファイルを選択してください。");
	                request.getRequestDispatcher("student_bulk.jsp").forward(request, response);
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            
	            request.setAttribute("error", "登録中にエラーが発生しました：" + e.getMessage());
	            request.getRequestDispatcher("student_bulk.jsp").forward(request, response);
	        } finally {
	            if (br != null) {
	                try { br.close(); 
	                } catch (Exception e) { 
	                	
	                }
	            }
	        }
	    }
	}