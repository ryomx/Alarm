package apl.sampleAlarm.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import apl.sampleAlarm.R;
import apl.sampleAlarm.preferences.AlarmSettingInfo;

/**
 * Created by ryota on 2017/04/08.
 */
//public class AlarmInfoAdapter extends ArrayAdapter<AlarmInfo> {
public class AlarmInfoAdapter extends ArrayAdapter<AlarmSettingInfo> {

    // レイアウトxmlファイルからIDを指定してViewが使用可能
    private LayoutInflater mLayoutInflater;

    //public AlarmInfoAdapter(Context context, int resourceId, List<AlarmInfo> objects) {
    public AlarmInfoAdapter(Context context, int resourceId, List<AlarmSettingInfo> objects) {
            super(context, resourceId, objects);
            // getLayoutInflater()メソッドはActivityじゃないと使えない
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

    // getView()メソッドは各行を表示しようとした時に呼ばれる
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 特定行(position)のデータを得る
        //AlarmInfo item = (AlarmInfo)getItem(position);
        AlarmSettingInfo item = (AlarmSettingInfo)getItem(position);
        // convertViewは使いまわされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) convertView = mLayoutInflater.inflate(R.layout.list_item, null);

        // AlarmInfoのデータをViewの各Widgetにセットする
        TextView textViewListAlarmTime = (TextView)convertView.findViewById(R.id.textView_listAlarmTime);
        textViewListAlarmTime.setText(item.getAlarmTime());

        TextView textViewListMemo = (TextView)convertView.findViewById(R.id.textView_listMemo);
        textViewListMemo.setText(item.getMemo());

        Switch switchListAlarmTime = (Switch)convertView.findViewById(R.id.switch_listAlarmTime);
        switchListAlarmTime.setChecked(item.getAlarmSwitch());

        return convertView;
    }

}
