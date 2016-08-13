package kr.co.team.LKLH.ufit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ccei on 2016-08-07.
 */
public class UFitMainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    Calendar calender = Calendar.getInstance(Locale.getDefault());
    int year;
    int month;
    int day;
    int maximum_day;
    int currentPosition;
    boolean initial_counter;

    public UFitMainFragmentPagerAdapter(FragmentManager fm, int year, int month, int day) {
        super(fm);
        this.year = year;
        this.month = month;
        this.day = day;
        this.maximum_day = calender.getActualMaximum(calender.DAY_OF_MONTH);
        this.currentPosition = 25000 + day;
    }

    @Override
    public int getCount() {
        return 50000;
    }

    @Override
    public Fragment getItem(int position) {
        if(initial_counter){
            if(position > currentPosition){
                day++;
                currentPosition = position;
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if(position < currentPosition){
                day--;
                currentPosition = position;
                return UFitMainFragment.newInstance(year, month, day);
            }
        }
        else if(!initial_counter){
            initial_counter = !initial_counter;
            currentPosition = position;
            return UFitMainFragment.newInstance(year, month, day);
        }
        return UFitMainFragment.newInstance(year, month, day);

    }
}
