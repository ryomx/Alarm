package apl.sampleAlarm.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apl.sampleAlarm.preferences.AlarmSettingInfo;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AlarmContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<AlarmSettingInfo> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Integer, AlarmSettingInfo> ITEM_MAP = new HashMap<>();

public static void setItem(List<AlarmSettingInfo> item) {
    ITEMS = item;

    for (int i = 0; i < item.size(); i++) {
        ITEM_MAP.put(i, item.get(i));
    }
}


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
