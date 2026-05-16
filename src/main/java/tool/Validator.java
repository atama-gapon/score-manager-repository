package tool;

import java.util.Map;

/**
 * 画面からの入力値に対する必須検証や型変換チェックを一元管理する共通バリデーションユーティリティクラス
 */
public class Validator {

	/**
	 * 指定された値が未入力（nullまたは空文字、空白スペースのみ）であるか検証し、不正な場合はエラーマップにメッセージを格納します。
	 * 
	 * @param value     検証対象の文字列
	 * @param fieldName エラーが発生した入力項目のフィールド名（キー）
	 * @param message   画面に表示するエラーメッセージ
	 * @param errors    エラーメッセージを蓄積するマップオブジェクト
	 */
	public static void required(String value, String fieldName, String message, Map<String, String> errors) {
		// エラーマップが初期化されていない場合は処理をスキップ（NullPointerException防止ガード句）
		if (errors == null) {
			return;
		}

		// 値がnull、または空文字・半角全角スペースのみの場合にエラーをセット
		if (value == null || value.isBlank()) {
			errors.put(fieldName, message);
		}
	}

	/**
	 * 指定された文字列を整数型（Integer）に変換できるか検証し、変換に失敗した場合はエラーマップにメッセージを格納します。
	 * 
	 * @param value     検証対象の文字列
	 * @param fieldName エラーが発生した入力項目のフィールド名（キー）
	 * @param message   画面に表示するエラーメッセージ
	 * @param errors    エラーメッセージを蓄積するマップオブジェクト
	 * @return 変換成功時はパースされたIntegerオブジェクト、失敗時（またはエラーマップが無効時）はnull
	 */
	public static Integer integer(String value, String fieldName, String message, Map<String, String> errors) {
		// エラーマップが初期化されていない場合は処理をスキップ（NullPointerException防止ガード句）
		if (errors == null) {
			return null;
		}

		try {
			// 文字列を数値（int）に変換して返却
			return Integer.parseInt(value);

		} catch (NumberFormatException e) {
			// 数値への変換に失敗した場合はエラーメッセージをセットしてnullを返却
			errors.put(fieldName, message);
			return null;
		}
	}
}