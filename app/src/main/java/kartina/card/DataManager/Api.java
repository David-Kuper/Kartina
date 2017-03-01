package kartina.card.DataManager;

/**
 * Created by David on 2016/9/16.
 */

public enum Api {
    /**
     * 获取课程详情.
     */
    api_get_classdetail("getCourseDetail", "1.0", "获取课程详情"),
    /**
     * 获取话题详情.
     */
    api_get_talkdetail("getTopicDetail", "1.0", "获取话题详情"),
    /**
     * 获取帖子详情.
     */
    api_get_notedetail("getPostDetail", "1.0", "获取帖子详情"),
    /**
     * 获取老师详情.
     */
    api_get_teacherdetail("getTeacherDetail", "1.0", "获取老师详情"),
    /**
     * 更多帖子.
     */
    api_get_mornote("get_list_num", "1.0", "更多帖子"),
    /**
     * 更多话题.
     */
    api_get_mortalk("get_list_num", "1.0", "更多话题"),
    /**
     * 更多评论.
     */
    api_get_mortcomment("get_list_num", "1.0", "更多评论"),
    /**
     * 发布帖子.
     */
    api_publish_note("publish", "1.0", "发布帖子"),
    /**
     * 发布话题.
     */
    api_publish_talk("publish", "1.0", "发布话题"),
    /**
     * 关键字搜索.
     */
    api_searchby_keyword("search", "1.0", "关键字搜索"),
    /**
     * 关注.
     */
    api_follow("addFollow", "1.0", "关注"),
    /**
     * 点赞.
     */
    api_like("addPraise", "1.0", "点赞"),
    /**
     * 评论.
     */
    api_comment("addComment", "1.0", "评论"),
    /**
     * 获取我的关注.
     */
    api_get_myfollow("getMyFollow", "1.0", "获取我的关注"),
    /**
     * 获取我的点赞.
     */
    api_get_mylike("getMyPraise", "1.0", "获取我的点赞"),
    /**
     * 获取我的评论.
     */
    api_get_mycomment("getMyComment", "1.0", "获取我的评论"),
    /**
     * 获取我的帖子.
     */
    api_get_mynote("getMyPost", "1.0", "获取我的帖子"),
    /**
     * 获取我的话题.
     */
    api_get_mytopic("getMyTopic", "1.0", "获取我的话题"),
    /**
     * 获取热门搜索标签.
     */
    api_get_hottag("getHotTag", "1.0", "获取热门搜索标签"),
    /**
     * 首页获取更多.
     */
    api_get_homemore("getHomeMore", "1.0", "首页获取更多"),
    /**
     * 修改个人信息.
     */
    api_update_userinfo("updateStudentInfo", "1.0", "修改个人信息"),
    /**
     * 订阅动态.
     */
    api_subscribe_dynamic("", "1.0", "订阅动态"),
    /**
     * 获取首页信息.
     */
    api_get_home("getHome", "1.0", "获取首页信息"),
    /**
     * .
     */
    api_get_discover_class("getDicoverCourse", "1.0", "获取发现课程信息"),
    /**
     * 注册.
     */
    api_register("register", "1.0", "注册"),
    /**
     * 获取个人信息.
     */
    api_getuserinfo("getUserInfo", "1.0", "获取个人信息"),
    /**
     * 获取发现讨论信息列表.
     */
    api_get_discover_topic("getDiscoverTopic", "1.0", "获取发现讨论信息列表"),
    /**
     * 登录.
     */
    api_login("login", "1.0", "登录"),
    /**
     * 注销登录
     */
    api_logout("logout", "1.0", "注销");

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