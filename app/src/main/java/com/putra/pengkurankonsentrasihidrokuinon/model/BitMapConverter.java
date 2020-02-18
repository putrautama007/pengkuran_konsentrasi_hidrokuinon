package com.putra.pengkurankonsentrasihidrokuinon.model;

import android.graphics.Bitmap;
import android.graphics.Color;


//class yang digunakan untuk mendapatkan nilai RGB dari gambar yang sudah di crop
public class BitMapConverter {
    private Bitmap bitmap;

    public BitMapConverter(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int[] getAverageColorRGB() {
        final int width = bitmap.getWidth() / 2;
        final int height = bitmap.getHeight() / 2;
        int size = width * height;
        int pixelColor;
        int r, g, b;
        r = g = b = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = bitmap.getPixel(x, y);
                if (pixelColor == 0) {
                    size--;
                    continue;
                }
                r += Color.red(pixelColor);
                g += Color.green(pixelColor);
                b += Color.blue(pixelColor);
            }
        }
        r /= size;
        g /= size;
        b /= size;
        return new int[]{
                r, g, b,
        };
    }
}
