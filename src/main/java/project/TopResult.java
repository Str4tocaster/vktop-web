package project;

/**
 * Created by valer on 06.01.2016.
 */
public class TopResult {
    private String vkIdUer;
    private String result;

    public TopResult(String vkIdUer, String result) {
        this.vkIdUer = vkIdUer;
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}