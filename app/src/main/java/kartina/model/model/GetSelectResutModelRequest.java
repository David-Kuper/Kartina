/*
 * 文件名：GetSelectModelRequest.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： GetSelectModelRequest.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月22日
 * 修改内容：新增
 */
package kartina.model.model;

import com.kartina.service.model.AbstractRequest;

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
public class GetSelectResutModelRequest extends AbstractRequest {

    /**
     * 搜索关键词/标签.
     */
    private String keyWord;

    /**
     * 搜索类型 帖子 话题 课程 老师 or 所有.
     */
    private int selectType;

    /**
     * 设置keyWord.
     * 
     * @return 返回keyWord
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * 获取keyWord.
     * 
     * @param keyWord
     *            要设置的keyWord
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * 设置selectType.
     * 
     * @return 返回selectType
     */
    public int getSelectType() {
        return selectType;
    }

    /**
     * 获取selectType.
     * 
     * @param selectType
     *            要设置的selectType
     */
    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

}
