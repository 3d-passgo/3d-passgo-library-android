package net.passgo.passgo3d;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.passgo.passgo3d.PassGoView.DisplayMode;

public class ChangePassGoActivity extends Activity {
    private final static String TAG = "ChangePassGoActivity";

    private RelativeLayout currentLayout ;

    private Context mContext;
    private String tmp_password = "";

    private PassGoView_Change mPassGoView_Change;
    private PassGoUtils mPassGoUtils;
    private TextView mDrawPatternTitle;

    private boolean isFirstDraw = true;
    private boolean isRightPattern = false;

//    private RelativeLayout mNextLayout;
    private TextView mSetTextView;
//    private TextView mConfirmTextView;

    private Button defaultbutton = null;
    private Button backbutton = null;
    private Button clearbutton = null;

    private Button continuebutton = null;
    private Button confirmbutton = null;

    private float getx, gety, x_last = 0, y_last = 0;
    private int move_pre_count = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(TAG, "Action was MOVE" + event.getX() + "-------" + event.getY());
                move_pre_count++;
                getx = event.getX();
                gety = event.getY();

                if (x_last != 0 && y_last != 0 && move_pre_count > 30 && isFirstDraw == true) {
                    mPassGoView_Change.azimuth -= (getx - x_last) / 5;
                    mPassGoView_Change.elevation -= (gety - y_last) / 5;
                    clearbutton.setVisibility(View.GONE);
                    backbutton.setVisibility(View.GONE);
                    defaultbutton.setVisibility(View.VISIBLE);
                }


                mPassGoView_Change.invalidate();
                x_last = getx;
                y_last = gety;
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(TAG, "Action was UP");
                x_last = 0;
                y_last = 0;
                move_pre_count = 0;
                return true;

            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // comment out, for new UI
        setActionBarLayout(R.layout.change_passgo_activity_actionbar);
        setContentView(R.layout.change_passgo_activity);

        mContext = this;



        Intent intent = new Intent(this,
                SelectGridSizeActivity.class);

//        intent.putExtra("calling-activity", PassGoGlobalData.PATTERN_CALL_REFRESH);
        startActivity(intent);

    }

    private void initPassGo() {
        Log.i(TAG, "this is initpassgo() ");
        mDrawPatternTitle = (TextView) findViewById(R.id.lockscreen_pattern_draw_activity_title_TEXTVIEW);

        mPassGoView_Change = (PassGoView_Change) findViewById(R.id.ChangePassGo_PassGoView_Change);
//        mPassGoView_Change.mIsChangingPassGo=true;
        mPassGoUtils = new PassGoUtils(this);

        initButton();

        mPassGoView_Change.setOnPassGoListener(new PassGoView_Change.onPassGoListener() {

            @Override
            public void onPassGoDetected(String password, boolean mIfWrongPassGo, boolean mIfCorrectPassGo) {
                // TODO Auto-generated method stub
                if (!password.isEmpty()) {
                    tmp_password = password;
                    Log.i(TAG, "so far tmp PassGo is " + password);
                    if (isFirstDraw) {

                        defaultbutton.setVisibility(View.GONE);
                        backbutton.setVisibility(View.GONE);
                        clearbutton.setVisibility(View.VISIBLE);
                        clearbutton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                mPassGoView_Change.clearPassGo();
                                continuebutton.setEnabled(false);
                                continuebutton.setTextColor(Color.parseColor("#FF999999"));
                                defaultbutton.setVisibility(View.VISIBLE);
                                clearbutton.setVisibility(View.GONE);

                            }
                        });
                        // continuebutton.getBackground().setColorFilter(null);
                        continuebutton.setEnabled(true);
                        continuebutton.setTextColor(Color.parseColor("#FFFFFFFF"));
                        continuebutton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nextStep();
                                continuebutton.setVisibility(View.GONE);
                                confirmbutton.setVisibility(View.VISIBLE);
//	                        defaultbutton.setText(R.string.tab_back);
                                defaultbutton.setVisibility(View.GONE);
                                backbutton.setVisibility(View.VISIBLE);
                                mPassGoView_Change.isFirstDraw = false;
                            }
                        });

                    } else {

                        clearbutton.setVisibility(View.GONE);

                        if (!mIfWrongPassGo) { // so far so good passgo
                            Log.i(TAG, "so far so good passgo");
                            if (mIfCorrectPassGo) { // 2nd draw is good and complete,
                                // ready to save it to be
                                // offical

                                mPassGoView_Change
                                        .setDisplayMode(DisplayMode.Correct);
                                mDrawPatternTitle
                                        .setText(R.string.DRAW_PATTERN_HELP_SUCCESS);

                                //mNextLayout.setVisibility(View.VISIBLE);
                                isRightPattern = true;

                                // confirmbutton.getBackground().setColorFilter(null);
                                confirmbutton.setEnabled(true);
                                confirmbutton.setTextColor(Color
                                        .parseColor("#FFFFFFFF"));
                                confirmbutton
                                        .setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                nextStep();
                                            }
                                        });

                            }
                        } else { // if so far not good already, clear it
                            Log.i(TAG, "so far is not good, clear it");

                            mDrawPatternTitle
                                    .setText(R.string.DRAW_PATTERN_HELP_ERROR);
                            mPassGoView_Change.setDisplayMode(DisplayMode.Wrong);
                            mPassGoView_Change.switchHold();


                            new CountDownTimer(1000, 100) {
                                @Override
                                public void onTick(long m) {
                                }

                                @Override
                                public void onFinish() {
                                    if (PassGoGlobalData.getDataBool(mContext, PassGoGlobalData.PASSGO_CLEAR_LAST, false)) {
                                        mPassGoView_Change.clearLastStroke();
                                    } else {
                                        mPassGoView_Change.clearPassGo();
                                    }
                                    mPassGoView_Change.setDisplayMode(DisplayMode.Correct);
                                    mPassGoView_Change.switchHold();
                                }
                            }.start();

                        }
                    } // end if (isFirstDraw)
                }//end if ( ! password.isEmpty())
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        PassGoGlobalData.Changing_PassGo = true;
        isFirstDraw = true;

        setContentView(R.layout.change_passgo_activity);
        currentLayout= (RelativeLayout) findViewById(R.id.ChangePassGo_activity_layout);
        currentLayout.setBackgroundColor(PassGoGlobalData.getDataInt(mContext,
                PassGoGlobalData.PASSGO_BACKGROUND_COLOR, Color.BLACK));
//        PassGoGlobalData.IsDrawing = true;
        initPassGo();

    }


    public void setActionBarLayout(int layoutId) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater) this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v, layout);
//            mNextLayout = (RelativeLayout) v
//                    .findViewById(R.id.lockscreen_pattern_draw_actionbar_nextselector_RELATIVELAYOUT);

            mSetTextView = (TextView) v
                    .findViewById(R.id.home_launcher_select_actionbar_set_TEXTVIEW);
            mSetTextView.setVisibility(View.VISIBLE);
//            mConfirmTextView = (TextView) v
//                    .findViewById(R.id.home_launcher_select_actionbar_confirm_TEXTVIEW);
//            mConfirmTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initButton() {

        clearbutton = (Button) findViewById(R.id.clearbutton);
        confirmbutton = (Button) findViewById(R.id.confirmbutton);
        backbutton = (Button) findViewById(R.id.backbutton);
        defaultbutton = (Button) findViewById(R.id.defaultbutton);


        backbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "backbutton pressed");

                if (!isFirstDraw) {

                    isFirstDraw = true;
                    mPassGoView_Change.isFirstDraw = true;
                    mDrawPatternTitle.setText(R.string.DRAW_PATTERN_HELP);
                    defaultbutton.setText(R.string.tab_default);
                    continuebutton.setVisibility(View.VISIBLE);
                    continuebutton.setEnabled(false);
                    continuebutton.setTextColor(Color.parseColor("#FF999999"));
                    confirmbutton.setVisibility(View.GONE);
//                    mConfirmTextView.setVisibility(View.INVISIBLE);
                    mSetTextView.setVisibility(View.VISIBLE);
                    mPassGoView_Change.clearPassGo();


                } else {
//					jumptoMain();
                    finish();
                }


            }
        });


        defaultbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "defaultbutton clicked");

                if (!isFirstDraw) { //this is impossible situation

                } else {
                    mPassGoView_Change.goto_default_shape();

                    mPassGoView_Change.clearPassGo();
                    defaultbutton.setVisibility(View.GONE);
                    backbutton.setVisibility(View.VISIBLE);

                }


            }
        });


        continuebutton = (Button) findViewById(R.id.continuebutton);
        continuebutton.setEnabled(false);
        continuebutton.setTextColor(Color.parseColor("#FF999999"));

        clearbutton.setVisibility(View.GONE);
        defaultbutton.setVisibility(View.GONE);

        confirmbutton.setVisibility(View.GONE);
        confirmbutton.setEnabled(false);
        confirmbutton.setTextColor(Color.parseColor("#FF999999"));

        if (!(mPassGoView_Change.azimuth == -20 && mPassGoView_Change.elevation == 15 && mPassGoView_Change.scaleFactor == 150)) {
            defaultbutton.setVisibility(View.VISIBLE);
            clearbutton.setVisibility(View.GONE);
            backbutton.setVisibility(View.GONE);
        }
    }

    private void nextStep() {
        if (isFirstDraw) {
            // first time to draw pattern, save it
            mPassGoUtils.savePassGo(tmp_password, false);
            Log.i(TAG, "save the 1st draw to be the unofficial pattern" + tmp_password);
            isFirstDraw = false;
            mPassGoView_Change.clearPassGo();
            mDrawPatternTitle.setText(R.string.DRAW_PATTERN_HELP_II);

            clearbutton.setVisibility(View.GONE);
            defaultbutton.setVisibility(View.VISIBLE);
        } else if (isRightPattern) {

            // save row,col,hei,azimuth,elevation,offset_x,offset_y,scalefactor
            mPassGoUtils.savePassGo(tmp_password, true);
            Toast.makeText(this, R.string.PassGo_changed, Toast.LENGTH_SHORT).show();
//            PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.IS_OFFICIAL_PASSGO, true);
            int row = PassGoGlobalData.getDataInt(mContext,
                    PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_ROW, 2);
            int col = PassGoGlobalData.getDataInt(mContext,
                    PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_COL, 2);
            int hei = PassGoGlobalData.getDataInt(mContext,
                    PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_HEI, 2);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.OFFICIAL_PASSGO_NUM_ROW, row);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.OFFICIAL_PASSGO_NUM_COL, col);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.OFFICIAL_PASSGO_NUM_HEI, hei);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.AZIMUTH, mPassGoView_Change.azimuth);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.ELEVATION, mPassGoView_Change.elevation);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.OFFSET_X, mPassGoView_Change.offset_x);
            PassGoGlobalData.setDataInt(mContext,
                    PassGoGlobalData.OFFSET_Y, mPassGoView_Change.offset_y);
            PassGoGlobalData.setDataFloat(mContext,
                    PassGoGlobalData.SCALEFACTOR, mPassGoView_Change.scaleFactor);

            Log.i(TAG, "row col hei is" + row + col + hei);
            PassGoGlobalData.setDataStr(mContext,
                    PassGoGlobalData.KEY_CUR_LOCK_SCREEN_TYPE, "Pattern");

            this.finish();

            Log.i(TAG, "");
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        PassGoGlobalData.sizeChanged = false;
//        PassGoGlobalData.Changing_PassGo = false;
//        PassGoGlobalData.IsDrawing = false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.faq) {
            Intent faq_intent = new Intent(this, PassGoFAQActivity.class);
            faq_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(faq_intent);
        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

}
