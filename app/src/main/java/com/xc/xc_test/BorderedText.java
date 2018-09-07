/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.xc.xc_test;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.Vector;

/**
 * A class that encapsulates the tedious bits of rendering legible, bordered text onto a canvas.
 */

public class BorderedText {
    private final Paint interiorPaint;
    private final Paint exteriorPaint;
    private final Paint exPaint,contentBgPaint;
    private final TextPaint contextPaint;

    private final float textSize;

    /**
     * Creates a left-aligned bordered text object with a white interior, and a black exterior with
     * the specified text size.
     *
     * @param textSize text size in pixels
     */
    public BorderedText(final float textSize) {
        this(Color.parseColor("#33b2ce"), Color.BLACK, textSize);
    }

    /**
     * Create a bordered text object with the specified interior and exterior colors, text size and
     * alignment.
     *
     * @param interiorColor the interior text color
     * @param exteriorColor the exterior text color
     * @param textSize      text size in pixels
     */
    public BorderedText(final int interiorColor, final int exteriorColor, final float textSize) {
        interiorPaint = new Paint();
        interiorPaint.setTextSize(textSize);
        interiorPaint.setColor(interiorColor);
        interiorPaint.setStyle(Style.FILL);
        interiorPaint.setAntiAlias(false);
        interiorPaint.setAlpha(255);

        exteriorPaint = new Paint();
        exteriorPaint.setTextSize(textSize);
        exteriorPaint.setColor(exteriorColor);
        exteriorPaint.setStyle(Style.FILL_AND_STROKE);
        exteriorPaint.setStrokeWidth(textSize / 8);
        exteriorPaint.setAntiAlias(false);
        exteriorPaint.setAlpha(255);


        exPaint = new Paint();
        exPaint.setTextSize(textSize);
        exPaint.setColor(Color.WHITE);
        exPaint.setStyle(Style.FILL);
        exPaint.setAntiAlias(true);
        exPaint.setAlpha(255);


        contentBgPaint = new Paint();
        contentBgPaint.setTextSize(textSize);
        contentBgPaint.setColor(Color.parseColor("#33b2ce"));
        contentBgPaint.setStyle(Style.FILL);
        contentBgPaint.setAntiAlias(true);
        contentBgPaint.setAlpha(255);


        contextPaint = new TextPaint();
        contextPaint.setTextSize(textSize*4/5);
        contextPaint.setColor(Color.WHITE);
        contextPaint.setAntiAlias(true);



        this.textSize = textSize;
    }

    public void setTypeface(Typeface typeface) {
        interiorPaint.setTypeface(typeface);
        exteriorPaint.setTypeface(typeface);
        exPaint.setTypeface(typeface);
    }

    public void drawText(RectF rectF ,final Canvas canvas, final float posX, final float posY, final String text) {
        canvas.drawRoundRect(rectF,20, 20,contentBgPaint);
        StaticLayout layout = new StaticLayout(text, contextPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        // 这里的参数300，表示字符串的长度，当满300时，就会换行，也可以使用“\r\n”来实现换行
        canvas.save();
        canvas.translate(posX+10,posY);//从100，100开始画
        layout.draw(canvas);
        canvas.restore();//别忘了restore
    }

    public void drawText2(Path path,RectF rectF, final Canvas canvas, final float posX, final float posY, final String text) {
        RectF left = new RectF(rectF.left,rectF.top,rectF.right,rectF.bottom);
        canvas.drawRoundRect(left,20, 20,exPaint);
        canvas.drawText(text, posX, posY, interiorPaint);
    }

    public void drawLines(Canvas canvas, final float posX, final float posY, Vector<String> lines) {
        int lineNum = 0;
        for (final String line : lines) {
//            drawText(canvas, posX, posY - getTextSize() * (lines.size() - lineNum - 1), line);
            ++lineNum;
        }
    }


    public void setInteriorColor(final int color) {
        interiorPaint.setColor(color);
    }

    public void setExteriorColor(final int color) {
        exteriorPaint.setColor(color);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setAlpha(final int alpha) {
        interiorPaint.setAlpha(alpha);
        exteriorPaint.setAlpha(alpha);
    }

    public void getTextBounds(
            final String line, final int index, final int count, final Rect lineBounds) {
        interiorPaint.getTextBounds(line, index, count, lineBounds);
    }

    public void setTextAlign(final Align align) {
        interiorPaint.setTextAlign(align);
        exteriorPaint.setTextAlign(align);
    }




}
