package tool;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class StudentUtil {
	public static List<Integer> createEntYearList(HttpServletRequest req) throws Exception {
		List<Integer> list = new ArrayList<>();

		int currentYear = Year.now().getValue();

		for (int i = currentYear - 10; i <= currentYear + 1; i++) {
			list.add(i);
		}

		return list;
	}
}