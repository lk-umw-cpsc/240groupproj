package code.ui.systemtray;

public class SysTrayInfoBuilder 
{
    
    public static String buildInfo()
    {
        //calculates events available and posts on notification.
        int events = 0;
        String noteCounter = "";

        if (events == 0)
        {
            noteCounter =  "(" + events + ") event today!";
        }
        else
        {
            noteCounter =  "(" + events + ") events today!";
        }

        String info = "Cal the Assistant\n" + noteCounter;

        return info;
    }
    
}
