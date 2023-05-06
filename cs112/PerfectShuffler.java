import java.util.Arrays;
import java.util.Scanner;

/**
 * Simulate a perfect shuffle.
 *
 * @author (Barry Tang)
 * @version (Sep 6, 2022)
 */
public class PerfectShuffler
{   
    private int[] cardArray;
    
    public static void main(String[] args) {
        PerfectShuffler s = new PerfectShuffler();
        System.out.println(s.toString());
        
        Scanner input = new Scanner(System.in);
        String command;
        while (input.hasNext()) {
            command = input.next();
            switch (command) {
                case "i":
                    s.inShuffle();
                    System.out.println(s.toString());
                    break;
                case "o":
                    s.outShuffle();
                    System.out.println(s.toString());
                    break;
            }
        }
    }
    
    public PerfectShuffler() {
        cardArray = new int[52];
        for (int i = 0; i < 52; i++) {
            cardArray[i] = i;
        }
    }
    
    public int getIndexAt(int index) {
        return cardArray[index];
    }
    
    public void inShuffle() {
        int[] topHalf = Arrays.copyOfRange(cardArray, 0, 26);
        int[] botHalf = Arrays.copyOfRange(cardArray, 26, 52);

        for (int i = 0, j = 0; i < 52; i+=2, j++) {
            cardArray[i] = botHalf[j];
            cardArray[i + 1] = topHalf[j];
        }
    }
    
    public void outShuffle() {
        int[] topHalf = Arrays.copyOfRange(cardArray, 0, 26);
        int[] botHalf = Arrays.copyOfRange(cardArray, 26, 52);
        
        for (int i = 0, j = 0; i < 52; i+=2, j++) {
            cardArray[i] = topHalf[j];
            cardArray[i + 1] = botHalf[j];
        }
    }
    
    public String toString() {
        String array = Arrays.toString(cardArray);
        return array.substring(1, array.length() - 1);
    }
}
