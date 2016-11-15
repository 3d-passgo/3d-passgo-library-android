package net.passgo.passgo3d;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class PassGoView_Style extends PassGoView {


    public PassGoView_Style(Context context, AttributeSet attrs) {

        super(context, attrs);

        mGridRowNum = 2;
        mGridColNum = 2;
        mGridHeiNum = 2;
        min_grid_size = 2;
        max_grid_size = 2;
        offset_row = 1;
        offset_col = 1;
        offset_hei = 1;

        mPassword = "010102000000020102010101020101000000";
        scaleFactor=0;

        azimuth=-20;
        elevation=15;
        offset_x=0;
        offset_y=0;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onSizeChanged(int width_new, int height_new, int width_old, int height_old) {
//        super.onSizeChanged(width_new, height_new, width_old, height_old);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return true;
    }

    @Override
    void handleActionUp(MotionEvent event) {

    }


    public void update_Color(int what, int color) {

        switch (what) {
            case 1: //line
                mPassGo_Line_Color = color;
                Log.i("ddd", "color is "+color);
                break;
            case 2: //dot
                mPassGo_Dot_Color = color;
                break;
            case 3: //grid
                mGrid_Line_Color = color;
                break;
            case 4: //grid
                mPassGo_Background_Color = color;
                break;
            case 5: //grid circle
                mGrid_Circle_Color = color;
                break;
            default:
                break;
        }
        invalidate();
    }


    public void update_thick(int what, int thick) {

        switch (what) {
            case 1: //line
                mPassGo_Line_Thickness_Factor = thick;

                ;
                break;
            case 2: //dot
                mPassGo_Dot_Radius_Factor = thick;
                break;
            case 3: //grid line
                mGrid_Line_Thickness_Factor = thick;
                break;
            case 5: //grid circle
                mGrid_Circle_Thickness_Factor = thick;
                break;
            default:
                break;
        }

        invalidate();
    }


    public void update_show(int what, boolean show) {
        switch (what) {
            case 1: //show passgo
                mHide_PassGo = show;
                break;
            case 2: //dot
                mShow_Grid_Circle = show;
                break;
            case 3: //grid
                mShow_Grid_Line = show;
                break;
            default:
                break;
        }
        invalidate();
    }


    @Override
    int decide_Grid_Line_Color() {
        if (mShow_Grid_Line){
            return mGrid_Line_Color;
        }else {
            return Color.TRANSPARENT;
        }

    }

    @Override
    int decide_Grid_Circle_Color () {
        if (mShow_Grid_Circle) {
            return mGrid_Circle_Color;
        }else {
            return Color.TRANSPARENT;
        }

    }


    @Override
    int decide_PassGo_Line_Color() {
        if (mHide_PassGo){
            return Color.TRANSPARENT;
        }else {
            return mPassGo_Line_Color;
        }

    }

    @Override
    int decide_PassGo_Dot_Color() {
        if (mHide_PassGo) {
            return Color.TRANSPARENT;
        } else {
            return mPassGo_Dot_Color;
        }
    }

    @Override
    int decide_PassGo_Background_Color() {
        return mPassGo_Background_Color;
    }


}
