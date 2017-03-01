/*
 * 文件名：PostDetailModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： PostDetailModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;

/**
 * TODO 添加类的一句话简单描述.
 * <p>
 * TODO 详细描述
 * <p>
 * TODO 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author shiqinghuang
 */
public class PostDetailModel {

    public String itemId;

    public String avatarUrl;

    public String userName;

    public String time;

    public String title;

    public String describe;

    // 关注
    public boolean isFocused = false;

    // 点赞
    public boolean isLiked = false;

    /**
     * 添加字段注释.
     */
    private ArrayList<CardBean> cardList;

    /**
     * 设置itemId.
     * 
     * @return 返回itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 获取itemId.
     * 
     * @param itemId
     *            要设置的itemId
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * 设置avatarUrl.
     * 
     * @return 返回avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 获取avatarUrl.
     * 
     * @param avatarUrl
     *            要设置的avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * 设置userName.
     * 
     * @return 返回userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 获取userName.
     * 
     * @param userName
     *            要设置的userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 设置time.
     * 
     * @return 返回time
     */
    public String getTime() {
        return time;
    }

    /**
     * 获取time.
     * 
     * @param time
     *            要设置的time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 设置title.
     * 
     * @return 返回title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取title.
     * 
     * @param title
     *            要设置的title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置describe.
     * 
     * @return 返回describe
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 获取describe.
     * 
     * @param describe
     *            要设置的describe
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * 设置isFocused.
     * 
     * @return 返回isFocused
     */
    public boolean isFocused() {
        return isFocused;
    }

    /**
     * 获取isFocused.
     * 
     * @param isFocused
     *            要设置的isFocused
     */
    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    /**
     * 设置isLiked.
     * 
     * @return 返回isLiked
     */
    public boolean isLiked() {
        return isLiked;
    }

    /**
     * 获取isLiked.
     * 
     * @param isLiked
     *            要设置的isLiked
     */
    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    /**
     * 设置cardList.
     * 
     * @return 返回cardList
     */
    public ArrayList<CardBean> getCardList() {
        return cardList;
    }

    /**
     * 获取cardList.
     * 
     * @param cardList
     *            要设置的cardList
     */
    public void setCardList(ArrayList<CardBean> cardList) {
        this.cardList = cardList;
    }

}
