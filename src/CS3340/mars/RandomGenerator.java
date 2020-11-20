package CS3340.mars;

import java.util.Random;

public class RandomGenerator {

    private static RandomGenerator instance;
    private Random random;

    public RandomGenerator() {
        random = new Random();
    }

    public static RandomGenerator getInstance() {
        if (instance == null) {
            instance = new RandomGenerator();
        }
        return instance;
    }

    public int nextInt(int upperExclusiveBound) {
        return random.nextInt(upperExclusiveBound);
    }
}
