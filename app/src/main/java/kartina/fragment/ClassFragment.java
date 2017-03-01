package kartina.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.google.gson.JsonElement;
import com.kartina.service.model.AbstractResponse;

import java.util.ArrayList;
import java.util.List;

import kartina.R;
import kartina.activity.ClassActivity;
import kartina.card.CardAdapter;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.ViewType.CardViewType;
import kartina.card.bean.CardBean;
import kartina.card.bean.Tag;
import kartina.model.UIModel.DiscoverDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.CourseModel;
import kartina.util.DensityUtil;

/**
 * Created by David on 2016/11/4.
 */

public class ClassFragment extends SuperFragment {
    private RecyclerView mHeadView;
    private ListViewController listViewController;
    private HeaderAdapter headerAdapter;
    private static final int spanCount = 3;
    private DiscoverDo discoverDo;
    private List<Tag> allTags;
    private List<Tag> ISubscribeTags = new ArrayList<>();

    public ClassFragment() {
        discoverDo = new DiscoverDo();
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_class, null, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHeadView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.class_header, null, false);
        CardListView cardListView = (CardListView) view.findViewById(R.id.listview);
        headerAdapter = new HeaderAdapter(getContext());
        listViewController = new ListViewController(getActivity(), false);

        mHeadView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));

        cardListView.addHeaderView(mHeadView);
        listViewController.setView(cardListView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        refreshTop();
    }

    private void init() {
        listViewController.setOnItemClickListener(new CardAdapter.CardAdapterListener() {
            @Override
            public void OnViewPosition(View view, int position,String itemId, CardViewType cardViewType) {
                Intent intent = new Intent(getContext(),ClassActivity.class);
                intent.putExtra("itemId",itemId);
                startActivity(intent);
            }
        });
        listViewController.setOnCheckMoreListener(new CardAdapter.CardAdapterMoreListener() {
            @Override
            public void OnChekMoreClicked(boolean hasNext) {
                Toast.makeText(getContext(), "Click Check More!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshTop() {
        loadData();
    }

    @Override
    protected void loadData() {
//        List<Tag> list = new ArrayList<>();
//        list.add(new Tag("文", "1"));
//        list.add(new Tag("理", "2"));
//        list.add(new Tag("工", "3"));
//        list.add(new Tag("商", "4"));
//        list.add(new Tag("医", "5"));

//        List<CardBean> cardList = new ArrayList<>();
//        CardBean<CardBean1005> cardBean;
//        for (int i = 0; i < 8; i++) {
//            ItemHeader itemHeader = new ItemHeader();
//            ItemBottom itemBottom = new ItemBottom();
//            NoteBody noteBody = new NoteBody();
//            CardBean1005 cardBean1005 = new CardBean1005();
//            cardBean = new CardBean<>();
//            cardBean.cardViewType = 1005;
//
//            itemBottom.comment_num = 141;
//            itemBottom.focus_num = 182;
//            itemBottom.praise_num = 232;
//            itemHeader.rid = R.mipmap.ic_launcher;
//            itemHeader.classes = "高数";
//            itemHeader.from_who = "张二";
//            itemHeader.time = "23:23";
//            itemHeader.origin = "来自王二老师的课程";
//            noteBody.describe = "内容啊阿斯蒂芬了可使肌肤算法发送方认为" + i;
//            noteBody.title = "标题啊搜房网分阿斯蒂芬无非是发生反倒是访问阿斯蒂芬";
//
//            cardBean1005.mItemHeader = itemHeader;
//            cardBean1005.mItemBottom = itemBottom;
//            cardBean1005.noteBody = noteBody;
//
//            cardBean.data = cardBean1005;
//            cardList.add(cardBean);
//        }
        showLoading(VIEW_LOADING);
        JsonElement jsonElement = RequestUtil.getInstance().toJsonObject("requestUserId ", UserInfoDo.getInstance().getUid());
        discoverDo.loadCourseList(jsonElement, new SuperService.CallBack<AbstractResponse<CourseModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<CourseModel>> response) {
                  if (response.object.getSuccess().equals("1")){
                      showLoading(VIEW_CONTENT);
                      final CourseModel courseModel = response.object.getData();
                      if (courseModel != null){
                          discoverDo.setCourseModel(courseModel);
                          allTags = courseModel.getTagList();
                          ISubscribeTags.clear();
                          for(int i = 0; i < 5 && i < allTags.size();i++){
                              ISubscribeTags.add(allTags.get(i));
                          }
                          headerAdapter.setTags(ISubscribeTags);

                          headerAdapter.setSpanCount(spanCount);
                          mHeadView.setAdapter(headerAdapter);

                          List<CardBean> cardList = courseModel.getCardList();
                          CardProcess.parseCardList(cardList, new CardProcess.CallBack<List<CardBean>>() {
                              @Override
                              public void parseCardSuccess(List<CardBean> list) {
                                  listViewController.setData(list,courseModel.getHasNext().getObject());
                              }
                          },getActivity());
                      }

                  }else {
                      showLoading(VIEW_LOADFAILURE);
                  }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {
                  if (response.error instanceof ServerError){
                      showLoading(VIEW_SERVERERROR);
                  }else if (response.error instanceof NetworkError){
                      showLoading(VIEW_WIFIFAILUER);
                  }else {
                      showLoading(VIEW_LOADFAILURE);
                  }
            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<CourseModel>> response, int curValue, int totalValue) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class HeaderAdapter extends RecyclerView.Adapter<HeaderViewHolder> {
        private List<Tag> tags;
        private Context context;
        private int mSpanCount = 3;
        private ClassFragment.OnItemClickLitener mOnItemClickListener;

        public HeaderAdapter(@NonNull List<Tag> tags, Context context) {
            this.tags = tags;
            this.context = context;
        }

        public HeaderAdapter(Context context) {
            this.context = context;
        }

        public void setTags(@NonNull List<Tag> tags) {
            this.tags = tags;
            notifyDataSetChanged();
        }

        public void setSpanCount(int count) {
            if (count > 4) {
                mSpanCount = 4;
            } else if (count < 0) {
                mSpanCount = 3;
            } else {
                mSpanCount = count;
            }
        }

        public void addTag(@NonNull Tag tag) {
            if (tags == null)
                return;
            tags.add(tag);
            notifyDataSetChanged();
        }

        @Override
        public HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            HeaderViewHolder viewHolder;
            View view;
            int width = DensityUtil.getWindowWidth(getActivity()) / mSpanCount;
            GridLayoutManager.LayoutParams params = new GridLayoutManager.LayoutParams(width, width);
            if (viewType == 1) {
                view = LayoutInflater.from(context).inflate(R.layout.circle_text, parent, false);
                view.setTag(1);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.circle_add, parent, false);
                view.setTag(2);
            }
            view.setLayoutParams(params);
            viewHolder = new HeaderViewHolder(view);
            return viewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < tags.size()) {
                return 1;
            } else {
                return 2;
            }
        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickListener = mOnItemClickLitener;
        }

        @Override
        public void onBindViewHolder(HeaderViewHolder holder, int position) {
            final int p = position;
            final HeaderViewHolder viewHolder = holder;
            if (position < tags.size()) {
                holder.textView.setText(tags.get(position).getTag());
                if (mOnItemClickListener != null) {
                    holder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Clicked Item", Toast.LENGTH_SHORT).show();
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(viewHolder.textView, p);
                            }
                        }
                    });
                }
            } else if (mOnItemClickListener != null) {
                holder.addView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked Item", Toast.LENGTH_SHORT).show();
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(viewHolder.textView, p);
                        }
                    }
                });

            }

        }

        @Override
        public int getItemCount() {
            return tags.size() + 1;
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView addView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            if ((Integer) itemView.getTag() == 1) {
                textView = (TextView) itemView.findViewById(R.id.txt);
            } else if ((Integer) itemView.getTag() == 2) {
                addView = (ImageView) itemView.findViewById(R.id.add_img);
            }
        }
    }
}
