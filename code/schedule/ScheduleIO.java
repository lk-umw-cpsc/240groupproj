package code.schedule;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ScheduleIO 
{
        //List<ScheduledReminder> reminders,
        //List<ScheduledEvent> events
        public static List<ScheduledEvent> scheduledEvents = new ArrayList<ScheduledEvent>();  
        public static List<ScheduledReminder> scheduledReminders = new ArrayList<ScheduledReminder>();  

        
        public static void loadSchedules() throws FileNotFoundException
        {
                loadScheduledEvents();
                loadScheduledReminders();
        }

        public static void saveSchedules() throws IOException
        {
                saveScheduledEvents();
                saveScheduledReminders();

        }

        
        public static void loadScheduledEvents() throws FileNotFoundException
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
                        scheduledEvents.add(temp);
                }

                fileIn.close();


        }

        public static void loadScheduledReminders() throws FileNotFoundException
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
                        scheduledReminders.add(temp);
                }

                fileIn.close();

        }

        
        public static void saveScheduledEvents() throws IOException
        {
                
                FileOutputStream fileOutName = new FileOutputStream("240GROUPPROJ/../data/Events.txt");
                PrintWriter fileOut = new PrintWriter(fileOutName);

                fileOut.println("Description###StartDateTStartTime###Duration");
                for (ScheduledEvent scheduledEvent : scheduledEvents)
                {
                        fileOut.println(scheduledEvent.getDescription() + "###" + scheduledEvent.getStartTime() + "###" + scheduledEvent.getDuration());
                }

                fileOut.flush();
                fileOutName.close();


        }

        public static void saveScheduledReminders() throws IOException
        {

                FileOutputStream fileOutName = new FileOutputStream("240GROUPPROJ/../data/Reminders.txt");
                PrintWriter fileOut = new PrintWriter(fileOutName);

                fileOut.println("Name###Description###Date###Reps");
                for (ScheduledReminder ScheduledReminder : scheduledReminders)
                {
                        fileOut.println(ScheduledReminder.getName() + "###" + ScheduledReminder.getDescription() + "###" + ScheduledReminder.getWhenDue() + "###" + ScheduledReminder.getDaysBetweenRepetitions());
                }

                fileOut.flush();
                fileOutName.close();

        }
    
}
