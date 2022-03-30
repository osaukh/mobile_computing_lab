package com.ahinea.handwrittendigits;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ahinea.handwrittendigits.models.Classification;
import com.ahinea.handwrittendigits.models.Classifier;
import com.ahinea.handwrittendigits.models.TensorFlowLiteClassifier;
import com.ahinea.handwrittendigits.views.DrawModel;
import com.ahinea.handwrittendigits.views.DrawView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final int PIXEL_WIDTH = 28;

    // ui elements
    private Button clearBtn, classBtn;
    private TextView resText;
    private List<Classifier> mClassifiers = new ArrayList<>();

    // views
    private DrawModel drawModel;
    private DrawView drawView;
    private PointF mTmpPiont = new PointF();

    private float mLastX;
    private float mLastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get drawing view from XML (where the finger writes the number)
        drawView = (DrawView) findViewById(R.id.draw);
        // get the model object
        drawModel = new DrawModel(PIXEL_WIDTH, PIXEL_WIDTH);

        // init the view with the model object
        drawView.setModel(drawModel);
        drawView.setOnTouchListener(this);

        // clear button
        // clear the drawing when the user taps
        clearBtn = (Button) findViewById(R.id.btn_clear);
        clearBtn.setOnClickListener(this);

        // when the button is tapped, this performs classification on the drawn image
        classBtn = (Button) findViewById(R.id.btn_class);
        classBtn.setOnClickListener(this);

        // the text to show the output of the classification
        resText = (TextView) findViewById(R.id.tfRes);

        // TensorFlow: load up our saved model to perform inference from local storage
        loadModel();
    }

    @Override
    protected void onResume() {
        drawView.onResume();
        super.onResume();
    }

    @Override
    // OnPause() is called when the user receives an event like a call or a text message,
    protected void onPause() {
        drawView.onPause();
        super.onPause();
    }
    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClassifiers.add(
                            TensorFlowLiteClassifier.create(getAssets(), "TensorFlowLite",
                                    "converted_model.tflite", "labels.txt"));
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing classifiers!", e);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_clear) {
            drawModel.clear();
            drawView.reset();
            drawView.invalidate();
            resText.setText("");
        } else if (view.getId() == R.id.btn_class) {
            float pixels[] = drawView.getPixelData();

            String text = "";
            for (Classifier classifier : mClassifiers) {
                final Classification res = classifier.recognize(pixels);
                if (res.getLabel() == null) {
                    text += classifier.name() + ": ?\n";
                } else {
                    text += String.format("%s: %s, %f\n", classifier.name(), res.getLabel(),
                            res.getConfidence());
                }
            }
            resText.setText(text);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;

        if (action == MotionEvent.ACTION_DOWN) {
            processTouchDown(event);
            return true;
        } else if (action == MotionEvent.ACTION_MOVE) {
            processTouchMove(event);
            return true;
        } else if (action == MotionEvent.ACTION_UP) {
            processTouchUp();
            return true;
        }
        return false;
    }

    private void processTouchDown(MotionEvent event) {
        mLastX = event.getX();
        mLastY = event.getY();
        drawView.calcPos(mLastX, mLastY, mTmpPiont);
        float lastConvX = mTmpPiont.x;
        float lastConvY = mTmpPiont.y;
        drawModel.startLine(lastConvX, lastConvY);
    }

    private void processTouchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        drawView.calcPos(x, y, mTmpPiont);
        float newConvX = mTmpPiont.x;
        float newConvY = mTmpPiont.y;
        drawModel.addLineElem(newConvX, newConvY);

        mLastX = x;
        mLastY = y;
        drawView.invalidate();
    }

    private void processTouchUp() {
        drawModel.endLine();
    }
}
