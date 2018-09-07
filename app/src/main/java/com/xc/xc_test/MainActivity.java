package com.xc.xc_test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xc.xc_test.utils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends Activity {
    private RelativeLayout layout;
    RectView rectView;
    RectView rectView2;
    private ImageView iv;
    public final static String PATH = Environment.getExternalStorageDirectory() + "/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout =findViewById(R.id.layout);
        iv = findViewById(R.id.iv);

        try {
            Bitmap bitmap = decodeFile(PATH + "kobe.jpg");
            byte[] bytesByBitmap = getBytesByBitmap2(bitmap);
            Log.e("bytesByBitmap",bytesByBitmap.length+"    ");
            byte[] bytes = BitmapToByte(bitmap);
            Log.e("bytesByBitmap2",bytes.length+"    ");
            iv.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public synchronized static byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public synchronized static String BytesToString(byte[] imagedata){
        return Base64.encodeToString(imagedata, Base64.DEFAULT);
    }

    public synchronized static Bitmap DrawableToBitmap(Drawable drawable){
        return (((android.graphics.drawable.BitmapDrawable) drawable).getBitmap());
    }

    ///////////////////////////
    public byte[] getBytesByBitmap2(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }


    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }

    public static String saveBitmap(byte[] unityneed) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeByteArray(unityneed, 0, unityneed.length);
        File file = BitmapUtils.saveBitmapFile(bitmap);
        return file.getName();
    }

    /**
     * 根据 路径 得到 file 得到 bitmap
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Bitmap decodeFile(String filePath) throws IOException {
        Bitmap b = null;
        int IMAGE_MAX_SIZE = 600;

        File f = new File(filePath);
        if (f == null) {
            return null;
        }
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = new FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();

        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        fis = new FileInputStream(f);
        b = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();
        return b;
    }

}
