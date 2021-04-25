package com.example.puzzle;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class PuzzleView extends RelativeLayout {
    private TextView[] tvs;
    private RelativeLayout.LayoutParams[] params;
    private int[] colors;

    private int labelHeight;
    private int startY; // start y coordinate of TextView being moved
    private int startTouchY; // start y coordinate of current touch

    public PuzzleView(Activity activity, int width, int height, int numberOfPieces){
        super(activity);
        buildGuiByCode(activity, width, height, numberOfPieces);
    }

    public void buildGuiByCode(Activity activity, int width, int height, int numberOfPieces){
        tvs = new TextView[numberOfPieces];
        colors = new int[tvs.length];
        params = new RelativeLayout.LayoutParams[tvs.length];
        Random random = new Random();
        labelHeight = height / numberOfPieces;
        for(int i = 0; i < tvs.length; i++){
            tvs[i] = new TextView(activity);
            tvs[i].setGravity(Gravity.CENTER);
            colors[i] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            tvs[i].setBackgroundColor(colors[i]);
            params[i] = new RelativeLayout.LayoutParams(width, labelHeight);
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;
            addView(tvs[i],params[i]);
        }
    }

    public void fillGui(String[] scrambledText){
        for(int i = 0; i < tvs.length; i++)
            tvs[i].setText(scrambledText[i]);
    }

    // Returns the index of tv within the array tvs
    public int indexOfTextView(View tv){
        if(!(tv instanceof TextView))
            return -1;
        for(int i = 0; i < tvs.length; i++){
            if(tv == tvs[i])
                return i;
        }
        return -1;
    }

    public void updateStartPositions(int index, int y){
        startY = params[index].topMargin;
        startTouchY = y;
    }

    // moves the TextView at index index
    public void moveTextViewVertically(int index, int y){
        params[index].topMargin = startY + y - startTouchY;
        tvs[index].setLayoutParams(params[index]);
    }

    public void enableListener(View.OnTouchListener listener){
        for(int i = 0; i < tvs.length; i++)
            tvs[i].setOnTouchListener(listener);
    }
}
