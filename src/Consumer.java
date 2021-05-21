import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * For testing of the added class I'd recommend next:
 * 1 - check expected value with some inputs (negatives, zeros, etc.)
 * 2 - validate the numbers included in mean calculation (according to timestamp)
 * 3 - check behavior with no numbers for last five minutes
 */
class Consumer {
    public static void main(String[] args) throws InterruptedException {
        Consumer consumer = new Consumer();
        consumer.accept(1000);
        Thread.sleep(5 * 60 * 1000);
        consumer.accept(55);
        consumer.accept(20);
        System.out.println(consumer.mean());
    }

    private final List<ConsumeInfo> consumeInfoList = new LinkedList<>();
    private static final int TIME_THRESHOLD = 5 * 60 * 1000;

    /**
     * Called periodically to consume an integer.
     */
    public void accept(int number) {
        synchronized (consumeInfoList) {
            consumeInfoList.add(new ConsumeInfo(System.currentTimeMillis(), number));
        }
    }

    /**
     * Returns the mean (aka average) of numbers consumed in the
     * last 5 minute period.
     */
    public double mean() {
        long fiveMinutesAgo = System.currentTimeMillis() - TIME_THRESHOLD;
        double total = 0;
        int count = 0;
        ListIterator<ConsumeInfo> listIterator = consumeInfoList.listIterator(consumeInfoList.size());

        while (listIterator.hasPrevious()) {
            ConsumeInfo consumeInfo = listIterator.previous();
            if (consumeInfo.getTimestamp() <= fiveMinutesAgo) {
                break;
            }
            total += consumeInfo.getNumber();
            count++;
        }
        if (count == 0) {
            throw new RuntimeException("There are no numbers for last five minutes");
        }
        return total / count;
    }

    private static class ConsumeInfo {
        private final long timestamp;
        private final int number;

        public ConsumeInfo(long timestamp, int number) {
            this.timestamp = timestamp;
            this.number = number;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public int getNumber() {
            return number;
        }
    }

}