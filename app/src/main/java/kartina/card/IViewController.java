package kartina.card;

import android.view.View;

/**
 * Created by David on 2016/9/16.
 */

public abstract class IViewController<T extends View,D extends Object> {
    public abstract void setData(D data,boolean hasNext);
    public abstract void addData(D data,boolean hasNext);
    public abstract void setView(T view);
}
