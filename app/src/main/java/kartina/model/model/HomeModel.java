/*
 * 文件名：HomeModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： HomeModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;
import kartina.model.BannerBean;

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
public class HomeModel {

    /**
     * 滚动栏.
     */
    private ArrayList<BannerBean> bannerBeanList;

    /**
     * cardBean.
     */
    private ArrayList<CardBean> cardList;

    /**
     * 设置bannerBeanList.
     * 
     * @return 返回bannerBeanList
     */
    public ArrayList<BannerBean> getBannerBeanList() {
        return bannerBeanList;
    }

    /**
     * 获取bannerBeanList.
     * 
     * @param bannerBeanList
     *            要设置的bannerBeanList
     */
    public void setBannerBeanList(ArrayList<BannerBean> bannerBeanList) {
        this.bannerBeanList = bannerBeanList;
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
