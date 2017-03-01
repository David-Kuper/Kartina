package kartina.card.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kartina.R;
import kartina.card.IViewGroup;
import kartina.card.bean.CardBean;
import kartina.card.bean.TalkHeadBean;
import kartina.model.NoteBody;
import kartina.util.DensityUtil;
import kartina.view.AvatarView;
import kartina.view.NoteBodyView;
/**
 * 话题头部
 */

/**
 * Created by David on 2016/11/8.
 */

public class TalkHeadView extends IViewGroup<LinearLayout,TalkHeadBean> {
    private LinearLayout tagsLayout;
    private NoteBodyView talkContent;
    private AvatarView avatarView;
    private TextView noteMums;
    private ImageView action;
    TalkHeadBean cardBean1009;
    private OnActionClickListener actionClickListener;

    private View view;

    public TalkHeadView(Context context) {
        super(context);
    }

    public TalkHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TalkHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected LinearLayout initView(Context context) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.cardview_1009, null, false);
            tagsLayout = (LinearLayout) view.findViewById(R.id.tags_layout);
            talkContent = (NoteBodyView) view.findViewById(R.id.talk_content);
            avatarView = (AvatarView) view.findViewById(R.id.avatar_view);
            action = (ImageView) view.findViewById(R.id.action);
            noteMums = (TextView) view.findViewById(R.id.note_num);
            talkContent.setTitleSize(DensityUtil.dp2px(getContext(),15));

            action.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Action Clicked!",Toast.LENGTH_SHORT).show();
                    if (cardBean1009.isActioned){
                        cardBean1009.isActioned = false;
                        action.setImageResource(R.mipmap.focus_normal);
                    }else {
                        cardBean1009.isActioned = true;
                        action.setImageResource(R.mipmap.focus_clicked);
                    }
                    if (actionClickListener != null){
                        actionClickListener.onActionClick();
                    }
                }
            });
        }
        return (LinearLayout) view;
    }

    @Override
    protected void fillView(LinearLayout containerView) {

    }

    public void fillViewWithData(TalkHeadBean data) {
        if (data == null )
            return;
        cardBean1009 = data;

        if (cardBean1009.tagList != null && cardBean1009.tagList.size() > 0 ){

        }
        NoteBody noteBody = new NoteBody();
        noteBody.describe = cardBean1009.content;
        noteBody.title = cardBean1009.title;
        talkContent.setData(noteBody);
        avatarView.setData(cardBean1009.creatoAvatar,cardBean1009.creator,cardBean1009.createTime);
    }


    public void setNoteCounts(int counts){
         noteMums.setText("(" + counts+" )");
    }
    public void setActionClickListener(OnActionClickListener actionClickListener) {
        this.actionClickListener = actionClickListener;
    }

    public interface OnActionClickListener{
        void onActionClick();
    }
}
