package com.aghajari.amirtextbuttonview;

import android.graphics.Color;
import android.graphics.Typeface;

public class AmirTextData {
    
    public CharSequence text;
    public long duration;
    public int textColor;
    public float textSize;
    public  long sleep;
    public Typeface textTypeface;

    public AmirTextData (CharSequence text,long duration,long sleep,int textColor,float textSize,Typeface textTypeface){
        this.text=text;
        this.duration=duration;
        this.textColor=textColor;
        this.textSize=textSize;
        this.textTypeface=textTypeface;
        this.sleep = sleep;
    }

    public AmirTextData (CharSequence text,long duration,long sleep,int textColor,float textSize){
        this.text=text;
        this.duration=duration;
        this.textColor=textColor;
        this.textSize=textSize;
        this.textTypeface=Typeface.DEFAULT;
        this.sleep = sleep;
    }

    public AmirTextData (CharSequence text,long duration,long sleep){
        this.text=text;
        this.duration=duration;
        this.textColor= Color.WHITE;
        this.textSize= 14;
        this.textTypeface=Typeface.DEFAULT;
        this.sleep = sleep;
    }

    public AmirTextData (CharSequence text,long duration){
        this.text=text;
        this.duration=duration;
        this.textColor= Color.WHITE;
        this.textSize= 16;
        this.textTypeface=Typeface.DEFAULT;
        this.sleep = duration*4;
    }

}
