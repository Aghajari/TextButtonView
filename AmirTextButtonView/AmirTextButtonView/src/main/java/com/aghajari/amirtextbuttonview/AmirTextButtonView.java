package com.aghajari.amirtextbuttonview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : Amir Hossein Aghajari
 * @version : 1.02
 */
public class AmirTextButtonView extends AmirBaseParent {

    private  AmirTextButtonListener listener = null;
    private  List<AmirTextData> texts = new ArrayList<AmirTextData>();
    private Drawable mBG1=null,mBG3=null;
    private  GradientDrawable mBG2=null;
    private  boolean mBGAnimationRunning = false;
    private  AmirBaseParent mIcon = null;
    private  AmirTextData buttonText = new AmirTextData("SUBMIT",500);
    private  AmirTextData button2Text = new AmirTextData("CANCEL",500);
    private  AmirBaseParent buttonParent;
    private  AmirBaseParent buttonParent2;
    private  TextView button;
    private  TextView button2;
    private  TextView label;
    private AppCompatEditText mEditText;
    private  int mIconParentWidth = -1;
    private  int mIconWidth = -1;
    private  int radius = -1;
    private  boolean AnimRunning = false;
    private  boolean canSwitchToSubmit = false;
    private  boolean autoBack=true;

    public static final int STATE_TEXTSHOW=3;
    public static final int STATE_EDITTEXT=2;
    public static final int STATE_BUTTON=1;
    public static final int STATE_BUTTON2=-1;
    private int state = 0;
    private int mEditTextLeftMargin = -1,mEditTextRightMargin = -1;
    public AmirTextButtonView(Context context) {
        super(context);
        init();
    }

    public AmirTextButtonView(Context context,  AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public AmirTextButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        if (mIconWidth==-1) mIconWidth = dp(24);
        if (mEditTextLeftMargin==-1) mEditTextLeftMargin = dp(12);
        if (mEditTextRightMargin==-1) mEditTextRightMargin = dp(12);
        if (mIconParentWidth==-1) mIconParentWidth = dp(48);
        if (radius==-1) radius = dp(60);
        state = STATE_BUTTON;
    }

    private boolean builded=false;

    public void setButtonData (AmirTextData textData){
        buttonText = textData;
        if (builded) setTextViewCS(button,textData);
    }

    public void setButton2Data (AmirTextData textData){
        button2Text = textData;
        if (builded) setTextViewCS(button2,textData);
    }

    /**
     * auto switch to STATE_BUTTON after STATE_TEXTSHOW
     */
    public void setAutoSwitchToButton (boolean value){
        this.autoBack = value;
    }

    public void setIconWidth (int IconSize,int ParentSize){
        mIconWidth = IconSize;
        mIconParentWidth = ParentSize;
    }

    public void addText (AmirTextData data){
        texts.add(data);
    }

    public void removeText (int index){
        texts.remove(index);
    }

    public AmirTextData getText (int index){
        return texts.get(index);
    }

    public List<AmirTextData> getTexts() {
        return texts;
    }

    public void setEditTextMargin(int Margin){
        mEditTextLeftMargin = Margin;
        mEditTextRightMargin = Margin;
    }

    public void setEditTextLeftMargin(int Margin){
        mEditTextLeftMargin = Margin;
    }

    public void setEditTextRightMargin(int Margin){
        mEditTextRightMargin = Margin;
    }

    public void setDefaultBackground(Drawable bg){
        mBG1 = bg;
        if (builded) {
            setBackground(copy(bg));
            buttonParent.enableClipToOutline();
            enableClipToOutline();
        }
    }

    public void setButtonBackground(GradientDrawable bg){
        mBG2 = bg;
        if (builded) {
            if (builded) {
                buttonParent.setBackground(copy(bg));
                buttonParent.enableClipToOutline();
                enableClipToOutline();
            }
        }
    }

    public void setListener(AmirTextButtonListener listener){
        this.listener=listener;
    }

    public void setIcon(Drawable bg){
        mBG3 = bg;
        if (builded && mIcon!=null) {
            AppCompatImageView img = (AppCompatImageView) mIcon.getChildAt(0);
            img.setImageDrawable(copy(bg));
        }
    }

    public void setRadius(int radius){
        this.radius=radius;
    }
    public  int getRadius(){
        return this.radius;
    }

    public int getState(){
        return  state;
    }

    private CharSequence hint = "";
    private  int hintColor = Color.GRAY;
    private  float edtSize = 16;
    private  int edtColor = Color.BLACK;
    private  Typeface edtTypeface = Typeface.DEFAULT;
    public void setEditTextData (CharSequence hint, int hintColor, float textSize, int textColor, Typeface typeface){
        this.hint = hint;
        this.hintColor = hintColor;
        this.edtSize = textSize;
        this.edtColor = textColor;
        this.edtTypeface = typeface;
        if (builded){
            mEditText.setHint(hint);
            mEditText.setHintTextColor(hintColor);
            mEditText.setTextSize(edtSize);
            mEditText.setTextColor(edtColor);
            mEditText.setTypeface(edtTypeface);
        }
    }


    public AppCompatEditText getEditText() {
         return mEditText;
    }

    public AppCompatImageView getImageView() {
        return (AppCompatImageView) mIcon.getChildAt(0);
    }

    public void build(){
        if (builded) return;

        mEditText = new AppCompatEditText(getContext());
        addView(mEditText,new AmirBaseParent.LayoutParams
                (mEditTextLeftMargin,0,GetWidth()-mIconParentWidth-mEditTextRightMargin,GetHeight()));
        mEditText.setHint(hint);
        mEditText.setHintTextColor(hintColor);
        mEditText.setTextSize(edtSize);
        mEditText.setTextColor(edtColor);
        mEditText.setTypeface(edtTypeface);
        mEditText.setBackground(null);
        mEditText.setGravity(Gravity.LEFT);
        mEditText.setVisibility(View.GONE);
        mEditText.setEnabled(false);

        button = new TextView(getContext());

        label = new TextView(getContext());
        buttonParent = new AmirBaseParent(getContext());

        setTextViewCS(button,buttonText);
        this.addView(buttonParent,new AmirBaseParent.LayoutParams(0,0,GetWidth(),GetHeight()));
        this.addView(button,new AmirBaseParent.LayoutParams(0,0,GetWidth(),GetHeight()));
        buttonParent.setBackground(copy(mBG2));
        AmirBaseParent.setClickEffect(button,false);
        buttonClick();
        buttonParent.enableClipToOutline();

        if (this.change) {
            button2 = new TextView(getContext());
            buttonParent2= new AmirBaseParent(getContext());
            this.addView(buttonParent2, new AmirBaseParent.LayoutParams(0, 0, GetWidth(), GetHeight()));
            this.addView(button2, new AmirBaseParent.LayoutParams(0, 0, GetWidth(), GetHeight()));
            buttonParent2.setBackground(copy(mBG4));
            AmirBaseParent.setClickEffect(button2, false);
            button2Click();
            buttonParent2.enableClipToOutline();
            setTextViewCS(button2,button2Text);
            if (this.btn2select) {
                buttonParent.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                buttonParent.setEnabled(false);
                button.setEnabled(false);
                state = STATE_BUTTON2;
            } else {
                buttonParent2.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                buttonParent2.setEnabled(false);
                button2.setEnabled(false);
                state = STATE_BUTTON;
            }
        }
        this.addView(label,new AmirBaseParent.LayoutParams(0,0,GetWidth(),GetHeight()));
        label.setVisibility(View.GONE);

        setBackground(copy(mBG1));
        enableClipToOutline();

        builded=true;
    }

    private void buttonClick(){
    button.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (AnimRunning) return;
            boolean can = true;
            if (listener!=null) can=listener.buttonClick(STATE_BUTTON);
            if (can&&state == STATE_BUTTON){
                startCloseButtonAnimation();
                mEditText.setText("");
                setTextVisible(mEditText,buttonText.duration/3*2,true);
                setKeyboardV(true);
            }
        }
    });
    }

    private void button2Click(){
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (AnimRunning) return;
                boolean can = true;
                if (listener!=null) can=listener.buttonClick(STATE_BUTTON2);
                if (can && state == STATE_BUTTON2){
                    switchToBtn1();
                }
            }
        });
    }

    public boolean isAnimationRunning(){
        return  AnimRunning;
    }

    public void toggleButton(){
        if (!change) return;
        if (state == STATE_BUTTON){
            switchToBtn2();
        }else if (state == STATE_BUTTON2){
            switchToBtn1();
        }
    }

    public void switchToButton1(){
        if (state == STATE_BUTTON2){
            switchToBtn1();
        }
    }

    public void switchToButton2(){
        if (!change) return;
        if (state == STATE_BUTTON){
            switchToBtn2();
        }
    }

    private void switchToBtn1(){
        state = STATE_BUTTON;
        if (listener!=null) listener.onStateChanged(state,buttonText);
        setAlphaVisible(buttonParent2,button2Text.duration,false);
        setTextVisible(button2,button2Text.duration,false);
        setAlphaVisible(buttonParent,buttonText.duration,true);
        setTextVisible(button,buttonText.duration,true);
    }

    private void switchToBtn2(){
        state = STATE_BUTTON2;
        if (listener!=null) listener.onStateChanged(state,button2Text);
        setAlphaVisible(buttonParent2,button2Text.duration,true);
        setTextVisible(button2,button2Text.duration,true);
        setAlphaVisible(buttonParent,buttonText.duration,false);
        setTextVisible(button,buttonText.duration,false);
    }

    private void startCloseButtonAnimation(){
        AnimRunning = true;
        state = STATE_EDITTEXT;
        if (listener!=null) listener.onStateChanged(state,null);
        ValueAnimator anim_Close = ValueAnimator.ofInt(0,buttonParent.GetWidth()-mIconParentWidth);
        anim_Close.setDuration(buttonText.duration);
        final int w = GetWidth();
        anim_Close.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                buttonParent.SetLeft((int)arg0.getAnimatedValue());
                buttonParent.SetWidth(w-buttonParent.GetLeft());
                if (mBGAnimationRunning==false && arg0.getCurrentPlayTime()>=arg0.getDuration()/4){
                    mBGAnimationRunning = true;
                    ValueAnimator anim_bg = ValueAnimator.ofFloat(radius,0);
                    anim_bg.setDuration(Math.abs(arg0.getDuration()-arg0.getCurrentPlayTime()));
                    anim_bg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            GradientDrawable bg = (GradientDrawable) copy(mBG2);
                            float r = (float)valueAnimator.getAnimatedValue();
                            bg.setCornerRadii(new float[] {r,r,radius,radius,radius,radius,radius,radius});
                            buttonParent.setBackground(bg);
                        }
                    });
                    anim_bg.start();

                    //AddIcon
                    AmirBaseParent iconParent = new AmirBaseParent(getContext());
                    buttonParent.addView(iconParent,new AmirBaseParent.LayoutParams(0,0,mIconParentWidth,GetHeight()));
                    AppCompatImageView img = new AppCompatImageView(getContext());
                    iconParent.addView(img,new AmirBaseParent.LayoutParams((iconParent.GetWidth()/2)-(mIconWidth/2)
                            ,(iconParent.GetHeight()/2)-(mIconWidth/2),mIconWidth,mIconWidth));
                    if (mBG3!=null) img.setImageDrawable(mBG3);

                    buttonIconClick(iconParent);
                    iconParent.enableClipToOutline();

                    setTextVisible(iconParent,anim_bg.getDuration(),true);
                    mIcon = iconParent;
                }else{
                    buttonParent.setBackground(copy(mBG2));
                }
            }
        });
        anim_Close.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationEnd(Animator animator) {
                mBGAnimationRunning=false;
                AnimRunning=false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        anim_Close.start();

        setTextVisible(button,buttonText.duration/2,false);
    }

    private void buttonIconClick(AmirBaseParent iconParent) {
        iconParent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (AnimRunning) return;
                boolean can=true;
                if (listener!=null) can=listener.buttonClick(STATE_EDITTEXT);
                if (can && !hideTexts && listener!=null) can = listener.canAcceptText(mEditText.getText());
                if (can && state == STATE_EDITTEXT) {
                    startShowButtonAnimation();
                    setKeyboardV(false);
                    setTextVisible(mEditText,buttonText.duration/3*2,false);
                    SleepTimer timer = new SleepTimer(buttonText.duration / 3 * 2, new SleepTimer.SleepTimerListener() {
                        @Override
                        public void onEnd() {
                            mEditText.setText("");
                        }
                    });
                    timer.setEnabled(true);
                }
            }
        });
    }

    private void startShowButtonAnimation(){
        AnimRunning = true;
        if (texts.size()==0 || hideTexts) {
            //
        }else{
            state = STATE_TEXTSHOW;
        }
        ValueAnimator anim_Show = ValueAnimator.ofInt(buttonParent.GetLeft(),0);
        anim_Show.setDuration(buttonText.duration);
        final int w = GetWidth();
        anim_Show.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                buttonParent.SetLeft((int)arg0.getAnimatedValue());
                buttonParent.SetWidth(w-buttonParent.GetLeft());
                if (mBGAnimationRunning==false && arg0.getCurrentPlayTime()>=arg0.getDuration()/4){
                    mBGAnimationRunning = true;
                    ValueAnimator anim_bg = ValueAnimator.ofFloat(0,radius);
                    anim_bg.setDuration(Math.abs(arg0.getDuration()-arg0.getCurrentPlayTime()));
                    anim_bg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            GradientDrawable bg = (GradientDrawable) copy(mBG2);
                            float r = (float)valueAnimator.getAnimatedValue();
                            bg.setCornerRadii(new float[] {r,r,radius,radius,radius,radius,radius,radius});
                            buttonParent.setBackground(bg);
                        }
                    });
                    anim_bg.start();
                }else{
                    buttonParent.setBackground(copy(mBG2));
                }
            }
        });
        anim_Show.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}
            @Override
            public void onAnimationEnd(Animator animator) {
                mBGAnimationRunning=false;
                AnimRunning=false;
                if (mIcon!=null){
                    mIcon.removeAllViews();
                    mIcon.removeView();
                    mIcon=null;
                }
                if (texts.size()>0 && hideTexts==false){
                    showTexts(0,true);
                }
                hideTexts=false;
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        anim_Show.start();

        if (mIcon!=null) setTextVisible(mIcon,buttonText.duration/2,false);
        if (hideTexts){
            state = STATE_BUTTON;
            if (listener != null) listener.onStateChanged(state, buttonText);
            setTextVisible(button, buttonText.duration, true);
        }else {
            if (texts.size() == 0) {
                if (change) {
                    switchToBtn2();
                } else {
                    state = STATE_BUTTON;
                    if (listener != null) listener.onStateChanged(state, buttonText);
                    setTextVisible(button, buttonText.duration, true);
                }
            }
        }
    }

    public boolean canSwitchToButton(){
        return canSwitchToSubmit;
    }

    public boolean canToggleButton(){
        if (!change) return false;
        if (state==STATE_BUTTON || state ==STATE_BUTTON2) return  true;
        return false;
    }

    boolean hideTexts=false;
    public boolean switchToButton(){
        if (canSwitchToSubmit){
            setTextVisible(button, buttonText.duration / 2, true);
            state = STATE_BUTTON;
            if (listener != null) listener.onStateChanged(state, buttonText);
            canSwitchToSubmit=false;
            return  true;
        }
        if (state == STATE_EDITTEXT){
            hideTexts=true;
            onIconClick();
        }
        return  false;
    }

    public void onButtonClick(){
        button.callOnClick();
    }

    public void onIconClick(){
        if (mIcon!=null){
            mIcon.callOnClick();
        }
    }

    private void showTexts(final int index,final boolean shows){
        if (texts.size()<=index){
            if (change==false) {
                if (autoBack) {
                    setTextVisible(button, buttonText.duration / 2, true);
                    state = STATE_BUTTON;
                    if (listener != null) listener.onStateChanged(state, buttonText);
                } else {
                    canSwitchToSubmit = true;
                }
            }else{
                //setTextVisible(button, buttonText.duration / 4, true);
                switchToBtn2();
            }
        }else {
            canSwitchToSubmit=false;
            label.setVisibility(View.VISIBLE);
            state = STATE_TEXTSHOW;
            final AmirTextData data = texts.get(index);
            setTextViewCS(label, data);
            if (listener!=null) listener.onStateChanged(state,data);
            ObjectAnimator anim_ALPHA;
            ObjectAnimator anim_SCALE_X;
            ObjectAnimator anim_SCALE_Y;
            anim_ALPHA = ObjectAnimator.ofFloat(label, "alpha", 0, 1);
            anim_ALPHA.setDuration(data.duration);
            anim_SCALE_X = ObjectAnimator.ofFloat(label, "scaleX", 0.6f, 1f);
            anim_SCALE_X.setDuration(data.duration);
            anim_SCALE_Y = ObjectAnimator.ofFloat(label, "scaleY", 0.6f, 1f);
            anim_SCALE_Y.setDuration(data.duration);
            anim_SCALE_X.setInterpolator(new android.view.animation.OvershootInterpolator());
            anim_SCALE_Y.setInterpolator(new android.view.animation.OvershootInterpolator());
            anim_SCALE_Y.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {}
                @Override
                public void onAnimationEnd(Animator animator) {
                    if (shows){
                        SleepTimer timer = new SleepTimer(data.sleep, new SleepTimer.SleepTimerListener() {
                            @Override
                            public void onEnd() {
                                showTexts(index,false);
                            }
                        });
                        timer.setEnabled(true);
                    }else{
                        SleepTimer timer = new SleepTimer(50, new SleepTimer.SleepTimerListener() {
                            @Override
                            public void onEnd() {
                                showTexts(index+1,true);
                            }
                        });
                        timer.setEnabled(true);
                    }
                }
                @Override
                public void onAnimationCancel(Animator animator) {}
                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
            if (shows) {
                anim_ALPHA.start();
                anim_SCALE_X.start();
                anim_SCALE_Y.start();
            }else {
                anim_ALPHA.reverse();
                anim_SCALE_X.reverse();
                anim_SCALE_Y.reverse();
            }
        }
    }


    private void showRangeTexts(final int end,final int index,final boolean shows){
        if (texts.size()<=index || end<index){
            if (change==false) {
                if (autoBack) {
                    setTextVisible(button, buttonText.duration / 2, true);
                    state = STATE_BUTTON;
                    if (listener != null) listener.onStateChanged(state, buttonText);
                } else {
                    canSwitchToSubmit = true;
                }
            }else{
                //setTextVisible(button, buttonText.duration / 4, true);
                switchToBtn2();
            }
        }else {
            canSwitchToSubmit=false;
            label.setVisibility(View.VISIBLE);
            state = STATE_TEXTSHOW;
            final AmirTextData data = texts.get(index);
            setTextViewCS(label, data);
            if (listener!=null) listener.onStateChanged(state,data);
            ObjectAnimator anim_ALPHA;
            ObjectAnimator anim_SCALE_X;
            ObjectAnimator anim_SCALE_Y;
            anim_ALPHA = ObjectAnimator.ofFloat(label, "alpha", 0, 1);
            anim_ALPHA.setDuration(data.duration);
            anim_SCALE_X = ObjectAnimator.ofFloat(label, "scaleX", 0.6f, 1f);
            anim_SCALE_X.setDuration(data.duration);
            anim_SCALE_Y = ObjectAnimator.ofFloat(label, "scaleY", 0.6f, 1f);
            anim_SCALE_Y.setDuration(data.duration);
            anim_SCALE_X.setInterpolator(new android.view.animation.OvershootInterpolator());
            anim_SCALE_Y.setInterpolator(new android.view.animation.OvershootInterpolator());
            anim_SCALE_Y.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {}
                @Override
                public void onAnimationEnd(Animator animator) {
                    if (shows){
                        SleepTimer timer = new SleepTimer(data.sleep, new SleepTimer.SleepTimerListener() {
                            @Override
                            public void onEnd() {
                                showRangeTexts(end,index,false);
                            }
                        });
                        timer.setEnabled(true);
                    }else{
                        SleepTimer timer = new SleepTimer(50, new SleepTimer.SleepTimerListener() {
                            @Override
                            public void onEnd() {
                                showRangeTexts(end,index+1,true);
                            }
                        });
                        timer.setEnabled(true);
                    }
                }
                @Override
                public void onAnimationCancel(Animator animator) {}
                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
            if (shows) {
                anim_ALPHA.start();
                anim_SCALE_X.start();
                anim_SCALE_Y.start();
            }else {
                anim_ALPHA.reverse();
                anim_SCALE_X.reverse();
                anim_SCALE_Y.reverse();
            }
        }
    }


    private void setTextVisible (View view,long duration,boolean shows){
        view.setVisibility(View.VISIBLE);
        view.setEnabled(shows);
        ObjectAnimator anim_ALPHA;
        ObjectAnimator anim_SCALE_X;
        ObjectAnimator anim_SCALE_Y;
        anim_ALPHA = ObjectAnimator.ofFloat(view, "alpha", 0,1);
        anim_ALPHA.setDuration(duration);
        anim_SCALE_X = ObjectAnimator.ofFloat(view, "scaleX", 0.6f,1f);
        anim_SCALE_X.setDuration(duration);
        anim_SCALE_Y = ObjectAnimator.ofFloat(view, "scaleY", 0.6f,1f);
        anim_SCALE_Y.setDuration(duration);
        anim_SCALE_X.setInterpolator(new android.view.animation.OvershootInterpolator());
        anim_SCALE_Y.setInterpolator(new android.view.animation.OvershootInterpolator());
        if (shows) {
            anim_ALPHA.start();
            anim_SCALE_X.start();
            anim_SCALE_Y.start();
        }else {
            anim_ALPHA.reverse();
            anim_SCALE_X.reverse();
            anim_SCALE_Y.reverse();
        }
    }

    private void setAlphaVisible (View view,long duration,boolean shows){
        view.setVisibility(View.VISIBLE);
        view.setEnabled(shows);
        ObjectAnimator anim_ALPHA;
        anim_ALPHA = ObjectAnimator.ofFloat(view, "alpha", 0,1);
        anim_ALPHA.setDuration(duration);
        if (shows) {
            anim_ALPHA.start();
        }else {
            anim_ALPHA.reverse();
        }
    }

    private void setTextViewCS(TextView v,AmirTextData data){
        v.setText(data.text);
        v.setTextColor(data.textColor);
        v.setTextSize(data.textSize);
        v.setGravity(Gravity.CENTER);
        v.setTypeface(data.textTypeface);
    }

    private Drawable copy (Drawable d){
        return  d.getConstantState().newDrawable();
    }

    private Drawable mBG4 = null;
    private boolean change=false,btn2select=false;

    /**
     * Switch Gradient after accept text.
     */
    public void setButton2Background (Drawable bg,boolean enabled,boolean Selected){
        mBG4 = bg;
        this.btn2select = Selected;
        change = enabled;
    }

    private void switchToNextButton(boolean firstBtn){
        if (firstBtn){
            state = STATE_BUTTON;
        }else{
            state = STATE_BUTTON2;
        }

    }


    private  boolean keyv = false,keyh = false;

    public void showTexts(long hideDuration,long sleepDuration,final int startIndex){
        if (state == STATE_BUTTON){
            setTextVisible(button,hideDuration,false);
            SleepTimer sleepTimer = new SleepTimer(hideDuration+sleepDuration, new SleepTimer.SleepTimerListener() {
                @Override
                public void onEnd() {
                    showTexts(startIndex,true);
                }
            });
            sleepTimer.setEnabled(true);
        }
    }

    public void showTexts(long hideDuration,long sleepDuration,final int startIndex,final  int endIndex){
        if (state == STATE_BUTTON){
            setTextVisible(button,hideDuration,false);
            SleepTimer sleepTimer = new SleepTimer(hideDuration+sleepDuration, new SleepTimer.SleepTimerListener() {
                @Override
                public void onEnd() {
                    showRangeTexts(endIndex,startIndex,true);
                }
            });
            sleepTimer.setEnabled(true);
        }
    }

    public boolean canShowTexts(){
        if (state == STATE_BUTTON) return  true;
        return  false;
    }

    public void setKeyboardManager(boolean Show,boolean Hide){
        keyv=Show;
        keyh=Hide;
    }

    private void setKeyboardV (boolean show){
        if (show) {mEditText.bringToFront();}else{
            buttonParent.bringToFront();
            label.bringToFront();
            button.bringToFront();
            if (change) {
                buttonParent2.bringToFront();
                button2.bringToFront();
            }
        }
        if (keyv && show){
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText,InputMethodManager.SHOW_IMPLICIT);
        }else if (keyh && show==false){
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
}
