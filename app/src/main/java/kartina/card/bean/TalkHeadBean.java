package kartina.card.bean;

import java.util.List;

/**
 * 话题子项 CardViewType1007
 */

/**
 * Created by David on 2016/11/8.
 */

public class TalkHeadBean {
    public String itemId;
    public String creator;     //话题创立人
    public String creatoAvatar;   // 头像Url
    public String createTime;   //话题创立时间
    public String content;      //话题内容
    public String title;        //话题标题
    public boolean isActioned;
    public List<Tag> tagList;    //话题标签
}
