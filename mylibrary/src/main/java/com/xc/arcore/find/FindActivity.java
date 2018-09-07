package com.xc.arcore.find;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.xc.arcore.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FindActivity extends Activity {

    private TextView tvFind;
    private ImageView ivFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        byte[] aByte = (byte[]) getIntent().getSerializableExtra("byte");
//        byte[] aByte = (byte[]) SPUtil.get(FindActivity.this, "byte", null);
        tvFind = findViewById(R.id.find_tv);
        ivFind = findViewById(R.id.find_iv);
        tvFind.setText(aByte.length+" *****");
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        if(aByte !=null) {
            try {
                Bitmap bitmap = saveBitmap(aByte);
                ivFind.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Bitmap picFromBytes = getPicFromBytes(aByte, o);

        }
//        Bitmap bitmap = BitmapFactory.decodeFile(Common.PATH  + "aaa.jpg", null);

    }
    public static Bitmap saveBitmap(byte[] unityneed) throws Exception {
        Bitmap bitmap = BitmapFactory.decodeByteArray(unityneed, 0, unityneed.length);
//        File file =  saveBitmapFile(bitmap);
        return bitmap ;
    }
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


    /**将字节数组转换为ImageView可调用的Bitmap对象
     * @param
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

//    ///接收跳转回来的参数
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
////这个 requestCode 是在唤起 AppB 的时候传进
//// startActivityForResult 方法里的
////用于判断是哪个功能唤起的 AppB
//            if (resultCode == RESULT_OK) {   //在这里接收
//                String result = data.getStringExtra("data");
//                UnityPlayer.UnitySendMessage("NativePlatform", "OnBBHLoginReturn", result);
//            }
//        }
//    }
//
//    ///跳转到 AppB ，并将自定义的参数传过去
//    public void JumpForLogin(final String mKey) {
//        ComponentName componetName = new ComponentName(
//                "com.test.demo",
//                "com.test.demo.TestActivit");
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
//        Bundle bundle = new Bundle();
//        bundle.putString("keyNumber", mKey);
//        intent.putExtras(bundle);
//        startActivityForResult(intent, 1);  //这个 1 就是上面的 requestCode （请求码），需要大于0
//    }


}
