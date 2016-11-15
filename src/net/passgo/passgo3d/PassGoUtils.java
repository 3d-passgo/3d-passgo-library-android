package net.passgo.passgo3d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PassGoUtils {
	private Context mContext;
	private SharedPreferences preference;

	 public PassGoUtils(Context context) {
	        mContext = context;
	        preference = PreferenceManager.getDefaultSharedPreferences(mContext);

	 }

    public void savePassGo(String password, boolean isOfficial){
    	Editor editor = preference.edit();
    	if(isOfficial) {
    		editor.putString(PassGoGlobalData.OFFICIAL_PASSGO_PWD, password);
    	}
    	else {
    		editor.putString(PassGoGlobalData.UNOFFICIAL_PASSGO_PWD, password);
    	}
    	editor.commit();
    }

    public String getPassGo(boolean isOfficial){
    	if(isOfficial) {
    		return preference.getString(PassGoGlobalData.OFFICIAL_PASSGO_PWD, "");
    	}
    	else {
    		return preference.getString(PassGoGlobalData.UNOFFICIAL_PASSGO_PWD, "");
    	}
    }

    public boolean checkIfCorrectPassGo(String password, boolean isOfficial) {
    	String stored = getPassGo(isOfficial);
    	if(stored != null){
    		System.out.println(isOfficial + " stored is " + stored);
    		return  password.contains(stored)?true:false;
    	}
    	return false;
    }

    public boolean checkIfWrongPassGo(String password, boolean isOfficial) {
    	String stored = getPassGo(isOfficial);

    	if(stored != null && !stored.isEmpty()){
    		if (password.length()<=stored.length()){
    			return  password.equals(stored.substring(0,password.length()))?false:true;
    		} else {
    			return  true;
    		}
    	}
    	return false;
    }

}
