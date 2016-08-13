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
public class CalendarFragmentPagerAdapter extends FragmentStatePagerAdapter {
    Calendar calender = Calendar.getInstance(Locale.getDefault());
    int year;
    int month;
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


    public CalendarFragmentPagerAdapter(FragmentManager fm, int year, int month) {
        super(fm);
        this.year = year;
        this.month = month;
        this.maximum_day = calender.getActualMaximum(calender.DAY_OF_MONTH);
        this.currentPosition = 20736 + month;


//        calender.set(Calendar.YEAR, year);
//        calender.set(Calendar.MONTH, month);
//        calender.set(Calendar.DATE, 1);

        this.startDay = calender.get(Calendar.DAY_OF_WEEK);

        Log.e("첫째날 기본값된다 ? ? ", "" + startDay);

        Log.e("이달의 최대일수는 ? ", "" + this.maximum_day);

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

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 1 && isinital_month_january == true) {
                    year++;
                    isinital_month_january = false;
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 10 && isInital_month_december == true) {
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 0 && isInital_month_december == true) {
                    year++;
                    toggler_year_increment = true;
                    isInital_month_december = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                }
            } else {
                if (fragment_month == 0 && position > currentPosition) {
                    year++;
                    toggler_year_increment = true;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 1 && toggler_year_increment == true) {
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                }
/// 11 - 12 - 1- 11 // skipper test
                else if (fragment_month == 10 && (currentPosition - position) > 2) {
                    year--;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 9 && toggler_year_increment == true) {
                    year--;
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 11 && currentPosition > position) {
                    year--;
                    toggler_year_decrement = true;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 10 && toggler_year_decrement == true) {
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 1 && (position - currentPosition) > 2) {
                    year++;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                }

//          if the inital month  = 12
                else if (fragment_month == 1 && isInital_month_december == true) {
                    year--;
                    isInital_month_december = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 9 && toggler_year_increment == true) {
                    year--;
                    toggler_year_increment = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else if (fragment_month == 2 && toggler_year_decrement == true) {
                    year++;
                    toggler_year_decrement = false;
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
                } else {
                    calender.set(year, month, 1);
                    startDay = calender.get(Calendar.DAY_OF_WEEK);
                    maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                    currentPosition = position;

                    return CalendarFragment.newInstance(year, month, maximum_day, startDay);
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

            return CalendarFragment.newInstance(year, month, maximum_day, startDay);
        }
        return CalendarFragment.newInstance(year, month, maximum_day, startDay);
    }


/* 8888888888888888888888888888888888
        if(initial_counter != 0) {


            Log.e("asdasdasd", "" + calender.get(Calendar.MONTH) + ", " + fragment_month);
//            if(toggler && fragment_month == 2 && currentPosition < position){
//                year++;
//                month = fragment_month;
//                calender.set(year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = false;
//                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
//            }
//
//            if(toggler && fragment_month == 9 && currentPosition > position){
//                year--;
//                month = fragment_month;
//                calender.set(year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = false;
//                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
//            }
            Log.i("yr, f_m, c_p, p", "yr-"+ year + " f_m-" + fragment_month + "c_p-" + currentPosition + "po-" + position);

            if(toggler && month == 11 && (currentPosition - position) > 2){
                year--;
                toggler = !toggler;
            }
            if(toggler && month == 0&& (position - currentPosition) > 2){
                year++;
                toggler = !toggler;
            }


//            if(fragment_month == 10){
//                year = previous_year;
//            }
//            if(fragment_month == 2){
//                year = previous_year;
//            }

            if(fragment_month == 11 && currentPosition > position){
                year--;
//                Log.i("yr, f_m, c_p, p", "yr-"+ year + " f_m-" + fragment_month + "c_p-" + currentPosition + "po-" + position);
//                previous_year = year - 1;
                month = fragment_month;
                calender.set(year, month, 1);
                startDay = calender.get(Calendar.DAY_OF_WEEK);
                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                toggler = true;

                String _month = "" + (month + 1);
                if((month + 1)< 10){
                    _month = "0" + (month + 1);
                }

//                new TrainerMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximum_day);
                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
            }


            else if(fragment_month == 0 && currentPosition < position){
                year++;
//                next_year = year + 1;
                Log.i("year", "" +  year);
                month = fragment_month;
                calender.set(year, month, 1);
                startDay = calender.get(Calendar.DAY_OF_WEEK);
                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                toggler = true;

                String _month = "" + (month + 1);
                if((month + 1)< 10){
                    _month = "0" + (month + 1);
                }

//                new TrainerMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximum_day);
                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
            }

            else{
                Log.i("year", "" + year);
                month = fragment_month;
                calender.set(year, month, 1);
                startDay = calender.get(Calendar.DAY_OF_WEEK);
                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
                currentPosition = position;

                String _month = "" + (month + 1);
                if((month + 1)< 10){
                    _month = "0" + (month + 1);
                }

//                new TrainerMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximum_day);
                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
            }
        }

//            if (fragment_month == 0 && position > currentPosition) {
//                month = fragment_month;
//                calender.set(++year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = true;
//                return CalendarFragment.newInstance(year--, month, maximum_day, startDay);
//            }
//
//            else if(fragment_month == 1 && position > currentPosition){
//                year++;
//                month = fragment_month;
//                calender.set(year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = true;
//                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
//            }
//
//            else if(fragment_month == 11 && currentPosition > position){
//                month = fragment_month;
//                calender.set(--year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = true;
//                return CalendarFragment.newInstance(year++, month, maximum_day, startDay);
//            }
//            else if(fragment_month == 1 && currentPosition > position){
//                year--;
//                month = fragment_month;
//                calender.set(year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                toggler = true;
//                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
//            }
//
//            else{
//                Log.i("year", "" + year);
//                month = fragment_month;
//                calender.set(year, month, 1);
//                startDay = calender.get(Calendar.DAY_OF_WEEK);
//                maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
//                currentPosition = position;
//                return CalendarFragment.newInstance(year, month, maximum_day, startDay);
//            }
//
//        }
//
        else{
            Log.i("year", "" + year);

            calender.set(year, month, 1);
            startDay = calender.get(Calendar.DAY_OF_WEEK);
            maximum_day = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
            int today = calender.get(Calendar.DAY_OF_MONTH);
            currentPosition = position;
            initial_counter++;

            String _month = "" + (month + 1);
            if((month + 1)< 10){
                _month = "0" + (month + 1);
            }

//
//            TrainerMonthlySchedule ts = new TrainerMonthlySchedule();
//            ts.execute("1","" + year + _month + "01", "" + year + _month + maximum_day);
//            int[] asyncResult = null;
//            try {
//                asyncResult= ts.get();
//            } catch (InterruptedException e) {
//            } catch (ExecutionException e) {
//            }
//            return CalendarFragment.newInstance(year, month, maximum_day, startDay, monthlySchedule);
            return CalendarFragment.newInstance(year, month, maximum_day, startDay);
        }
 88888888888888888888888888888888888888888888*/


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
