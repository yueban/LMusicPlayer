package com.bigfat.lmusicplayer.util;

import android.graphics.Bitmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yueban on 15/4/12.
 */
public class Utils {
    /**
     * 从一个Bitmap对象中获取颜色最多的值
     *
     * @param bitmap 获取的Bitmap
     * @return Bitmap对象中颜色最多的值
     */
    private int getMostColorFromBitmap(Bitmap bitmap) {
        int flag = 10;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int widthPeriod = width / flag + 1;
        int heightPeriod = height / flag + 1;

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        int mostColorCount = 0;

        for (int i = 0; i < flag; i++) {
            int widthIndex = widthPeriod * i;
            if (widthIndex > width - 1) {
                break;
            }
            for (int j = 0; j < flag; j++) {
                int heightIndex = heightPeriod * i;
                if (heightIndex > height - 1) {
                    break;
                }
                int pixel = bitmap.getPixel(widthIndex, heightIndex);
                if (map.containsKey(pixel)) {
                    int colorCount = map.get(pixel) + 1;
                    mostColorCount = colorCount > mostColorCount ? colorCount : mostColorCount;
                    map.put(pixel, colorCount);
                } else {
                    map.put(pixel, 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (mostColorCount == entry.getValue()) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
