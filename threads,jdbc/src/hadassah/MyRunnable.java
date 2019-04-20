package hadassah;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyRunnable implements Runnable
{
    private double sleepTime;
    private double num_ret;
    private TaskStatus status;
    URL myURL;
    URLConnection myURLConnection;
    public  String mimeType;
    private long startTime;
    private long endTime;
    static int  i =0;



    MyRunnable(TaskStatus str,double sleepTime,double num_ret)
    {
        status=str;
        this.sleepTime=sleepTime;
        this.num_ret= num_ret;

    }


    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        setstartTime(System.currentTimeMillis());

           if (!isValid(status.getUrl())) {//check url if valid

            System.out.println("the Url is not valid1");
            System.exit(0);
        }


        boolean success = false;
        int count = 0;
             while (!success && count++ < num_ret) {
                 try{
                 myURL = new URL(status.getUrl());
                 myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                this.mimeType = myURLConnection.getContentType();//check if content image

                 success = true;
                pause(sleepTime);//thread sleep
                 }catch (MalformedURLException e){
                    status.setTime(-1);
                    return;
                 }catch (Exception e){

                 }
        }

        if(!success){
            System.out.println("cannot reach the page");
            //throw new Exception()
            return;

        }


        if(mimeType.toLowerCase().startsWith("image"))//make image with small letters
        {
            System.out.println("contain image");
            status.setImage(true);
        }


        setendTime(System.currentTimeMillis()-startTime);
        status.setTime(getendtime());

    }




    public static boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
    private void pause(double seconds)
    {
        try{
            Thread.sleep(Math.round(100.0*seconds));}
        catch(InterruptedException ie){}
    }
    public void setendTime(long endTime) {
        this.endTime = endTime;
    }
    public void setstartTime(long startTime) {
        this.startTime = startTime;
    }
    public double getendtime() {
        return endTime;
    }


}
