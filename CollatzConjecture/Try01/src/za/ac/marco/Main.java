package za.ac.marco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.concurrent.*;

public class Main {

    static long numToIterate, backup = 0L;
    static long iterations = 0;

    public static void main(String[] args) {
        new Main();
    }

    /*
    Task: Input a number and use multiple threads to compute the iterations concurrently.
     */
    public Main() {
        while (true) {

            traversNum();
            long startTime = System.nanoTime();
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            System.out.println("Time taken: " + timeElapsed + " nano seconds (s)");
            System.out.println("Time taken: " + timeElapsed / 1000000.0 + " milli seconds (s)");
            System.out.println("Time taken: " + timeElapsed / 1000000.0 / 1000.0 + " seconds (s)");

        }
    }

    long getNumFromConsole() {

        String number = null;
        while (number == null) {
            // Enter data using BufferReader
            System.out.print("enter number to iterate: ");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading data using readLine

            try {
                number = reader.readLine();
            } catch (Exception e) {
                System.out.println("Error: enter num again...");
            }
            // length
            if (number.length() > 19) {
                System.out.println("Too long - enter number between 0 and 19 digits in length!");
                number = null;
            }
            // letters and floats
            if (!isNumeric(number)) {
                System.out.println("Not long number - enter number(s) between 0 and 9!");
                number = null;
            }

        }
        return Long.parseLong(number);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            long g = Long.parseLong(strNum);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    void traversNum() {
        // gets num from user (console)
        numToIterate = getNumFromConsole();
        backup = numToIterate;

        // reset iterations.
        iterations = 0;

        // define concurrent thread mechanism.
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // declare future.
        Future<?> future;

        int threadCounter = 0;
        while (numToIterate > 1) {

            threadCounter++;
            // add future and traverse num once.
            int finalThreadCounter = threadCounter;
            future = executorService.submit(() -> {
                try {
                    iterateOnce();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            try {
                // get result of thread after done.
                if (future.get() != null) System.out.println("thread " + finalThreadCounter + " failure ");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Completed (using " + threadCounter + " different thread calls and 10 total threads in async) iteration count = " + iterations + "\n");
    }

    /**
     * Iterates and prints iterations per number.
     */
    void iterateOnce() throws IOException {
        traverseOnce();
    }

    void traverseOnce() {

        if (numToIterate % 2 == 0) numToIterate /= 2;
        else {
            long replacer = (numToIterate * 3) + 1;
            numToIterate = replacer;
        }
        iterations++;
    }
}
