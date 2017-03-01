package kartina.model;

/**
 * Created by David on 2016/11/2.
 */

public class BannerBean extends SuperModel {
    private String coverUrl;
    private String title;
    private String describe;
    private int coverRid;

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setCoverRid(int coverRid) {
        this.coverRid = coverRid;
    }

    public int getCoverRid() {
        return coverRid;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public String getTitle() {
        return title;
    }
}
