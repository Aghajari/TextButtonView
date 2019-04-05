package com.aghajari.amirtextbuttonview;
import android.os.Handler;

class SleepTimer {
  Handler handler;
  private long interval;
  private boolean enabled = false;
  private static int relevantTimer = 0;
  private  TickTack tt;
  SleepTimerListener l;
  interface SleepTimerListener{
    void onEnd();
  }

  public SleepTimer(long Interval,SleepTimerListener l) {
	  interval = Interval; 
	  handler = new Handler();
	  this.l=l;
  }
  
  public boolean getEnabled(){
    return enabled;
  }
  

  public void setInterval(long Interval){
    if (interval == Interval) return;
    interval = Interval;
    if (enabled) {
      stopTicking();
      startTicking();
    }
  }
  
  public long getInterval() { 
	  return interval; 
}
  
  private void startTicking() {
    tt = new TickTack(relevantTimer, this,l);
    handler.postDelayed(tt, interval);
  }
  
  public void setEnabled(boolean Enabled) {
    if (Enabled == enabled)
      return;
    if (Enabled) {
      startTicking();
    }else {
      stopTicking();
    }
    enabled = Enabled; 
    }
  
  
  static class TickTack implements Runnable { 
	 private SleepTimer timer;
    SleepTimerListener l;
    public TickTack(int currentTimer, SleepTimer timer,SleepTimerListener l) {
    this.currentTimer = currentTimer;
      this.timer = timer;
      this.l=l;
    }
    
    public void run() {
    	SleepTimer parentTimer = timer;
      if ((parentTimer == null) || (currentTimer != relevantTimer)) return;
      if (l!=null){
        l.onEnd();
      }
      timer.setEnabled(false);
    }
    
    private final int currentTimer;
  }
  
  private void stopTicking() {
    relevantTimer += 1;
    if (tt!=null) {
    	tt.timer=null;
    	tt=null;
    }
  }
}