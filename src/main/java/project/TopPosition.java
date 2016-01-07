package project;

/**
 * Created by valer on 02.12.2015.
 */
public class TopPosition {
    private Integer id;
    private String title;
    private int count;

    public TopPosition(int id, String title, int count) {
        this.id = id;
        this.title = title;
        this.count = count;
    }

    public TopPosition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
