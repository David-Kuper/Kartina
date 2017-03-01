/*
 * 文件名：MyPublish.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： MyPublish.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import java.util.ArrayList;

import kartina.card.bean.CardBean;
import kartina.card.bean.Tag;

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
public class MyComment {

    /**
     * 页数.
     */
    private int pageNum;

    /**
     * 总的项数.
     */
    private int totalNum;

    /**
     * 每页的项数.
     */
    private int rowPage;

    /**
     * 实际返回的数目.
     */
    private int realNum;

    /**
     * 添加字段注释.
     */
    ArrayList<CardBean> cardList;

    /**
     * 添加字段注释.
     */
    Tag<Boolean> hasNext;

    /**
     * 设置pageNum.
     * 
     * @return 返回pageNum
     */
    public int getPageNum() {
        return pageNum;
    }

    /**
     * 获取pageNum.
     * 
     * @param pageNum
     *            要设置的pageNum
     */
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 设置totalNum.
     * 
     * @return 返回totalNum
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * 获取totalNum.
     * 
     * @param totalNum
     *            要设置的totalNum
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 设置rowPage.
     * 
     * @return 返回rowPage
     */
    public int getRowPage() {
        return rowPage;
    }

    /**
     * 获取rowPage.
     * 
     * @param rowPage
     *            要设置的rowPage
     */
    public void setRowPage(int rowPage) {
        this.rowPage = rowPage;
    }

    /**
     * 设置realNum.
     * 
     * @return 返回realNum
     */
    public int getRealNum() {
        return realNum;
    }

    /**
     * 获取realNum.
     * 
     * @param realNum
     *            要设置的realNum
     */
    public void setRealNum(int realNum) {
        this.realNum = realNum;
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

    /**
     * 设置hasNext.
     * 
     * @return 返回hasNext
     */
    public Tag<Boolean> getHasNext() {
        return hasNext;
    }

    /**
     * 获取hasNext.
     * 
     * @param hasNext
     *            要设置的hasNext
     */
    public void setHasNext(Tag<Boolean> hasNext) {
        this.hasNext = hasNext;
    }

}
