package cc.zenking.edu.zhjx.timessquare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanhy.library_tools.activity.BaseActivity;
import com.yuanhy.library_tools.presenter.BasePresenter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.zenking.edu.zhjx.R;
import cc.zenking.edu.zhjx.ui.activity.ApplyForLeave;

public class SampleTimesSquareActivity extends BaseActivity {
    private static final String TAG = "SampleTimesSquareActiviy";
    private CalendarPickerView calendar;
    private AlertDialog theDialog;
    private CalendarPickerView dialogView;
    private TextView cancel = null;
    private TextView save = null;
    private TextView totaldays = null;
    private final Set<Button> modeButtons = new LinkedHashSet<Button>();
    public static final int SAMPLETIMESSQUARERESULTCODE = 57;
    private Calendar nextYear;
    private Calendar lastYear;
    private final String mPageName = "SampleTimesSquareActivity";
    // 开始时间结束时间选择方式
    public static final String STARTEND = "startendtype";
    // 每天时间段方式
    public static final String QUANTUM = "timequantum";
    // 普通方式，两种都不用，都不显示
    public static final String COMMON = "common";
    private String selecttype = COMMON;
    private RelativeLayout rl_otherview;
    private TextView tv_starttime, tv_endtime;
    private CheckBox cb_selecttime;
    private PopupWindow popupWindow;
    private RelativeLayout ll_back;
    private String page = null;
    // 真实数据
    private int hourOfDay;
    private int minute;
    // 临时记录
    private Date date_temp = new Date();
    // 当前时间
    private int curHour;
    private int curMinu;
    // 传入的开始时间和结束时间
    private String starttime;
    private String endtime;
    private List<String> hours = new ArrayList<String>();
    private List<String> minutes = new ArrayList<String>();
    private View view_bg;
    public static int SCREENWIDTH = 0;
    private int minNum = -6;
    private int maxNum = 5;
    private PickerView pv_hour, pv_minute;
    private int selectedMinute;
    private String time = null;
    private boolean isToday = false;
    private boolean singleCanCancel = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_calendar_picker);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        SCREENWIDTH = wm.getDefaultDisplay().getWidth();
        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        ll_back = (RelativeLayout) findViewById(R.id.ll_back);
        view_bg = findViewById(R.id.view_bg);
        totaldays = (TextView) findViewById(R.id.totaldays);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);
        // 获取当前时间用于初始化时间控件
        getCurrentTime();
        minNum = -6;
        maxNum = 5;
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 6);

        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.MONTH, -6);

        Intent intent = getIntent();
        singleCanCancel = intent.getBooleanExtra("singleCanCancel", false);
        boolean onlyShow = intent.getBooleanExtra("onlyShow", false);
        // 获取开始时间和结束时间用于初始化时间控件
        starttime = intent.getStringExtra("starttime");
        endtime = intent.getStringExtra("endtime");
        // 获取选择时间的类型
        String selecttypeString = intent.getStringExtra("selecttype");
        if (selecttypeString != null) {
            selecttype = selecttypeString;
        }
        // 获取是单选还是多选
        String type = intent.getStringExtra("type");
        // 需要回显的已经选择的数据
        List<Date> dates_one = (List<Date>) intent
                .getSerializableExtra("dates_one");
        List<Date> selected_dates = (List<Date>) intent
                .getSerializableExtra("selected_dates");
        // 获取服务器时间
        time = intent.getStringExtra("time");
        Date nowDate = null;
        // 获取是哪个页面跳转来的
        page = intent.getStringExtra("page");
        try {
            Date date = new Date();
            if (time != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                nowDate = sdf.parse(time);
                Calendar currentNetCalendar = Calendar.getInstance();
                currentNetCalendar.setTime(nowDate);
                calendar.setCurrentNetCalendar(currentNetCalendar);
                // time = time.replace(time.substring(8, 10), "01");
                if (!"IllegalActivity".equals(page) && !"BehaviorTraceActivity".equals(page)) {
                    // 不能选择当天时间之前的页面，不能调用这两行代码
                    StringBuilder sb = new StringBuilder();
                    time = sb.append(time.substring(0, 8) + "01").toString();
                }
                date = sdf.parse(time);
            }
            nextYear.setTime(date);
            lastYear.setTime(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ApplyForLeave.PAGENAME.equals(page)) {
            // nextYear.add(Calendar.MONTH, 1);
            nextYear.add(Calendar.MONTH, 6);
            lastYear.add(Calendar.MONTH, -1);
            minNum = -1;
            maxNum = 5;
            calendar.setPageName(page);
            initView();
            initClickEvent();
        } else if ("IllegalActivity".equals(page)) {
            nextYear.add(Calendar.DAY_OF_MONTH, 1);//包括今天
            lastYear.add(Calendar.MONTH, -6);
            minNum = -6;
            maxNum = 0;//0个月表示只能选当前月
            calendar.setPageName(ApplyForLeave.PAGENAME);
            initView2();
            //回显上次选中的时间
            String timeString = intent.getStringExtra("illegaltime");
            selectedMinute = curMinu;
            if (timeString != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = null;
                String timeOfDay = null;
                int selMin = -1;
                try {
                    date = sdf.parse(timeString);
                    timeOfDay = timeString.split(" ")[1];
                    selMin = Integer.parseInt(timeOfDay.split(":")[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null && timeOfDay != null && selMin != -1) {
                    selected_dates = new ArrayList<Date>();
                    selected_dates.add(date);
                    tv_starttime.setText(timeOfDay);
                    tv_starttime.setTextColor(Color.parseColor("#494949"));
                    selectedMinute = selMin;
                }
            }
            initClickEvent();
        } else if ("BehaviorTraceActivity".equals(page)) {
            nextYear.add(Calendar.DAY_OF_MONTH, 1);//包括今天
            lastYear.add(Calendar.MONTH, -6);
            minNum = -6;
            maxNum = 0;//0个月表示只能选当前月
            calendar.setPageName(ApplyForLeave.PAGENAME);
        } else {
            nextYear.add(Calendar.MONTH, 6);
            lastYear.add(Calendar.MONTH, -1);
            minNum = -1;
            maxNum = 5;
        }
        calendar.init(lastYear.getTime(), nextYear.getTime(), minNum, maxNum) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());

        initButtonListeners(nextYear, lastYear);
        initOtherView();
        // 根据接收的参数再次刷新初始化设置
        initRepeat(type, dates_one, selected_dates, nowDate, onlyShow);
    }

    @Override
    public void setTransparent() {

    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    private void getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            curHour = Integer.parseInt(split[0]);
            curMinu = Integer.parseInt(split[1]);
        }
    }

    private void initRepeat(String type, List<Date> dates_one,
                            List<Date> selected_dates, Date nowDate, boolean onlyShow) {
        // 单选
        if ("single".equals(type)) {
            calendar.setCustomDayView(new DefaultDayViewAdapter());
            calendar.setDecorators(Collections
                    .<CalendarCellDecorator>emptyList());
            CalendarPickerView.FluentInitializer inMode = calendar.init(lastYear.getTime(),
                    nextYear.getTime(), minNum, maxNum) //
                    .inMode(CalendarPickerView.SelectionMode.SINGLE);
            if (selected_dates != null && selected_dates.size() == 1) {
                inMode.withSelectedDate(selected_dates.get(0));
                totaldays.setText("共选择：1天");
            }
            // 多选
        } else if ("multi".equals(type)) {
            calendar.setCustomDayView(new DefaultDayViewAdapter());
            calendar.setDecorators(Collections
                    .<CalendarCellDecorator>emptyList());
            // 将原来选中的但是以前已经请过的假去除
            if (dates_one != null && dates_one.size() != 0
                    && selected_dates != null && selected_dates.size() != 0) {
                for (Date date : dates_one) {
                    if (selected_dates.contains(date)) {
                        selected_dates.remove(date);
                    }
                }
            }
            // 如果不需要选择当前日期之前的，需要传入nowDate==null?new Date():nowDate
            // 如果需要选择当前日期，需要传入lastYear.getTime()
            calendar.init(lastYear.getTime(), nextYear.getTime(), minNum, maxNum)
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                    .withSelectedDates(selected_dates);
            // 将传入的dates_one，封装成map集合
            if (dates_one != null && dates_one.size() != 0) {
                Map<Integer, List<Date>> dates = new HashMap<Integer, List<Date>>();
                int month = -1;
                List<Date> list = null;
                for (int i = 0; i < dates_one.size(); i++) {
                    Date date = dates_one.get(i);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int temp = cal.get(Calendar.MONTH) + 1;
                    if (temp != month) {
                        if (month != -1 && list != null) {
                            dates.put(month, list);
                        }
                        month = temp;
                        list = new ArrayList<Date>();
                    }
                    list.add(date);
                    if (i == dates_one.size() - 1) {
                        dates.put(month, list);
                    }
                }
                calendar.setPrivateDates(dates);
            }
            calendar.setOnlyShow(onlyShow);
            if (selected_dates != null && selected_dates.size() >= 0) {
                if (ApplyForLeave.PAGENAME.equals(page)) {
                    if (selected_dates.size() > 0) {
                        totaldays.setText("已选" + selected_dates.size() + "个日期");
                    } else {
                        totaldays.setText("");
                    }
                } else {
                    totaldays.setText("共选择：" + selected_dates.size() + "天");
                }
            }
        }
        // 现在是跳转到当前时间，也可以跳转到选中的第一个时间
        if (ApplyForLeave.PAGENAME.equals(page)) {
            calendar.setListViewTop();
        } else {
            calendar.scrollToDate(nowDate == null ? new Date() : nowDate);
        }
        calendar.setSingleCanCancel(singleCanCancel);
    }

    private void getPopupWindow(int flag) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(flag);
        }
    }

    private void initPopuptWindow(final int flag) {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(
                R.layout.popupwindow_time, null, false);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        int width = d.getWidth();
        int height = d.getHeight();
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, width, 14 * width / 25,
                true);

        TextView cancel, save;
        // TimePicker timePicker;
        cancel = (TextView) popupWindow_view.findViewById(R.id.cancel);
        save = (TextView) popupWindow_view.findViewById(R.id.save);
        pv_hour = (PickerView) popupWindow_view.findViewById(R.id.pv_hour);
        pv_minute = (PickerView) popupWindow_view.findViewById(R.id.pv_minute);
        pv_hour.setData(hours);
        pv_hour.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String hour) {
                date_temp.setHours(Integer.parseInt(hour));
                setMinuteCount(flag, hour);
            }
        });
        pv_minute.setData(minutes);
        pv_minute.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String minute) {
                date_temp.setMinutes(Integer.parseInt(minute));
                selectedMinute = Integer.parseInt(minute);
            }
        });
        pv_hour.setSelected(hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay);
        pv_minute.setSelected(minute < 10 ? "0" + minute : "" + minute);

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View paramView) {
                closePopup();
            }
        });

        save.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View paramView) {
                String dateToString = dateToString(date_temp, "HH:mm");
                if (flag == 1) {
                    // 开始时间
                    tv_starttime.setText(dateToString);
                    tv_starttime.setTextColor(Color.parseColor("#494949"));
                } else if (flag == 2) {
                    // 结束时间
                    tv_endtime.setText(dateToString);
                    tv_endtime.setTextColor(Color.parseColor("#494949"));
                }
                changeSaveButton();
                closePopup();
            }
        });
        if (flag == 1 && "IllegalActivity".equals(page)) {
            // TODO 判断当前选中的是哪一天
            List<Date> selectedDates = calendar.getSelectedDates();
            if (selectedDates != null && selectedDates.size() != 0) {
                Date date = selectedDates.get(0);
                String dateString = dateToString(date, "yyyy-MM-dd");
                if (time != null) {
                    isToday = time.equals(dateString);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                    String str = formatter.format(curDate);
                    if (str != null) {
                        isToday = str.equals(dateString);
                    } else {
                        isToday = false;
                    }
                }
            } else {
                isToday = false;
            }
        }
        String time = tv_starttime.getText().toString();
        if (time != null && time.split(":")[0] != null) {
            setMinuteCount(flag, time.split(":")[0]);
        }
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                view_bg.setVisibility(View.GONE);
                popupWindow = null;
                String time = tv_starttime.getText().toString();
                if (time != null && time.contains(":")) {
                    selectedMinute = Integer.parseInt(time.split(":")[1]);
                }
            }
        });
    }

    private void setMinuteCount(int flag, String hour) {
        getCurrentTime();
        if (flag == 1 && "IllegalActivity".equals(page) && isToday) {
            if ("请选择".equals(hour)) {
                //设置小时和分钟
                hours.clear();
                for (int i = 0; i <= curHour; i++) {
                    hours.add(i < 10 ? "0" + i : "" + i);
                }
                pv_hour.setData(hours);
                pv_hour.setSelected(curHour < 10 ? "0" + curHour : "" + curHour);
                date_temp.setHours(curHour);
                minutes.clear();
                for (int i = 0; i <= curMinu; i++) {
                    minutes.add(i < 10 ? "0" + i : "" + i);
                }
                pv_minute.setData(minutes);
                pv_minute.setSelected(curMinu < 10 ? "0" + curMinu : "" + curMinu);
                date_temp.setMinutes(curMinu);
                selectedMinute = curMinu;
                return;
            }
            //设置小时
            hours.clear();
            for (int i = 0; i <= curHour; i++) {
                hours.add(i < 10 ? "0" + i : "" + i);
            }
            pv_hour.setData(hours);
            if (curHour <= Integer.parseInt(hour)) {
                pv_hour.setSelected(curHour < 10 ? "0" + curHour : "" + curHour);
                date_temp.setHours(curHour);
            } else {
                pv_hour.setSelected(hour);
                date_temp.setHours(Integer.parseInt(hour));
            }
            // 如果hour是当前时间的小时，就需要给minute重新赋值
            if (curHour <= Integer.parseInt(hour)) {
                minutes.clear();
                for (int i = 0; i <= curMinu; i++) {
                    minutes.add(i < 10 ? "0" + i : "" + i);
                }
                pv_minute.setData(minutes);
                if (selectedMinute > curMinu) {
                    pv_minute.setSelected(curMinu < 10 ? "0" + curMinu : "" + curMinu);
                    date_temp.setMinutes(curMinu);
                    selectedMinute = curMinu;
                } else {
                    pv_minute.setSelected(selectedMinute < 10 ? "0" + selectedMinute : "" + selectedMinute);
                    date_temp.setMinutes(selectedMinute);
                }
            } else {
                minutes.clear();
                for (int i = 0; i < 60; i++) {
                    minutes.add(i < 10 ? "0" + i : "" + i);
                }
                pv_minute.setData(minutes);
                pv_minute.setSelected(selectedMinute < 10 ? "0" + selectedMinute : "" + selectedMinute);
                date_temp.setMinutes(selectedMinute);
            }
        } else {
            hours.clear();
            for (int i = 0; i < 24; i++) {
                hours.add(i < 10 ? "0" + i : "" + i);
            }
            pv_hour.setData(hours);
            minutes.clear();
            for (int i = 0; i < 60; i++) {
                minutes.add(i < 10 ? "0" + i : "" + i);
            }
            pv_minute.setData(minutes);
            if ("请选择".equals(hour)) {
                pv_hour.setSelected(curHour < 10 ? "0" + curHour : "" + curHour);
                date_temp.setHours(curHour);
                pv_minute.setSelected(curMinu < 10 ? "0" + curMinu : "" + curMinu);
                date_temp.setMinutes(curMinu);
                selectedMinute = curMinu;
            } else {
                pv_hour.setSelected(hour);
                date_temp.setHours(Integer.parseInt(hour));
                pv_minute.setSelected(selectedMinute < 10 ? "0" + selectedMinute : "" + selectedMinute);
                date_temp.setMinutes(selectedMinute);
            }
        }
    }

    private void closePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void initClickEvent() {
        tv_starttime.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                String string = tv_starttime.getText().toString();
                if (string != null && string.contains(":")) {
                    String[] split = string.split(":");
                    hourOfDay = Integer.parseInt(split[0]);
                    minute = Integer.parseInt(split[1]);
                    date_temp.setHours(hourOfDay);
                    date_temp.setMinutes(minute);
                } else {
                    // 赋值当前时间
                    hourOfDay = curHour;
                    minute = curMinu;
                    date_temp.setHours(curHour);
                    date_temp.setMinutes(curMinu);
                }
                getPopupWindow(1);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                view_bg.setVisibility(View.VISIBLE);
                popupWindow.showAtLocation(ll_back, Gravity.BOTTOM, 0, 0);
            }
        });
        tv_endtime.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                String string = tv_endtime.getText().toString();
                if (string != null && string.contains(":")) {
                    String[] split = string.split(":");
                    hourOfDay = Integer.parseInt(split[0]);
                    minute = Integer.parseInt(split[1]);
                    date_temp.setHours(hourOfDay);
                    date_temp.setMinutes(minute);
                } else {
                    // 赋值当前时间
                    hourOfDay = curHour;
                    minute = curMinu;
                    date_temp.setHours(curHour);
                    date_temp.setMinutes(curMinu);
                }
                getPopupWindow(2);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                view_bg.setVisibility(View.VISIBLE);
                popupWindow.showAtLocation(ll_back, Gravity.BOTTOM, 0, 0);
            }
        });
        cb_selecttime.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    selecttype = QUANTUM;
                } else {
                    selecttype = STARTEND;
                }
            }
        });
    }

    TextView tv_perDay_hint;

    private void initView2() {
        // rl_otherview = (RelativeLayout) findViewById(R.id.rl_otherview);
        totaldays.setVisibility(View.GONE);
        rl_otherview = (RelativeLayout) View.inflate(this,
                R.layout.view_timersquare, null);
        calendar.addFooterView(rl_otherview);
        View line = findViewById(R.id.line);
        TextView tv_start_time_hint = (TextView) findViewById(R.id.tv_start_time_hint);
        TextView tv_end_time_hint = (TextView) findViewById(R.id.tv_end_time_hint);
        ImageView iv_endtime = (ImageView) findViewById(R.id.iv_endtime);
        View view_line2 = findViewById(R.id.view_line2);
        View view_line3 = findViewById(R.id.view_line3);
        View view_line4 = findViewById(R.id.view_line4);
        tv_starttime = (TextView) findViewById(R.id.tv_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        cb_selecttime = (CheckBox) findViewById(R.id.cb_selecttime);
        tv_perDay_hint = (TextView) findViewById(R.id.tv_perDay_hint);
        line.setVisibility(View.GONE);
        tv_endtime.setVisibility(View.GONE);
        cb_selecttime.setVisibility(View.GONE);
        tv_perDay_hint.setVisibility(View.GONE);
        tv_end_time_hint.setVisibility(View.GONE);
        iv_endtime.setVisibility(View.GONE);
        view_line2.setVisibility(View.GONE);
        view_line3.setVisibility(View.GONE);
        view_line4.setVisibility(View.GONE);
        tv_start_time_hint.setText("选择时间");
        for (int i = 0; i < 24; i++) {
            hours.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++) {
            minutes.add(i < 10 ? "0" + i : "" + i);
        }
    }

    private void initView() {
        // rl_otherview = (RelativeLayout) findViewById(R.id.rl_otherview);
        rl_otherview = (RelativeLayout) View.inflate(this,
                R.layout.view_timersquare, null);
        calendar.addFooterView(rl_otherview);
        tv_starttime = (TextView) findViewById(R.id.tv_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        cb_selecttime = (CheckBox) findViewById(R.id.cb_selecttime);
        for (int i = 0; i < 24; i++) {
            hours.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++) {
            minutes.add(i < 10 ? "0" + i : "" + i);
        }
    }

    /**
     * 根据选择时间方式，加载不同界面
     */
    private void initOtherView() {
        if (ApplyForLeave.PAGENAME.equals(page)) {
            if (STARTEND.equals(selecttype)) {
                // rl_otherview.setVisibility(View.VISIBLE);
                cb_selecttime.setChecked(false);
                // totaldays.setVisibility(View.GONE);
                if (starttime != null && endtime != null) {
                    tv_starttime.setText(starttime);
                    tv_endtime.setText(endtime);
                    tv_starttime.setTextColor(Color.parseColor("#494949"));
                    tv_endtime.setTextColor(Color.parseColor("#494949"));
                }
            } else if (QUANTUM.equals(selecttype)) {
                // rl_otherview.setVisibility(View.VISIBLE);
                // rb_gd.setChecked(true);
                cb_selecttime.setChecked(true);
                // totaldays.setVisibility(View.GONE);
                if (starttime != null && endtime != null) {
                    tv_starttime.setText(starttime);
                    tv_endtime.setText(endtime);
                    tv_starttime.setTextColor(Color.parseColor("#494949"));
                    tv_endtime.setTextColor(Color.parseColor("#494949"));
                }
            } else {
                // rl_otherview.setVisibility(View.GONE);
                // totaldays.setVisibility(View.VISIBLE);
            }
            changeSaveButton();
        } else if ("IllegalActivity".equals(page) || "BehaviorTraceActivity".equals(page)) {
            changeSaveButton();
            // rl_otherview.setVisibility(View.GONE);
            // totaldays.setVisibility(View.VISIBLE);
        }
    }

    private void initButtonListeners(final Calendar nextYear,
                                     final Calendar lastYear) {
        // final Button single = (Button) findViewById(R.id.button_single);
        // final Button multi = (Button) findViewById(R.id.button_multi);
        // final Button range = (Button) findViewById(R.id.button_range);
        // final Button displayOnly = (Button)
        // findViewById(R.id.button_display_only);
        // final Button dialog = (Button) findViewById(R.id.button_dialog);
        // final Button customized = (Button)
        // findViewById(R.id.button_customized);
        // final Button decorator = (Button)
        // findViewById(R.id.button_decorator);
        // final Button hebrew = (Button) findViewById(R.id.button_hebrew);
        // final Button arabic = (Button) findViewById(R.id.button_arabic);
        // final Button customView = (Button)
        // findViewById(R.id.button_custom_view);
        // modeButtons.addAll(Arrays.asList(single, multi, range, displayOnly,
        // decorator, customView));
        //
        // single.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setButtonsEnabled(single);
        //
        // calendar.setCustomDayView(new DefaultDayViewAdapter());
        // calendar.setDecorators(Collections
        // .<CalendarCellDecorator> emptyList());
        // calendar.init(lastYear.getTime(), nextYear.getTime()) //
        // .inMode(SelectionMode.SINGLE) //
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // multi.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setButtonsEnabled(multi);
        //
        // calendar.setCustomDayView(new DefaultDayViewAdapter());
        // Calendar today = Calendar.getInstance();
        // ArrayList<Date> dates = new ArrayList<Date>();
        // for (int i = 0; i < 5; i++) {
        // today.add(Calendar.DAY_OF_MONTH, 3);
        // dates.add(today.getTime());
        // }
        // calendar.setDecorators(Collections
        // .<CalendarCellDecorator> emptyList());
        // calendar.init(new Date(), nextYear.getTime()) //
        // .inMode(SelectionMode.MULTIPLE) //
        // .withSelectedDates(dates);
        // }
        // });
        //
        // range.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setButtonsEnabled(range);
        //
        // calendar.setCustomDayView(new DefaultDayViewAdapter());
        // Calendar today = Calendar.getInstance();
        // ArrayList<Date> dates = new ArrayList<Date>();
        // today.add(Calendar.DATE, 3);
        // dates.add(today.getTime());
        // today.add(Calendar.DATE, 5);
        // dates.add(today.getTime());
        // calendar.setDecorators(Collections
        // .<CalendarCellDecorator> emptyList());
        // calendar.init(new Date(), nextYear.getTime()) //
        // .inMode(SelectionMode.RANGE) //
        // .withSelectedDates(dates);
        // }
        // });
        //
        // displayOnly.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setButtonsEnabled(displayOnly);
        //
        // calendar.setCustomDayView(new DefaultDayViewAdapter());
        // calendar.setDecorators(Collections
        // .<CalendarCellDecorator> emptyList());
        // calendar.init(new Date(), nextYear.getTime()) //
        // .inMode(SelectionMode.SINGLE) //
        // .withSelectedDate(new Date()) //
        // .displayOnly();
        // }
        // });
        //
        // dialog.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // String title = "I'm a dialog!";
        // showCalendarInDialog(title, R.layout.dialog_time);
        // dialogView.init(lastYear.getTime(), nextYear.getTime()) //
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // customized.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // showCalendarInDialog("Pimp my calendar!",
        // R.layout.dialog_customized);
        // dialogView.init(lastYear.getTime(), nextYear.getTime())
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // decorator.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        // setButtonsEnabled(decorator);
        //
        // calendar.setCustomDayView(new DefaultDayViewAdapter());
        // calendar.setDecorators(Arrays
        // .<CalendarCellDecorator> asList(new SampleDecorator()));
        // calendar.init(lastYear.getTime(), nextYear.getTime()) //
        // .inMode(SelectionMode.SINGLE) //
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // hebrew.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // showCalendarInDialog("I'm Hebrew!", R.layout.dialog_time);
        // dialogView.init(lastYear.getTime(), nextYear.getTime(),
        // new Locale("iw", "IL")) //
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // arabic.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // showCalendarInDialog("I'm Arabic!", R.layout.dialog_time);
        // dialogView.init(lastYear.getTime(), nextYear.getTime(),
        // new Locale("ar", "EG")) //
        // .withSelectedDate(new Date());
        // }
        // });
        //
        // customView.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // setButtonsEnabled(customView);
        //
        // calendar.setDecorators(Collections
        // .<CalendarCellDecorator> emptyList());
        // calendar.setCustomDayView(new SampleDayViewAdapter());
        // calendar.init(lastYear.getTime(), nextYear.getTime())
        // .inMode(SelectionMode.SINGLE)
        // .withSelectedDate(new Date());
        // }
        // });

        // findViewById(R.id.done_button).setOnClickListener(
        // new OnClickListener() {
        // @Override
        // public void onClick(View view) {
        // Log.d(TAG, "Selected time in millis: "
        // + calendar.getSelectedDate().getTime());
        // // String toast = "Selected: "
        // // + calendar.getSelectedDate().getTime();
        // // Toast.makeText(SampleTimesSquareActivity.this, toast,
        // // LENGTH_SHORT).show();
        // }
        // });

        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击取消按钮
                finish();
            }
        });

        save.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击保存按钮
                List<Date> selectedDates = calendar.getSelectedDates();
                Intent intent = new Intent();
                intent.putExtra("dates", (Serializable) selectedDates);
                if (!COMMON.equals(selecttype)) {
                    String starttimeString = tv_starttime.getText().toString();
                    String endtimeString = tv_endtime.getText().toString();
                    int startTimeNum = string2int(starttimeString);
                    int endTimeNum = string2int(endtimeString);
                    // 判断数据的合理性
                    if (STARTEND.equals(selecttype)) {
                        // 连续时间，如果选择的是一天，那么开始时间必须小于结束时间
                        if (selectedDates != null && selectedDates.size() == 1) {
                            if (startTimeNum >= endTimeNum) {
                                Toast.makeText(SampleTimesSquareActivity.this,
                                        "开始时间必须小于结束时间", Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                        }
                    } else if (QUANTUM.equals(selecttype)) {
                        // 每天固定时间，开始时间必须小于结束时间
                        if (startTimeNum >= endTimeNum) {
                            Toast.makeText(SampleTimesSquareActivity.this,
                                    "开始时间必须小于结束时间", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    intent.putExtra("selecttype", selecttype);
                    intent.putExtra("starttime", starttimeString);
                    intent.putExtra("endtime", endtimeString);
                    setResult(SAMPLETIMESSQUARERESULTCODE, intent);
                    finish();
                } else {
                    if ("IllegalActivity".equals(page)) {
                        String starttimeString = tv_starttime.getText().toString();
                        Date date = selectedDates.get(0);
                        String dateString = dateToString(date, "yyyy-MM-dd");
                        intent.putExtra("illegalDate", dateString + " " + starttimeString);
                    }
                    setResult(SAMPLETIMESSQUARERESULTCODE, intent);
                    finish();
                }

            }
        });
        // 选择监听
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            @Override
            public void onDateUnselected(Date date) {
                List<Date> selectedDates = calendar.getSelectedDates();
                if (ApplyForLeave.PAGENAME.equals(page)) {
                    if (selectedDates.size() > 0) {
                        totaldays.setText("已选" + selectedDates.size() + "个日期");
                    } else {
                        totaldays.setText("");
                    }
                } else {
                    totaldays.setText("共选择：" + selectedDates.size() + "天");
                }
                if (ApplyForLeave.PAGENAME.equals(page)) {
                    changeSaveButton();
                }
            }

            @Override
            public void onDateSelected(Date date) {
                List<Date> selectedDates = calendar.getSelectedDates();
                if (ApplyForLeave.PAGENAME.equals(page)) {
                    if (selectedDates.size() > 0) {
                        totaldays.setText("已选" + selectedDates.size() + "个日期");
                    } else {
                        totaldays.setText("");
                    }
                } else {
                    totaldays.setText("共选择：" + selectedDates.size() + "天");
                }
                if (ApplyForLeave.PAGENAME.equals(page) || "IllegalActivity".equals(page) || "BehaviorTraceActivity".equals(page)) {
                    changeSaveButton();
                }
                boolean isToday = false;
                String time = null;
                if (tv_starttime != null) {
                    time = tv_starttime.getText().toString();
                }
                if ("IllegalActivity".equals(page) && !"请选择".equals(time)) {
                    // 判断当前选中的是哪一天
                    if (selectedDates != null && selectedDates.size() != 0) {
                        Date dateone = selectedDates.get(0);
                        String dateString = dateToString(dateone, "yyyy-MM-dd");
                        if (SampleTimesSquareActivity.this.time != null) {
                            isToday = SampleTimesSquareActivity.this.time.equals(dateString);
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
                            String str = formatter.format(curDate);
                            if (str != null) {
                                isToday = str.equals(dateString);
                            } else {
                                isToday = false;
                            }
                        }
                    } else {
                        isToday = false;
                    }
                }
                if (isToday) {
                    getCurrentTime();
                    if (time != null && time.split(":")[0] != null) {
                        if (curHour < Integer.parseInt(time.split(":")[0])) {
                            tv_starttime.setText((curHour < 10 ? "0" + curHour : curHour) + ":" + (curMinu < 10 ? "0" + curMinu : curMinu));
                            Toast.makeText(SampleTimesSquareActivity.this, "已将时间调整为当前时间！", Toast.LENGTH_SHORT).show();
                        } else if (curHour == Integer.parseInt(time.split(":")[0])) {
                            //判断分钟是否大于当前分钟
                            if (time.split(":").length == 2 && time.split(":")[1] != null && Integer.parseInt(time.split(":")[1]) > curMinu) {
                                tv_starttime.setText(time.split(":")[0] + ":" + (curMinu < 10 ? "0" + curMinu : curMinu));
                                Toast.makeText(SampleTimesSquareActivity.this, "已将时间调整为当前时间！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    private void showCalendarInDialog(String title, int layoutResId) {
        dialogView = (CalendarPickerView) getLayoutInflater().inflate(
                layoutResId, null, false);
        theDialog = new AlertDialog.Builder(this)
                //
                .setTitle(title)
                .setView(dialogView)
                .setNeutralButton("Dismiss",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(
		                            DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
        theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
//				Log.d(TAG, "onShow: fix the dimens!");
                dialogView.fixDialogDimens();
            }
        });
        theDialog.show();
    }

    private void setButtonsEnabled(Button currentButton) {
        for (Button modeButton : modeButtons) {
            modeButton.setEnabled(modeButton != currentButton);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        boolean applyFixes = theDialog != null && theDialog.isShowing();
        if (applyFixes) {
//			Log.d(TAG,
//					"Config change: unfix the dimens so I'll get remeasured!");
            dialogView.unfixDialogDimens();
        }
        super.onConfigurationChanged(newConfig);
        if (applyFixes) {
            dialogView.post(new Runnable() {
                @Override
                public void run() {
//					Log.d(TAG, "Config change done: re-fix the dimens!");
                    dialogView.fixDialogDimens();
                }
            });
        }
    }

    private String dateToString(Date date, String dateandtime) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateandtime);
        String str = sdf.format(date);
        return str;
    }

    private void changeSaveButton() {
        List<Date> selectedDates = calendar.getSelectedDates();
        String starttimeString = null;
        String endtimeString = null;
        if (tv_starttime != null) {
            starttimeString = tv_starttime.getText().toString();
        }
        if (tv_endtime != null) {
            endtimeString = tv_endtime.getText().toString();
        }
        if ("IllegalActivity".equals(page)) {
            if ((selectedDates != null && selectedDates.size() != 0)
                    && (starttimeString != null && starttimeString.contains(":"))) {
                save.setClickable(true);
            } else {
                save.setClickable(false);
            }
        } else if ("BehaviorTraceActivity".equals(page)) {
//            if (selectedDates != null && selectedDates.size() != 0) {
            save.setClickable(true);
//            } else {
//                save.setTextColor(Color.parseColor("#868686"));
//                save.setClickable(false);
//            }
        } else {
            if ((selectedDates != null && selectedDates.size() != 0)
                    && (starttimeString != null && starttimeString.contains(":"))
                    && (endtimeString != null && endtimeString.contains(":"))) {
                save.setClickable(true);
            } else {
                save.setClickable(false);
            }
        }
    }

    private int string2int(String msg) {
        String replace = msg.replace(":", "");
        int parseInt = Integer.parseInt(replace);
        return parseInt;
    }
}
