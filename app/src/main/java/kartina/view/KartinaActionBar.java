package kartina.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import kartina.R;


/**
 * Created by David on 2016/10/31.
 */

public class KartinaActionBar extends FrameLayout {

    /**
     * Style1 默认风格
     */
    private ImageView mLeftIcon_Style1;
    private ImageView mRigthIcon_Style1;
    private TextView mCenterTxt_Style1;

    /**
     * Style2
     */
    private ImageView mLeftIcon_Style2;
    private EditText mCenterEdit_Style2;
    private ImageView mRightTxt_Style2;
    private OnItemClickListener onItemClickListener;

    private int type;
    private View mStyleView1;
    private View mStyleView2;
    private Context mContext;
    public static final int STYLE1 = 1;
    public static final int STYLE2 = 2;
    public static final int LEFT = 1;
    public static final int EDIT = 2;
    public static final int SPINNER = 4;
    public static final int RIGHT = 3;

    public KartinaActionBar(Context context) {
        this(context, null);
    }

    public KartinaActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KartinaActionBar);
            type = typedArray.getInt(R.styleable.KartinaActionBar_style,1);
            if (type == 1 ){
                initStyle1(context);
            }else if (type == 2 ){
                initStyle2(context);
            }
            typedArray.recycle();
        }
    }

    public KartinaActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setStyle(int style){
        if (style == 1){
            if (mStyleView1 == null){
                initStyle1(mContext);
            }
            if (mStyleView2 != null)
                mStyleView2.setVisibility(GONE);

            mStyleView1.setVisibility(VISIBLE);
        }else if (style == 2){
            if (mStyleView2 == null){
                initStyle2(mContext);
            }
            if (mStyleView1 != null)
                mStyleView1.setVisibility(GONE);

            mStyleView2.setVisibility(VISIBLE);
        }
    }

    private void initStyle1(Context context) {
        if (context == null)
            return;
        if (mStyleView1 == null){
            mStyleView1 = LayoutInflater.from(context).inflate(R.layout.action_bar_style1, this, false);
            mLeftIcon_Style1 = (ImageView) mStyleView1.findViewById(R.id.action_bar_style1_left);
            mRigthIcon_Style1 = (ImageView) mStyleView1.findViewById(R.id.action_bar_style1_right);
            mCenterTxt_Style1 = (TextView) mStyleView1.findViewById(R.id.action_bar_style1_center);
            mRigthIcon_Style1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(STYLE1,RIGHT,mRigthIcon_Style1);
                    }
                }
            });

            mLeftIcon_Style1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(STYLE1,LEFT,mLeftIcon_Style1);
                    }
                }
            });

            if (mStyleView2 != null){
                mStyleView2.setVisibility(GONE);
            }

            this.addView(mStyleView1);
        }

    }


    private void initStyle2(Context context){
        if (context == null)
            return;
        if (mStyleView2 == null) {
            mStyleView2 = LayoutInflater.from(context).inflate(R.layout.action_bar_style2, this, false);
            mLeftIcon_Style2 = (ImageView) mStyleView2.findViewById(R.id.action_bar_style2_left);
            mRightTxt_Style2 = (ImageView) mStyleView2.findViewById(R.id.action_bar_style2_right);
            mCenterEdit_Style2 = (EditText) mStyleView2.findViewById(R.id.action_bar_style2_center);


            mCenterEdit_Style2.clearFocus();
            mRightTxt_Style2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(STYLE2,RIGHT,mRightTxt_Style2);
                    }
                }
            });

            mLeftIcon_Style2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.OnItemClick(STYLE2,LEFT,mLeftIcon_Style2);
                    }
                }
            });
            mCenterEdit_Style2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (onItemClickListener != null){
                         onItemClickListener.OnItemClick(STYLE2,EDIT,mCenterEdit_Style2);
                     }
                }
            });

            if (mStyleView1 != null) {
                mStyleView1.setVisibility(GONE);
            }

            this.addView(mStyleView2);
        }
    }



    public void setStyle1CenterTitle(String title){
        if (title == null || mCenterTxt_Style1 == null)
            return;
        mCenterTxt_Style1.setText(title);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int style,int position,View view);
    }

}
