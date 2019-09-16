package com.backsy;

import java.math.BigInteger;

import static java.lang.StrictMath.pow;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

class Tests {

    private static RandomBitGenerator randGen = DefaultGenerator.factory();

    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger FOUR = new BigInteger("4");
    private static final BigInteger FIVE = new BigInteger("5");

    private static boolean isPrime(int n) {
        if ((n < 2) || n%2 == 0)
            return false;
        if (n < 9)
            return true;
        if (n % 3 == 0)
            return false;
        int r = (int) pow(n, 0.5);
        int f = 5;
        while (f <= r) {
            if (n % f == 0 || n % (f + 2) == 0)
                return false;
            f += 6;
        }
        return true;
    }

    private static boolean isThisBigIntCool(BigInteger integer){

        for (int i = 0; i < 2000; i++) {
            if (isPrime(i)){
                if (!integer.gcd((BigInteger.valueOf(i))).equals(ONE)){
                    return false;
                }
            }
        }

        return true;
    }

    static BigInteger getCoolBigInt(int bitSize){

        BigInteger integer;

        do {
            byte[] rba = randGen.randomArray(bitSize);
            rba[0] = 1;
            rba[bitSize-1] = 1;
            StringBuilder binary = new StringBuilder();
            for (byte a: rba) {
                binary.append(a);
            }
            integer = new BigInteger(binary.toString(), 2);

        } while (!isThisBigIntCool(integer));

        return integer;
    }

    /** Function to calculate jacobi (a/b) **/
    private static int Jacobi(BigInteger a, BigInteger b)
    {
        if (b.compareTo(ZERO) <= 0 || b.mod(TWO).equals(ZERO))
            return 0;

        int j = 1;
        if (a.compareTo(ZERO) < 0)
        {

            if (b.mod(FOUR).equals(THREE))
                j = -j;
        }
        while (!a.equals(ZERO))
        {
            while (a.mod(TWO).equals(ZERO))
            {
                a = a.divide(TWO);
                BigInteger r = b.mod(new BigInteger("8"));
                if (r.equals(THREE) || r.equals(FIVE))
                    j = -j;
            }

            BigInteger temp = a;
            a = b;
            b = temp;
            if (a.mod(FOUR).equals(THREE) && b.mod(FOUR).equals(THREE))
                j = -j;
            a = a.mod(b);
        }
        if (b.equals(ONE))
            return j;
        return 0;
    }

    // gcd уже есть в BigInteger

    // k - количество раундов
    // k = коэффициент точности. Метод возвращает ноль, если число составное
    // или точность, с которой оно является простым

    static boolean testSoloveyShtrassen(BigInteger N, int k){

        for (int i = 0; i < k; i++) {

            byte[] bigByteArray = N.toByteArray();
            byte[] raw = new byte[bigByteArray.length];
            for (int j = 0; j < raw.length; j++) {
                raw[j] = (byte) (bigByteArray[j] * randGen.next());
            }
            BigInteger A = new BigInteger(raw);

            BigInteger jacobian = N.add(BigInteger.valueOf(Jacobi(A, N))).mod(N);
            BigInteger mod = A.modPow(N.subtract(ONE).divide(TWO), N);
            if(jacobian.equals(ZERO) || !mod.equals(jacobian)) {
                return false;
            }
        }
        return true;
    }
}
