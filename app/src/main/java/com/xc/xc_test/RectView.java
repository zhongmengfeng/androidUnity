package com.xc.xc_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.xc.xc_test.utils.DensityUtil;

/**
 * Created by yidouco.ltdyellow on 2018/6/25.
 */

public class RectView extends View {
    private int colorId = 4;
    private int[] ints;
    Paint paint;
    private Paint exPaint, commentPaint, persionPaint, likePaint;
    private Context context;
    private String persion, like;
    String comment = "(BBBBOX)动力够用，操控够用空间还好，价....";

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);//设置实心

        //标题
        exPaint = new Paint();
        exPaint.setTextSize(DensityUtil.sp2px(context, 15));
        exPaint.setColor(Color.WHITE);
        exPaint.setStyle(Paint.Style.FILL);//设置实心
        exPaint.setAlpha(255);
        exPaint.setLetterSpacing(0.1f);
        exPaint.setTypeface(Typeface.DEFAULT_BOLD);

        //内容
        commentPaint = new Paint();
        commentPaint.setTextSize(DensityUtil.sp2px(context, 13));
        commentPaint.setColor(Color.WHITE);
        commentPaint.setStyle(Paint.Style.FILL);//设置实心
        commentPaint.setAlpha(255);
        commentPaint.setLetterSpacing(0.1f);

        //人
        persionPaint = new Paint();
        persionPaint.setTextSize(DensityUtil.sp2px(context, 12));
        persionPaint.setColor(Color.WHITE);
        persionPaint.setStyle(Paint.Style.FILL);//设置实心
        persionPaint.setAlpha(255);
        persionPaint.setLetterSpacing(0.1f);

        //点赞
        likePaint = new Paint();
        likePaint.setTextSize(DensityUtil.sp2px(context, 12));
        likePaint.setColor(Color.WHITE);
        likePaint.setStyle(Paint.Style.FILL);//设置实心
        likePaint.setAlpha(255);
        likePaint.setLetterSpacing(0.1f);

        //背景
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);

        float[] pos = {0.3f, 0.5f};
        if (colorId == 1) {
            ints = new int[]{Color.parseColor("#f46c4d"), Color.parseColor("#e69971")};
        } else if (colorId == 2) {
            ints = new int[]{Color.parseColor("#5fbe97"), Color.parseColor("#99d270")};
        } else if (colorId == 3) {
            ints = new int[]{Color.parseColor("#cbcf57"), Color.parseColor("#d9e779")};
        } else if (colorId == 4) {
            ints = new int[]{Color.parseColor("#03b7d8"), Color.parseColor("#04c7a9")};
        } else if (colorId == 5) {
            ints = new int[]{Color.parseColor("#000000"), Color.parseColor("#ffffff")};
        }
        // RectF(375.9709, 743.00745, 768.9709, 1005.00745)
        RectF rectF = new RectF(810, 480, 250, 200);
        Shader mShader = new LinearGradient(0, 0, getMeasuredHeight(), getMeasuredWidth(), ints,
                pos, Shader.TileMode.REPEAT);
        paint.setShader(mShader);// 用Shader中定义定义的颜色来话
        canvas.drawRoundRect(rectF, 20f, 20f, paint);

        canvas.drawText("甲壳虫汽车", rectF.right + 30, rectF.bottom + 55, exPaint);

        canvas.drawText(comment.substring(0, 17), rectF.right + 30, rectF.bottom + 50 * 2, commentPaint);

        canvas.drawText(comment.substring(17, 23) + "....", rectF.right + 30, rectF.bottom + 50 * 2 + 45, commentPaint);

        canvas.drawText("BIBJOX、CICRELDr等85人赞过", rectF.right + 30, rectF.bottom + 50 * 3 + 60, persionPaint);

        canvas.drawText("45条评论       已评论", rectF.right + 30, rectF.bottom + 50 * 4 + 55, likePaint);

    }

    public void drawText2(RectF rectF, final Canvas canvas, final float posX, final float posY, final String text) {
        float[] pos = {0.0f, 1.0f};
        if (colorId == 1) {
            ints = new int[]{Color.parseColor("#f46c4d"), Color.parseColor("#e69971")};
        } else if (colorId == 2) {
            ints = new int[]{Color.parseColor("#5fbe97"), Color.parseColor("#99d270")};
        } else if (colorId == 3) {
            ints = new int[]{Color.parseColor("#cbcf57"), Color.parseColor("#d9e779")};
        } else if (colorId == 4) {
            ints = new int[]{Color.parseColor("#03b7d8"), Color.parseColor("#04c7a9")};
        } else if (colorId == 5) {
            ints = new int[]{Color.parseColor("#000000"), Color.parseColor("#ffffff")};
        }
        RectF left = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        Shader mShader = new LinearGradient(left.left, left.top, left.right, left.bottom, ints, pos, Shader.TileMode.REPEAT);
        paint.setShader(mShader);// 用Shader中定义定义的颜色来话
        canvas.drawRoundRect(left, 20, 20, paint);
        canvas.drawText(text, posX, posY, exPaint);
    }

}
