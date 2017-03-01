package kartina.model.UIModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kartina.service.model.AbstractResponse;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.model.model.CourseModel;
import kartina.model.model.HomeModel;
import kartina.model.model.TopicModel;

/**
 * Created by David on 2016/11/21.
 */

public class DiscoverDo {
    CourseModel courseModel;
    TopicModel topicModel;
    Gson gson;

    public DiscoverDo() {
        gson = new Gson();
    }

    public void setTopicModel(TopicModel topicModel) {
        this.topicModel = topicModel;
    }

    public TopicModel getTopicModel() {
        return topicModel;
    }

    public void setCourseModel(CourseModel courseModel) {
        this.courseModel = courseModel;
    }

    public CourseModel getCourseModel() {
        return courseModel;
    }

    public <T extends AbstractResponse> void loadCourseList(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_discover_class, gson.toJson(requestBody), callBack, new TypeToken<AbstractResponse<CourseModel>>() {
        }.getType());
    }

    public <T extends AbstractResponse> void loadTopicList(JsonElement requestBody, SuperService.CallBack<T> callBack) {
        RequestUtil.getInstance().postMethod(Api.api_get_discover_topic, gson.toJson(requestBody), callBack, new TypeToken<AbstractResponse<TopicModel>>(){}.getType());
    }

}
