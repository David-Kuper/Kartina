package kartina.card.bean;

import kartina.model.SuperModel;

/**
 * Created by David on 2016/11/6.
 */

public class Tag<T> extends SuperModel {
    private String tag;
    private String id;
    private T object;
    public Tag(String tag){
        this.tag = tag;
    }
    public Tag(String tag,String id){
        this.tag = tag;
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public T getObject() {
        return object;
    }
}
