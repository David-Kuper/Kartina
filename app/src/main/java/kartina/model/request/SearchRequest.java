package kartina.model.request;

import com.kartina.service.model.AbstractRequest;

/**
 * Created by David on 2016/11/23.
 */

public class SearchRequest extends AbstractRequest {
    private String keyWord;
    private int selectType;

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public int getSelectType() {
        return selectType;
    }

    public String getKeyWord() {
        return keyWord;
    }
}
