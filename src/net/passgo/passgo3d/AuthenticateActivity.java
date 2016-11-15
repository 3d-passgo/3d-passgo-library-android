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

public class AuthenticateActivity extends Activity {

    private final static String TAG = "AuthenticateActivity";
    private RelativeLayout currentLayout;
    private Context mContext;
    private PassGoView_Login mAuthenticate_PassGoView_Login;
    private TextView mPassGoGuideInfoTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarLayout(R.layout.authenticate_activity_actionbar);
        setContentView(R.layout.authenticate_activity);

        mContext = this;
        currentLayout = (RelativeLayout) findViewById(R.id.Authenticate_Activity_Layout);
        currentLayout.setBackgroundColor(PassGoGlobalData.getDataInt(mContext,
                PassGoGlobalData.PASSGO_BACKGROUND_COLOR, Color.BLACK));
        initPassGo();

    }

    private void initPassGo() {

        mPassGoGuideInfoTextview = (TextView) findViewById(R.id.Authenticate_PassGo_Guide_Info_TEXTVIEW);
        mAuthenticate_PassGoView_Login = (PassGoView_Login) findViewById(R.id.Authenticate_PassGoView_Login);

        mAuthenticate_PassGoView_Login.setOnPassGoListener(new PassGoView_Login.onPassGoListener() {

            @Override
            public void onPassGoDetected(String password, boolean mIfWrongPassGo, boolean mIfCorrectPassGo) {
                // TODO Auto-generated method stub

                if (!mIfWrongPassGo) {
                    if (mIfCorrectPassGo) { //right pattern
                        PassGoGlobalData.Wrong_PassGo_Count=0;
                        Intent intent = new Intent(mContext, ChangePassGoActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else { //wrong pattern
                    mPassGoGuideInfoTextview.setVisibility(View.VISIBLE);

                    if (PassGoGlobalData.Wrong_PassGo_Count > mAuthenticate_PassGoView_Login.mAllowedTryTime) { // if failed too many times
                        String delay = mContext.getResources().getString(R.string.delay);
                        String seconds = mContext.getResources().getString(R.string.seconds);
                        mPassGoGuideInfoTextview.setText(delay + " 30 " + seconds);
                        new CountDownTimer(mAuthenticate_PassGoView_Login.mFreezeTime, 1000) {

                            @Override
                            public void onTick(long m) {
                            }

                            @Override
                            public void onFinish() {

                                mPassGoGuideInfoTextview.setVisibility(View.INVISIBLE);
                            }
                        }.start();


                    } else { // if not failed more than allowed times

                        mPassGoGuideInfoTextview.setText(R.string.LOGIN_PATTERN_HELP_ERROR);

                        new CountDownTimer(mAuthenticate_PassGoView_Login.mWrongPassGoLastTime * 3, 100) {

                            @Override
                            public void onTick(long m) {
                            }

                            @Override
                            public void onFinish() {

                                mPassGoGuideInfoTextview.setVisibility(View.INVISIBLE);
                            }
                        }.start();
                    }

                }
            }

        });

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
            LayoutParams layout = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v, layout);

        }
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
