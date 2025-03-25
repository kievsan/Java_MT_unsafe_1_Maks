package ru.mail.kievsan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main_LockedCodeBlock {
    private static int counter1 = 0;
    private static int counter2 = 0;

    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 100_000;
        CountDownLatch latch = new CountDownLatch(tasksCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        ReentrantLock locker = new ReentrantLock();

        for (int i = 0; i < tasksCount; i++) {
            executor.submit(() -> {
                locker.lock(); // !
                try {
                    // unsafe:
                    counter1++;
                    counter2++;
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock(); // !
                }
            });
        }

        latch.await();

        System.out.println(counter1);
        System.out.println(counter2);
        System.exit(0);
    }
}