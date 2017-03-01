package kartina.model.UIModel;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kartina.service.model.AbstractResponse;

import kartina.card.DataManager.Api;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.SuperService;
import kartina.model.model.CourseDetailModel;
import kartina.model.model.PostDetailModel;
import kartina.model.model.TeacherDetailModel;
import kartina.model.model.TopicDetailModel;
import kartina.model.model.TopicModel;

/**
 * Created by David on 2016/11/21.
 */

public class DetailDo {
    CourseDetailModel courseDetailModel;
    PostDetailModel postDetailModel;
    Gson gson;
    public DetailDo(){
        gson = new Gson();
    }

    public void setPostDetailModel(PostDetailModel postDetailModel) {
        this.postDetailModel = postDetailModel;
    }

    public PostDetailModel getPostDetailModel() {
        return postDetailModel;
    }

    public void setCourseDetailModel(CourseDetailModel courseDetailModel) {
        this.courseDetailModel = courseDetailModel;
    }

    public CourseDetailModel getCourseDetailModel() {
        return courseDetailModel;
    }

    public <T extends AbstractResponse> void loadClassDetailInfo(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_classdetail,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<CourseDetailModel>>(){}.getType());
    }

    public <T extends AbstractResponse> void loadTopicDetailInfo(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_talkdetail,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<TopicDetailModel>>(){}.getType());
    }

    public <T extends AbstractResponse> void loadNoteDetailInfo(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_notedetail,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<PostDetailModel>>(){}.getType());
    }

    public <T extends AbstractResponse> void loadTeacherDetailInfo(JsonElement requestBody, SuperService.CallBack<T> callBack){
        RequestUtil.getInstance().postMethod(Api.api_get_teacherdetail,gson.toJson(requestBody),callBack,new TypeToken<AbstractResponse<TeacherDetailModel>>(){}.getType());
    }


}
