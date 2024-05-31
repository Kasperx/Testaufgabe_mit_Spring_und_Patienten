package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
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
        /*
        10 ^ x-1 = minimum of wanted number with count of digits
        (10 ^ x) -1 = maximum of wanted number with count of digits
         */
        return (int)(Math.pow(10, length-1)) + new Random().nextFloat() * ((int)(Math.pow(10, length)-1) - (int)(Math.pow(10, length-1)));
    }

    public static String localdatetimeToString(LocalDateTime ldt){
        return ldt.format(DateTimeFormatter.ofPattern(ProgramService.DATE_FORMAT));
    }

    public static String localdateToString(LocalDate ld){
        return ld.format(ProgramService.formatter);
    }

    public static LocalDateTime instantToLocaldatetime(Instant instant){
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
