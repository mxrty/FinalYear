import java.util.*;

public class Main {
    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) {

    }

    int countPairs(int numCount, List<Integer> ratingValues, int target) {
        int distinctPairs = 0;

        Collections.sort(ratingValues);

        // Index of last rating that is viable for a pair
        int backPointer = 0;

        for (int i = 0; i < numCount; i++) {
            if (ratingValues.get(i) >= target) {
                backPointer = i - 1;
                break;
            }
        }

        for (int i = 0; i < backPointer; i++) {
            int front = ratingValues.get(i);
            int back = ratingValues.get(backPointer);
            if (front + back == target) {
                distinctPairs++;
                backPointer--;
            } else {
                while (front <= back && (front + back > target)) {
                    backPointer--;
                    back = ratingValues.get(backPointer);
                    if (front + back == target) {
                        distinctPairs++;
                        backPointer--;
                        break;
                    }
                }
            }
        }

        return distinctPairs;
    }

}