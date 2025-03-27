package ru.mail.kievsan.multitreading_patterns;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonLogger {

    private static SingletonLogger INSTANCE = null;

    private final Map<String, Integer> freqs = new ConcurrentHashMap<>();
    private final Map<UUID, Log> logs = new ConcurrentHashMap<>();

    private SingletonLogger() { }

    public static SingletonLogger getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonLogger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingletonLogger();
                }
            }
        }
        return INSTANCE;
    }

    public void log(String level, String msg) {
        freqs.put(level, freqs.getOrDefault(level, 0) + 1);
        logs.put(UUID.randomUUID(), new Log(msg));

        System.out.println("[" + level + "#" + freqs.get(level) + "]" +
                LocalDateTime.now() + " === " + msg);
    }

    static class Log {
        public Log(String msg) {
            // TODO
        }
        // TODO
    }
}
