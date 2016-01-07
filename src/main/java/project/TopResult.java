package project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valer on 06.01.2016.
 */
public class TopResult {
    private String vkIdUer;
    private List<TopPosition> result;

    public TopResult(String vkIdUer, List<TopPosition> result) {
        this.vkIdUer = vkIdUer;
        this.result = new ArrayList<TopPosition>();
        this.result.addAll(result);
    }

    public List<TopPosition> getResult() {
        return result;
    }
}