package apl.sampleAlarm.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import apl.sampleAlarm.R;
import apl.sampleAlarm.alarm.ItemListActivity;
import apl.sampleAlarm.config.ConfigActivity;
import apl.sampleAlarm.info.InfoActivity;

/**
 * Created by ryota on 2017/04/09.
 */
public class MainFragment extends Fragment {

    private TextView mTextView;

    // Fragmentで表示するViewを作成するメソッド
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // レイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TextViewをひも付けます
        mTextView = (TextView) view.findViewById(R.id.textView_main);
        // アラームのイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemListIntent = new Intent(getActivity(), ItemListActivity.class);
                startActivity(itemListIntent);
            }
        });

        // サークル情報のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(getActivity(), InfoActivity.class);
                startActivity(infoIntent);
            }
        });

        // 設定のイメージをクリックした時の処理
        view.findViewById(R.id.imageButton_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent configIntent = new Intent(getActivity(), ConfigActivity.class);
                startActivity(configIntent);
            }
        });
    }
}
