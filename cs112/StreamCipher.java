import java.util.Random;

public class StreamCipher implements Cipher {
    private long seed;
    Random random;
    int[] array = new int[0x10000];

    public StreamCipher(long seed){
        this.seed = seed;
        random = new Random(seed);
    }

    public String encrypt(String message){
    	random.setSeed(seed);
        char[] charArray = message.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] ^ random.nextInt(0x10000));
        }
        return new String(charArray);

    }

    public String decrypt(String encryptedMessage){
        return encrypt(encryptedMessage);
    }

//    public static void main(String[] args) {
//        StreamCipher test = new StreamCipher(42);
//        String secretMessage = "\uBA03\u0D9B\uAEC7\u0C36\u4F7D\uF159\u468B\uB50C\uAA15\u170E\uE763\u73E1\u5E1A\u61DA\u46F9\uB0D4\u7692\uC361\uC803\uFFFD\uEB2B\u26C6\u6FF2\u70E0\uBF98\uEE51\u6282\uCC35\u2D06\u26EF\u9806\u56BB\u359B\u400B\uD31A\u5D0F\u2C62\u28ED\u9612\u460A\uC07C\uA4EB\u9256\u14E2\u9409\u1741\uC0D1\uCC5C\u0869\u516D\u5B8F\u328D\uD13F\u0294\u6AB2\uC55F\uF968\u14D1\uB6EA\u881C\u7B4F\uDFCF\u4A8A\u381B";
//        System.out.println(test.encrypt(secretMessage));
//        System.out.println(test.decrypt(secretMessage));
//    }

}
