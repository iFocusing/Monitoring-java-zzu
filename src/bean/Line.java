package bean;

/**
 * Created by Administrator on 2016/3/30.
 */
public class Line {
    private long lid;
    private String name;

    public Line(){
    }
    public Line(String name, long lid) {
        this.name = name;
        this.lid = lid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLid() {
        return lid;
    }

    public void setLid(long lid) {
        this.lid = lid;
    }
}
