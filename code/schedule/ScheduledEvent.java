package code.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Period;
import java.time.Duration;

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
   private LocalDateTime start;
   private LocalTime end;
   private String location;
    
   public ScheduledEvent(String desc, String sDate, String sTime, String eDate, String eTime, String location) {   
       this.description = desc; 
       start = LocalDateTime.parse(sDate + "T" + sTime);
       end = start.plusMinutes(lDur);
       this.location = location;
    }

    public ScheduledEvent(String desc, LocalDateTime start, LocalTime end, String location) {
        this.start = start;
        this.end = end;
        this.description = desc;
        this.location = location;
    }

   public String getDescription() {
       return this.description;
   }

   public void setDescription(String desc) {
       this.description = desc;
   }


   public void setStart(LocalDateTime dt) {
       start = dt;
   }

   public void setEnd(LocalTime dt) {
       end = dt;
   }

   public LocalTime getStartTime() {
       return start.toLocalTime();
   }

   public LocalDate getStartDate() {
       return start.toLocalDate();
   }

   public LocalDateTime getStartDateTime() {
       return start;
   }

   public LocalTime getEnd() {
       return end;
   }
   public String getLocation() {
       return this.location;
   }

   public void setLocation(String s) {
       this.location = s;
   }

   public String toBriefString() {
       int hour = start.getHour();
       String brief;
       if (hour >= 12) {
           if (hour > 12) {
               hour %= 12;
           }
           brief = hour + "p";
       } else {
           if (hour == 0) {
               hour = 12;
           }
           brief = hour + "a";
       }
       return brief + " " + description;
   }
}
