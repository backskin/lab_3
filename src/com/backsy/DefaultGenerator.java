package com.backsy;

import java.util.Random;

import static java.lang.Math.abs;

public class DefaultGenerator implements RandomBitGenerator {

    private Random r;

    private DefaultGenerator(){
        r = new Random();
    }

    static RandomBitGenerator factory(){
        return new DefaultGenerator();
    }

    @Override
    public byte next() {
        return (byte) r.nextInt(2);
    }

    @Override
    public byte[] randomArray(int length){

        byte[] output = new byte[length];
        for (int i = 0; i < length; i++) {
            output[i] = next();
        }

        return output;
    }
}
