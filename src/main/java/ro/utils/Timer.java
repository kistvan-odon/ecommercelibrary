package ro.utils;

import sun.util.calendar.CalendarSystem;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Istvan on 29.03.2015.
 */
public class Timer {

    private Calendar startTime;
    private Calendar endTime;

    public Timer(int seconds) {
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, seconds);
    }

    public Timer(int time, TimeUnit timeUnit) {
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        int milliseconds = (int)TimeUnit.MILLISECONDS.convert(time, timeUnit);
        endTime.add(Calendar.MILLISECOND, milliseconds);
    }

    public boolean stillCounting(){
        return Calendar.getInstance().compareTo(endTime) < 0;
    }

    public boolean isDone(){
        return Calendar.getInstance().compareTo(endTime) >= 0;
    }

    public Integer getRemainingTime(TimeUnit timeUnit){
        int currentMillis = Calendar.getInstance().get(Calendar.MILLISECOND);
        int endMillis = endTime.get(Calendar.MILLISECOND);
        return (int) timeUnit.convert(endMillis - currentMillis, TimeUnit.MILLISECONDS);
    }
    
    public void abort(){
    	endTime = Calendar.getInstance();
    }
}
