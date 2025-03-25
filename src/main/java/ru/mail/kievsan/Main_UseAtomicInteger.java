package ru.mail.kievsan;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main_UseAtomicInteger {
    private static final AtomicInteger counter1 = new AtomicInteger(0);
    private static final AtomicInteger counter2 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 100_000;
        CountDownLatch latch = new CountDownLatch(tasksCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < tasksCount; i++)
            executor.submit(() -> {
                // safe:
                counter1.addAndGet(1); // atomic
                counter2.addAndGet(1); // atomic
                latch.countDown();
            });

        latch.await();

        System.out.println(counter1);
        System.out.println(counter2);
        System.exit(0);
    }
}
