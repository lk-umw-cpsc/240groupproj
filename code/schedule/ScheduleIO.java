package code.schedule;

import java.util.List;
import java.util.Scanner;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// import code.medical.MedicalIO;

/**
 * All saving and loading of app data should be performed within this class's methods.
 * 
 * loadSchedule is called by BackgroundDaemon upon application launch. All loading should be
 * done within loadSchedule.
 * 
 * saveSchedule is called upon program exit, upon a reminder expiring, or upon the user adding
 * or removing an event/reminder. All saving of program data should be performed within
 * saveSchedule.
 */
public class ScheduleIO 
{
        private static final File EVENTS_FILE = new File("Data/Events.txt");
        private static final File REMINDERS_FILE = new File("Data/Reminders.txt");

        public static void loadSchedule(List<ScheduledReminder> reminders, List<ScheduledEvent> events)
        {
                loadEvents(events);
                loadReminders(reminders);

                // Moved this here to match program design. Saving of Medical info should be done in
                // saveSchedule once implemented.
                //MedicalIO.buildMedical();
        }

        public static void saveSchedule(List<ScheduledReminder> reminders, List<ScheduledEvent> events)
        {
                saveEvents(events);
                saveReminders(reminders);

        }

        
        public static void loadEvents(List<ScheduledEvent> events)
        {
                if (!EVENTS_FILE.exists())
                {
                        return;
                }

                try (Scanner fileIn = new Scanner(EVENTS_FILE)) {

                        //this deletes the first line which contains Description###StartDateTStartTime###Duration
                        String newLine = fileIn.nextLine();
                        while (fileIn.hasNextLine())
                        {
                                newLine = fileIn.nextLine();
                                String[] parser = newLine.split("###");
                                if (parser[4].equals("NONE"))
                                        parser[4] = "";
                                ScheduledEvent temp = new ScheduledEvent(parser[0], parser[1], parser[2], parser[3], parser[4]);
                                events.add(temp);
                        }

                } catch (IOException e) {
                        e.printStackTrace();
                }


        }

        public static void loadReminders(List<ScheduledReminder> reminders)
        {
                if (!REMINDERS_FILE.exists()) {
                        return;
                }

                try (Scanner fileIn = new Scanner(REMINDERS_FILE)) {

                        //this deletes the first line which contains Name###Description###Date###Reps
                        String newLine = fileIn.nextLine();
                        while (fileIn.hasNextLine())
                        {
                                newLine = fileIn.nextLine();
                                String[] parser = newLine.split("###");
                                String[] splitTimeParser = parser[2].split("T");
                                ScheduledReminder temp = new ScheduledReminder(parser[0], parser[1], splitTimeParser[0], splitTimeParser[1], Integer.parseInt(parser[3]));
                                reminders.add(temp);
                        }

                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

        
        public static void saveEvents(List<ScheduledEvent> events)
        {
                
                try (PrintWriter fileOut = new PrintWriter(EVENTS_FILE)) {

                        fileOut.println("Name###Date###StartTime###EndTime###location");
                        for (ScheduledEvent event : events)
                        {
                                String location = event.getLocation();
                                if (location.isEmpty())
                                        location = "NONE";
                                fileOut.println(event.getName() + "###" + event.getDate()
                                        + "###" + event.getStartTime() + "###" + event.getEndTime() 
                                        + "###" + location);
                        }

                        fileOut.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

        public static void saveReminders(List<ScheduledReminder> reminders)
        {

                try (PrintWriter fileOut = new PrintWriter(REMINDERS_FILE))
                {

                        fileOut.println("Name###Description###Date###Reps");
                        for (ScheduledReminder reminder : reminders)
                        {
                                fileOut.println(reminder.getName() + "###" + reminder.getDescription() + "###" + reminder.getWhenDue() + "###" + reminder.getDaysBetweenRepetitions());
                        }

                        fileOut.flush();
                } catch(IOException e) {
                        e.printStackTrace();
                }

        }

        
    
}
