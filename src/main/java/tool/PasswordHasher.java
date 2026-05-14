package tool;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {
	public static String hash(String password) throws Exception {
		// 毎回異なるランダムなソルト生成
		byte[] salt = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);

		// PBKDF2 + SHA256 でハッシュ生成
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = factory.generateSecret(spec).getEncoded();

		// 連結されたソルトとハッシュ値を返す
		return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
	}

	// パスワード照合
	public static boolean verify(String password, String storedPasswordHash) throws Exception {
		// 連結されたソルトとハッシュ値を分解
		String[] parts = storedPasswordHash.split(":");
		byte[] salt = Base64.getDecoder().decode(parts[0]);
		byte[] hash = Base64.getDecoder().decode(parts[1]);

		// 入力されたパスワードを、登録時と同じ条件でハッシュ生成
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] testHash = factory.generateSecret(spec).getEncoded();

		// equalsメソッドだと、比較時間から推測される可能性があるため、MessageDigest.isEqualメソッドで比較
		return java.security.MessageDigest.isEqual(hash, testHash);
	}
}
