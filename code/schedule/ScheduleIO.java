package code.schedule;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import code.BackgroundDaemon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ScheduleIO 
{

        public static void loadSchedules(List<ScheduledEvent> events, List<ScheduledReminder> reminders) throws FileNotFoundException
        {
                loadEvents(events);
                loadReminders(reminders);
        }

        public static void saveSchedules(List<ScheduledEvent> events, List<ScheduledReminder> reminders) throws IOException
        {
                saveEvents(events);
                saveReminders(reminders);

        }

        
        public static void loadEvents(List<ScheduledEvent> events) throws FileNotFoundException
        {

                FileInputStream fileInName = new FileInputStream("240GROUPPROJ/../data/Events.txt");
                Scanner fileIn = new Scanner(fileInName);

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

                fileIn.close();


        }

        public static void loadReminders(List<ScheduledReminder> reminders) throws FileNotFoundException
        {

                FileInputStream fileInName = new FileInputStream("240GROUPPROJ/../data/Reminders.txt");
                Scanner fileIn = new Scanner(fileInName);

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

                fileIn.close();

        }

        
        public static void saveEvents(List<ScheduledEvent> events) throws IOException
        {
                
                FileOutputStream fileOutName = new FileOutputStream("240GROUPPROJ/../data/Events.txt");
                PrintWriter fileOut = new PrintWriter(fileOutName);

                fileOut.println("Description###StartDateTStartTime###Duration");
                for (ScheduledEvent event : events)
                {
                        fileOut.println(event.getDescription() + "###" + event.getStartTime() + "###" + event.getDuration());
                }

                fileOut.flush();
                fileOutName.close();


        }

        public static void saveReminders(List<ScheduledReminder> reminders) throws IOException
        {

                FileOutputStream fileOutName = new FileOutputStream("240GROUPPROJ/../data/Reminders.txt");
                PrintWriter fileOut = new PrintWriter(fileOutName);

                fileOut.println("Name###Description###Date###Reps");
                for (ScheduledReminder reminder : reminders)
                {
                        fileOut.println(reminder.getName() + "###" + reminder.getDescription() + "###" + reminder.getWhenDue() + "###" + reminder.getDaysBetweenRepetitions());
                }

                fileOut.flush();
                fileOutName.close();

        }

        
    
}
