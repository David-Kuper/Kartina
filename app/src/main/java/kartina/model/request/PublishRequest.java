package kartina.model.request;

import com.kartina.service.model.AbstractRequest;

import java.io.Serializable;

/**
 * Created by David on 2016/11/22.
 */

public class PublishRequest extends AbstractRequest implements Serializable {
    private int publishType;
    /**
     * 帖子对应的课程、话题ID。
     */
    private String scopeId;

    private String userName;
    private String title;
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishType(int publishType) {
        this.publishType = publishType;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishType() {
        return publishType;
    }

    public String getContent() {
        return content;
    }

    public String getScopeId() {
        return scopeId;
    }

    public String getUserName() {
        return userName;
    }
}
