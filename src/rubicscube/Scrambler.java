package rubicscube;

import java.util.Random;

public class Scrambler {
	public static void scramble(Cube cube) {
		Random rand = new Random();
		for (int i = 0; i < 1000000; i++) {
			cube.rotateSide(rand.nextInt(6), rand.nextBoolean());
		}
	}
}
