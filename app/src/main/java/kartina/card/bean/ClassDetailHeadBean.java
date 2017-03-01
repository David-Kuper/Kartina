package kartina.card.bean;

import java.util.List;

import kartina.model.ClassItem;
/**
 * 课程详情页头部 对应CardViewType1009
 */

/**
 * Created by David on 2016/11/7.
 */

public class ClassDetailHeadBean {
    public String itemId;
    public String className; // 课程名
    public String classCredit; // 课程学分
    public String classHour;   //课程学时
    public String classNum;    //课程号
    public String describe;    //课程描述
    public boolean isActioned;
    public List<ClassItem>  classItemList;
}
