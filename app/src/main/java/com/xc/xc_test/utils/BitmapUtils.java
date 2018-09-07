package com.xc.xc_test.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yidouco.ltdyellow on 2018/7/5.
 */

public class BitmapUtils {

    public static File saveBitmapFile(final Bitmap bitmap) {
        String fileName;
        final String root =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        File appDir = new File(root);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = System.currentTimeMillis() / 1000000 + "tiantian.jpg";
        final File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static int sum = 0;

    public static String getHash(Bitmap bitmap){
        Bitmap temp = Bitmap.createScaledBitmap(bitmap, 8, 8, false);
        int[] grayValues = reduceColor(temp);
        int average = sum/grayValues.length;
        String reslut = computeBits(grayValues, average);
        return reslut;
    }

    private static String computeBits(int[] grayValues, int average) {
        char[] result = new char[grayValues.length];
        for (int i = 0; i < grayValues.length; i++)
        {
            if (grayValues[i] < average)
                result[i] = '0';
            else
                result[i] = '1';
        }
        return new String(result);
    }

    private static int[] reduceColor(Bitmap bitmap) {
        sum = 0;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i("th", "scaled bitmap's width*heith:" + width + "*" + height);

        int[] grayValues = new int[width * height];
        int[] pix = new int[width * height];
        bitmap.getPixels(pix, 0, width, 0, 0, width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                int x = j * width + i;
                int r = (pix[x] >> 16) & 0xff;
                int g = (pix[x] >> 8) & 0xff;
                int b = pix[x] & 0xff;
                int grayValue = (r * 30 + g * 59 + b * 11) / 100;
                sum+=grayValue;
                grayValues[x] = grayValue;
            }
        return grayValues;
    }

    public static void saveToFile(Bitmap ...bitmaps ){
        int i=0;
        for (Bitmap bitmap : bitmaps) {
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path+"/"+(i++)+".jpg");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos );
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
