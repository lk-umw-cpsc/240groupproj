package code.schedule.scheduleTools;

import java.net.MalformedURLException;
import java.net.URL;

public class Gift {
    private String name;
    private URL link;


    public Gift(String s) {
        this.name = s;
    }
    public Gift(String s, String l) throws MalformedURLException {
        this.name = s;
        try {
            link = new URL(l);
        } catch (MalformedURLException e) {
            System.out.println("Link not formatted correctly, please input a propper URl");
        }
    }
}
