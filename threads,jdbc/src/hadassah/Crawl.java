package hadassah;
import java.lang.Thread;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.sql.Timestamp;

import java.net.ConnectException;


public class Crawl {
    public static void main(String[] args) throws IOException {
        String Doc_name;
        String Parm[] = new String[4];
        // todo :check args size
        int i;
        for (i = 0; i < args.length; i++) {
            Parm[i] = args[i];

        }
       // Doc_name = "urls.txt";
        double thread_num = Double.parseDouble(Parm[0]);
        double delay_ret = Double.parseDouble(Parm[1]);
        double num_ret = Double.parseDouble(Parm[2]);
        Doc_name=args[3];

        File file = new File(Doc_name);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<TaskStatus> urls = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            urls.add(new TaskStatus(line));
        }


        String url;
        int thr_num = (int) (thread_num);

        ExecutorService executor = Executors.newFixedThreadPool(thr_num);
        for(TaskStatus task : urls) {
                Runnable r1 = new MyRunnable(task, delay_ret, num_ret);
                Thread t1 = new Thread(r1);
            long threadId = Thread.currentThread().getId();
            executor.execute(r1);
            task.setid(threadId);


        }

            executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }catch (InterruptedException e){

        }



        for(TaskStatus status : urls){
            if(status.getTime() > 0) {
                System.out.println(status.getUrl() + ": " + status.getTime()+"ms");
            }
            if(status.getTime() == 0){
                System.out.println(status.getUrl() + ": timeout");
            }
            if(status.getTime() == -1){
                System.out.println(status.getUrl() + ": failed");
            }
            if(status.isImage())
            {
                try {
                    Class.forName("com.mysql.jdbc.Driver");//connect to driver and make connection with server
                    Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?user=root&password=");
                    String sql = "INSERT INTO `images` (id,date_added,url) VALUES(?,?,?)";
                    PreparedStatement stmt= mycon.prepareStatement(sql);
                    // pstmt.clearParameters();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                    stmt.setLong(1,status.getid());// set id in the db
                    stmt.setTimestamp(2,timestamp);//set time
                    stmt.setString(3,status.getUrl());//set url that contain images
                    stmt.executeUpdate();
                    stmt.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }


            }
        }


    }



