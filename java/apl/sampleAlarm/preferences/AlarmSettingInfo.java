package apl.sampleAlarm.preferences;

/**
 * Created by ryota on 2017/04/22.
 */
public class AlarmSettingInfo {
    private int hour;
    private int minute;
    private boolean alarmSwitch;
    private boolean snoozeFlg;
    private int snoozeTime;
    private int alarmMsgNo;
    private String memo;

    AlarmSettingInfo() {
        this.hour = 0;
        this.minute = 0;
        this.alarmSwitch = false;
        this.snoozeFlg = false;
        this.snoozeTime = 0;
        this.alarmMsgNo = 0;
        this.memo = "";
    }

    AlarmSettingInfo(int hour,
                     int minute,
                     boolean snoozeFlg,
                     int snoozeTime,
                     int alarmMsgNo,
                     String memo) {
        this.hour = hour;
        this.minute = minute;
        this.snoozeFlg = snoozeFlg;
        this.snoozeTime = snoozeTime;
        this.alarmMsgNo = alarmMsgNo;
        this.memo = memo;
    }

    public String getAlarmTime() {
        return hour + ":" + minute;
    }

    public String getMemo() {
        return memo;
    }

    public boolean getAlarmSwitch() {
        return alarmSwitch;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public boolean getSnoozeFlg() { return snoozeFlg; }

    public int getSnoozeTime() { return snoozeTime; }

    public int getAlarmMsgNo() { return alarmMsgNo; }

}
