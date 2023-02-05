public class SampleData {
    private int time;
    private int imageview;
    private String pillname;

    public SampleData(int time, String imageview, String pillname){
        this.time = time;
        this.pillname = pillname;

    }

    public int getTime()
    {
        return this.time;
    }
    public int getImageview()
    {
        return this.imageview;
    }

    public String getPillname()
    {
        return this.pillname;
    }

}