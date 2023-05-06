import java.util.Scanner;

public class CipherDemo {
	private static Scanner in = new Scanner(System.in);

	public static boolean testCipher(Cipher cipher) {
		System.out.print("Your message: ");
		String message = in.nextLine();
		String encryptedMessage = cipher.encrypt(message);
		System.out.println("Encrypted message: " + encryptedMessage);
		String decryptedMessage = cipher.decrypt(encryptedMessage);
		System.out.println("Decrypted message: " + decryptedMessage);
		return message.equals(decryptedMessage);
	}

	public static void main(String[] args) {
//
//		// Caesar Cipher
//		System.out.printf("Rotation (1-25)? ");
//		int rotation = Integer.parseInt(in.nextLine());
//		if (rotation >= 1 && rotation <= 25)
//			System.out.println(testCipher(new CaesarCipher(rotation)) ? "PASS" : "FAIL");
//		else
//			System.err.println("Invalid rotation: " + rotation);
//
//		// Unicode Substition Cipher
//		System.out.printf("Seed? ");
//		System.out.println(testCipher(new UnicodeSubstitutionCipher(Long.parseLong(in.nextLine()))) ? "PASS" : "FAIL");

		// Stream Cipher (HW)
		System.out.printf("Seed? ");
		System.out.println(testCipher(new StreamCipher(Long.parseLong(in.nextLine()))) ? "PASS" : "FAIL");
		
		
		in.close();
	}

}
