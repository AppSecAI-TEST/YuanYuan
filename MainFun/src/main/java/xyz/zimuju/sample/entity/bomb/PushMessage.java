package xyz.zimuju.sample.entity.bomb;

/*
 * @description PushMessage
 * @author Nathaniel
 * @time 2017/8/3 - 10:00
 * @version 1.0.0
 */
public class PushMessage {
    private String title;
    private String content;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
