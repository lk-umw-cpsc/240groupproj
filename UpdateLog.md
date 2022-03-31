Instructions: When updating, please add date and a short description. 
You do not need to add a log for every update. 
Just add a log for the end of day or large update.
Copy and paste the format below and make it the top most entry when adding updates.

Example
********************************************
Date: 3/15/2022
Person: Joon
Log: Added project folders
Updated ReadMe
Added and updated UpdateLog

********************************************
Format
********************************************
Date: 
Person: 
Log: 
********************************************

Logs:
Add below this line!
********************************************

Paste over new entry here.

Date: 3/30/2022
Person: Lauren
Log: Further progress on calender view

Date: 3/28/2022
Person: Joon
Log: Added DIADesigns folder

Date: 3/27/2022
Person: Lauren
Log: Started MonthViewFrame
Edit button in reminder manager frame completed

Date: 3/26/2022
Person: Lauren
Log: Finishing the day off with...
Fixed scrolling bug
Tweaked borders to make them prettier (softer colors, borders no longer "double up" between entries)
Implemented "Remind me in one hour" and "Remind me tomorrow"

Date: 3/26/2022
Person: Joon
Log: Updated System tray add reminders to include
Add add next hour, this evening, tomorrow

Date: 3/26/2022
Person: Lauren
Log: Reminder manager window skin changed (fonts, colors)
There is a persistent bug where the last reminder in the list
is cut off by the scroll panel. I need to figure out how to fix that.

Date: 3/26/2022
Person: Lauren
Log: Reverted BackgroundDaemon to old version.
Tweaked ScheduleIO so that the exceptions are thrown within its methods rather
than making BackgroundDaemon catch them.
Added try/catch-with-resources within ScheduleIO's methods.
Added static File objects for the two files the project works with.
Added check to see if input files exist before attempting to read from them in
ScheduleIO.load methods.
Reverted name of loadSchedule and saveSchedule to line up with method called
from BackgroundDaemon.
Saving and loading tested and confirmed working.
Data folder added to .gitignore so we aren't downloading each other's data files.
Moved calls to MedicalIO methods to ScheduleIO. I would like all app data loading 
and saving to be performed from within loadSchedule and saveSchedule. We can rename 
ScheduleIO and load/saveSchedule to something else if desired.

Date: 3/26/2022
Person: Joon
Log: Update ScheduleIO to update days, added fully functional system that saves 
and loads from txt files. Prints to list for use. Confirmed working WindowsOS.
Updated MedicalIO to print doctors prescriptions on request, build doctor prescriptions
based of doctors name in prescriptions list. Added print full prescription list.

Date: 3/22/2022 (evening)
Person: Lauren
Log: Began work on ReminderManagerFrame. I would like to make this frame look
nicer... it's very 90's-chic right now :)

Date: 3/22/2022
Person: Lauren
Log: AddReminderFrame completed. Next focus will be on a reminder manager frame.

Date: 3/21/2022
Person: Lauren
Log: Daemon now spawns a reminder 1 minute in the future upon running

Date: 3/21/2022
Person: Joon
Log: Updated Medical class added create doctor/prescript and get dr prescript(needs work)
Updated Update log to include updates

Date: 3/20/2022
Person: Jayden
Log: Created skeleton of the ScheduledReminder class, added Set/Get methods, and isDue method.

Date: 3/20/2022
Person: Jayden
Log: updated requirements, comment tweak, moved folders, created remind GUI, added add reminder
to SysTray

Date: 3/20/2022
Person: Joon
Log: Added Medical folder/System tray folder and moved files, created medical class,
prescription class, doctors class, added data folder with doctors/prescription.txt
Doctor class/prescription are constructors and have get/set methods
txt files have "dummy" data.

Date: 3/19/2022
Person: Lauren
Log: Added info for Project Idea's and updated file location for system tray sprite sheet

Date: 3/19/2022
Person: Joon
Log: Added System tray from other project here, added a sprite sheet and code that pulls
sprite sheet according to number of day. 

Date: 3/17/2022
Person: Joon
Log: Updated locations of project code and sprite window to improve project location.

Date: 3/16/2022
Person: Lauren
Log: Created Animation window with sprite

Date: 3/15/2022
Person: Joon
Log: Added project folders
Updated ReadMe
Added and updated UpdateLog

Date: 3/20/2022
Person: Jayden
Log: Created skeleton of the ScheduledReminder class, added Set/Get methods, and isDue method.

Date: 3/21/2022
Person: Jayden
Log: Added functiond to ScheduledReminder class, rpeetition of reminders. Fixed isDue, need to add time comparison.

Date: 3/22/2022
Person: Jayden
Log: Changed sepperate variables into combined LocalDateTime, added constructors for ScheduledReminder class, tested constructors.

Date: 3/23/2023
Person: Jayden
Log: Created Skeleton of ScheduledEvent class, has 2 constructors, one taking start and end dates/times, another taking a start date, and a duration int

Date: 3/23/2023
Person: Jayden
Log: Changed constructors of Scheduled Event class, using only duration variable, also made it so duration is handeled in minutes, conversion to take place outside of ScheduledReminder Class