package ru.mail.kievsan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main_SynchronizedCodeBlock {

    private static int counter1 = 0;
    private static int counter2 = 0;

    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 100_000;
        CountDownLatch latch = new CountDownLatch(tasksCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < tasksCount; i++) {
            executor.submit(() -> {
                // safe:
                synchronized (latch) {
                    // unsafe:
                    counter1++; // non-atomic
                    counter2++; // non-atomic
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println(counter1);
        System.out.println(counter2);
        System.exit(0);
    }
}
