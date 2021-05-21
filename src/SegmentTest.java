import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * For testing of the added class I'd recommend next:
 * 1 - check if the sum of list elements equals total with variety parameters
 * 2 - validate min and max params
 * 3 - check that we have different outputs with the same input
 * 4 - run the method with same params and check the frequency of each number in given range
 * 5 - test with some unusual inputs (zero, negative, etc.)
 */
public class SegmentTest {
    public static void main(String[] args) {
        List<Integer> list = segment(100, 5, 25);
        System.out.println(list);
    }

    /**
     * Breaks an integer into a list of randomly generated integers that add
     * up to the original number. Each of the generated numbers fall within
     * the specified range.
     *
     * @param total the integer to be segmented
     * @param min   the low end of the range (inclusive) for generated numbers
     * @param max   the high end of the range (inclusive) for generated numbers
     * @return a list containing the number segments
     */
    public static List<Integer> segment(int total, int min, int max) {
        List<Integer> list = new ArrayList<>();
        int number = total;
        Random random = new Random();
        int i = 0;
        while (number != 0) {
            if (number >= min && number <= max) {
                list.add(number);
                break;
            }
            i = random.nextInt(max - min + 1);
            i += min;
            if (number - i < min) {
                continue;
            }
            number -= i;
            list.add(i);
        }
        return list;
    }
}
