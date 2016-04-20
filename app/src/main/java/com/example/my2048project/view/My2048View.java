package com.example.my2048project.view;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.apache.http.impl.io.ContentLengthInputStream;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * Created by 854638 on 2016/4/19.
 */
public class My2048View extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private static final int margin = 5;

    public static final int gameType = 0;

    private static int bgColor = Color.parseColor("#e2e2ea");

    private static int color0Cell = Color.parseColor("#858585");

    private static int color2Cell = Color.parseColor("#9492fd");

    private static int color4Cell = Color.parseColor("#7193ea");

    private static int color8Cell = Color.parseColor("#ff8623");

    private static int color16Cell = Color.parseColor("#02c562");

    private static int color32Cell = Color.parseColor("#ff8623");

    private static int color64Cell = Color.parseColor("#419ada");

    private static int color128Cell = Color.parseColor("#288dff");

    private static int color256Cell = Color.parseColor("#479eff");

    private static int color512Cell = Color.parseColor("#2586f4");

    private static int color1024Cell = Color.parseColor("#83ff80");

    private static int color2048Cell = Color.parseColor("#9090f7");

    private static int color4096Cell = Color.parseColor("#f0474e");

    private static int color9192Cell = Color.parseColor("#c0f1474e");

    private static int color18384Cell = Color.parseColor("#c1253b");

    private static int row = 4;

    private int rawWidth, rawHeight, width;

    private int[][] cellNum;

    private int leftZero;

    private int addScore;

    private GestureDetector mGestureDetector;

    private OnGamePlayListener onGamePlayListener;

    public void SetOnGamePlayListener(OnGamePlayListener onGamePlayListener) {
        this.onGamePlayListener = onGamePlayListener;
    }

    public My2048View(Context context, AttributeSet attrs) {
        super(context, attrs);
        cellNum = new int[row][row];
        for(int i = 0; i < row; i++)
            for(int j = 0; j < row; j++)
                cellNum[i][j] = 0;
        leftZero = row * row -2;
        mGestureDetector = new GestureDetector(this.getContext(), this);
        setOnTouchListener(this);
        initView();
    }

    public void restart() {
        for(int i = 0; i < row; i++)
            for(int j = 0; j < row; j++)
                cellNum[i][j] = 0;
        leftZero = row * row -2;
        initView();
        invalidate();
    }

    public void initView(){
        int x1 = (int)(Math.random()*row);
        int y1 = (int)(Math.random()*row);
        int x2 = (int)(Math.random()*row);
        int y2 = (int)(Math.random()*row);
        while(x1 == x2 && y1 == y2) {
            x2 = (int)(Math.random()*row);
            y2 = (int)(Math.random()*row);
        }
        cellNum[x1][y1] = 2;
        cellNum[x2][y2] = 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        rawWidth = (width - (row + 1)*margin)/row;
        rawHeight = rawWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(bgColor);
        mPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#000000"));
        mTextPaint.setTextSize(rawHeight * 0.5f);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        Log.i("hah", "getWidth: " + getWidth() + ", getHeight: " + getHeight());
        for(int i = 0; i < row; i++)
            for(int j = 0; j < row; j++)
            {
                switch (cellNum[i][j]) {
                    case 0:
                        mPaint.setColor(color0Cell);
                        break;
                    case 2:
                        mPaint.setColor(color2Cell);
                        break;
                    case 4:
                        mPaint.setColor(color4Cell);
                        break;
                    case 8:
                        mPaint.setColor(color8Cell);
                        break;
                    case 16:
                        mPaint.setColor(color16Cell);
                        break;
                    case 32:
                        mPaint.setColor(color32Cell);
                        break;
                    case 64:
                        mPaint.setColor(color64Cell);
                        break;
                    case 128:
                        mPaint.setColor(color128Cell);
                        break;
                    case 256:
                        mPaint.setColor(color256Cell);
                        break;
                    case 512:
                        mPaint.setColor(color512Cell);
                        break;
                    case 1024:
                        mPaint.setColor(color1024Cell);
                        break;
                    case 2048:
                        mPaint.setColor(color2048Cell);
                        break;
                    case 4096:
                        mPaint.setColor(color4096Cell);
                        break;
                    case 9192:
                        mPaint.setColor(color9192Cell);
                        break;
                    case 18384:
                        mPaint.setColor(color18384Cell);
                        break;
                }
                int rawLeft = j*rawWidth + (j + 1) * margin;
                int rawTop = i*rawHeight + (i + 1) * margin;
                int rawRight = (j+1)*rawWidth + (j + 1) * margin;
                int rawBottom = (i+1)*rawHeight + (i+1)*margin;
                Log.i("hah", "rawLeft: " + rawLeft + ", rawTop: " + rawTop + "rawRight: " + rawRight + ", rawBottom: " + rawBottom);
                canvas.drawRect(rawLeft, rawTop, rawRight, rawBottom, mPaint);
                float cellTextX = rawLeft + (rawWidth - mTextPaint.measureText(String.valueOf(cellNum[i][j])))/2f;
                float cellTextY = rawTop + rawHeight*5/8f;
                if(cellNum[i][j] != 0) {
                    canvas.drawText(String.valueOf(cellNum[i][j]), cellTextX, cellTextY, mTextPaint);
                }
            }
    }

    //每次滑动后生成新的元素
    public boolean generateNewItem() {
        int x = (int)(Math.random()*leftZero) +1;
        int count = 0;
        boolean flag = false;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < row; j++) {
                if(cellNum[i][j] == 0)
                    count ++;
                if(count == x) {
                    cellNum[i][j] = 2;
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
        }
        leftZero--;
        Log.i("num", "x : " + x + ", leftZero: " + leftZero);
        invalidate();
        return canContinue();
    }

    //向左滑动计算
    public void calLeftSwipe() {
        boolean hasChanged = false;
        addScore = 0;
        for(int i = 0; i < row; i++) {
            int temp  = 0;
            int index = 0;
            for(int j = 0; j < row; j ++) {
                int origin = cellNum[i][j];
                if(cellNum[i][j] != 0) {
                    if(temp == 0) {
                        temp = cellNum[i][j];
                        cellNum[i][j] = 0;
                        cellNum[i][index] = temp;
                    } else {
                        if(temp == cellNum[i][j]) {
                            addScore += temp;
                            cellNum[i][index] = temp *2;
                            cellNum[i][j] = 0;
                            temp = 0;
                            leftZero ++;
                            index ++;
                        } if(temp != cellNum[i][j]) {
                            temp = cellNum[i][j];
                            cellNum[i][j] = 0;
                            index ++;
                            cellNum[i][index] = temp;
                        }
                    }
                }
                if(cellNum[i][j] != origin)
                    hasChanged = true;
            }
        }
        if(hasChanged) {
            invalidate();
            if(onGamePlayListener != null)
                onGamePlayListener.onScore(addScore);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(generateNewItem()) {
                        invalidate();
                    } else {
                        if(onGamePlayListener != null)
                            onGamePlayListener.onFail();
                    }
                }
            }, 300);
        }
    }

    //向右滑动计算
    public void calRightSwipe() {
        boolean hasChanged = false;
        addScore = 0;
        for(int i = 0; i <row ; i++) {
            int temp  = 0;
            int index = row-1;
            for(int j = row -1; j >= 0; j --) {
                int origin = cellNum[i][j];
                if(cellNum[i][j] != 0) {
                    if(temp == 0) {
                        temp = cellNum[i][j];
                        cellNum[i][j] = 0;
                        cellNum[i][index] = temp;
                    } else {
                        if(temp == cellNum[i][j]) {
                            addScore += temp;
                            cellNum[i][index] = temp *2;
                            cellNum[i][j] = 0;
                            leftZero ++;
                            temp = 0;
                            index --;
                        } if(temp != cellNum[i][j]) {
                            temp = cellNum[i][j];
                            cellNum[i][j] = 0;
                            index --;
                            cellNum[i][index] = temp;
                        }
                    }
                }
                if(origin != cellNum[i][j])
                    hasChanged = true;
            }
        }
        if(hasChanged) {
            invalidate();
            if(onGamePlayListener != null)
                onGamePlayListener.onScore(addScore);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(generateNewItem()) {
                        invalidate();
                    } else {
                        if(onGamePlayListener != null)
                            onGamePlayListener.onFail();
                    }
                }
            }, 300);
        }
    }

    //向上滑动计算
    public void calUpSwipe() {
        boolean hasChanged = false;
        addScore = 0;
        for(int i = 0; i < row; i++) {
            int temp  = 0;
            int index = 0;
            for(int j = 0; j < row; j ++) {
                int origin = cellNum[j][i];
                if(cellNum[j][i] != 0) {
                    if(temp == 0) {
                        temp = cellNum[j][i];
                        cellNum[j][i] = 0;
                        cellNum[index][i] = temp;
                    } else {
                        if(temp == cellNum[j][i]) {
                            addScore += temp;
                            cellNum[index][i] = temp *2;
                            cellNum[j][i] = 0;
                            temp = 0;
                            leftZero ++;
                            index ++;
                        } if(temp != cellNum[j][i]) {
                            temp = cellNum[j][i];
                            cellNum[j][i] = 0;
                            index ++;
                            cellNum[index][i] = temp;
                        }
                    }
                }
                if(origin != cellNum[j][i])
                    hasChanged = true;
            }
        }
        if(hasChanged) {
            invalidate();
            if(onGamePlayListener != null)
                onGamePlayListener.onScore(addScore);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(generateNewItem()) {
                        invalidate();
                    } else {
                        if(onGamePlayListener != null)
                            onGamePlayListener.onFail();
                    }
                }
            }, 300);
        }
    }

    //向下滑动计算
    public void calDownSwipe() {
        boolean hasChanged = false;
        addScore = 0;
        for(int i = 0; i < row; i++) {
            int temp  = 0;
            int index = row -1;
            for(int j = row-1; j >= 0; j --) {
                int origin = cellNum[j][i];
                if(cellNum[j][i] != 0) {
                    if(temp == 0) {
                        temp = cellNum[j][i];
                        cellNum[j][i] = 0;
                        cellNum[index][i] = temp;
                    } else {
                        if(temp == cellNum[j][i]) {
                            addScore += temp;
                            cellNum[index][i] = temp *2;
                            cellNum[j][i] = 0;
                            temp = 0;
                            leftZero ++;
                            index --;
                        } if(temp != cellNum[j][i]) {
                            temp = cellNum[j][i];
                            cellNum[j][i] = 0;
                            index --;
                            cellNum[index][i] = temp;
                        }
                    }
                }
                if(origin != cellNum[j][i])
                    hasChanged = true;
            }
        }
        if(hasChanged) {
            invalidate();
            if(onGamePlayListener != null)
                onGamePlayListener.onScore(addScore);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(generateNewItem()) {
                        invalidate();
                    } else {
                        if(onGamePlayListener != null)
                            onGamePlayListener.onFail();
                    }
                }
            }, 300);
        }
    }

    //判断游戏是否结束
    public boolean canContinue() {
        for(int i = 0; i < row; i++)
            for(int j = 0; j < row; j++) {
                if(cellNum[i][j] == 0)
                    return true;
                else {
                    if(i != row -1 && j != row-1) {
                        if(cellNum[i][j] == cellNum[i+1][j] || cellNum[i][j] ==cellNum[i][j+1] || cellNum[i][j] == 0)
                            return true;
                    } else if(i == row - 1 && j != row -1) {
                        if(cellNum[i][j] == cellNum[i][j+1])
                            return true;
                    } else if(i != row -1 && j == row -1) {
                        if(cellNum[i][j] == cellNum[i+1][j])
                            return true;
                    } else {
                        return false;
                    }
                }
            }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i("heihei", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i("heihei", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i("heihei", "onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i("heihei", "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float x1 = e1.getX();
        float y1 = e1.getY();
        float x2 = e2.getX();
        float y2 = e2.getY();
        Log.i("heihei: ", "x1 : "+ x1 +" x2: " + x2 + " y1: "+ y1 + "y2: " + y2);
        if (x2 - x1 > 0 && x2 - x1 > 120) {
            Log.i("xixi: ", "RightSwipe!");
            calRightSwipe();
            return false;
        }
        if(x2 - x1 < 0 && x2 - x1 < -120) {
            Log.i("xixi: ", "LeftSwipe!");
            calLeftSwipe();
            return false;
        }
        if(y2 - y1 > 0 && y2 - y1 > 120) {
            Log.i("xixi: ", "DownSwipe!");
            calDownSwipe();
            return false;
        }
        if(y2 - y1 <0 && y2 -y1 < -120) {
            Log.i("xixi: ", "UpSwipe!");
            calUpSwipe();
            return false;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("heihei", "tocuMEEEEEEEEEEEE");
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public interface OnGamePlayListener {
        public void onScore(int score);

        public void onFail();
    }
}
