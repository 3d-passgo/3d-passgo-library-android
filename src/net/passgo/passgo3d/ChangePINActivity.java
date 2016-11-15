package net.passgo.passgo3d;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ChangePINActivity extends Activity implements OnClickListener{
	private final static String TAG = "ChangePINActivity";
	
	private RelativeLayout mNextLayout;
	private TextView mNextTextView;
	
	private TextView mBackupPINTitle;
	private ImageButton mDeleteButton;
	private EditText mPasswordEditText;
	private ImageButton mNum1Button;
	private ImageButton mNum2Button;
	private ImageButton mNum3Button;
	private ImageButton mNum4Button;
	private ImageButton mNum5Button;
	private ImageButton mNum6Button;
	private ImageButton mNum7Button;
	private ImageButton mNum8Button;
	private ImageButton mNum9Button;
	private ImageButton mNum0Button;
	
	private boolean mIsFirstTime = true;
	private String mPINPassword = null;
	
	private Button cancelbutton = null;
	private Button continuebutton = null;
	private Button confirmbutton = null;
	private boolean calledByPassGo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG,"entering ChangePINActivity");
		setContentView(R.layout.change_pin_activity);
		setActionBarLayout(R.layout.change_pin_activity_actionbar);
		
		mBackupPINTitle = (TextView) findViewById(R.id.lockscreen_pattern_backup_pin_activity_title_TEXTVIEW);
		mDeleteButton = (ImageButton) findViewById(R.id.lockscreen_pattern_backup_pin_delete_password_IMAGEBUTTON);
		mPasswordEditText = (EditText) findViewById(R.id.lockscreen_pattern_backup_pin_activity_password_char_EDITTEXT);
		
		
		mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
        		
        		if (mPasswordEditText.getText().toString().length()>=4){
        		    continuebutton.setEnabled(true);
        		    continuebutton.setTextColor(Color.parseColor("#FFFFFFFF"));
        		     // Create clean button.
        		    continuebutton.setOnClickListener(new OnClickListener() {
        		     	@Override
        		     	public void onClick(View v) {
        		     		if (mPasswordEditText.getText().toString().length()>=4){
            		     		nextStep();
            		     		confirmbutton.setVisibility(View.VISIBLE);
            		     		continuebutton.setVisibility(View.GONE);
        		     		} else {
        		    			Toast.makeText(getApplicationContext(), R.string.INPUT_PIN_LESS_THAN_FHOUR_TOAST,
        		   				     Toast.LENGTH_SHORT).show();
        		     		}

        		     	}
        		     });
        		}
        		
        		
        		
        		if(!mPasswordEditText.getText().toString().isEmpty() && !mIsFirstTime )
        		{
        			if(mPINPassword != null) {
        				if(mPINPassword.equals(mPasswordEditText.getText().toString())) {
        					
        					confirmbutton.setEnabled(true);
        					confirmbutton.setTextColor(Color.parseColor("#FFFFFFFF"));
        					confirmbutton.setOnClickListener(new OnClickListener() {
        				     	@Override
        				     	public void onClick(View v) {
        				     		nextStep();
        				     	}
        				     });

        				}
        			}
        			
        		}
            	
            }
        });
		
		
		mNum1Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_1_IMAGEBUTTON);
		mNum2Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_2_IMAGEBUTTON);
		mNum3Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_3_IMAGEBUTTON);
		mNum4Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_4_IMAGEBUTTON);
		mNum5Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_5_IMAGEBUTTON);
		mNum6Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_6_IMAGEBUTTON);
		mNum7Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_7_IMAGEBUTTON);
		mNum8Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_8_IMAGEBUTTON);
		mNum9Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_9_IMAGEBUTTON);
		mNum0Button = (ImageButton) findViewById(R.id.lockscreen_pattern_pin_activity_i_activity_0_IMAGEBUTTON);
		
		mDeleteButton.setOnClickListener(this);
		mNum1Button.setOnClickListener(this);
		mNum2Button.setOnClickListener(this);
		mNum3Button.setOnClickListener(this);
		mNum4Button.setOnClickListener(this);
		mNum5Button.setOnClickListener(this);
		mNum6Button.setOnClickListener(this);
		mNum7Button.setOnClickListener(this);
		mNum8Button.setOnClickListener(this);
		mNum9Button.setOnClickListener(this);
		mNum0Button.setOnClickListener(this);
		
		if(getIntent().getBooleanExtra("callingPinSetting", false))
		{
			calledByPassGo = true;
		}
		
		initButton();
	    
	}
	
	private void initButton(){
		cancelbutton = (Button) findViewById(R.id.cancelbutton);
	    // Create clean button.
	    cancelbutton.setOnClickListener(new OnClickListener() {
	     	@Override
	     	public void onClick(View v) {
	     		
//	     		jumptoMainDialog();
	     		finish();
	     		
	     	}
	     });
	    
	    
	    continuebutton = (Button) findViewById(R.id.continuebutton);
	    
	    continuebutton.setEnabled(false);
	    continuebutton.setTextColor(Color.parseColor("#FF999999"));

	    
	    confirmbutton = (Button) findViewById(R.id.confirmbutton);
	    confirmbutton.setVisibility(View.GONE);
	    confirmbutton.setEnabled(false);
	    confirmbutton.setTextColor(Color.parseColor("#FF999999"));
	}
	
	private void goToPreStep() {
		this.finish();
	}
	
	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar( );
	    if(actionBar != null){
	        actionBar.setDisplayShowHomeEnabled(false);
	        actionBar.setDisplayShowTitleEnabled(false);
	        actionBar.setDisplayHomeAsUpEnabled(false);
	        actionBar.setDisplayShowCustomEnabled(true);
	        LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(layoutId, null);
	        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        actionBar.setCustomView(v,layout);
	        mNextLayout = (RelativeLayout) v.findViewById(R.id.lockscreen_pattern_backup_pin_actionbar_nextselector_RELATIVELAYOUT);
	        mNextLayout.setOnClickListener(this);
	        mNextTextView = (TextView) v.findViewById(R.id.home_launcher_select_actionbar_refresh_TEXTVIEW);
	    }
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id =v.getId();
		
			if(id==R.id.lockscreen_pattern_backup_pin_actionbar_nextselector_RELATIVELAYOUT)
				nextStep();
			else if(id== R.id.lockscreen_pattern_backup_pin_delete_password_IMAGEBUTTON)
				deleteNum();
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_1_IMAGEBUTTON)
				enterNum("1");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_2_IMAGEBUTTON)
				enterNum("2");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_3_IMAGEBUTTON)
				enterNum("3");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_4_IMAGEBUTTON)
				enterNum("4");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_5_IMAGEBUTTON)
				enterNum("5");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_6_IMAGEBUTTON)
				enterNum("6");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_7_IMAGEBUTTON)
				enterNum("7");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_8_IMAGEBUTTON)
				enterNum("8");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_9_IMAGEBUTTON)
				enterNum("9");
			else if(id== R.id.lockscreen_pattern_pin_activity_i_activity_0_IMAGEBUTTON)
				enterNum("0");

	}
	
	private void enterNum(String numStr) {
		
		
		String curPwd = mPasswordEditText.getText().toString();
		curPwd += numStr;
		mPasswordEditText.setText(curPwd);
		
		if (curPwd.length()>=4){
		    continuebutton.setEnabled(true);
		    System.out.println("number has been entered, continue should light up!, current passwor dlength" + curPwd );
		    continuebutton.setTextColor(Color.parseColor("#FFFFFFFF"));
		     // Create clean button.
		    continuebutton.setOnClickListener(new OnClickListener() {
		     	@Override
		     	public void onClick(View v) {
		     		if (mPasswordEditText.getText().toString().length()>=4){
    		     		nextStep();
    		     		confirmbutton.setVisibility(View.VISIBLE);
    		     		continuebutton.setVisibility(View.GONE);
		     		} else {
		    			Toast.makeText(getApplicationContext(), R.string.INPUT_PIN_LESS_THAN_FHOUR_TOAST,
   		   				     Toast.LENGTH_SHORT).show();
   		     		}
		     	}
		     });
		}

	    

		
		if(!mPasswordEditText.getText().toString().isEmpty() && !mIsFirstTime )
		{
			if(mPINPassword != null) {
				if(mPINPassword.equals(mPasswordEditText.getText().toString())) {
					confirmbutton.setEnabled(true);
					confirmbutton.setTextColor(Color.parseColor("#FFFFFFFF"));
					confirmbutton.setOnClickListener(new OnClickListener() {
				     	@Override
				     	public void onClick(View v) {
				     		nextStep();
				     	}
				     });
					
				}
			}
			
		}
	}
	
	private void deleteNum() {
		String curPwd = mPasswordEditText.getText().toString();
		try {
			mPasswordEditText.setText(curPwd.substring(0, curPwd.length()-1));
        } catch (Exception e) {
        	mPasswordEditText.setText("");
        }
	}
	
	private void nextStep() {
		if(mPasswordEditText.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), R.string.DRAW_PATTERN_PIN_HELP_ERROR_TOAST,
				     Toast.LENGTH_SHORT).show();
		} 
		else {
			
			if(mIsFirstTime) {
				mPINPassword = mPasswordEditText.getText().toString();
				mIsFirstTime = false;
				mPasswordEditText.setText(null);
				//mNextTextView.setText(R.string.COMMON_OK);
				mBackupPINTitle.setText(R.string.DRAW_PATTERN_PIN_HELP_II);
				
			}
			else {
				if(mPINPassword != null) {
					if(mPINPassword.equals(mPasswordEditText.getText().toString())) {
						//input the right PIN, save it
						SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
						Editor editor = preference.edit();
						editor.putString(PassGoGlobalData.OFFICIAL_PIN_PWD, mPINPassword);
						Toast.makeText(this, R.string.PIN_changed, Toast.LENGTH_SHORT).show();
						editor.commit();
						
						this.finish();
					}
					else {
						//the wrong PIN, reset a new one again
						mPasswordEditText.setText(null);
						mBackupPINTitle.setText(R.string.DRAW_PATTERN_PIN_HELP_ERROR);
					}
				}
			}
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
	
	private void launchMarket() {
	    Uri uri = Uri.parse("market://details?id=" + getPackageName());
	    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	        startActivity(myAppLinkToMarket);
	    } catch (ActivityNotFoundException e) {
	        Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
	    }
	}
}
