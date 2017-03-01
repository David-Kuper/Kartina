package kartina.model.model;

/**
 * Created by David on 2016/9/16.
 */

public enum Api {
    /**
     * 添加字段注释.
     */
    api_get_classdetail("getCourseDetail", "1.0", "获取课程详情"),
    /**
     * 添加字段注释.
     */
    api_get_talkdetail("getTopicDetail", "1.0", "获取话题详情"),
    /**
     * 添加字段注释.
     */
    api_get_notedetail("getPostDetail", "1.0", "获取帖子详情"),
    /**
     * 添加字段注释.
     */
    api_get_teacherdetail("getTeacherDetail", "1.0", "获取老师详情"),
    /**
     * 添加字段注释.
     */
    api_get_mornote("get_list_num", "1.0", "更多帖子"),
    /**
     * 添加字段注释.
     */
    api_get_mortalk("get_list_num", "1.0", "更多话题"),
    /**
     * 添加字段注释.
     */
    api_get_mortcomment("get_list_num", "1.0", "更多评论"),
    /**
     * 添加字段注释.
     */
    api_publish_note("get_list_num", "1.0", "发布帖子"),
    /**
     * 添加字段注释.
     */
    api_publish_talk("get_list_num", "1.0", "发布话题"),
    /**
     * 添加字段注释.
     */
    api_searchby_keyword("get_list_num", "1.0", "关键字搜索"),
    /**
     * 添加字段注释.
     */
    api_follow("get_list_num", "1.0", "关注"),
    /**
     * 添加字段注释.
     */
    api_like("get_list_num", "1.0", "点赞"),
    /**
     * 添加字段注释.
     */
    api_comment("addComment", "1.0", "评论"),
    /**
     * 添加字段注释.
     */
    api_get_myfollow("get_list_num", "1.0", "获取我的关注"),
    /**
     * 添加字段注释.
     */
    api_get_mylike("get_list_num", "1.0", "获取我的点赞"),
    /**
     * 添加字段注释.
     */
    api_get_mycomment("get_list_num", "1.0", "获取我的评论"),
    /**
     * 添加字段注释.
     */
    api_get_mynote("get_list_num", "1.0", "获取我的帖子"),
    /**
     * 添加字段注释.
     */
    api_get_mytopic("get_list_num", "1.0", "获取我的话题"),
    /**
     * 添加字段注释.
     */
    api_get_hottag("get_list_num", "1.0", "获取热门搜索标签"),
    /**
     * 添加字段注释.
     */
    api_get_homemore("get_list_num", "1.0", "首页获取更多"),
    /**
     * 添加字段注释.
     */
    api_update_userinfo("updateStudentInfo", "1.0", "修改个人信息"),
    /**
     * 添加字段注释.
     */
    api_subscribe_dynamic("", "1.0", "订阅动态"),
    /**
     * 添加字段注释.
     */
    api_get_home("getHome", "1.0", "获取首页信息"),
    /**
     * 添加字段注释.
     */
    api_get_discover_class("getDicoverCourse", "1.0", "获取发现课程信息"),
    /**
     * 添加字段注释.
     */
    api_register("register", "1.0", "注册"),
    /**
     * 添加字段注释.
     */
    api_getuserinfo("get_list_num", "1.0", "获取个人信息"),
    /**
     * 添加字段注释.
     */
    api_get_discover_topic("getDiscoverTopic", "1.0", "获取发现讨论信息列表"),
    /**
     * 添加字段注释.
     */
    api_login("login", "1.0", "登录");

    /**
     * 添加字段注释.
     */
    public String apiName;

    /**
     * 添加字段注释.
     */
    public String version;

    /**
     * 添加字段注释.
     */
    public String describe;

    /**
     * 构造函数.
     * 
     * @param apiName
     *            名字
     * @param version
     *            版本
     * @param describe
     *            描述
     */
    Api(String apiName, String version, String describe) {
        this.version = version;
        this.apiName = apiName;
        this.describe = describe;
    }
}
