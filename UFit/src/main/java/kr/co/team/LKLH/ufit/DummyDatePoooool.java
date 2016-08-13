package kr.co.team.LKLH.ufit;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.io.Serializable;

/**
 * Created by Admin on 2016-07-29.
 */
public class DummyDatePoooool implements Serializable {
    private String year;
    private String title;
    private String name;
    private String time;
    private String img;
    private String chest, armfit, calf, thigh, waist;
    private String centi = "cm";
    private int mid;


    public void setData(int mid, String img, String name){
        this.mid = mid;
        this.img = img;
        this.name= name;
    }
    public void setData(String img, String name, String time){
        this.img = img;
        this.name = name;
        this.time = time;
    }
    public void setData(String img, String name){
        this.img = img;
        this.name = name;
    }
    public void setData(String chest, String thigh, String calf, String armfit, String waist) {
        SpannableStringBuilder builder = new SpannableStringBuilder(centi);
        builder.setSpan(new ForegroundColorSpan(Color.RED),0,centi.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(builder);
        this.calf = calf + builder;
        this.chest = chest;
        this.thigh = thigh;
        this.armfit = armfit;
        this.waist = waist;
    }

    public String getImg(){
        return img;
    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }

    public int getMid() {
        return mid;
    }

    public String getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getChest() {
        return chest;
    }

    public String getArmfit() {
        return armfit;
    }

    public String getCalf() {
        return calf;
    }

    public String getThigh() {
        return thigh;
    }

    public String getWaist() {
        return waist;
    }

    public String getCenti() {
        return centi;
    }
}
