package net.passgo.passgo3d;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PassGoGlobalData {
	public static String TOP_PROCESS = "";
	public static String TOP_PROCESS_2 = "";
	public static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
	public static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
	public static List<String> TempStopProtectPacknames=new ArrayList<String>();
	public static int ClearDelay=10;
	public static boolean sizeChanged=false;
    public static final int BACKGROUND_COLOR_CODE = 1009;
    public static final int PASSGO_DOT_COLOR_CODE = 1004;
    public static final int PASSGO_LINE_COLOR_CODE = 1005;
    public static int Wrong_PassGo_Count = 0;
	public static String AllowedProcessname="";
	public final static String KEY_FIRST_START = "key_first_start";
	public final static String KEY_TIMER_TRIGGER = "key_timer_trigger";
//	public final static String IS_OFFICIAL_PASSGO = "is_official_passgo";
	public final static String OFFICIAL_PASSGO_NUM_ROW = "official_passgo_num_row";
	public final static String OFFICIAL_PASSGO_NUM_COL = "official_passgo_num_col";
	public final static String OFFICIAL_PASSGO_NUM_HEI = "official_passgo_num_hei";
	public final static String OFFICIAL_PASSGO_PWD = "official_passgo_pwd";
	public final static String OFFICIAL_PIN_PWD = "official_pin_pwd";
	public final static String UNOFFICIAL_PASSGO_NUM_ROW = "unofficial_passgo_num_row";
	public final static String UNOFFICIAL_PASSGO_NUM_COL = "unofficial_passgo_num_col";
	public final static String UNOFFICIAL_PASSGO_NUM_HEI = "unofficial_passgo_num_hei";
	public final static String UNOFFICIAL_PASSGO_PWD = "unofficial_passgo_pwd";
	public final static String UNOFFICIAL_PIN_PWD = "unofficial_pin_pwd";
	public final static String KEY_LOCK_SCREEN_SERVICE = "key_lock_screen_service";
	public final static String KEY_APP_LOCK_SERVICE = "key_app_lock_service";
	public final static String KEY_CUR_LOCK_SCREEN_TYPE = "key_cur_lock_screen_type";

	public final static String VIBRATE = "vibrate";
	public final static String HIDE_PASSGO = "passgo_hide";
	public final static String PASSGO_CLEAR_LAST = "key_lock_screen_pattern_clear_last";
	public final static String SHOW_GRID_CIRCLE = "show_circle";
	public final static String SHOW_GRID_LINE = "show_gridline";
	public final static String PASSGO_LINE_COLOR = "passgo_line_color";
	public final static String PASSGO_DOT_COLOR = "passgo_dot_color";
	public final static String PASSGo_LINE_THICKNESS = "passgo_line_thickness";
	public final static String PASSGO_DOT_RADIUS = "passgo_dot_thickness";
	public final static String GRID_LINE_THICKNESS = "grid_line_thickness";
	public final static String GRID_CIRCLE_THICKNESS = "grid_circle_thickness";
	public final static String GRID_LINE_COLOR = "grid_color";
	public final static String GRID_CIRCLE_COLOR = "grid_circle_color";
	public final static String PASSGO_BACKGROUND_COLOR = "key_lock_screen_back_ground_color";
	public final static String KEY_LOCK_SCREEN_GRID_OUTLINE_COLOR = "key_lock_screen_grid_outline_color";
	public final static String KEY_LOCK_SCREEN_TIMER_SETTING = "key_lock_screen_alarm_timer_setting";
	public final static String KEY_LOCK_SCREEN_DECORATION_AMPM_COLOR = "key_lock_screen_decoration_ampm_color";
	public final static String KEY_LOCK_SCREEN_DECORATION_TIME_COLOR = "key_lock_screen_decoration_time_color";
	public final static String KEY_LOCK_SCREEN_DECORATION_DATE_COLOR = "key_lock_screen_decoration_date_color";
	public final static String KEY_LOCK_SCREEN_DECORATION_CONTACT_COLOR = "key_lock_screen_decoration_contact_color";
	public final static String KEY_LOCK_SCREEN_DECORATION_AMPM_IS_HIDE = "key_lock_screen_decoration_ampm_is_hide";
	public final static String KEY_LOCK_SCREEN_DECORATION_TIME_IS_HIDE = "key_lock_screen_decoration_time_is_hide";
	public final static String KEY_LOCK_SCREEN_DECORATION_DATE_IS_HIDE = "key_lock_screen_decoration_date_is_hide";
	public final static String KEY_LOCK_SCREEN_DECORATION_CAMERA_IS_HIDE = "key_lock_screen_decoration_camera_is_hide";
	public final static String KEY_LOCK_SCREEN_DECORATION_CONTACT_IS_HIDE = "key_lock_screen_decoration_contact_is_hide";
	public final static String ACTION_LOCK_SCREEN = "FIRE_LCOK_SCREEN";
	public final static String ACTION_REBOOT_RESET_ALARM = "RESET_ALARM_AFTER_REBOOT";
	public final static String ACTION_TRIGGER_ALARM = "FIRE_ALARM";
	public final static String KEY_LOCK_SCREEN_PHONE = "key_lock_screen_phone";
	public final static String KEY_LOCK_APP_PACKAGE = "key_lock_app_package";
	public final static String KEY_LOCK_APP_TASK = "key_lock_app_task";
	public final static String BACKGROUND_TYPE = "key_lock_screen_background_type";
	public final static int KEY_BG_NONE = 0;
	public final static int KEY_BG_WALLPAPER = 1;
	public final static int KEY_BG_GALLERY = 2;
	public final static int KEY_BG_CAMERA = 3;
	public final static int KEY_BG_PURECOLOR = 4;
	public static String KEY_IS_LOCKED = "key_is_locked";
	public static String KEY_IS_APP_LOCKED = "key_is_app_locked";
	public static String KEY_IS_CALLING = "key_is_calling";
	public static String KEY_IS_USING_CAMERA = "key_is_using_camera";
	public static boolean IsTalkingIncomingCall=false;
	public static int Just_Unlock_Screen=0;
	public static int current_rotate=1;
//	public static boolean Changing_PassGo=false;
	public static boolean ScreenOff=false;
//	public static boolean Do_not_lock=false;
	public static boolean First_ring=true;
//	public static boolean IsDrawing=false;
	public static String  AZIMUTH="azimuth";
	public static String ELEVATION="elevation";
	public static String SCALEFACTOR="scaleFactor";
	public static String OFFSET_X="offset_x";
	public static String OFFSET_Y="offset_y";
	public static boolean to_lock_app_since_screen_on=false;
	public final static String TESTING_FLAG = "testing_flag";
	public static final String KEY_IS_LOCKSCREEN_DESTROYED = "key_is_lockscreen_destroyed";
	
	
	public static void setDataStr(Context context, String key, String value) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static void setDataInt(Context context, String key, int value) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static void setDataFloat(Context context, String key, float value) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	
	public static void setDataBool(Context context, String key, boolean value) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static String getDataStr(Context context, String key, String defValue) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		String str = preference.getString(key, defValue);
		return str;
	}
	
	public static int getDataInt(Context context, String key, int defValue) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		int str = preference.getInt(key, defValue);
		return str;
	}
	
	public static float getDataFloat(Context context, String key, float defValue) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		float str = preference.getFloat(key, defValue);
		return str;
	}
	
	public static boolean getDataBool(Context context, String key, boolean defValue) {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
		boolean str = preference.getBoolean(key, defValue);
		return str;
	}
}
