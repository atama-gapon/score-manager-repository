package tool;

import java.util.Map;

public class Validator {
	public static void required(String value, String fieldName, String message, Map<String, String> errors) {
		if (value == null || value.isBlank()) {
			errors.put(fieldName, message);
		}
	}

	public static Integer integer(String value, String fieldName, String message, Map<String, String> errors) {
		try {
			return Integer.parseInt(value);

		} catch (NumberFormatException e) {
			errors.put(fieldName, message);
			return null;
		}
	}
}
