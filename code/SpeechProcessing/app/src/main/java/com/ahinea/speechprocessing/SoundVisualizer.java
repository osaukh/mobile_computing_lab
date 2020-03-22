package com.ahinea.speechprocessing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SoundVisualizer extends View implements AudioRecorderDataReceiveListener{

    private static final int COORDINATES_PER_LINE = 4;
    private static final int VISUAL_SAMPLING_RATE = 16;
    private static final float MAX_LINE_HEIGHT_IN_PERCENTAGE = 0.95f;

    private RingArrayList<Float> samplesToVisualize;
    private float[] linesCoordinates;

    private Rect rect = new Rect();
    private Paint forePaint = new Paint();

    public SoundVisualizer(Context context) {
        super(context);
    }

    public SoundVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SoundVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (samplesToVisualize != null && samplesToVisualize.size() > 0) {
            rect.set(0, 0, getWidth(), getHeight());
            canvas.drawLines(linesCoordinates, 0, samplesToVisualize.size() * COORDINATES_PER_LINE, forePaint);
        }
    }

    public void onDataReceive(final float[] data, final int length) {
        // create internal data structure or resize if needed
        if (samplesToVisualize == null && getWidth() > 0) {
            samplesToVisualize = new RingArrayList<>(getWidth());
            linesCoordinates = new float[samplesToVisualize.capacity() * COORDINATES_PER_LINE];
        } else if (samplesToVisualize == null) {
            // TODO getWidth == 0
            samplesToVisualize = new RingArrayList<>(length);
            linesCoordinates = new float[samplesToVisualize.capacity() * COORDINATES_PER_LINE];
        } else if (samplesToVisualize.size() != getWidth()) {
            // TODO inconsistent samples array
            // resize
        }

        for (int i = 0; i < length; i += VISUAL_SAMPLING_RATE) {
            // Get max sample
            float max = data[i];
            for (int j = i; j < (i + VISUAL_SAMPLING_RATE); ++j) {
                if (j < length && max < data[j]) {
                    max = data[j];
                }
            }
            samplesToVisualize.add(max);
        }

        convertSamplesToLinesCoordinates();
        invalidate();
    }

    private void convertSamplesToLinesCoordinates() {
        for (int i = 0; i < samplesToVisualize.size(); ++i) {
            /* Draw a series of lines. Each line is taken from 4 consecutive values in the pts array. Thus
             * to draw 1 line, the array must contain at least 4 values. This is logically the same as
             * drawing the array as follows: drawLine(pts[0], pts[1], pts[2], pts[3]) followed by
             * drawLine(pts[4], pts[5], pts[6], pts[7]) and so on.
             *
             * @param pts Array of points to draw [x0 y0 x1 y1 x2 y2 ...]
             *
             * * (x0, y0)
             * |
             * | lineLength
             * |
             * * (x1, y1) ...x0 = x1
             */

            /* lineLength */
            float lineLength = rect.height() * MAX_LINE_HEIGHT_IN_PERCENTAGE * Math.abs(samplesToVisualize.get(i));
            /* x0 */
            linesCoordinates[i * 4] = rect.width() * ((float) i / (samplesToVisualize.size())); // TODO set position
            /* y0 */
            linesCoordinates[i * 4 + 1] = (rect.height() / 2.0f) - (lineLength / 2.0f);
            /* x1 = x0 */
            linesCoordinates[i * 4 + 2] = linesCoordinates[i * 4];
            /* y1 = y0 - lineLength */
            linesCoordinates[i * 4 + 3] = linesCoordinates[i * 4 + 1] + lineLength;
        }
    }
}
