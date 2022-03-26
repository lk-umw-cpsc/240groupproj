package code.schedule;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ScheduleIO 
{
        private static final File EVENTS_FILE = new File("Data/Events.txt");
        private static final File REMINDERS_FILE = new File("Data/Reminders.txt");

        public static void loadSchedule(List<ScheduledReminder> reminders, List<ScheduledEvent> events)
        {
                loadEvents(events);
                loadReminders(reminders);
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
                                String[] splitTimeParser = parser[1].split("T");
                                ScheduledEvent temp = new ScheduledEvent(parser[0], splitTimeParser[0], splitTimeParser[1], Integer.parseInt(parser[2]));
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

                        fileOut.println("Description###StartDateTStartTime###Duration");
                        for (ScheduledEvent event : events)
                        {
                                fileOut.println(event.getDescription() + "###" + event.getStartTime() + "###" + event.getDuration());
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
