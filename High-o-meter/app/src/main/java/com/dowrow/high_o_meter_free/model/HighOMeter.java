package com.dowrow.high_o_meter_free.model;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.logging.Level;

import static java.lang.Math.*;

/**
 * Created by Diego on 21/06/2015.
 */
public class HighOMeter {
    /**
     * Measure rednes of eyes picture
     * Returns value between 0 and 100
     * @param img
     * @return
     */
    public static double measureRedness (Bitmap img) {
        long redBucket = 0;
        long greenBucket = 0;
        long blueBucket = 0;
        int pixels[] = new int[img.getWidth() * img.getHeight()];
        long pixelCount =  pixels.length;

        img.getPixels(pixels,0,img.getWidth(),0,0,img.getWidth(), img.getHeight());
        for (int pixel: pixels) {
            redBucket += Color.red(pixel);
            greenBucket += Color.green(pixel);
            blueBucket += Color.blue(pixel);
        }

        redBucket /= pixelCount;
        greenBucket /= pixelCount;
        blueBucket /= pixelCount;


        double distance = Math.sqrt(Math.pow(255 - redBucket, 2) + Math.pow(0 - greenBucket, 2) + Math.pow(0 - blueBucket, 2));
        return  (1 - (distance / 441.67295593)) * 100;
    }

    public enum level { BUZZED, HIGH, STONED, BLAZED, GONE };

    /**
     * Returns number between 5-10
     * @param percent
     * @return
     */
    public static level percentToScore(double percent) {
        if (percent < 44) {
            return level.BUZZED;
        }
        if (percent < 40) {
            return level.HIGH;
        }
        if (percent < 50) {
            return  level.STONED;
        }
        if (percent < 70) {
            return level.BLAZED;
        }
        return level.GONE;
    }
}
