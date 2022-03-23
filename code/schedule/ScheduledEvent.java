package code.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This represents a blocked out portion of the user's schedule.
 * 
 * Doctor's appointments, scheduled meetings, etc. should all 
 * BE A ScheduledEvent (subclass)
 * 
 * Includes a List of optional ScheduledReminders to remind
 * the user about the event/appointment/etc.
 * 
 * Start time
 * End time/duration
 * Desc
 * Location?
 * 
 * Implementation assigned to Jayden
 */
public class ScheduledEvent {
   private String description;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private int duration;

   public ScheduledEvent(String desc, String sDate, String sTime, String eDate, String eTime) {
       this.description = desc;
       startTime = LocalDateTime.parse(sDate + "T" + sTime);
       endTime = LocalDateTime.parse(eDate + "T" + eTime);
   }

   public ScheduledEvent(String desc, String sDate, String sTime, int duration) {
       Long lDur = Long.valueOf(duration);   
       this.description = desc; 
       startTime = LocalDateTime.parse(sDate + "T" + sTime);
       endTime = startTime.plusHours(lDur);
    }

   public String getDescription() {
       return this.description;
   }

   public void setDescription(String desc) {
       this.description = desc;
   }

   public LocalDateTime getStartTime() {
       return startTime;
   }

   public void setStartTime(String date, String time) {
       startTime = LocalDateTime.parse(date + "T" + time);
   }

   public LocalDateTime getEndTime() {
       return endTime;
   }

   public void setEndTime(String date, String time) {
       endTime = LocalDateTime.parse(date + "T" + time);
   }

   public int getDuration() {
       return duration;
   }

   public void setDuration(int i) {
       this.duration = i;
   }
}
