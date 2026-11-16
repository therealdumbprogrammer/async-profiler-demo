public class DemoCPU {


    static long calculate() {
        return calculatePrimes() + calculateRoots();
    }

    static long calculatePrimes() {
        long count = 0;

        for (int n = 2; n < 20_000; n++) {
            boolean prime = true;

            for (int d = 2; d * d <= n; d++) {
                if (n % d == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime) count++;
        }

        return count;
    }

    static long calculateRoots() {
        long sum = 0;

        for (int i = 1; i < 200_000; i++) {
            sum += (long) Math.sqrt(i);
        }

        return sum;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("PID: " + ProcessHandle.current().pid());

        Thread.ofPlatform().name("calculator").start(() -> {
            while (true) calculate();
        });

        Thread.ofPlatform().name("waiting-thread").start(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

}
