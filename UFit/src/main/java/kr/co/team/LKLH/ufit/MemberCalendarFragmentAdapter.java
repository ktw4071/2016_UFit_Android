package kr.co.team.LKLH.ufit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Admin on 2016-08-01.
 */
public class MemberCalendarFragmentAdapter extends FragmentStatePagerAdapter {
    Calendar calender = Calendar.getInstance(Locale.getDefault());
    int year;
    int month;
    int _mid;
    int maximum_day;
    int currentPosition;
    int initial_counter;
    int startDay;
    boolean toggler;
    boolean toggler_year_increment;
    boolean toggler_year_decrement;
    boolean isinital_month_january;
    boolean isInital_month_december;
    int[] monthlySchedule;



    public MemberCalendarFragmentAdapter(FragmentManager fm, int year, int month, int _mid) {
        super(fm);
        this.year = year;
        this.month = month;
        this._mid = _mid;
        this.maximum_day = calender.getActualMaximum(calender.DAY_OF_MONTH);
        this.currentPosition = 20736 + month;


//        calender.set(Calendar.YEAR, year);
//        calender.set(Calendar.MONTH, month);
//        calender.set(Calendar.DATE, 1);

        this.startDay = calender.get(Calendar.DAY_OF_WEEK);

        Log.e("첫째날 기본값된다 ? ? ", ""+ startDay);

        Log.e("이달의 최대일수는 ? ", ""+this.maximum_day);

//        int abc = calender.getFirstDayOfWeek();
//        Log.e("123123첫재쭈 일수는 ? ? ", ""+ abc);
//        Log.e("111첫재쭈 일수는 ? ? ", ""+ calender.DAY_OF_WEEK_);

        //*** 첫쨰날 밀어내기 이거쓰자
//        calender.set(2015, 6, 1);
        startDay = calender.get(Calendar.DAY_OF_WEEK);

//        abc = calender.getFirstDayOfWeek();
//        Log.e("야야야 일수는 ? ", ""+ startDay);

//        calender.set(Calendar.YEAR, 2015);
//        calender.set(Calendar.MONTH, 6);
//        calender.set(Calendar.DATE, 1);
//
//        startDay = calender.get(Calendar.DAY_OF_WEEK);

//        Log.e("동일하지만다른 일수는 ? ", ""+ startDay);

    }

    //    public CalenderFragmentPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }


    @Override
    public int getCount() {
        return 50000;
    }

    @Override
    public Fragment getItem(int position) {
        int fragment_month = (position % 12);
        month = fragment_month;
        Log.e("프래그먼트 포지션 getITEM", "" + position);
        Log.e("다음달 포지션월은 뭐지?", "" + (position % 12));

        if (initial_counter != 0) {
            //          if the inital month  = 1

            if (isinital_month_january || isInital_month_december) {
                if (fragment_month == 11 && isinital_month_january == true) {
                    year--;
                    toggler_year_decrement = true;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 1 && isinital_month_january == true) {
                    year++;
                    isinital_month_january = false;
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 10 && isInital_month_december == true) {
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 0 && isInital_month_december == true) {
                    year++;
                    toggler_year_increment = true;
                    isInital_month_december = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                }
            } else {
                if (fragment_month == 0 && position > currentPosition) {
                    year++;
                    toggler_year_increment = true;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 1 && toggler_year_increment == true) {
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                }
/// 11 - 12 - 1- 11 // skipper test
                else if (fragment_month == 10 && (currentPosition - position) > 2) {
                    year--;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 9 && toggler_year_increment == true) {
                    year--;
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 11 && currentPosition > position) {
                    year--;
                    toggler_year_decrement = true;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 10 && toggler_year_decrement == true) {
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 1 && (position - currentPosition) > 2) {
                    year++;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                }

//          if the inital month  = 12
                else if (fragment_month == 1 && isInital_month_december == true) {
                    year--;
                    isInital_month_december = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 9 && toggler_year_increment == true) {
                    year--;
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else if (fragment_month == 2 && toggler_year_decrement == true) {
                    year++;
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                } else {
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
                }
            }

        } else {
            Log.i("year", "" + year);

            if (month == 0) {
                isinital_month_january = true;
            }

            if (month == 11) {
                isInital_month_december = true;
            }

            calender.set(year, month, 1);
            startDay = calender.get(Calendar.DAY_OF_WEEK);
            maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
            currentPosition = position;
            initial_counter++;

            return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
        }
        return MemberCalendarFragment.newInstance(year, month, maximum_day, startDay, _mid);
    }



    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        Log.d("파괴", position + "번째");

        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("프래그먼트 포지션 tantiateItem", "" + position);
        return super.instantiateItem(container, position);
    }

//    @Override
//    public float getPageWidth(int position) {
//        return 0.5f;
//    }


}
