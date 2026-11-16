public class LockDemo {
    static final Object lock = new Object();

    static void work() {
        while (true) {
            synchronized (lock) {
                try {
                    Thread.sleep(50); // Deliberately hold the lock to create contention.
                } catch (InterruptedException e) {
                    return;
                }
            }
            try { Thread.sleep(10); } catch (InterruptedException e) { return; }
        }
    }

    public static void main(String[] args) {
        System.out.println("PID: " + ProcessHandle.current().pid());

        new Thread(LockDemo::work, "worker-1").start();
        new Thread(LockDemo::work, "worker-2").start();
    }
}
