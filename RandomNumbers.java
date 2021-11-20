import java.util.Random;

public class RandomNumbers {

	public static int randNum(int x) {
		Random rand = new Random();
		return rand.nextInt(x);
	}
}
