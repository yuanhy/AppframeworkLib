package com.yuanhy.library_tools.util;

import android.util.Log;
import android.view.View;

import com.yuanhy.library_tools.app.AppFramentUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2019/4/10.
 */

public class TimeUtil {
    /**
     * 获取日期
     *
     * @return
     */
    @Deprecated
    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    /**
     * 获取日期的第一秒
     *
     * @return
     */
    public static long getDateStart( long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
//============
        Date date = new Date(timeMillis);//时间管理类
        String dateStr = simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss
        try {
            Date date1 = simpleDateFormat2.parse(dateStr);
            return  date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //============
        return 0;
    }


    /**
     * 获取日期的最后一秒
     * 传入当天日期的任意一个时间段
     * @return
     */
    public static long getDateEnd( long timeMillis) {
        return  getDateStart(timeMillis)+24*60*60-1;
    }
    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate(String pattern, long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);// HH:mm:ss
//============
        Date date = new Date(timeMillis);//时间管理类
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//格式
//		data.setText(format.format(date));//设置时间
        //============
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDate2() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static Long getDateLong() {
        Date date = new Date(System.currentTimeMillis());
        return date.getTime();
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static Long getDateLong(int dayNum) {

long daylong=24*60*60;






//        long dayLong=getDateLong();
        long dayLong=getDateCurrent("yyyy/MM/dd");
        return (dayLong-daylong*dayNum)*1000;
    }

    /**
     * 当前天数的 第一秒时间long
     *
     * @return
     */
    public static long getDateCurrent(String pattern) {//yyyy/MM/dd
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String dateStr = simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);// HH:mm:ss
        try {
            Date date1 = simpleDateFormat2.parse(dateStr);
            return  date1.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  0;
    }

    /**
     * 当前天数的最后一秒时间long
     *
     * @return
     */
    public static long getDateCurrent2(String pattern) {

        return  getDateCurrent(pattern)+24*60*60-1;
    }

    /**
     * 获取日期
     *
     * @return
     */
    @Deprecated
    public static String getDate3() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 是否相差 numberDay 天
     *
     * @param day1      比较早的时间 2019-06-15 (标准) 或者 2019-06-15 18:22 在或者 2019-06-15 18:22:22
     * @param day2      比较晚的时间
     * @param numberDay 相差的天数
     * @return true >=numberDay
     */
    public static boolean isDifferNumberDays(String day1, String day2, int numberDay) {
        long longTime1;
        long longTime2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        try {
            Date date1 = sdf.parse(day1);
            Date date2 = sdf.parse(day2);

            ca.setTime(date1);
            ca.add(Calendar.DAY_OF_MONTH, numberDay);
            Date lastMonth = ca.getTime(); //结果
            System.out.println("字符串 ：" + sdf.format(lastMonth) + "    :" + lastMonth.getTime());
            longTime1 = lastMonth.getTime();

            ca.setTime(date2);
            lastMonth = ca.getTime(); //结果
            lastMonth = sdf.parse(sdf.format(lastMonth));
            System.out.println("字符串2：" + sdf.format(lastMonth) + "    :" + lastMonth.getTime());
            longTime2 = lastMonth.getTime();
            if (longTime2 >= longTime1) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getDateYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getDateMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取当前几号
     *
     * @return
     */
    public static String getDateDay() {
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DATE);
        return day + "";
    }

    /**
     * 获取某个月份的第一天
     */
    public static long getMonthFirstDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);

        System.out.println("time:" + getDate("yyyy-MM-dd HH:mm:ss", calendar.getTime().getTime()));
//		AppFramentUtil.logCatUtil.v("time:"+getDate("yyyy-MM-dd",calendar.getTime().getTime()));;
        return calendar.getTime().getTime() / 1000;//除1000是去除毫秒
//		String firstDayOfMonth = format.format(calendar.getTime());
//
//		System.out.println(firstDayOfMonth);
    }

    /**
     * 获取某个日期的最后一天
     */
    public static long getMonthLastDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);//这里先设置要获取月份的下月的第一天
        calendar.add(Calendar.DATE, -1);//这里将日期值减去一天，从而获取到要求的月份最后一天
// 时
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        // 分
        calendar.set(Calendar.MINUTE, 59);
        // 秒
        calendar.set(Calendar.SECOND, 59);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("time:" + getDate("yyyy-MM-dd HH:mm:ss", calendar.getTime().getTime()));
        ;
//		AppFramentUtil.logCatUtil.v("time:"+getDate("yyyy-MM-dd",calendar.getTime().getTime()));;
        return calendar.getTime().getTime() / 1000;//除1000是去除毫秒
//		String lastDayOfMonth = format.format(calendar.getTime());
//
//		System.out.println(lastDayOfMonth);
    }


    private static long lastClickTime = 0L;

    /**
     * 在一定事件范围内是否有效
     *
     * @param intervalTime 事件触发的间隔  1s=1000   false:无效。
     */
    public static boolean invalidTime(long intervalTime) {
        if (System.currentTimeMillis() - lastClickTime <= intervalTime) {//小于等于1秒
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }
}
