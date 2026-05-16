package tool;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * PBKDF2アルゴリズムを用いてパスワードのハッシュ化および安全な照合を行うセキュリティユーティリティクラス
 */
public class PasswordHasher {

	// ハッシュ化の暗号強度を設定する定数定義
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 256;
	private static final int SALT_LENGTH = 16;

	/**
	 * 平文のパスワードからランダムなソルトを含むセキュアなハッシュ文字列を生成します。
	 * 
	 * @param password 平文のパスワード
	 * @return ソルトとハッシュ値をコロン「:」で連結したBase64文字列
	 * @throws Exception 暗号化処理中に発生した例外
	 */
	public static String hash(String password) throws Exception {
		// 毎回異なるランダムなソルトを生成
		byte[] salt = new byte[SALT_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);

		// PBKDF2 + SHA256 でハッシュ値を生成（ストレッチング実行）
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] hash = factory.generateSecret(spec).getEncoded();

		// 連結されたソルトとハッシュ値をBase64エンコードして返却
		return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
	}

	/**
	 * 入力された平文のパスワードと、データベース等に保存されているハッシュ文字列を安全に照合します。
	 * 
	 * @param password           検証対象の平文パスワード
	 * @param storedPasswordHash 比較対象のハッシュ文字列（ソルト:ハッシュ値）
	 * @return 一致した場合はtrue、それ以外はfalse
	 * @throws Exception 照合処理中に発生した例外
	 */
	public static boolean verify(String password, String storedPasswordHash) throws Exception {
		// パラメータが不正な場合は即座に不一致とするガード句
		if (storedPasswordHash == null || !storedPasswordHash.contains(":")) {
			return false;
		}

		// 連結されたソルトとハッシュ値を分解
		String[] parts = storedPasswordHash.split(":");
		byte[] salt = Base64.getDecoder().decode(parts[0]);
		byte[] hash = Base64.getDecoder().decode(parts[1]);

		// 入力されたパスワードを、登録時と全く同じ条件（ソルト・回数・長さ）で再現ハッシュ生成
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] testHash = factory.generateSecret(spec).getEncoded();

		// タイミング攻撃（比較時間からの推測）を防ぐため、安全なMessageDigest.isEqualで定時比較を実行
		return MessageDigest.isEqual(hash, testHash);
	}
}