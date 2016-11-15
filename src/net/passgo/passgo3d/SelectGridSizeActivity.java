package net.passgo.passgo3d;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;


public class SelectGridSizeActivity extends Activity implements
        OnValueChangeListener, OnScrollListener, Formatter {

    private static final String TAG = "SelectGridSizeActivity";

    private Context mContext;
    private RelativeLayout mConfirmPattern;
    private NumberPicker mRowPicker;
    private NumberPicker mColPicker;
    private NumberPicker mHeiPicker;

    private int mRowNum;
    private int mColNum;
    private int mHeiNum;
    private boolean valueChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_grid_size_activity);
//        setActionBarLayout();

        mContext = this;
        mRowNum = PassGoGlobalData.getDataInt(mContext,
                PassGoGlobalData.OFFICIAL_PASSGO_NUM_ROW, 2);
        mColNum = PassGoGlobalData.getDataInt(mContext,
                PassGoGlobalData.OFFICIAL_PASSGO_NUM_COL, 2);
        mHeiNum = PassGoGlobalData.getDataInt(mContext,
                PassGoGlobalData.OFFICIAL_PASSGO_NUM_HEI, 2);

        mRowPicker = (NumberPicker) findViewById(R.id.lockscreen_type_pattern_kind_activity_pattern_row_NUMBERPICKER);
        mRowPicker.setFormatter(this);
        mRowPicker.setOnValueChangedListener(this);
        mRowPicker.setOnScrollListener(this);
        mRowPicker.setMaxValue(19);
        mRowPicker.setMinValue(1);
        mRowPicker.setValue(mRowNum);

        mColPicker = (NumberPicker) findViewById(R.id.lockscreen_type_pattern_kind_activity_pattern_col_NUMBERPICKER);
        mColPicker.setFormatter(this);
        mColPicker.setOnValueChangedListener(this);
        mColPicker.setOnScrollListener(this);
        mColPicker.setMaxValue(19);
        mColPicker.setMinValue(1);
        mColPicker.setValue(mColNum);


        mHeiPicker = (NumberPicker) findViewById(R.id.lockscreen_type_pattern_kind_activity_pattern_hei_NUMBERPICKER);
        mHeiPicker.setFormatter(this);
        mHeiPicker.setOnValueChangedListener(this);
        mHeiPicker.setOnScrollListener(this);
        mHeiPicker.setMaxValue(19);
        mHeiPicker.setMinValue(1);
        mHeiPicker.setValue(mHeiNum);

        mConfirmPattern = (RelativeLayout) findViewById(R.id.lockscreen_patternbutton_layout_RELATIVELAYOUT);
        mConfirmPattern.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                goToNextStep();
            }
        });


    }

   @Override
    public String format(int value) {
        // TODO Auto-generated method stub
        String tmpStr = String.valueOf(value);
        return tmpStr;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        // TODO Auto-generated method stub
        valueChanged = true;
        Log.i(TAG, "value changed");

        int id = picker.getId();
        if (id == R.id.lockscreen_type_pattern_kind_activity_pattern_row_NUMBERPICKER) {
            mRowNum = newVal;
        } else if (id == R.id.lockscreen_type_pattern_kind_activity_pattern_col_NUMBERPICKER) {
            mColNum = newVal;
        } else if (id == R.id.lockscreen_type_pattern_kind_activity_pattern_hei_NUMBERPICKER) {
            mHeiNum = newVal;
        }


    }

    private void goToNextStep() {
        //
        PassGoGlobalData.setDataInt(mContext,
                PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_ROW, mRowNum);
        PassGoGlobalData.setDataInt(mContext,
                PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_COL, mColNum);
        PassGoGlobalData.setDataInt(mContext,
                PassGoGlobalData.UNOFFICIAL_PASSGO_NUM_HEI, mHeiNum);
        if (valueChanged) {
            PassGoGlobalData.sizeChanged = true;
            Log.i(TAG, "SIZE changed");
        }

        this.finish();

    }


}
