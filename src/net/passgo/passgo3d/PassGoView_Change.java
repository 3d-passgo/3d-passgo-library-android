package net.passgo.passgo3d;

import android.content.Context;
import android.graphics.Color;
//import android.nfc.Tag;
//import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class PassGoView_Change extends PassGoView {

    private String TAG = "PassGoView_Change";

    public PassGoView_Change(Context context, AttributeSet attrs) {
        super(context, attrs);
//        Log.i(TAG, "here is "+TAG);
//        Log.i(TAG,"size  "+mGridRowNum+mGridColNum+mGridHeiNum);
        if (PassGoGlobalData.sizeChanged) {
            mGridRowNum = mGridRowNum_UnOfficial;
            mGridColNum = mGridColNum_UnOfficial;
            mGridHeiNum = mGridHeiNum_UnOfficial;
            min_grid_size = Math.min(Math.min(mGridRowNum, mGridColNum), mGridHeiNum);
            max_grid_size = Math.max(Math.max(mGridRowNum, mGridColNum), mGridHeiNum);
            offset_row = mGridRowNum / 2;
            offset_col = mGridColNum / 2;
            offset_hei = mGridHeiNum / 2;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    @Override
    protected void handleActionPointerDown (MotionEvent event) {
        if (onesecond_timer_b4_move != null) {
            onesecond_timer_b4_move.cancel();
        }
        if (isFirstDraw == true) {
            mode = ZOOM;
//                    if (PassGoGlobalData.Changing_PassGo) {
//                        notifyPassGoDetected();
//                    }
        }
        if (!mPassword.isEmpty() && !mPassword.endsWith("000000")) {
            clearLastStroke();
        }
        Dist_Start = spacing(event);
        scaleFactor_Start = scaleFactor;  // scaleFactor_Start changes each time 2nd finger down
    }


    @Override
    void handleActionUp(MotionEvent event) {

        if (onesecond_timer_b4_move != null) {
            onesecond_timer_b4_move.cancel();
        }
        // if last xy is not 0, then should append a "00" to the PassGo
        if (last_x != 0 && !mPassword.isEmpty()) {
            if (!mPassword.endsWith("000000")) {
                mPassword = mPassword + "000000";
            }
            last_x = 0;
            last_y = 0;
            last_z = 0;
            mx = 0;
            my = 0;
            //check if entered passgo so far is good or not
            PassGoUtils mPassGoUtils = new PassGoUtils(getContext());
            mIfWrongPassGo = mPassGoUtils.checkIfWrongPassGo(mPassword, false);
            mIfCorrectPassGo = mPassGoUtils.checkIfCorrectPassGo(mPassword, false);

        }
        notifyPassGoDetected();
        setWillNotDraw(false);
        rotating = false;
        rotating_start = false;

        down_point_x = 0;
        down_point_y = 0;
//        convertGridTo2D();
        postInvalidate();
        mode = NONE;
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
        if (mPassGoDisplayMode == DisplayMode.Wrong) {
            return Color.GRAY;
        } else {
            return mPassGo_Line_Color;
        }
    }

    @Override
    int decide_PassGo_Dot_Color() {
        if (mPassGoDisplayMode == DisplayMode.Wrong) {
            return Color.GRAY;
        } else {
            return mPassGo_Dot_Color;
        }
    }

    @Override
    int decide_PassGo_Background_Color() {
        return mPassGo_Background_Color;
    }

}
