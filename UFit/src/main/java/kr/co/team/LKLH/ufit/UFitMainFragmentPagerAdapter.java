package kr.co.team.LKLH.ufit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ccei on 2016-08-07.
 */
public class UFitMainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    int year;
    int month;
    int day;
    int currentPosition;
    int initial_counter;

    public UFitMainFragmentPagerAdapter(FragmentManager fm, int year, int month, int day) {
        super(fm);
        this.year = year;
        this.month = month;
        this.day = day;
        this.currentPosition = 25000 + day;
    }

    @Override
    public int getCount() {
        return 50000;
    }

    @Override
    public Fragment getItem(int position) {
        if(initial_counter> 2){
            if((currentPosition-position) == 2){
                calendar.add(Calendar.DAY_OF_YEAR, -2);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if((position-currentPosition) == 2){
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if((currentPosition-position) > 2){
                calendar.add(Calendar.DAY_OF_YEAR, -3);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if((position-currentPosition) > 2){
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if(position > currentPosition){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if(position < currentPosition){
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }
        }
        else if(initial_counter <= 2){
            if(currentPosition == position){
                initial_counter++;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if(position < currentPosition){
                initial_counter++;
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                currentPosition = position;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }

            else if(position > currentPosition){
                initial_counter++;
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                currentPosition = position - 1;
                Log.i("나는 프레그먼트 처음포지션", "" + position);
                Log.i("리턴하는인스턴스", ""+year + month + day);
                return UFitMainFragment.newInstance(year, month, day);
            }
        }
        Log.i("나는 프레그먼트 처음포지션", "" + position);
        Log.i("리턴하는인스턴스", ""+year + month + day);
        return UFitMainFragment.newInstance(year, month, day);

    }
}
