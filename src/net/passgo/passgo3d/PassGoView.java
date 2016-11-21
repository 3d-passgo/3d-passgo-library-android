package net.passgo.passgo3d;

import android.graphics.DashPathEffect;
import android.graphics.Point;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;


public abstract class PassGoView extends View {

    protected static String TAG = "PassGoView";

    public int mAllowedTryTime = 5;
    public int mFreezeTime = 30000;
    public int mWrongPassGoLastTime = 500;

    protected CountDownTimer onesecond_timer_b4_move;
//    private boolean long_hold = true;

    private int width_view, height_view, viewHeight_extra;

    //  grid size
    protected final int mGridRowNum_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.OFFICIAL_PASSGO_NUM_ROW, 2);
    protected final int mGridColNum_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.OFFICIAL_PASSGO_NUM_COL, 2);
    protected final int mGridHeiNum_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.OFFICIAL_PASSGO_NUM_HEI, 2);
    protected final int mGridRowNum_UnOfficial = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_ROW, 2);
    protected final int mGridColNum_UnOfficial = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_COL, 2);
    protected final int mGridHeiNum_UnOfficial = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_HEI, 2);
    protected int mGridRowNum, mGridColNum, mGridHeiNum, min_grid_size, max_grid_size;
    protected int offset_row, offset_col, offset_hei;
    double mCell_Side_Length_min;
    float mCell_Side_Length_min_float;

    // read settings from db
    private final boolean mHide_PassGo_Setting = PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.HIDE_PASSGO, false);
    private final boolean mShow_Grid_Circle_Setting = PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.SHOW_GRID_CIRCLE, true);
    private final boolean mShow_Grid_Line_Setting = PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.SHOW_GRID_LINE, true);

    private final int mPassGo_Line_Color_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.PASSGO_LINE_COLOR, Color.RED);
    private final int mPassGo_Dot_Color_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.PASSGO_DOT_COLOR, Color.RED);
    private final int mGrid_Line_Color_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.GRID_LINE_COLOR, Color.WHITE);
    private final int mGrid_Circle_Color_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.GRID_CIRCLE_COLOR, Color.WHITE);

    private final int mPassGo_Line_Thickness_Factor_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.PASSGo_LINE_THICKNESS, 3);
    private final int mPassGo_Dot_Radius_Factor_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.PASSGO_DOT_RADIUS, 3);
    private final int mGrid_Line_Thickness_Factor_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.GRID_LINE_THICKNESS, 1);
    private final int mGrid_Circle_Thickness_Factor_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.GRID_CIRCLE_THICKNESS, 1);

    protected int mPassGo_Background_Color_Setting = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.PASSGO_BACKGROUND_COLOR, Color.BLACK);
    private final boolean mIsVibrate_Setting = PassGoGlobalData.getDataBool(getContext(), PassGoGlobalData.VIBRATE, true);


    protected int mGrid_Line_Color;
    protected int mGrid_Circle_Color;
    protected int mPassGo_Line_Color;
    protected int mPassGo_Dot_Color;
    protected int mPassGo_Background_Color;

    protected int mGrid_Line_Thickness_Factor;
    protected int mGrid_Circle_Thickness_Factor;
    protected int mPassGo_Line_Thickness_Factor;
    protected int mPassGo_Dot_Radius_Factor;
//    protected int mPassGo_Line_Thickness_factor;
//    protected int mPassGo_Dot_Radius_Factor;

//    protected int mBackground_Color = 1;


    protected boolean mHide_PassGo = false;
    protected boolean mShow_Grid_Circle = true;
    protected boolean mShow_Grid_Line = true;


    //angel & location stored
    protected final int azimuth_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.AZIMUTH, -20);
    protected final int elevation_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.ELEVATION, 15);
    protected final int offset_x_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.OFFSET_X, 0);
    protected final int offset_y_Official = PassGoGlobalData.getDataInt(getContext(), PassGoGlobalData.OFFSET_Y, 0);
    // scaleFactor stored
    protected final float scaleFactor_Official = PassGoGlobalData.getDataFloat(getContext(), PassGoGlobalData.SCALEFACTOR, 0);

    public int azimuth;
    public int elevation;
    public int offset_x;
    public int offset_y;
    private float near = 100;  // distance from eye to near plane
    private float nearToObj = 10.5f;// distance from near plane to center of object

    public float scaleFactor = scaleFactor_Official;
    private float scaleFactor_Default;
    protected float scaleFactor_Start; // change each time 2nd finger is down
    private float scaleFactor_ratio = 1;  // scaleFactor/scaleFactor_Default
    private float scaleFactor_ratio_sqrt = 1;

    private int line_thickness_base;
    private int mGrid_Line_Thickness;
    private int mPassGo_Line_Thickness = 1;
    private int mPassGo_Line_Thickness_max = 10;
    private float mPassGo_Line_Thickness_Factor_Float;

    private int mGrid_Circle_Thickness;
    private float mPassGo_Dot_Radius_factor_float;

    private float mPassGo_Dot_Radius, mGrid_Circle_Radius;
    private float circle_Radius_Base, dot_Radius_Base;
    private float star_width;
    protected float Dist_Start = 1f;

    private Point[][][] intersection;
    protected float mx;
    protected float my;
    private float new_mx;
    private float new_my;  // the most recently recorded mouse coordinates
    protected float down_point_x;
    protected float down_point_y;
    private float x0 = 0, y0 = 0, z0 = 0, center_offset_row = 0, center_offset_col = 0, center_offset_hei = 0;
    private int coord[];
    protected int last_x;
    protected int last_y;
    protected int last_z;
    private int mX, mY;

    protected boolean rotating = false;
    protected boolean rotating_start = false;
    private boolean just_lift_2nd = false;

    protected boolean mHold = false;
    protected boolean mIfWrongPassGo = false;
    protected boolean mIfCorrectPassGo = false;
    protected String mPassword = "";

//    private boolean mIsOfficialPassGo = true;
    private onPassGoListener mPassGoListener;
    protected DisplayMode mPassGoDisplayMode = DisplayMode.Correct;
    public boolean isFirstDraw = true;

    protected static final int NONE = 0;
    protected static final int ZOOM = 2;
    protected static final int GRID_MOVE = 3;
    protected int mode = NONE;

    private Paint Grid_Line_paint = new Paint();
    private Paint Grid_Circle_paint = new Paint();
    private Paint Go_line_paint = new Paint();
    private Paint Go_dot_paint = new Paint();


    // constructor
    // constructor
    // constructor
    // constructor
    public PassGoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Log.i(TAG, "context plus attrib");

        // define grid size
//        mIsOfficialPassGo = !PassGoGlobalData.Changing_PassGo;

        mHide_PassGo = mHide_PassGo_Setting;
        mShow_Grid_Circle = mShow_Grid_Circle_Setting;
        mShow_Grid_Line = mShow_Grid_Line_Setting;

        // grid size

        mGridRowNum = mGridRowNum_Official;
        mGridColNum = mGridColNum_Official;
        mGridHeiNum = mGridHeiNum_Official;
        min_grid_size = Math.min(Math.min(mGridRowNum, mGridColNum), mGridHeiNum);
        max_grid_size = Math.max(Math.max(mGridRowNum, mGridColNum), mGridHeiNum);
        offset_row = mGridRowNum / 2;
        offset_col = mGridColNum / 2;
        offset_hei = mGridHeiNum / 2;


        mPassGo_Line_Thickness_Factor = mPassGo_Line_Thickness_Factor_Setting;
        mPassGo_Dot_Radius_Factor = mPassGo_Dot_Radius_Factor_Setting;

        // define grid style (line & circle)
//        Grid_Line_paint.setColor(mGrid_Line_Color_Setting);

        mGrid_Line_Color = mGrid_Line_Color_Setting;
        mGrid_Circle_Color = mGrid_Circle_Color_Setting;
        mPassGo_Line_Color = mPassGo_Line_Color_Setting;
        mPassGo_Dot_Color = mPassGo_Dot_Color_Setting;
        mPassGo_Background_Color = mPassGo_Background_Color_Setting;

        mGrid_Line_Thickness_Factor = mGrid_Line_Thickness_Factor_Setting;
        mGrid_Circle_Thickness_Factor = mGrid_Circle_Thickness_Factor_Setting;

        azimuth = azimuth_Official;
        elevation = elevation_Official;
        offset_x = offset_x_Official;
        offset_y = offset_y_Official;


    } // end of constructor


    public void setOnPassGoListener(onPassGoListener passGoListener) {
        mPassGoListener = passGoListener;
    }

    public void setDisplayMode(DisplayMode displayMode) {
        mPassGoDisplayMode = displayMode;
        invalidate();
    }

    protected void notifyPassGoDetected() {
        if (mPassGoListener != null) {
            mPassGoListener.onPassGoDetected(mPassword, mIfWrongPassGo, mIfCorrectPassGo);
        }
//        PassGo mPassGo = new PassGo(mPassword);
//        mPassGo.readablecode();
    }


    // view size
    // view size
    // view size
    // view size
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewWidth_given = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight_given = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth_given = viewHeight_given = Math.min(viewWidth_given, viewHeight_given);
        viewHeight_extra = (int) (viewHeight_given * 0);
        int viewHeight_final = viewHeight_given + viewHeight_extra;
        int viewWidth_final = viewWidth_given;
//        Log.v(TAG, "LockPatternView dimensions: " + viewWidth_given + "x" + viewHeight_given);
        setMeasuredDimension(viewWidth_final, viewHeight_final);
    }

    protected void onSizeChanged(int width_new, int height_new, int width_old, int height_old) {
        width_view = width_new - getPaddingLeft() - getPaddingRight();
        height_view = height_new - viewHeight_extra - getPaddingTop() - getPaddingBottom();
        mCell_Side_Length_min = Math.min(Math.min(width_view / mGridColNum, width_view / mGridRowNum), width_view / mGridHeiNum);
        mCell_Side_Length_min_float = (float) mCell_Side_Length_min;

        if (max_grid_size >= 6) {
            mPassGo_Line_Thickness_max = width_view / 20;
        } else {
            mPassGo_Line_Thickness_max = width_view / 10;
        }

        if (max_grid_size == 2) {
            line_thickness_base = (int) mCell_Side_Length_min / 10;
        } else if (max_grid_size == 3) {
            line_thickness_base = (int) mCell_Side_Length_min / 8;
        } else if (max_grid_size <= 5) {
            line_thickness_base = (int) mCell_Side_Length_min / 7;
        } else if (max_grid_size <= 7) {
            line_thickness_base = (int) mCell_Side_Length_min / 5;
        } else if (max_grid_size <= 9) {
            line_thickness_base = (int) mCell_Side_Length_min / 5;
        } else {
            line_thickness_base = (int) mCell_Side_Length_min / 6;
        }


        // star (reference aids) size
        star_width = (float) (mCell_Side_Length_min_float * 0.08);
        if (max_grid_size > 5) {
            star_width = (float) (mCell_Side_Length_min_float * 0.1);
        }
        if (max_grid_size >= 11) {
            star_width = (float) (mCell_Side_Length_min_float * 0.13);
        }
        if (max_grid_size >= 15) {
            star_width = (float) (mCell_Side_Length_min_float * 0.18);
        }

        scaleFactor_Default = (Math.min(width_view, height_view) / max_grid_size);
    }


    // receive touch event and handle it
    // receive touch event and handle it
    // receive touch event and handle it
    // receive touch event and handle it
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        if (mHold) {
            return true;
        }
//        switch(event.getAction() & MotionEvent.ACTION_MASK) {
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_OUTSIDE:
//        		Log.i("ttttttttttttt","outside down");
//                start.set(event.getX(), event.getY());
                handleActionDown(event);
                return true;

            case MotionEvent.ACTION_DOWN:

//                start.set(event.getX(), event.getY());
                handleActionDown(event);
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                handleActionPointerDown(event);
                return true;

            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                return true;

            case MotionEvent.ACTION_UP:
                handleActionUp(event);

                return true;

            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                rotating = true;
                rotating_start = false;
                just_lift_2nd = true;
                down_point_x = event.getX();
                down_point_y = event.getY();
                mx = event.getX();
                my = event.getY();
                new_mx = event.getX();
                new_my = event.getY();
//                if (isFirstDraw && (mGridRowNum < 10 && mGridColNum < 10 && mGridHeiNum < 10)) {
                if (isFirstDraw) {
                    One_Second_timer_b4_move();
                }
//                }
                return true;

            case MotionEvent.ACTION_CANCEL:
                if (onesecond_timer_b4_move != null) {
                    onesecond_timer_b4_move.cancel();
                }
                clearPassGo();
                return true;
        }
        return false;
    }

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
//        if (!mPassword.isEmpty() && !mPassword.endsWith("000000")) {
//            clearLastStroke();
//        }
        Dist_Start = spacing(event);
        scaleFactor_Start = scaleFactor;  // scaleFactor_Start changes each time 2nd finger down
    }

    private void handleActionDown(MotionEvent event) {

        if (onesecond_timer_b4_move != null) {
            onesecond_timer_b4_move.cancel();
        }

        float x_down = event.getX();
        float y_down = event.getY();
        down_point_x = x_down;
        down_point_y = y_down;
        new_mx = x_down;
        new_my = y_down;
        mx = x_down;
        my = y_down;
        coord = getLogical3DCoordicate(x_down, y_down);
        // if the down hit an intersection, add it to the PassGo
        if (coord[0] == 99 && isFirstDraw) {
            rotating = true;
//            if (mGridRowNum < 10 && mGridColNum < 10 && mGridHeiNum < 10) {
            One_Second_timer_b4_move();
//            }
        } else {
            rotating = false;
        }
        if (coord[0] != 99) {
            if (mIsVibrate_Setting) {
                vibrate_once();
            }
            String pad_x = "";
            String pad_y = "";
            String pad_z = "";
            if (coord[0] < 10) {
                pad_x = "0";
            }
            if (coord[1] < 10) {
                pad_y = "0";
            }
            if (coord[2] < 10) {
                pad_z = "0";
            }
            mPassword = mPassword + pad_x + coord[0] + pad_y + coord[1] + pad_z + coord[2];
//        	Log.i("down"," down add PassGo " +coord[0]+coord[1]+coord[2] );
            last_x = coord[0];
            last_y = coord[1];
            last_z = coord[2];
        }
    }

    private void handleActionMove(MotionEvent event) {
        float x_move = event.getX();
        float y_move = event.getY();
        // get the latest mouse position
        new_mx = x_move;
        new_my = y_move;
        if (just_lift_2nd) {
            down_point_x = x_move;
            down_point_y = y_move;
            just_lift_2nd = false;
        }
        if (mode == ZOOM) {
            // this condition is to prevent crash if only one finger on screen
            if (event.getPointerCount() >= 2) {
                float Dist_Real = spacing(event);
                if (Dist_Real > 10f) {
                    scaleFactor = scaleFactor_Start * Dist_Real / Dist_Start;
                }
            }
        } else if (mode == GRID_MOVE && isFirstDraw) {
            offset_x += (new_mx - mx);
            offset_y += (new_my - my);
        } else {
            if (rotating == true &&
                    Math.pow(new_mx - down_point_x, 2) + Math.pow(new_my - down_point_y, 2) >
                            Math.pow(width_view / 4, 2)) {
                rotating_start = true;
//                if (PassGoGlobalData.Changing_PassGo) {
//                    notifyPassGoDetected();
//                }
            }

            if (rotating_start == true && isFirstDraw) {

                azimuth -= (new_mx - mx) / 5;
                elevation += (new_my - my) / 5;
            } else {
                coord = getLogical3DCoordicate(x_move, y_move);

                //if the xy coordinates of the point is not zero, and is not as same as the last xy,
                //namely a new intersection is touched, then add it to the PassGo and change the new "last xy"
                if (coord[0] != 99 && rotating == false && (coord[0] != last_x || coord[1] != last_y || coord[2] != last_z)) {
                    if (mIsVibrate_Setting) {
                        vibrate_once();
                    }
                    String pad_x = "";
                    String pad_y = "";
                    String pad_z = "";
                    if (coord[0] < 10) {
                        pad_x = "0";
                    }
                    if (coord[1] < 10) {
                        pad_y = "0";
                    }
                    if (coord[2] < 10) {
                        pad_z = "0";
                    }
                    mPassword = mPassword + pad_x + coord[0] + pad_y + coord[1] + pad_z + coord[2];
//                    Log.i("move","coord added into PassGo  "+coord[0]+coord[1]+coord[2]);
                    last_x = coord[0];
                    last_y = coord[1];
                    last_z = coord[2];
                }
            }
        }
        // update_Color our data
        mx = new_mx;
        my = new_my;
        invalidate();
    }


    abstract void handleActionUp(MotionEvent event);

    // ondraw
    // ondraw
    // ondraw
    // ondraw
    // ondraw
    // ondraw
    // ondraw
    // ondraw

    @Override
    protected void onDraw(Canvas canvas) {

        this.setBackgroundColor(decide_PassGo_Background_Color());

        // if grid size changed when set up passgo, we need to go back to default angles setttings
        if (PassGoGlobalData.sizeChanged) {
            goto_default_shape();
            // prepare how grid and passgo will look
            // prepare how grid and passgo will look
            // prepare how grid and passgo will look
            // prepare how grid and passgo will look

            PassGoGlobalData.sizeChanged = false;
        }


        Grid_Line_paint.setColor(decide_Grid_Line_Color());
        Grid_Circle_paint.setColor(decide_Grid_Circle_Color());
        Go_line_paint.setColor(decide_PassGo_Line_Color());
        Go_dot_paint.setColor(decide_PassGo_Dot_Color());

        Grid_Line_paint.setStyle(Style.FILL);
//        Grid_Line_paint.setStrokeJoin(Paint.Join.ROUND);
//        Grid_Line_paint.setStrokeCap(Paint.Cap.ROUND);
        Grid_Circle_paint.setStyle(Style.STROKE);
        Go_line_paint.setStrokeJoin(Paint.Join.ROUND);
        Go_line_paint.setStrokeCap(Paint.Cap.ROUND);


        // define thickness of grid (line & circle)
        if (max_grid_size <= 8) {
            mGrid_Line_Thickness = mGrid_Line_Thickness_Factor * 3;
            mGrid_Circle_Thickness = mGrid_Circle_Thickness_Factor * 3;
        } else {
            mGrid_Line_Thickness = mGrid_Line_Thickness_Factor * 6;
            mGrid_Circle_Thickness = mGrid_Circle_Thickness_Factor * 6;
        }
        Grid_Line_paint.setStrokeWidth(mGrid_Line_Thickness);
        Grid_Circle_paint.setStrokeWidth(mGrid_Circle_Thickness);


        if (scaleFactor == 0) {
            scaleFactor = scaleFactor_Default;
        }
        scaleFactor_ratio = scaleFactor / scaleFactor_Default;
        scaleFactor_ratio_sqrt = (float) Math.sqrt(scaleFactor_ratio);

        // passgo line thickness & dot size

        if (mPassGo_Line_Thickness_Factor < 3) {
            mPassGo_Line_Thickness_Factor_Float = (float) (mPassGo_Line_Thickness_Factor + 1) / 4;
        } else if (mPassGo_Line_Thickness_Factor == 3) {
            mPassGo_Line_Thickness_Factor_Float = 1;
        } else {
            mPassGo_Line_Thickness_Factor_Float = (float) (mPassGo_Line_Thickness_Factor - 3) / 3 + 1;
        }


        if (mPassGo_Dot_Radius_Factor < 3) {
            mPassGo_Dot_Radius_factor_float = (float) (mPassGo_Dot_Radius_Factor + 1) / 4;
        } else if (mPassGo_Dot_Radius_Factor == 3) {
            mPassGo_Dot_Radius_factor_float = 1;
        } else {
            mPassGo_Dot_Radius_factor_float = (float) (mPassGo_Dot_Radius_Factor - 3) / 3 + 1;
        }


        mPassGo_Line_Thickness = Math.min(mPassGo_Line_Thickness_max,
                (int) ((float) line_thickness_base * mPassGo_Line_Thickness_Factor_Float * scaleFactor_ratio_sqrt));
        Go_line_paint.setStrokeWidth(mPassGo_Line_Thickness);

        //grid circle thickness


        //define the basic size of circles
        if (min_grid_size == 1) {
            if (max_grid_size == 1) {
                dot_Radius_Base = (float) 0.1;
                circle_Radius_Base = (float) 0.07;
            } else if (max_grid_size == 2) {
                dot_Radius_Base = (float) 0.15;
                circle_Radius_Base = (float) 0.07;
            } else if (max_grid_size == 3) {
                dot_Radius_Base = (float) 0.2;
                circle_Radius_Base = (float) 0.08;
            } else if (max_grid_size <= 5) {
                dot_Radius_Base = (float) 0.25;
                circle_Radius_Base = (float) 0.1;
            } else if (max_grid_size <= 7) {
                dot_Radius_Base = (float) 0.3;
                circle_Radius_Base = (float) 0.15;
            } else {
                dot_Radius_Base = (float) 0.4;
                circle_Radius_Base = (float) 0.2;
            }
        } else if (max_grid_size == 2) {
            circle_Radius_Base = (float) 0.07;
            dot_Radius_Base = (float) 0.13;
        } else if (max_grid_size == 3) {
            circle_Radius_Base = (float) 0.08;
            dot_Radius_Base = (float) 0.18;
        } else if (max_grid_size <= 5) {
            circle_Radius_Base = (float) 0.1;
            dot_Radius_Base = (float) 0.2;
        } else if (max_grid_size <= 7) {
            circle_Radius_Base = (float) 0.1;
            dot_Radius_Base = (float) 0.2;
        } else if (max_grid_size <= 11) {
            circle_Radius_Base = (float) 0.15;
            dot_Radius_Base = (float) 0.2;
        } else {
            circle_Radius_Base = (float) 0.2;
            dot_Radius_Base = (float) 0.2;
        }

        mGrid_Circle_Radius = circle_Radius_Base * mCell_Side_Length_min_float * 3 / 4
                * scaleFactor_ratio_sqrt;
//        Log.i(TAG, "circle_Radius_Base is " + circle_Radius_Base);
//        Log.i(TAG, "mCell_Side_Length_min_float is " + mCell_Side_Length_min_float);
//        Log.i(TAG, "scaleFactor_ratio_sqrt is " + scaleFactor_ratio_sqrt);

        mPassGo_Dot_Radius = dot_Radius_Base * mCell_Side_Length_min_float * 3 / 4
                * mPassGo_Dot_Radius_factor_float * scaleFactor_ratio_sqrt;

        // intersection [] array  ;
        intersection = new Point[mGridRowNum][mGridColNum][mGridHeiNum];

        convertGridTo2D();

        if (mode == GRID_MOVE) {
            Grid_Line_paint.setPathEffect(new DashPathEffect(new float[]{5, 10,}, 10));
            Grid_Circle_paint.setPathEffect(new DashPathEffect(new float[]{5, 10,}, 10));
        } else {
            Grid_Line_paint.setPathEffect(new DashPathEffect(new float[]{5, 0}, 0));
            Grid_Circle_paint.setPathEffect(new DashPathEffect(new float[]{5, 0}, 0));
        }

        // draw center point filled circle as a reference aid
        if (!(mGridRowNum == 1 || mGridColNum == 1 || mGridHeiNum == 1)) {
            if (mGridRowNum % 2 == 1 && mGridColNum % 2 == 1 && mGridHeiNum % 2 == 1) {
                int center_point_x = intersection[mGridRowNum / 2][mGridColNum / 2][mGridHeiNum / 2].x;
                int center_point_y = intersection[mGridRowNum / 2][mGridColNum / 2][mGridHeiNum / 2].y;
                Grid_Circle_paint.setStyle(Style.FILL);
                canvas.drawCircle(center_point_x, center_point_y, mGrid_Circle_Radius, Grid_Circle_paint);
                Grid_Circle_paint.setStyle(Style.STROKE);
            }
        }
//        Grid_Line_paint.setStyle(Paint.Style.STROKE);

        //draw the grid
        for (int x = 0; x < mGridRowNum; x++) {
            for (int y = 0; y < mGridColNum; y++) {
                for (int z = 0; z < mGridHeiNum; z++) {


                    //draw the grid lines
                    //draw the grid lines
//                    if (mShow_Grid_Line) {
                    if (x + 1 < mGridRowNum) {
                        // add following condition to resolve the issue of showing a diagonal line when intersection overlaps
                        if (intersection[x][y][z].x != intersection[x + 1][y][z].x || intersection[x][y][z].y != intersection[x + 1][y][z].y) {
                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y, intersection[x + 1][y][z].x, intersection[x + 1][y][z].y, Grid_Line_paint);
                        }
                    }
                    if (y + 1 < mGridColNum) {
                        if (intersection[x][y][z].x != intersection[x][y + 1][z].x || intersection[x][y][z].y != intersection[x][y + 1][z].y) {
                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y, intersection[x][y + 1][z].x, intersection[x][y + 1][z].y, Grid_Line_paint);
                        }
                    }
                    if (z + 1 < mGridHeiNum) {
                        if (intersection[x][y][z].x != intersection[x][y][z + 1].x || intersection[x][y][z].y != intersection[x][y][z + 1].y) {
                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y, intersection[x][y][z + 1].x, intersection[x][y][z + 1].y, Grid_Line_paint);
                        }
//                        }
//                        if ( x==0 ){
//                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y,
//                                            intersection[x+mGridRowNum-1][y][z].x,intersection[x+mGridRowNum-1][y][z].y,
//                                    Grid_Line_paint);
//                        }
//                        if ( y==0 ){
//                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y,
//                                    intersection[x][y+mGridColNum-1][z].x,intersection[x][y+mGridColNum-1][z].y,
//                                    Grid_Line_paint);
//                        }
//                        if ( z==0 ){
//                            canvas.drawLine(intersection[x][y][z].x, intersection[x][y][z].y,
//                                    intersection[x][y][z+mGridHeiNum-1].x,intersection[x][y][z+mGridHeiNum-1].y,
//                                    Grid_Line_paint);
//                        }

                    }

                    //draw circles
                    //draw circles
//                    if (mShow_Grid_Circle) {
                    canvas.drawCircle(intersection[x][y][z].x, intersection[x][y][z].y, mGrid_Circle_Radius, Grid_Circle_paint);
//                    };

                    //draw stars for 2D grid
                    if (mGridRowNum == 1 || mGridColNum == 1 || mGridHeiNum == 1) {
//                        Grid_Line_paint.setStyle(Style.FILL);
                        if (mGridRowNum == 1) {
                            mX = mGridColNum;
                            mY = mGridHeiNum;
                        } else if (mGridColNum == 1) {
                            mX = mGridRowNum;
                            mY = mGridHeiNum;
                        } else if (mGridHeiNum == 1) {
                            mX = mGridRowNum;
                            mY = mGridColNum;
                        }
                        // center star
                        // if mX mY both are odd
                        if (mX % 2 == 1 && mY % 2 == 1) {
                            // as long as grid is not 7x7 nor 3x3
                            if (!((mX == 7 && mY == 7) || (mX == 3 && mY == 3))) {
                                if (x == mGridRowNum / 2 && y == mGridColNum / 2 && z == mGridHeiNum / 2) {
                                    draw_star(canvas, x, y, z, star_width);
                                }
                            }
                        }
                        int XY_min = Math.min(mX, mY);
//                        int XY_max = Math.max(mX, mY);
                        // corner stars
                        if ((XY_min >= 7) && (XY_min < 13)) {

                            if ((mGridRowNum == 1 && (y == 2 || y == mGridColNum - 3) && (z == 2 || z == mGridHeiNum - 3)) ||
                                    (mGridColNum == 1 && (x == 2 || x == mGridRowNum - 3) && (z == 2 || z == mGridHeiNum - 3)) ||
                                    (mGridHeiNum == 1 && (x == 2 || x == mGridRowNum - 3) && (y == 2 || y == mGridColNum - 3))) {
                                draw_star(canvas, x, y, z, star_width);
                            }
                        } else if (XY_min >= 13) {
                            if ((mGridRowNum == 1 && (y == 3 || y == mGridColNum - 4) && (z == 3 || z == mGridHeiNum - 4)) ||
                                    (mGridColNum == 1 && (x == 3 || x == mGridRowNum - 4) && (z == 3 || z == mGridHeiNum - 4)) ||
                                    (mGridHeiNum == 1 && (x == 3 || x == mGridRowNum - 4) && (y == 3 || y == mGridColNum - 4))) {

                                draw_star(canvas, x, y, z, star_width);
                            }
                        }

                        // side stars
                        if ((mX % 2 == 1 && mY % 2 == 1) && XY_min >= 15) {
                            if (mGridRowNum == 1) {
                                if ((y == 3 && z == mGridHeiNum / 2) || (y == mGridColNum - 4 && z == mGridHeiNum / 2)
                                        || (z == 3 && y == mGridColNum / 2) || (z == mGridHeiNum - 4 && y == mGridColNum / 2)) {
                                    draw_star(canvas, x, y, z, star_width);
                                }
                            } else if (mGridColNum == 1) {
                                if ((x == 3 && z == mGridHeiNum / 2) || (x == mGridRowNum - 4 && z == mGridHeiNum / 2)
                                        || (z == 3 && x == mGridRowNum / 2) || (z == mGridHeiNum - 4 && x == mGridRowNum / 2)) {
                                    draw_star(canvas, x, y, z, star_width);
                                }
                            } else if (mGridHeiNum == 1) {
                                if ((x == 3 && y == mGridColNum / 2) || (x == mGridRowNum - 4 && y == mGridColNum / 2)
                                        || (y == 3 && x == mGridRowNum / 2) || (y == mGridColNum - 4 && x == mGridRowNum / 2)) {
                                    draw_star(canvas, x, y, z, star_width);
                                }
                            }
                        }
                    } // ending draw stars

                }
            }
        }

        // draw passgo now
        // draw passgo now
        // draw passgo now
        // draw passgo now

        //Go_line_paint.setColor(Color.parseColor("red"));
        // define passgo style (line & dot)
//        if (mHide_PassGo && PassGoGlobalData.Changing_PassGo == false) {
//            Log.i(TAG, "222222222");
//            Go_line_paint.setColor(Color.TRANSPARENT);
//            Go_dot_paint.setColor(Color.TRANSPARENT);
//        } else {
//
//            if (mPassGoDisplayMode == DisplayMode.Wrong) {
//                if (!mHide_PassGo || PassGoGlobalData.Changing_PassGo) {
//                    Go_line_paint.setColor(Color.GRAY);
//                    Go_dot_paint.setColor(Color.GRAY);
//                }
//            } else {
//                Go_line_paint.setColor(decide_PassGo_Line_Color());
//                Go_dot_paint.setColor(mPassGo_Dot_Color_Setting);
//            }
//        }
        int pass_array[] = new int[mPassword.length() / 2];
        for (int z = 0; z < mPassword.length(); z += 6) {
            String zx1 = Integer.toString(Integer.valueOf(mPassword.charAt(z)) - 48);
            String zx2 = Integer.toString(Integer.valueOf(mPassword.charAt(z + 1)) - 48);
            String zxs = zx1 + zx2;
            int zx = Integer.valueOf(zxs);
            String zy1 = Integer.toString(Integer.valueOf(mPassword.charAt(z + 2)) - 48);
            String zy2 = Integer.toString(Integer.valueOf(mPassword.charAt(z + 3)) - 48);
            String zys = zy1 + zy2;
            int zy = Integer.valueOf(zys);
            String zz1 = Integer.toString(Integer.valueOf(mPassword.charAt(z + 4)) - 48);
            String zz2 = Integer.toString(Integer.valueOf(mPassword.charAt(z + 5)) - 48);
            String zzs = zz1 + zz2;
            int zz = Integer.valueOf(zzs);

            pass_array[z / 2] = zx;
            pass_array[(z / 2) + 1] = zy;
            pass_array[z / 2 + 2] = zz;

            float x_real_pre, y_real_pre;

            if (zx == 0) {
                if (z == 6 || pass_array[z / 2 - 4] == 0) {
                    x_real_pre = intersection[pass_array[z / 2 - 3] - 1][pass_array[z / 2 - 2] - 1][pass_array[z / 2 - 1] - 1].x;
                    y_real_pre = intersection[pass_array[z / 2 - 3] - 1][pass_array[z / 2 - 2] - 1][pass_array[z / 2 - 1] - 1].y;
                    if (scaleFactor_ratio > 2.3) {
                        canvas.drawCircle(x_real_pre, y_real_pre, mPassGo_Dot_Radius, Go_dot_paint);
                    } else {
                        canvas.drawCircle(x_real_pre, y_real_pre, mPassGo_Dot_Radius, Go_dot_paint);
                    }
                }
            } else {
                if (z / 2 > 1 && pass_array[z / 2 - 1] > 0) {
                    x_real_pre = intersection[pass_array[z / 2 - 3] - 1][pass_array[z / 2 - 2] - 1][pass_array[z / 2 - 1] - 1].x;
                    y_real_pre = intersection[pass_array[z / 2 - 3] - 1][pass_array[z / 2 - 2] - 1][pass_array[z / 2 - 1] - 1].y;
                    if (!(x_real_pre == intersection[zx - 1][zy - 1][zz - 1].x && y_real_pre == intersection[zx - 1][zy - 1][zz - 1].y)) {
                        canvas.drawLine(x_real_pre, y_real_pre, intersection[zx - 1][zy - 1][zz - 1].x, intersection[zx - 1][zy - 1][zz - 1].y, Go_line_paint);
                    }
                }
            }
        }
    }


    // other functions
    // other functions
    // other functions
    // other functions

    // produce touch point coordinates
    // produce touch point coordinates(form xxx such as [2,3,4] starting from 1 but 0)
    public int[] getLogical3DCoordicate(float x, float y) {
        int x_3d = 99, y_3d = 99, z_3d = 99;
        float radius_sensitive;
        switch (Math.min(Math.min(mGridRowNum, mGridColNum), mGridHeiNum)) {
            case 1:
                radius_sensitive = (float) (mGrid_Circle_Radius * 4);
                break;
            case 2:
                radius_sensitive = (float) (mGrid_Circle_Radius * 3);
                break;
            case 3:
                radius_sensitive = (float) (mGrid_Circle_Radius * 2.5);
                break;
            default:
                radius_sensitive = (float) (mGrid_Circle_Radius * 2.0);
                break;
        }
        for (int i = 0; i < mGridRowNum; i++) {
            for (int j = 0; j < mGridColNum; j++) {
                for (int q = 0; q < mGridHeiNum; q++) {
                    if (Math.pow(new_mx - intersection[i][j][q].x, 2) + Math.pow(new_my - intersection[i][j][q].y, 2)
                            < Math.pow(radius_sensitive, 2)) {
                        x_3d = i + 1;
                        y_3d = j + 1;
                        z_3d = q + 1;
                        int[] r = {x_3d, y_3d, z_3d};
                        return r;
                    }
                }
            }
        }
        ;
        int[] r = {x_3d, y_3d, z_3d};
        return r;
    }


    private void convertGridTo2D() {
        // compute coefficients for the projection
        double theta = Math.PI * azimuth / 180.0;
        double phi = Math.PI * elevation / 180.0;
        float cosT = (float) Math.cos(theta), sinT = (float) Math.sin(theta);
        float cosP = (float) Math.cos(phi), sinP = (float) Math.sin(phi);
        float cosTcosP = cosT * cosP, cosTsinP = cosT * sinP,
                sinTcosP = sinT * cosP, sinTsinP = sinT * sinP;

        //convert all the intersection to 2D coordinates
        for (int x = 0; x < mGridRowNum; x++) {
            for (int y = 0; y < mGridColNum; y++) {
                for (int z = 0; z < mGridHeiNum; z++) {
                    if (mGridRowNum % 2 == 0) {
                        center_offset_row = 0.5f;
                    } else {
                        center_offset_row = 0;
                    }
                    if (mGridColNum % 2 == 0) {
                        center_offset_col = 0.5f;
                    } else {
                        center_offset_col = 0;
                    }
                    if (mGridHeiNum % 2 == 0) {
                        center_offset_hei = 0.5f;
                    } else {
                        center_offset_hei = 0;
                    }
                    x0 = x - offset_row + center_offset_row;
                    z0 = y - offset_col + center_offset_col;
                    y0 = z - offset_hei + center_offset_hei;

                    // compute an orthographic projection
                    float x1 = cosT * x0 + sinT * z0;
                    float y1 = -sinTsinP * x0 + cosP * y0 + cosTsinP * z0;
                    int x3, y3;

                    // now adjust things to get a perspective projection
                    float z1 = cosTcosP * z0 - sinTcosP * x0 - sinP * y0;
                    x1 = x1 * near / (z1 + near + nearToObj);
                    y1 = y1 * near / (z1 + near + nearToObj);

                    x3 = (int) (width_view / 2 + scaleFactor * x1 + 0.5);
                    y3 = (int) (height_view / 2 - scaleFactor * y1 + 0.5);
                    // the 0.5 is to round off when converting to int
                    intersection[x][y][z] = new Point(x3 + offset_x, y3 + offset_y + viewHeight_extra);
//	    		         intersection[x][y][z] = new Point(x3+offset_x,y3+offset_y);

                }
            }
        }
        return;
    }


    public void switchHold() {
        mHold = !mHold;
    }

    /**
     * Determine the space between the first two fingers
     */
    protected float spacing(MotionEvent event) {

        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float temp1 = x * x + y * y;
        double temp2 = temp1;
        //return FloatMath.sqrt(x * x + y * y);
        return (float) Math.sqrt(temp1);
    }

    private void draw_star(Canvas canvas, int x, int y, int z, float star_width) {
        //single 1x1x1 does not draw star
        star_width = star_width * scaleFactor_ratio_sqrt;
        if (!(mGridRowNum == 1 && mGridColNum == 1 && mGridHeiNum == 1)) {
            if (mShow_Grid_Circle) {
                Grid_Circle_paint.setStyle(Style.FILL);
                canvas.drawCircle(intersection[x][y][z].x, intersection[x][y][z].y, mGrid_Circle_Radius, Grid_Circle_paint);
                Grid_Circle_paint.setStyle(Style.STROKE);
            } else if (mShow_Grid_Line) {
                canvas.drawRect(intersection[x][y][z].x - star_width,
                        intersection[x][y][z].y - star_width,
                        intersection[x][y][z].x + star_width,
                        intersection[x][y][z].y + star_width,
                        Grid_Line_paint);
            }
            //if it is a single dot, draw a unfilled square to look better
        } else if (mShow_Grid_Line && !mShow_Grid_Circle) {
//            Grid_Line_paint.setStyle(Style.STROKE);
            canvas.drawRect(intersection[x][y][z].x - star_width / 2,
                    intersection[x][y][z].y - star_width / 2,
                    intersection[x][y][z].x + star_width / 2,
                    intersection[x][y][z].y + star_width / 2,
                    Grid_Line_paint);
        }
    }

    private void One_Second_timer_b4_move() {


        onesecond_timer_b4_move = new CountDownTimer(1000, 100) {
            boolean long_hold = true;
            int timer_count = 0;

            public void onTick(long millisUntilFinished) {
                timer_count++;
//                Log.i(TAG, timer_count + "");
                if (Distance_new_to_downpoint() > width_view / 8) {
                    onesecond_timer_b4_move.cancel();
                    long_hold = false;
                }
            }

            public void onFinish() {
                if (long_hold) {

                    mode = GRID_MOVE;
//                    Log.i(TAG, "mode is " + mode);
                    vibrate_once();
                    invalidate();
                }
            }

        }.start();
    }

    private double Distance_new_to_downpoint() {
        double distance = 0;
        distance = Math.sqrt(Math.pow(down_point_x - new_mx, 2) + Math.pow(down_point_y - new_my, 2));
        return distance;
    }

//
//    /**
//     * Calculate the mid point of the first two fingers
//     */
//    private void midPoint(PointF point, MotionEvent event) {
//        float x = event.getX(0) + event.getX(1);
//        float y = event.getY(0) + event.getY(1);
//        point.set(x / 2, y / 2);
//    }

    private void vibrate_once() {
        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,
                HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING
                        | HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
    }

    public void goto_default_shape() {
//        Log.i(TAG, "here is goto_default_shape");
        scaleFactor = 0;
        azimuth = -20;
        elevation = 15;
        offset_x = 0;
        offset_y = 0;

        if (mGridRowNum == 1) {
            azimuth = -90;
            elevation = 0;
        } else if (mGridColNum == 1) {
            azimuth = 0;
            elevation = 0;
        } else if (mGridHeiNum == 1) {
            azimuth = 0;
            elevation = 90;
        }
    }

    public void clearPassGo() {
        mPassword = "";
        mPassGoDisplayMode = DisplayMode.Correct;
        invalidate();
    }

    public void clearLastStroke() {
        String last_6_number;
        if (mPassword.length() == 12) {
            mPassword = "";
        } else {
            mPassword = mPassword.substring(0, mPassword.length() - 6);
            while (mPassword.length() > 0) {
                last_6_number = mPassword.substring(mPassword.length() - 6, mPassword.length());
                if (last_6_number.equals("000000")) {
                    break;
                } else {
                    mPassword = mPassword.substring(0, mPassword.length() - 6);
                }
            }
        }
        mPassGoDisplayMode = DisplayMode.Correct;
        invalidate();
    }


    public enum DisplayMode {
        Correct,
        Wrong
    }

    /**
     * The call back interface for detecting patterns entered by the user.
     */
    public interface onPassGoListener {

        void onPassGoDetected(String mPassword, boolean mIfWrongPassGo, boolean mIfCorrectPassGo);
    }


    abstract int decide_Grid_Line_Color();

    abstract int decide_Grid_Circle_Color();

    abstract int decide_PassGo_Line_Color();

    abstract int decide_PassGo_Dot_Color();

    abstract int decide_PassGo_Background_Color();

}