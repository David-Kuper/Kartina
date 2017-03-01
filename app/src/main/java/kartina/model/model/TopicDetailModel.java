/*
 * 文件名：TopicDetailModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： TopicDetailModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;
import kartina.card.bean.TalkHeadBean;

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
public class TopicDetailModel {

    /**
     * 添加字段注释.
     */
    private TalkHeadBean talkHeadBean;

    /**
     * 添加字段注释.
     */
    private ArrayList<CardBean> cardList;

    /**
     * 设置talkHeadBean.
     * 
     * @return 返回talkHeadBean
     */
    public TalkHeadBean getTalkHeadBean() {
        return talkHeadBean;
    }

    /**
     * 获取talkHeadBean.
     * 
     * @param talkHeadBean
     *            要设置的talkHeadBean
     */
    public void setTalkHeadBean(TalkHeadBean talkHeadBean) {
        this.talkHeadBean = talkHeadBean;
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
