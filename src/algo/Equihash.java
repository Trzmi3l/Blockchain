package algo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class Equihash {
    private static final int NUM_THREADS = 12; // Liczba wątków do obliczeń
    private static final String DATA = "daoda";
    private static final int DIFFICULTY = 8; // Trudność - ilość zer na początku hasha
    private static final int SHARE_DIFF = 5;
    static int SHARES = 1;

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        
        AtomicBoolean solutionFound = new AtomicBoolean(false);
        AtomicLong nonce = new AtomicLong(0);
        AtomicLong totalOperations = new AtomicLong(0);

        Runnable task = () -> {
           long ops = totalOperations.get();
           System.out.println("\rSpeed: " + ops);
           totalOperations.set(0);
        };

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> mine(DATA, DIFFICULTY, solutionFound, nonce, totalOperations));
            
        }
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private static void mine(String data, int difficulty, AtomicBoolean solutionFound, AtomicLong nonce, AtomicLong ops) {
        String targetPrefix = new String(new char[difficulty]).replace('\0', '0');
        System.out.println("");
        while (!solutionFound.get()) {
            
            long currentNonce = nonce.getAndIncrement();
            String hash = calculateHash(calculateHash(data + currentNonce));
        
            long operations = ops.incrementAndGet();


            if (hash.startsWith(targetPrefix)) {
                solutionFound.set(true);
                System.out.println("\nSolution found by thread: " + Thread.currentThread().getId());
                System.out.println("Nonce: " + currentNonce);
                System.out.println("Hash: " + hash);

            }
        
            
        }
    }

     private static String calculateHash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
