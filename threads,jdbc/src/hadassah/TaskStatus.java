package hadassah;

public class TaskStatus {
    private boolean isImage = false;
    private double time = 0;
    long  id=-1;
    private String url;

    public TaskStatus(String url) {//constructor
        this.url = url;
    }

    public String getUrl() {//get url
        return url;
    }

    public void setUrl(String url) {//set url
        this.url = url;
    }

    public boolean isImage() {//check if image
        return isImage;
    }

    public void setImage(boolean image) {//set image
        isImage = image;
    }

    public double getTime() {//get time
        return time;
    }
    void  setid(long id)//set id
    {
        this.id=id;
    }

    long getid()//get id
    {
    return id;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
