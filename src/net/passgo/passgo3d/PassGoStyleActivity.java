package net.passgo.passgo3d;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.app.AlertDialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.RelativeLayout;
import android.widget.SeekBar;
//import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class PassGoStyleActivity extends Activity
        implements ColorPickerView.OnColorChangedListener {
    public static final String TAG = "PassGoStyleActivity";

    private Context mContext;
    private int what = 1;
    private static final int GO_LINE = 1;
    private static final int GO_DOT = 2;
    private static final int GRID = 3;
    private static final int GRID_CIRCLE = 5;
    private static final int BG = 4;


    private Button button_line_color = null;
    private Button button_dot_color = null;
    private Button button_grid_color = null;
    private Button button_grid_circle_color = null;
    private Button button_bg_color = null;
    private Button button_cancel = null;
    private Button button_save = null;
    private Button button_default = null;
    private Button button_hide_passgo = null;
    private Button button_show_circles = null;
    private Button button_show_grid = null;

    private boolean isHidePassGo;
    private boolean isShowCircles;
    private boolean isShowGrid;

    private int mPatternLineColor;
    private int mPatternLineColor_dot;

    private boolean bg_color_changed = false;

    private int mPassGoLineColor;
    private int mPassGoDotColor;
    private int mGridColor;
    private int mGridCircleColor;
    private int mBackgroundColor;


    private PassGoView_Style mPassGoView_Style;
    private ColorPickerView mColorPicker;
    private ColorPickerView mColorPicker_dot;
    private ColorPickerPanelView mOldColor_dot;
    private ColorPickerPanelView mNewColor_dot;
    private boolean passGolinecolor = false;
    private int mPatternLineThickness = -1;
    private int mPatternDotThickness = -1;
    private int mGridLineThickness = -1;
    private int mGridCircleThickness = -1;
    private SeekBar mThicknesSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passgo_style_activity);


        mContext = this;


        mPassGoLineColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_LINE_COLOR, Color.RED);
        mPassGoDotColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_DOT_COLOR, Color.RED);
        mGridColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_LINE_COLOR, Color.WHITE);
        mGridCircleColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_COLOR, Color.WHITE);
        mBackgroundColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_BACKGROUND_COLOR, Color.GRAY);
        isHidePassGo = PassGoGlobalData.getDataBool(mContext, PassGoGlobalData.HIDE_PASSGO, false);
        isShowCircles = PassGoGlobalData.getDataBool(mContext, PassGoGlobalData.SHOW_GRID_CIRCLE, true);
        isShowGrid = PassGoGlobalData.getDataBool(mContext, PassGoGlobalData.SHOW_GRID_LINE, true);


        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        switch (callingActivity) {
            case PassGoGlobalData.PASSGO_LINE_COLOR_CODE:
                passGolinecolor = true;

                break;
            case PassGoGlobalData.PASSGO_DOT_COLOR_CODE:
                passGolinecolor = false;

                break;
        }
        passGolinecolor = true;

        setActionBarLayout(passGolinecolor);


        //newly adding here
        mPassGoView_Style = (PassGoView_Style) findViewById(R.id.PassGoView_Style);


        button_line_color = (Button) findViewById(R.id.button_line_color);
        button_dot_color = (Button) findViewById(R.id.button_dot_color);
        button_grid_color = (Button) findViewById(R.id.button_grid_color);
        button_grid_circle_color = (Button) findViewById(R.id.button_grid_circle_color);
        button_bg_color = (Button) findViewById(R.id.button_bg_color);
        button_cancel = (Button) findViewById(R.id.pallet_cancel_button);
        button_save = (Button) findViewById(R.id.pallet_save_button);
        button_default = (Button) findViewById(R.id.pallet_default_button);
        button_hide_passgo = (Button) findViewById(R.id.button_hide_passgo);
        button_show_circles = (Button) findViewById(R.id.button_show_circles);
        button_show_grid = (Button) findViewById(R.id.button_show_grid);

        reset_show_buttons();


        button_hide_passgo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isHidePassGo = !isHidePassGo;
                reset_show_buttons();
                mPassGoView_Style.update_show(1, isHidePassGo);

            }
        });

        button_show_circles.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isShowCircles = !isShowCircles;
                reset_show_buttons();
                mPassGoView_Style.update_show(2, isShowCircles);

            }
        });

        button_show_grid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isShowGrid = !isShowGrid;
                reset_show_buttons();
                mPassGoView_Style.update_show(3, isShowGrid);

            }
        });


//		button_line_color.setTextColor(Color.BLACK);
        button_line_color.setBackgroundColor(Color.parseColor("#000080"));

        button_line_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color_buttons(GO_LINE);
//				button_line_color.setTextColor(Color.BLACK);
                button_line_color.setBackgroundColor(Color.parseColor("#000080"));
                mThicknesSeekBar.setEnabled(true);
                if (mPatternLineThickness >= 0) {
                    mThicknesSeekBar.setProgress(mPatternLineThickness);
                } else {
                    mThicknesSeekBar.setProgress(PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGo_LINE_THICKNESS, 3));
                }

            }
        });
        button_dot_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color_buttons(GO_DOT);
                button_dot_color.setBackgroundColor(Color.parseColor("#000080"));
                mThicknesSeekBar.setEnabled(true);
                if (mPatternDotThickness >= 0) {
                    mThicknesSeekBar.setProgress(mPatternDotThickness);
                } else {
                    mThicknesSeekBar.setProgress(PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_DOT_RADIUS, 3));
                }
            }
        });
        button_grid_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color_buttons(GRID);
                button_grid_color.setBackgroundColor(Color.parseColor("#000080"));
                mThicknesSeekBar.setEnabled(true);
                if (mPatternLineThickness >= 0) {
                    mThicknesSeekBar.setProgress(mGridLineThickness);
                } else {
                    mThicknesSeekBar.setProgress(PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_LINE_THICKNESS, 1));
                }

            }
        });

        button_grid_circle_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color_buttons(GRID_CIRCLE);
                button_grid_circle_color.setBackgroundColor(Color.parseColor("#000080"));
                mThicknesSeekBar.setEnabled(true);
                if (mPatternLineThickness >= 0) {
                    mThicknesSeekBar.setProgress(mGridCircleThickness);
                } else {
                    mThicknesSeekBar.setProgress(PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_THICKNESS, 1));
                }

            }
        });

        button_bg_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_color_buttons(BG);
                button_bg_color.setBackgroundColor(Color.parseColor("#000080"));
                mThicknesSeekBar.setEnabled(false);
            }
        });

        button_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreStep();


            }
        });

        button_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_LINE_COLOR, mPassGoLineColor);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_DOT_COLOR, mPassGoDotColor);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_LINE_COLOR, mGridColor);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_COLOR, mGridCircleColor);

                if (bg_color_changed) {
                    Log.i("cc", "type chcanged for bg");
                    PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_BACKGROUND_COLOR, mBackgroundColor);
                    PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.BACKGROUND_TYPE, 4);
                }

                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGo_LINE_THICKNESS, mPatternLineThickness);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_DOT_RADIUS, mPatternDotThickness);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_LINE_THICKNESS, mGridLineThickness);
                PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_THICKNESS, mGridCircleThickness);

                PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.HIDE_PASSGO, isHidePassGo);
                PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.SHOW_GRID_CIRCLE, isShowCircles);
                PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.SHOW_GRID_LINE, isShowGrid);
                Toast.makeText(mContext, R.string.style_changed, Toast.LENGTH_SHORT).show();

                goToPreStep();

            }

        });


        button_default.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setMessage(R.string.change_to_default_question)
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        returnDefaultStyle();
                                        reset_color_buttons(GO_LINE);
                                        button_line_color.setBackgroundColor(Color.parseColor("#000080"));
                                        Toast.makeText(mContext, R.string.style_changed_to_default, Toast.LENGTH_SHORT).show();

                                        finish();

                                    }
                                })
                        .setNegativeButton(android.R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // do nothing
                                    }
                                })
                        .create().show();

            }
        });

        init(passGolinecolor);

        init_seekbar();

    }


    private void init_seekbar() {
        // TODO Auto-generated method stub
        mPatternLineThickness = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGo_LINE_THICKNESS, 3);
        mPatternDotThickness = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_DOT_RADIUS, 3);
        mGridLineThickness = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_LINE_THICKNESS, 1);
        mGridCircleThickness = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_THICKNESS, 1);


        mThicknesSeekBar = (SeekBar) findViewById(R.id.pallet_seekbar);
        mThicknesSeekBar.setMax(7);
        mThicknesSeekBar.setProgress(mPatternLineThickness);
        mThicknesSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub


                switch (what) {
                    case 1: //line
                        mPatternLineThickness = progress;
                        break;
                    case 2: //dot
                        mPatternDotThickness = progress;
                        break;
                    case 3: //grid line
                        mGridLineThickness = progress;
                        break;
                    case 5: //grid circle
                        mGridCircleThickness = progress;
                        break;
                    default:

                        break;

                }
                mPassGoView_Style.update_thick(what, progress);
            }
        });

    }


    private void init(boolean passGolinecolor) {
        // TODO Auto-generated method stub
        // To fight color banding.
        final boolean passgolinecolor = passGolinecolor;
        getWindow().setFormat(PixelFormat.RGBA_8888);


        if (passgolinecolor) {
            mPatternLineColor = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_LINE_COLOR, Color.WHITE);
            mColorPicker = (ColorPickerView) findViewById(R.id.color_picker_view);

            mColorPicker.setOnColorChangedListener(this);
            mColorPicker.setColor(mPatternLineColor, true);
        } else {
            mPatternLineColor_dot = PassGoGlobalData.getDataInt(mContext, PassGoGlobalData.PASSGO_DOT_COLOR, Color.WHITE);
            mColorPicker_dot = (ColorPickerView) findViewById(R.id.color_picker_view);

            mColorPicker_dot.setOnColorChangedListener(this);
            mColorPicker_dot.setColor(mPatternLineColor_dot, true);
            mOldColor_dot.setColor(mPatternLineColor_dot);
            mNewColor_dot.setColor(mPatternLineColor_dot);

        }

    }

    private void setActionBarLayout(boolean passGolinecolor) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void goToPreStep() {
        // TODO Auto-generated method stub
        this.finish();
    }

    @Override
    public void onColorChanged(int color) {
        // TODO Auto-generated method stub

        switch (what) {
            case 1: //line
                mPassGoLineColor = color;
                break;
            case 2: //dot
                mPassGoDotColor = color;
                break;
            case 3: //grid line
                mGridColor = color;
                break;
            case 4: //bg
                mBackgroundColor = color;
                bg_color_changed = true;
                break;
            case 5: //grid circle
                mGridCircleColor = color;
                break;
            default:
                break;
        }
        mPassGoView_Style.update_Color(what, color);
    }

    private void reset_color_buttons(int code) {
        what = code;

        button_line_color.setBackgroundColor(Color.parseColor("#ff548bd4"));
        button_dot_color.setBackgroundColor(Color.parseColor("#ff548bd4"));
        button_grid_color.setBackgroundColor(Color.parseColor("#ff548bd4"));
        button_grid_circle_color.setBackgroundColor(Color.parseColor("#ff548bd4"));
        button_bg_color.setBackgroundColor(Color.parseColor("#ff548bd4"));
    }


    private void reset_show_buttons() {

        if (isHidePassGo) {
            button_hide_passgo.setBackgroundColor(Color.parseColor("#000080"));
        } else {
            button_hide_passgo.setBackgroundColor(Color.parseColor("#ff548bd4"));
        }
        if (isShowCircles) {
            button_show_circles.setBackgroundColor(Color.parseColor("#000080"));
        } else {
            button_show_circles.setBackgroundColor(Color.parseColor("#ff548bd4"));
        }
        if (isShowGrid) {
            button_show_grid.setBackgroundColor(Color.parseColor("#000080"));
        } else {
            button_show_grid.setBackgroundColor(Color.parseColor("#ff548bd4"));
        }
    }

    private void returnDefaultStyle() {
        PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.SHOW_GRID_CIRCLE, true);
        PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.SHOW_GRID_LINE, true);
        PassGoGlobalData.setDataBool(mContext, PassGoGlobalData.HIDE_PASSGO, false);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_LINE_COLOR, Color.RED);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_DOT_COLOR, Color.RED);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGo_LINE_THICKNESS, 3);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_DOT_RADIUS, 3);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_LINE_THICKNESS, 1);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_THICKNESS, 1);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_LINE_COLOR, Color.WHITE);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.GRID_CIRCLE_COLOR, Color.WHITE);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.PASSGO_BACKGROUND_COLOR, Color.BLACK);
        PassGoGlobalData.setDataInt(mContext, PassGoGlobalData.BACKGROUND_TYPE, 1);


    }

}
