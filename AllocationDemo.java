public class AllocationDemo {
    static volatile byte[] result;

    static void allocate() throws InterruptedException {
        while (true) {
            result = new byte[100_000];
            Thread.sleep(10);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("PID: " + ProcessHandle.current().pid());
        allocate();
    }
}
