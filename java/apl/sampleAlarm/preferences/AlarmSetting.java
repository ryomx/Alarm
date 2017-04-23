package apl.sampleAlarm.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryota on 2017/04/21.
 */
public class AlarmSetting {

    private static AlarmSetting instance;
    private List<AlarmSettingInfo> alarmSettingInfoList;

    private AlarmSetting() {
        // singleton
    }

    // KEY
    private static final String ALARM_SETTING_KEY = "ALARM_SETTING";

    public List<AlarmSettingInfo> getAlarmSettingInfoList() {
        return alarmSettingInfoList;
    }

    // 保存情報取得
    public static AlarmSetting getInstance(Context context) {

        // 初回の場合
        if (instance == null) {
            // 保存情報を取得
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Gson gson = new Gson();
            String alarmSettingString = prefs.getString(ALARM_SETTING_KEY, "");

            // 保存したオブジェクトを取得
            if ( !TextUtils.isEmpty(alarmSettingString)) {
                instance = gson.fromJson(alarmSettingString, AlarmSetting.class);
            } else {
                // 何も保存されていない　初期時点はデフォルト値を入れる
                instance = getDefaultInstance();
            }
        }

        return instance;
    }

    // デフォルト値の入ったオブジェクトを返す
    public static AlarmSetting getDefaultInstance() {
        AlarmSetting instance = new AlarmSetting();
        instance.alarmSettingInfoList = new ArrayList<>();
        instance.alarmSettingInfoList.add(new AlarmSettingInfo());

        return instance;
    }

    // 状態保存
    public void saveInstance(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        // 現在のインスタンスの状態を保存
        prefs.edit().putString(ALARM_SETTING_KEY, gson.toJson(this)).apply();
    }
}
