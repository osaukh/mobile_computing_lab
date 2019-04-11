package com.ahinea.handwrittendigits.views;

import android.graphics.Canvas;
//The Color class provides methods for creating, converting and manipulating colors.
import android.graphics.Color;
//The Paint class holds the style and color information about how to draw geometries, text and bitmaps.
import android.graphics.Paint;

import com.ahinea.handwrittendigits.views.DrawModel;


public class DrawRenderer {

    public static void renderModel(Canvas canvas, DrawModel model, Paint paint,
                                   int startLineIndex) {

        paint.setAntiAlias(true);

        int lineSize = model.getLineSize();
        for (int i = startLineIndex; i < lineSize; ++i) {
            DrawModel.Line line = model.getLine(i);
            paint.setColor(Color.BLACK);
            int elemSize = line.getElemSize();
            if (elemSize < 1) {
                continue;
            }
            DrawModel.LineElem elem = line.getElem(0);
            float lastX = elem.x;
            float lastY = elem.y;

            for (int j = 0; j < elemSize; ++j) {
                elem = line.getElem(j);
                float x = elem.x;
                float y = elem.y;
                canvas.drawLine(lastX, lastY, x, y, paint);
                lastX = x;
                lastY = y;
            }
        }
    }
}