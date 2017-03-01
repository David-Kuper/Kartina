package kartina.model;
/**
 * 搜索课程结果
 */

/**
 * Created by David on 2016/11/7.
 */

public class ClassItem extends SuperModel {
    public String classId; //课程号
    public String classTeacher; //任课老师
    public String classTime;  //上课时间
    public String classPlace;  //上课地点
    public String className;  //课程名字
    public int likeNum;
    public boolean isActioned;
}
