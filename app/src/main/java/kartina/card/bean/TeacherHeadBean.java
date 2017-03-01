package kartina.card.bean;


import java.util.List;

import kartina.model.ClassItem;

/**
 * 老师详情头部 对应CardViewType1011
 */

/**
 * Created by David on 2016/11/9.
 */

public class TeacherHeadBean {
    public String itemId;
    public String avatar;
    public String name;
    public String academy;
    public String prosational;
    public String searchFiled;
    public String school;
    public boolean isActioned;
    public String describe;
    public List<ClassItem> teachList;
    public int commentNum;
}
