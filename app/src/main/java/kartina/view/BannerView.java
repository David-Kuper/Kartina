package kartina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;

/**
 * Created by David on 2016/11/2.
 */

public class BannerView extends FrameLayout {
    private TextView mTitle;
    private TextView mDescribe;
    private ImageView mCover;

    public BannerView(Context context) {
        this(context,null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_banner,null,false);
        mCover = (ImageView) view.findViewById(R.id.banner_cover);
        mDescribe = (TextView) view.findViewById(R.id.banner_describe);
        mTitle = (TextView) view.findViewById(R.id.banner_title);

        addView(view);
    }
    public void setTitle(String title){
         if (mTitle == null)
             return;
        mTitle.setText(title);
    }
    public void setDescribe(String describe){
       if (mDescribe == null)
           return;
        mDescribe.setText(describe);
    }

    public void setBannerCover(String url){
        if (mCover == null)
            return;
    }

    public void setBannerCover(int rid){
        if (mCover == null)
            return;
        mCover.setImageResource(rid);
    }


    public ImageView getCover(){
         return mCover;
    }
}
