/*
 * 文件名：UserInfoModel.java
 * 版权：Copyright 2007-2016 shiqinghuang  Tech. Co. Ltd. All Rights Reserved. 
 * 描述： UserInfoModel.java
 * 修改人：shiqinghuang
 * 修改时间：2016年11月20日
 * 修改内容：新增
 */
package kartina.model.model;

import com.kartina.service.model.user.UserInfo;

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
public class UserInfoModel {

    /**
     * 添加字段注释.
     */
    private UserInfo userInfo;

    /**
     * 设置userInfo.
     * 
     * @return 返回userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 获取userInfo.
     * 
     * @param userInfo
     *            要设置的userInfo
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

}
