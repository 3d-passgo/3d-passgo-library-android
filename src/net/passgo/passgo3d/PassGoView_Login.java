package net.passgo.passgo3d;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import net.passgo.passgo3d.PassGoUtils;


public class PassGoView_Login extends PassGoView {


    public PassGoView_Login(Context context, AttributeSet attrs) {

        super(context, attrs);
        PassGoUtils mPassGoUtils = new PassGoUtils(context);
        if (mPassGoUtils.checkIfPassGoNull()){
            Toast.makeText(context, R.string.no_passgo_yet, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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
            mIfWrongPassGo = mPassGoUtils.checkIfWrongPassGo(mPassword, true);
            mIfCorrectPassGo = mPassGoUtils.checkIfCorrectPassGo(mPassword, true);

//            if (!PassGoGlobalData.Changing_PassGo) { //only if it is not changing passgo which is handled by changepassgoactivity
            if (!mIfWrongPassGo) {
                if (mIfCorrectPassGo) { //right PassGo
                    setDisplayMode(DisplayMode.Correct);
                }
            } else { //wrong PassGo
                PassGoGlobalData.Wrong_PassGo_Count++;
//                Log.i("View_Login","count is "+PassGoGlobalData.Wrong_PassGo_Count);
                setDisplayMode(DisplayMode.Wrong);
                if (PassGoGlobalData.Wrong_PassGo_Count > mAllowedTryTime) {
                    mHold = true;
                    new CountDownTimer(mFreezeTime, 1000) {
                        @Override
                        public void onTick(long m) {
                        }

                        @Override
                        public void onFinish() {
                            if (PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.PASSGO_CLEAR_LAST, false)) {
                                clearLastStroke();
                            } else {
                                clearPassGo();
                            }
                            mHold = false;
                        }
                    }.start();
                } else {
                    new CountDownTimer(mWrongPassGoLastTime, 100) {
                        @Override
                        public void onTick(long m) {
                        }

                        @Override
                        public void onFinish() {
                            //after 5 seconds draw the second line
                            if (PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.PASSGO_CLEAR_LAST, false)) {
                                clearLastStroke();
                            } else {
                                clearPassGo();
                            }
                            setDisplayMode(DisplayMode.Correct);
                        }
                    }.start();
                }
            }
            notifyPassGoDetected();
        }

//        setWillNotDraw(false);
        rotating = false;
        rotating_start = false;

//        if (!PassGoGlobalData.Changing_PassGo) {  // if when Login, turn back to official angel/location/scale upon penup
        azimuth = azimuth_Official;
        elevation = elevation_Official;
        offset_x = offset_x_Official;
        offset_y = offset_y_Official;
        scaleFactor = scaleFactor_Official;


        down_point_x = 0;
        down_point_y = 0;
//        convertGridTo2D();
        postInvalidate();
        mode = NONE;
    }


    @Override
    int decide_Grid_Line_Color() {
        if (mShow_Grid_Line) {
            return mGrid_Line_Color;
        } else {
            return Color.TRANSPARENT;
        }

    }

    @Override
    int decide_Grid_Circle_Color() {
        if (mShow_Grid_Circle) {
            return mGrid_Circle_Color;
        } else {
            return Color.TRANSPARENT;
        }

    }


    @Override
    int decide_PassGo_Line_Color() {
        if (mHide_PassGo) {
            return Color.TRANSPARENT;
        } else if (mPassGoDisplayMode == DisplayMode.Wrong) {
            return Color.GRAY;
        } else {
            return mPassGo_Line_Color;
        }
    }

    @Override
    int decide_PassGo_Dot_Color() {

        if (mHide_PassGo) {
            return Color.TRANSPARENT;
        } else if (mPassGoDisplayMode == DisplayMode.Wrong) {
            return Color.GRAY;
        } else {
            return mPassGo_Dot_Color;
        }

    }

    @Override
    int decide_PassGo_Background_Color() {
        return 0;
    }

}
