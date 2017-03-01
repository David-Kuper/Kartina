package kartina.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import kartina.R;


/**
 * Created by David on 2016/10/31.
 */

public class KartinaNavBar extends FrameLayout {
    private LinearLayout mNav1;
    private LinearLayout mNav2;
    private LinearLayout mNav3;

    public static final int NAV1 = 0;
    public static final int NAV2 = 1;
    public static final int NAV3 = 2;

    private SelectAction mSelection;

    public KartinaNavBar(Context context) {
        this(context,null);
    }

    public KartinaNavBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KartinaNavBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context){
        if (context == null)
            return;
        View view = LayoutInflater.from(context).inflate(R.layout.kartina_navbar,this,false);
        mNav1 = (LinearLayout) view.findViewById(R.id.nav1);
        mNav2 = (LinearLayout) view.findViewById(R.id.nav2);
        mNav3 = (LinearLayout) view.findViewById(R.id.nav3);

        mNav1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectNav(NAV1);
                if (mSelection != null)
                    mSelection.selectNav(KartinaNavBar.NAV1);
            }
        });
        mNav2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectNav(NAV2);
                if (mSelection != null)
                    mSelection.selectNav(KartinaNavBar.NAV2);
            }
        });
        mNav3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectNav(NAV3);
                if (mSelection != null)
                    mSelection.selectNav(KartinaNavBar.NAV3);
            }
        });
        this.addView(view);
    }

    public void setSelection(SelectAction selection){
        if (selection != null)
            mSelection = selection;
    }

    public void selectNav(int index){
        ImageView imageView;
        resetState();
        switch (index){
            case NAV1:
                imageView = (ImageView)mNav1.findViewById(R.id.school_nav);
                imageView.setImageResource(R.mipmap.bank_clicked);
                break;
            case NAV2:
                imageView = (ImageView) mNav2.findViewById(R.id.discover_nav);
                imageView.setImageResource(R.mipmap.discover_clicked);
                break;
            case NAV3:
                imageView = (ImageView) mNav3.findViewById(R.id.person_nav);
                imageView.setImageResource(R.mipmap.person_clicked);
                break;
            default:
        }
    }

    private void resetState(){
        if (mNav1 == null || mNav2 == null || mNav3 == null)
            return;
        ImageView imageView;
        imageView = (ImageView)mNav1.findViewById(R.id.school_nav);
        imageView.setImageResource(R.mipmap.bank_normal);

        imageView = (ImageView) mNav2.findViewById(R.id.discover_nav);
        imageView.setImageResource(R.mipmap.discover_normal);

        imageView = (ImageView) mNav3.findViewById(R.id.person_nav);
        imageView.setImageResource(R.mipmap.person_normal);
    }

    public interface SelectAction{
        public void selectNav(int index);
    }
}
