package com.example.demo.service;

import java.util.Random;

public class Tools {
    public static int getRandomNumberWithLength(int length){
        return new Random().nextInt(
                ((int)(Math.pow(10, length))-1
                        - (int)(Math.pow(10, length-1)))
                        + (int)(Math.pow(10, length-1))
        );
    }

    public static int getRandomNumberWithMaxLength(int length){
        return new Random().nextInt(
                (length - 1) + 1
        );
    }

    public static float getRandomFloatWithLength(int length){
        // float random = min + r.nextFloat() * (max - min);
        return (int)(Math.pow(10, length-1)) + new Random().nextFloat() * ((int)(Math.pow(10, length)-1) - (int)(Math.pow(10, length-1)));
    }
    /*
    static int getRandomNumberWithLength(int length){
        int number = 0;
        switch (length){
            case 1:
                number = 1 + new Random().nextInt(9);
                break;
            case 2:
                number = 10 + new Random().nextInt(99);
                break;
            case 3:
                number = 100 + new Random().nextInt(999);
                break;
            case 4:
                number = 1000 + new Random().nextInt(9999);
                break;
            case 5:
                number = 10000 + new Random().nextInt(99999);
                break;
            case 6:
                number = 100000 + new Random().nextInt(999999);
                break;
            case 7:
                number = 1000000 + new Random().nextInt(9999999);
                break;
            case 8:
                number = 10000000 + new Random().nextInt(99999999);
                break;
            case 9:
                number = 100000000 + new Random().nextInt(999999999);
                break;
        }
        return number;
    }
     */
}
