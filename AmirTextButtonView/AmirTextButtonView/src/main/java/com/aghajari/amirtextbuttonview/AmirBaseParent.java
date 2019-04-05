package com.aghajari.amirtextbuttonview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;

class AmirBaseParent extends ViewGroup{
  public static float scale = 0.0F;
  private static float deviceScale = 0.0F;
  public static final int LEFT = 0;
  public static final int RIGHT = 1; 
  public static final int BOTH = 2; 
  public static final int TOP = 0; 
  public static final int BOTTOM = 1; 
  public static boolean disableAccessibility = false;

  private float density = 1;

  public AmirBaseParent(Context context) {
    super(context);
    density = context.getResources().getDisplayMetrics().density;
  }
  public AmirBaseParent(Context context, AttributeSet attrs, int defStyle) {
    super(context,attrs,defStyle);
    density = context.getResources().getDisplayMetrics().density;
  }
  public AmirBaseParent(Context context, AttributeSet attrs){
    super(context,attrs);
    density = context.getResources().getDisplayMetrics().density;
  }

  public int dp(float value) {
    if (value == 0) {
      return 0;
    }
    //return (int) Math.ceil(density * value);
    return  (int) (density * value);
  }

  public static void setDeviceScale(float scale) { 
	  deviceScale = scale; 
	}
  
  public static void setUserScale(float userScale) {
    if (Float.compare(deviceScale, userScale) == 0) {
      scale = 1.0F;
    } else
      scale = deviceScale / userScale;
  }
  
  public static float getDeviceScale() { return deviceScale; }
  
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int count = getChildCount();
    for (int i = 0; i < count; i++) {
      View child = getChildAt(i);
      if (child.getVisibility() != 8) {
        if ((child.getLayoutParams() instanceof LayoutParams)) {
          LayoutParams lp = 
            (LayoutParams)child.getLayoutParams();
          child.layout(lp.left, lp.top, 
            lp.left + child.getMeasuredWidth(), 
            lp.top + child.getMeasuredHeight());
        }
        else {
          child.layout(0, 0, child.getLayoutParams().width, child.getLayoutParams().height);
        }
      }
    }
  }
  

  public void addChildrenForAccessibility(ArrayList<View> c) {
    if (disableAccessibility)
      return;
    super.addChildrenForAccessibility(c);
  }
  
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measureChildren(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(resolveSize(this.getLayoutParams().width, widthMeasureSpec), 
      resolveSize(this.getLayoutParams().height, heightMeasureSpec));
  }
  
  public static class LayoutParams extends ViewGroup.LayoutParams {
    public int left;
    public int top;
    
    public LayoutParams(int left, int top, int width, int height) {
    super(width,height);
    this.width = width;
    this.height = height;
      this.left = left;
      this.top = top;
    }
    
    
    public LayoutParams() {
    	super(0,0); 
    }
    
    public void setFromUserPlane(int left, int top, int width, int height) {
      this.left = Math.round(left * AmirBaseParent.scale);
      this.top = Math.round(top * AmirBaseParent.scale);
      this.width = (width > 0 ? Math.round(width * AmirBaseParent.scale) : width);
      this.height = (height > 0 ? Math.round(height * AmirBaseParent.scale) : height);
    }
  }


  public int GetWidth() {
    return this.getLayoutParams().width;
  }

  public int GetHeight() {
    return this.getLayoutParams().height;
  }

  public int GetLeft(){
    AmirBaseParent.LayoutParams lp = (AmirBaseParent.LayoutParams)this.getLayoutParams();
    return lp.left;
  }

  public int GetTop(){
    AmirBaseParent.LayoutParams lp = (AmirBaseParent.LayoutParams)this.getLayoutParams();
    return lp.top;
  }

  public void SetWidth(int width){
    ViewGroup.LayoutParams lp = this.getLayoutParams();
    lp.width = width;
    requestLayout(this);
  }

  public void SetHeight(int height) {
    ViewGroup.LayoutParams lp = this.getLayoutParams();
    lp.height = height;
    requestLayout(this);
  }


  public void SetLeft(int left) {
    AmirBaseParent.LayoutParams lp = (AmirBaseParent.LayoutParams)this.getLayoutParams();
    lp.left = left;
    requestLayout(this);
  }

  public void SetTop(final int top) {
    AmirBaseParent.LayoutParams lp = (AmirBaseParent.LayoutParams)this.getLayoutParams();
    lp.top = top;
    requestLayout(this);
  }

  private void requestLayout(View v) {
    ViewParent parent = v.getParent();
    if (parent != null) {
      parent.requestLayout();
    }
  }

  public void setBaseElevation (float e) {
    if (Build.VERSION.SDK_INT >= 21) {
      this.setElevation(e);
    }
  }

  public void enableClipToOutline(){
    if (Build.VERSION.SDK_INT >= 21) {
      this.setClipToOutline(true);
    }
  }

  public static void setClickEffect(View View, boolean Borderless) {
    int[] attrs;
    if (Borderless) {
      attrs = new int[] { android.R.attr.selectableItemBackgroundBorderless };
    } else {
      attrs = new int[] { android.R.attr.selectableItemBackground };
    }
    TypedArray typedArray = View.getContext().obtainStyledAttributes(attrs);
    int backgroundResource = typedArray.getResourceId(0, 0);
    View.setBackgroundResource(backgroundResource);
  }

  public void removeView() {
    if (getParent() instanceof ViewGroup) {
      ViewGroup vg = (ViewGroup)getParent();
      vg.removeView(this);
    }
  }

}