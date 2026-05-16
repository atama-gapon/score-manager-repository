package tool;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生管理ドメインにおける共通の計算や、画面表示用データの動的生成を補助するユーティリティクラス
 */
public class StudentUtil {

	// 年度リストの動的生成範囲を設定する定数定義
	private static final int PAST_YEARS_RANGE = 10; // 現在の年から何年前まで遡るか
	private static final int FUTURE_YEARS_RANGE = 1; // 現在の年から何年先まで含めるか

	/**
	 * 現在のシステム日付を基準に、過去10年から未来1年までの入学年度リストを動的に生成して返却します。
	 * 
	 * @return 算出した年度（Integer）が格納されたリスト
	 */
	public static List<Integer> createEntYearList() {
		List<Integer> entYearList = new ArrayList<>();

		// 現在の西暦年を取得（例：2026年）
		int currentYear = Year.now().getValue();

		// 定数に定義された範囲（過去10年〜未来1年）でループを回して年度を格納
		for (int i = currentYear - PAST_YEARS_RANGE; i <= currentYear + FUTURE_YEARS_RANGE; i++) {
			entYearList.add(i);
		}

		return entYearList;
	}
}