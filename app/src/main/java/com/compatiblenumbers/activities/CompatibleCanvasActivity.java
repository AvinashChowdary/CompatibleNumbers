package com.compatiblenumbers.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.compatiblenumbers.R;
import com.compatiblenumbers.helper.ui.CirclesDrawingView;
import com.compatiblenumbers.model.TextBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avinash on 5/10/17.
 */

public class CompatibleCanvasActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = CompatibleCanvasActivity.class.getSimpleName();

    private Button firstBtn;

    private Button secondBtn;

    private Button thirdBtn;

    private Button fourthBtn;

    private EditText firstInput;

    private EditText secondInput;

    private Button fifthBtn;

    private CirclesDrawingView canvasView;

    private int height;

    private int width;

    private int canvasWidth;

    private float magicNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible_canvas);

        firstBtn = (Button) findViewById(R.id.first_value);
        secondBtn = (Button) findViewById(R.id.second_value);
        thirdBtn = (Button) findViewById(R.id.third_value);
        fourthBtn = (Button) findViewById(R.id.fourth_value);
        fifthBtn = (Button) findViewById(R.id.fifth_value);

        firstInput = (EditText) findViewById(R.id.number_one);
        secondInput = (EditText) findViewById(R.id.number_two);

        Button findCompatible = (Button) findViewById(R.id.show_me);
        Button refresh = (Button) findViewById(R.id.refresh);
        Button letMeTry = (Button) findViewById(R.id.me_try);

        findCompatible.setOnClickListener(this);
        refresh.setOnClickListener(this);
        letMeTry.setOnClickListener(this);

        canvasView = (CirclesDrawingView) findViewById(R.id.canvas);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = Math.round(metrics.heightPixels);
        width = Math.round(metrics.widthPixels);

//        float dp20Pixel = convertDpToPixel((float) 20.0, this);
//        canvasWidth = Math.round(width - 2*dp20Pixel);
        canvasWidth = width;
        Log.e(TAG, canvasWidth+"");

        magicNumber = ((float)canvasWidth)/100;
        Log.e(TAG, magicNumber+"");

        int margin = width - canvasWidth;
        int marginDp = Math.round(convertPixelsToDp((float)margin, this));
        LinearLayout.LayoutParams canvasParams = new LinearLayout.LayoutParams(canvasWidth, canvasWidth);
        canvasView.setLayoutParams(canvasParams);
        canvasView.setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_try:
                break;
            case R.id.show_me:
                int firstNum = Integer.parseInt(firstInput.getText().toString());
                int secondNum = Integer.parseInt(secondInput.getText().toString());

                int firstRemaining = firstNum%10;
                int firstCompatible = firstNum - firstRemaining;
                int secondRemaining = secondNum%10;
                int secondCompatible = secondNum - secondRemaining;

                int cp = firstCompatible*secondCompatible;
                int frSC = firstRemaining*secondCompatible;
                int srFC = secondRemaining*firstCompatible;
                int frSR = firstRemaining*secondRemaining;
                findCompatible(firstCompatible, firstRemaining, secondCompatible, secondRemaining, cp, frSC, srFC, frSR);
                break;
            case R.id.refresh:
                refresh();
                break;
            default:
        }
    }

    private void findCompatible(int firstCompatible, int firstRemaining, int secondCompatible, int secondRemaining, int cp, int frSC, int srFC, int frSR) {


        firstBtn.setText(firstCompatible+ " X "+ secondCompatible + " = " + cp);

        secondBtn.setText(firstRemaining+ " X " + secondCompatible + " = " + frSC);

        thirdBtn.setText(secondRemaining+ " X " + firstCompatible + " = " + srFC);

        fourthBtn.setText(firstRemaining+ " X " + secondRemaining + " = " + frSR);

        fifthBtn.setText(cp +" + "+ frSC +" + "+ srFC +" + "+ frSR +" = "+ (cp+frSC+srFC+frSR));

        int grand = (firstCompatible-1) + secondCompatible*10;
        int selectedIndex = grand/10;


    }

    private void refresh() {
        firstInput.setText("");
        secondInput.setText("");
        firstBtn.setText("");
        secondBtn.setText("");
        thirdBtn.setText("");
        fourthBtn.setText("");
        fifthBtn.setText("");
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                callDrawRect(event);
                break;
            default:
        }
        return false;
    }

    private void callDrawRect(MotionEvent event) {


        int roundedOffX = Math.round((event.getX()/magicNumber));
        int roundedOffY = Math.round((event.getY()/magicNumber));

        Log.e(TAG, roundedOffX+": Rounded X");
        Log.e(TAG, roundedOffY+": Rounded Y");

        drawRectsOnCanvas(roundedOffX, roundedOffY);
    }

    private void drawRectsOnCanvas(int roundedOffX, int roundedOffY) {

        List<Rect> rectList = new ArrayList<>(CirclesDrawingView.RECT_LIMIT);
        List<TextBlock> textBlocks = new ArrayList<>(CirclesDrawingView.RECT_LIMIT);

        int pointX = Math.round(roundedOffX * magicNumber);
        int pointY = Math.round(roundedOffY * magicNumber);

        Rect rect = new Rect();
        rect.set(0,0,pointX, pointY);
        rectList.add(rect);

        rect = new Rect();
        rect.set(pointX, pointY, canvasWidth, canvasWidth);
        rectList.add(rect);

        rect = new Rect();
        rect.set(0, pointY, pointX, canvasWidth);
        rectList.add(rect);

        rect = new Rect();
        rect.set(pointX, 0, canvasWidth, pointY);
        rectList.add(rect);

        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(255, 255, 255));
        myPaint.setStrokeWidth(10);
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTextSize(20);

        TextBlock textBlock = new TextBlock();
        textBlock.setPointX(pointX/2);
        textBlock.setPointY(pointY/2);
        textBlock.setPaint(myPaint);
        textBlock.setText(roundedOffX+ " X "+ roundedOffY + " = " + roundedOffX*roundedOffY);
        textBlocks.add(textBlock);

        // in the similar way add textblock objects
        // for other blocks also
        // add the values to text buttons too.

        canvasView.setRectangles(rectList, textBlocks);

    }
}
