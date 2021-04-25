package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static int STATUS_BAR_HEIGHT = 24; // in dp
    public static int ACTION_BAR_HEIGHT = 56; // in dp
    private PuzzleView puzzleView;
    private Puzzle puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        puzzle = new Puzzle();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        int screenHeight = size.y;
        int puzzleWidth = size.x;

        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        float pixelDensity = metrics.density;

        int actionBarHeight = (int)(pixelDensity * ACTION_BAR_HEIGHT);
        TypedValue tv = new TypedValue();
        if(getTheme().resolveAttribute(android.R.attr.actionBarSize,tv,true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, metrics);

        int statusBarHeight = (int)(pixelDensity*STATUS_BAR_HEIGHT);
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId != 0) // found resource for status bar height
            statusBarHeight = res.getDimensionPixelOffset(resourceId);

        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;
        puzzleView = new PuzzleView(this, puzzleWidth, puzzleHeight, puzzle.getNumberOfParts());
        String[] scrambled = puzzle.scramble();
        puzzleView.fillGui(scrambled);

        setContentView(puzzleView);
    }
}