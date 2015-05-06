package demo.jyl.com.mydemo.model;

import java.io.Serializable;

/**
 * ÆÀÂÛ¶ÔÏóModel
 * @author Jack Boy
 */
public class Comment implements Serializable {

    private String username;
    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + username + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
