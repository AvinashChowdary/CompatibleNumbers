package com.compatiblenumbers.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.compatiblenumbers.R;
import com.compatiblenumbers.adapters.GridAdapter;

/**
 * Created by avinash on 5/4/17.
 */

public class CompatibleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = CompatibleActivity.class.getSimpleName();

    private GridAdapter adapter;

    private GridView gridView;

    private Button firstBtn;

    private Button secondBtn;

    private Button thirdBtn;

    private Button fourthBtn;

    private EditText firstInput;

    private EditText secondInput;

    private Button fifthBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible);

        gridView = (GridView) findViewById(R.id.grid_view);

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

        adapter = new GridAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i=0; i<99; i++) {
                    gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#31156d"));
                }

                position++;

                int numCols = position / 10;
                int numRows = position % 10;

                numCols++;

                Log.e(TAG, numRows + "");
                Log.e(TAG, numCols + "");

                for (int i = 0, k = 0; i < 10; i++) {

                    if (i < numRows && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 10, k = 1; i < 20; i++) {

                    if (i < numRows + 10 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }


                for (int i = 20, k = 2; i < 30; i++) {

                    if (i < numRows + 20 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 30, k = 3; i < 40; i++) {

                    if (i < numRows + 30 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 40, k = 4; i < 50; i++) {

                    if (i < numRows + 40 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 50, k = 5; i < 60; i++) {

                    if (i < numRows + 50 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 60, k = 6; i < 70; i++) {

                    if (i < numRows + 60 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 70, k = 7; i < 80; i++) {

                    if (i < numRows + 70 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }


                for (int i = 80, k = 8; i < 90; i++) {

                    if (i < numRows + 80 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }

                for (int i = 90, k = 9; i < 99; i++) {

                    if (i < numRows + 90 && k < numCols) {
                        gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#f44336"));
                    }


                }



            }
        });
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

        gridView.performItemClick(gridView.findViewWithTag(gridView.getAdapter().getItem(selectedIndex)), selectedIndex, gridView.getAdapter().getItemId(selectedIndex));

    }

    private void refresh() {
        firstInput.setText("");
        secondInput.setText("");
        firstBtn.setText("");
        secondBtn.setText("");
        thirdBtn.setText("");
        fourthBtn.setText("");
        fifthBtn.setText("");
        adapter.notifyDataSetChanged();
    }
}
