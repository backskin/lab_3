package com.backsy;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {

    private static int bitSize;
    private static int k;

    private static void oneLittleTest(){
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
    }

    public static void main(String[] args) {


        try {
            bitSize = Integer.parseInt(args[0]);

            k = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {

            System.out.println(e.getLocalizedMessage());
            System.out.println("!WARNING! Entered wrong parameters!" +
                    "\narg[0] must be 'p' (bit size of Big Number)" +
                    "\narg[1] must be 'k' (accuracy of prime test)" +
                    "\n Exiting...");
            return;
        }


        findTheNemo();

    }
}
