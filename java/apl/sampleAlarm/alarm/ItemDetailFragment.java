package apl.sampleAlarm.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.util.Arrays;

import apl.sampleAlarm.R;
import apl.sampleAlarm.preferences.AlarmSetting;
import apl.sampleAlarm.preferences.AlarmSettingInfo;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private final String ALARM_DETAIL_KEY = "alarm_detail_key";

    private final String ALARM_DETAIL_SETTING = "alarm_detail_setting";

    private Spinner spinnerAlarmMsg;
    private TimePicker timePicker;
    private Switch switchSnooze;
    private Spinner spinnerSnoozeTime;
    private EditText editTextMemo;
    private ArrayAdapter<Integer> adapterSnoozeTime;

//    /**
//     * The dummy content this fragment is presenting.
//     */
//    //private AlarmContent.AlarmItem mItem;
//    private AlarmInfo mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {

    }

    private int listIndex;
    private AlarmSetting alarmSetting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 各画面要素を取得
        spinnerAlarmMsg = (Spinner)getView().findViewById(R.id.spinner_message);
        timePicker = (TimePicker)getView().findViewById(R.id.timePicker);
        switchSnooze = (Switch)getView().findViewById(R.id.switch_snooze);
        spinnerSnoozeTime = (Spinner)getView().findViewById(R.id.spinner_snooze);
        editTextMemo = (EditText)getView().findViewById(R.id.editText_memo);

        // アラームメッセージ種類の設定
        // ArrayAdapterインスタンスをResourceXMLから取得してアラームメッセージの選択内容を作成
        ArrayAdapter<CharSequence> adapterAlarmMsg = ArrayAdapter.createFromResource(
                getActivity(), R.array.alarm_msg,
                android.R.layout.simple_spinner_item);
        adapterAlarmMsg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlarmMsg.setAdapter(adapterAlarmMsg);

        // アラームを24時間表記に設定
        timePicker.setIs24HourView(true);

        // スヌーズ時間の選択値を設定
        adapterSnoozeTime = new ArrayAdapter<>(
                getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(R.array.snooze_time_val));
                //AlarmConstant.snoozeTimeVal);
        spinnerSnoozeTime.setAdapter(adapterSnoozeTime);

        // リスト画面から取得したインデックスを取得
        Bundle arguments =  getArguments();
        listIndex = arguments.getInt(ItemDetailFragment.ARG_ITEM_ID);
        // アラーム設定情報を取得
        alarmSetting = AlarmSetting.getInstance(getActivity().getApplicationContext());

        AlarmSettingInfo alarmInfo = alarmSetting.getAlarmSettingInfoList().get(listIndex);


        // 一時保存情報の初期化
        SharedPreferences workPrefs = this.getActivity().getSharedPreferences(ALARM_DETAIL_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = workPrefs.edit();
        editor.putBoolean("isPauseSave", false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        // onStart()後、onPause()から復帰

        // 一時保存情報を取得
        SharedPreferences workPrefs = this.getActivity().getSharedPreferences(ALARM_DETAIL_KEY, Context.MODE_PRIVATE);

        // ポーズ保存フラグ
        boolean isPauseSave = workPrefs.getBoolean("isPauseSave", false);

        // 一時退避保存情報がonPauseで保存された場合
        if (isPauseSave) {
            Gson gson = new Gson();
            String alarmDetailSetting = workPrefs.getString(ALARM_DETAIL_SETTING, "");

            // 保存したオブジェクトを取得して画面に設定
            if ( !TextUtils.isEmpty(alarmDetailSetting)) {
                AlarmSettingInfo alarmInfo = gson.fromJson(alarmDetailSetting, AlarmSettingInfo.class);
                setScreenValue(alarmInfo);

            } else {

            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Resources res = this.getActivity().getResources();
        // スヌーズ設定ラジオボタン(ON)
        String rbSnoozeOn = res.getString(R.string.alarm_set_snooze_on);

        // ----- 設定情報を取得してSharedPreferenceに保存（一時退避用） ---- /
        SharedPreferences prefs = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        TimePicker timePicker = (TimePicker)getView().findViewById(R.id.timePicker);
        int hour;
        int minute;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        editor.putInt("hour", hour);
        editor.putInt("minute", minute);

        // メッセージ種類
        Spinner spinnerMsg = (Spinner)getView().findViewById(R.id.spinner_message);
        String msgId =  (String)spinnerMsg.getSelectedItem();
        editor.putString("msgKind", msgId);

        // ポーズ保存フラグ
        editor.putBoolean("isPauseSave", true);
        // リスト番号
        editor.putInt("listIndex", listIndex);

        // 保存
        editor.apply();
    }

    /**
     * 画面表示内容設定
     * @param alarmInfo
     */
    private void setScreenValue(AlarmSettingInfo alarmInfo) {
        // ----- 設定情報を画面に表示 ---- /
        //メモ
        editTextMemo.setText(alarmInfo.getMemo());

        // アラーム時間
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            timePicker.setHour(alarmInfo.getHour());
            timePicker.setMinute(alarmInfo.getMinute());
        } else {
            timePicker.setCurrentHour(alarmInfo.getHour());
            timePicker.setCurrentMinute(alarmInfo.getMinute());
        }

        // スヌーズ設定
        switchSnooze.setChecked(alarmInfo.getSnoozeFlg());
        // スヌーズ時間
        spinnerSnoozeTime.setSelection(adapterSnoozeTime.getPosition(alarmInfo.getSnoozeTime()));
        int color = Color.BLACK;
        if (alarmInfo.getSnoozeFlg()) {
            spinnerSnoozeTime.setEnabled(true);
        } else {
            spinnerSnoozeTime.setEnabled(false);
            color = Color.GRAY;
        }
        // SpinnerからTextViewを取り出してテキストカラーを設定
        TextView textView = (TextView) spinnerSnoozeTime.getChildAt(0);
        textView.setTextColor(color);

        // アラームメッセージ
        spinnerAlarmMsg.setSelection(alarmInfo.getAlarmMsgNo());
    }
}
