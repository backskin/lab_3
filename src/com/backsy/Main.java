package com.backsy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.lang.Math.pow;

public class Main {

    private static int bitSize;
    private static int k;

    /*private static void oneLittleTest(){
        System.out.println("bit length of BigInteger will be '" + bitSize + "'");

        BigInteger coolBigInteger = Tests.getCoolBigInt(bitSize);

        System.out.println("Random Big Integer = " + coolBigInteger.toString());

        System.out.println("TESTING...");

        if (Tests.testSoloveyShtrassen(coolBigInteger, k)){
            double p = 1 - pow(2.0, -k);
            System.out.println("Number \n" +
                    coolBigInteger
                    + "\n is prime with accuracy = " + p);
        } else {
            System.out.println("Number \n" +
                    coolBigInteger
                    + "\n is composite");
        }
    }

    private static void findTheNemo(){

        int t = 0;
        boolean primeness = false;
        BigInteger coolBigInteger;
        do {

            coolBigInteger = Tests.getCoolBigInt(bitSize);

            System.out.print(".");
            t++;
            if (t > 70){
                System.out.println();
                t = 0;
            }

            primeness = Tests.testSoloveyShtrassen(coolBigInteger, k);

        } while (!primeness);

        double p = 1 - pow(2.0, -k);
        System.out.println("\n Number \n" +
                coolBigInteger
                + "\n is prime with accuracy = " + p);
    }*/

    private static void pressAnyKeyToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            int a = System.in.read();
        }
        catch(Exception ignored)
        {}
    }


    private static void findTheNemoWithLogs() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String date = dtf.format( LocalDateTime.now());
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("log-" + date + ".txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {

            System.out.println("WARNING! Error while creating a log file...");
        }
        if (writer != null) writer.print("\t------ Generating a probably prime Big Integer ------\n");
        if (writer != null) writer.print("\tParameters: p=" + bitSize + "  k=" + k + "\n");

        int t = 0;
        boolean primeness;
        BigInteger coolBigInteger;

        do {

            coolBigInteger = Tests.getCoolBigInt(bitSize);
            if (writer != null) writer.print("\tIteration N" + (t+1) + "  BG=" + coolBigInteger.toString());

            System.out.print(".");
            t++;
            if (t % 70 == 0){
                System.out.println();
            }

            primeness = Tests.testSoloveyShtrassen(coolBigInteger, k);
            if (!primeness && writer != null)
                writer.print(" | COMPOSITE\n");
        } while (!primeness);
        double p = 1 - pow(2.0, -k);
        if (writer != null) writer.print(" | PRIME\n");
        if (writer != null) writer.print("\t--------------------------------------------\n");
        if (writer != null) writer.print("\tNumber\n\t" + coolBigInteger.toString()
                + "\n\tis prime with accuracy = [" + p + "]");
        if (writer != null) writer.close();
        System.out.println("\nNumber \n" +
                coolBigInteger
                + "\n is prime with accuracy = " + p);
    }

    public static void main(String[] args) {

        try {

            if (args.length != 2)
                throw new NumberFormatException();

            bitSize = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[1]);

            if (bitSize <= 10 || k < 2)
                throw new NumberFormatException();

            findTheNemoWithLogs();

        } catch (NumberFormatException e) {

            System.out.println("!WARNING! Wrong (or null) parameters!" +
                    "\narg[0] must be 'p' (bit size of Big Number) " +
                    "\nand p > 10 for sure" +
                    "\narg[1] must be 'k' (accuracy of prime test)" +
                    "\nand k > 1 for sure" +
                    "\n");
        }

        pressAnyKeyToContinue();

    }
}
