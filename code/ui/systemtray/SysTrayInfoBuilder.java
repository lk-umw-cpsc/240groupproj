package code.ui.systemtray;

/**
 * class & method comments to be implemented by Joon
 */
public class SysTrayInfoBuilder 
{
    
    public static String buildInfo()
    {
        //calculates events available and posts on notification.
        int events = 0;
        String noteCounter = "";

        if (events == 1)
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
