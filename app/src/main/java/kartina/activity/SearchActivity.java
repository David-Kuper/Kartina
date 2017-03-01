package kartina.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.kartina.service.app.model.GetHotTagModelRequest;
import com.kartina.service.app.model.GetHotTagModelResponse;
import com.kartina.service.model.AbstractResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kartina.R;
import kartina.card.CardListView;
import kartina.card.DataManager.CardProcess;
import kartina.card.DataManager.RequestUtil;
import kartina.card.DataManager.ResponseParameter;
import kartina.card.DataManager.SuperService;
import kartina.card.ListViewController;
import kartina.card.bean.CardBean;
import kartina.card.bean.Tag;
import kartina.model.UIModel.SearchDo;
import kartina.model.UIModel.UserInfoDo;
import kartina.model.model.SelectResultModel;
import kartina.model.request.SearchRequest;
import kartina.util.DensityUtil;
import kartina.util.SharedpreferncesUtil;

public class SearchActivity extends SuperActivity {
    private Spinner searchTypeSpinner;
    private EditText searchContentView;
    private TextView searchTo;
    private CardListView cardListView;
    private ListViewController listViewController;
    private GridView hotTagGridView;
    private SearchAdapter hotTagAdapter;
    private ListView searchDefaultListView;
    private SearchAdapter historyAdapter;
    private LinearLayout resultLayout;
    private LinearLayout defaultLayout;
    private SearchDo searchDo;
    private int searchType = SEARCH_ALL;
    private ArrayList<Tag> searchedList = new ArrayList<>();
    private Map<Integer, Integer> searchTypeMap = new HashMap<Integer, Integer>() {
        {
            put(0, SEARCH_ALL);
            put(1, SEARCH_CLASS);
            put(2, SEARCH_NOTE_AND_TALK);
            put(3, SEARCH_TEACHER);
        }
    };
    private static final int SEARCH_ALL = 3;
    private static final int SEARCH_NOTE_AND_TALK = 2;
    private static final int SEARCH_CLASS = 0;
    private static final int SEARCH_TEACHER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUseParentActionBar(false);
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_search);
        init();
        initData();
    }

    private void init() {
        searchDo = new SearchDo();
        listViewController = new ListViewController(this, false);
        cardListView = (CardListView) findViewById(R.id.result_list);
        listViewController.setView(cardListView);

        defaultLayout = (LinearLayout) findViewById(R.id.default_search_layout);
        resultLayout = (LinearLayout) findViewById(R.id.search_result_layout);

        searchTypeSpinner = (Spinner) findViewById(R.id.search_type);
        searchContentView = (EditText) findViewById(R.id.search_edit);
        searchTo = (TextView) findViewById(R.id.search_to);

        View headView = LayoutInflater.from(getBaseContext()).inflate(R.layout.search_default_headlayout, null, false);
        hotTagGridView = (GridView) headView.findViewById(R.id.hot_tags);

        searchDefaultListView = (ListView) findViewById(R.id.search_defaul_list);

        searchDefaultListView.addHeaderView(headView);


        hotTagAdapter = new SearchAdapter(getBaseContext(), R.layout.hot_tag_text, true);
        historyAdapter = new SearchAdapter(getBaseContext(), R.layout.search_history_text, false);

        hotTagAdapter.setClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Tag tag) {
                search(tag.getTag());
                searchContentView.setText(tag.getTag());
            }
        });
        historyAdapter.setClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Tag tag) {
                search(tag.getTag());
                searchContentView.setText(tag.getTag());
            }
        });

        searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType = searchTypeMap.get(position);
                Toast.makeText(getBaseContext(), "SearchType " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchType = SEARCH_ALL;
            }
        });
        searchContentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    showDefault();
                } else {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = searchContentView.getText().toString();
                search(input);
            }
        });
    }


    private void initData() {
        GetHotTagModelRequest request = new GetHotTagModelRequest();
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        request.setPageNum(0);
        request.setRowPage(10);
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        searchDo.getHotTag(requestBody, new SuperService.CallBack<GetHotTagModelResponse>() {
            @Override
            public void OnSucess(ResponseParameter<GetHotTagModelResponse> response) {
                if (response.object.getSuccess().equals("1")) {
                    GetHotTagModelResponse hotTagModelResponse = response.object;
                    ArrayList<kartina.card.bean.Tag> tagList = hotTagModelResponse.getData().getTagList();
                    hotTagAdapter.setDataList(tagList);
                } else {
                    showToast("搜索失败\n" + response.object.getError().getMsg());
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {

            }

            @Override
            public void OnProcess(ResponseParameter<GetHotTagModelResponse> response, int curValue, int totalValue) {

            }
        });

        hotTagGridView.setAdapter(hotTagAdapter);
        searchDefaultListView.setAdapter(historyAdapter);

    }

    private void showDefault() {
        defaultLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.INVISIBLE);
    }

    private void showResult() {
        defaultLayout.setVisibility(View.INVISIBLE);
        resultLayout.setVisibility(View.VISIBLE);
    }

    private void search(String input) {
        showResult();
//        List<CardBean> beanList = new ArrayList<>();
//        CardBean cardBean;
//
//        cardBean = new CardBean();
//        CardBean1008 cardBean1008;
//
//        cardBean1008 = new CardBean1008();
//        cardBean1008.showMore = false;
//        cardBean1008.groupNum = "3";
//        cardBean1008.groupName = "帖子、话题";
//        cardBean.data = cardBean1008;
//        cardBean.cardViewType = 1008;
//        beanList.add(cardBean);
//
//        CardBean1003 cardBean1003;
//        for(int i = 0; i < 3; i++){
//            cardBean1003 = new CardBean1003();
//            cardBean = new CardBean();
//            cardBean.cardViewType = 1003;
//            cardBean.data = cardBean1003;
//
//            cardBean1003.commentNum = "100"+i;
//            cardBean1003.noteNum = "132"+i;
//            cardBean1003.type = i%2 + 1;
//            cardBean1003.avatar = null;
//            cardBean1003.likeNum = "32";
//            cardBean1003.describe = "啊拉开松井大辅我大是大非违法";
//            cardBean1003.title = "标题你说";
//            cardBean1003.focusNum = "481"+i;
//            beanList.add(cardBean);
//        }
//
//        //课程
//        cardBean1008 = new CardBean1008();
//        cardBean1008.showMore = false;
//        cardBean1008.groupNum = "3";
//        cardBean1008.groupName = "课程";
//        cardBean.data = cardBean1008;
//        cardBean.cardViewType = 1008;
//        beanList.add(cardBean);
//
//        CardBean1013 cardBean1013;
//        for(int i = 0; i < 3; i++){
//            cardBean1013 = new CardBean1013();
//            cardBean = new CardBean();
//            cardBean.cardViewType = 1013;
//            cardBean.data = cardBean1013;
//
//            cardBean1013.className = "高等数学"+i;
//            cardBean1013.classId = "03";
//            cardBean1013.classPlace = "江安 二基楼 B302";
//            cardBean1013.classTeacher = "赖洪亮";
//            cardBean1013.likeNum = 43;
//            cardBean1013.isActioned = false;
//            cardBean1013.classTime = "周二 上午 1—3 节";
//            beanList.add(cardBean);
//        }
//
//        //老师
//        cardBean1008 = new CardBean1008();
//        cardBean1008.showMore = false;
//        cardBean1008.groupNum = "3";
//        cardBean1008.groupName = "老师";
//        cardBean.data = cardBean1008;
//        cardBean.cardViewType = 1008;
//        beanList.add(cardBean);
//
//        CardBean1012 cardbean1012;
//        for(int i = 0; i < 3; i++){
//            cardbean1012 = new CardBean1012();
//            cardBean = new CardBean();
//            cardBean.cardViewType = 1012;
//            cardBean.data = cardbean1012;
//
//            cardbean1012.focusNum = "23"+i;
//            cardbean1012.likeNum = "33"+i;
//            cardbean1012.avarta = null;
//            cardbean1012.commentNum = "321"+i;
//            cardbean1012.describe = "教授  研究方向：历史文化   简介：阿拉山口就发文件";
//            cardbean1012.name = "周鼎";
//            cardbean1012.tag = "四川大学 历史文化学院";
//            beanList.add(cardBean);
//        }
        //添加到历史记录中
        historyAdapter.addData(new Tag(input));

        SearchRequest request = new SearchRequest();
        request.setKeyWord(input);
        request.setSelectType(searchType);
        request.setRequestUserId(UserInfoDo.getInstance().getUid());
        JsonElement requestBody = RequestUtil.getInstance().toJsonElement(request);
        searchDo.searchKeyWord(requestBody, new SuperService.CallBack<AbstractResponse<SelectResultModel>>() {
            @Override
            public void OnSucess(ResponseParameter<AbstractResponse<SelectResultModel>> response) {
                if (response.object.getSuccess().equals("1")) {
                    SelectResultModel resultModel = response.object.getData();
                    List<CardBean> beanList = resultModel.getCardList();
                    if (beanList != null && beanList.size() > 0) {
                        CardProcess.parseCardList(beanList, new CardProcess.CallBack<List<CardBean>>() {
                            @Override
                            public void parseCardSuccess(List<CardBean> list) {
                                listViewController.setData(list, false);
                            }
                        }, SearchActivity.this);
                    } else {
                        showLoading(VIEW_NODATA);
                    }
                } else {
                    showToast(response.object.getError().getMsg());
                }
            }

            @Override
            public <D> void OnError(ResponseParameter<D> response) {

            }

            @Override
            public void OnProcess(ResponseParameter<AbstractResponse<SelectResultModel>> response, int curValue, int totalValue) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Tag> tagArrayList = SharedpreferncesUtil.getSearchListFromDisk(getApplicationContext());
        if (searchedList != null && tagArrayList != null) {
            if (searchedList.size() < tagArrayList.size()) {
                searchedList.clear();
                searchedList.addAll(tagArrayList);
                historyAdapter.setDataList(searchedList);
            }
        }
    }

    @Override
    protected void loadData() {

    }

    private void refreshData() {

    }

    private void clearAllHistory() {
        if (searchDefaultListView == null || historyAdapter == null)
            return;
        if (historyAdapter.getCount() > 0)
            historyAdapter.clearList();
    }

    static class SearchAdapter extends BaseAdapter {

        private Context context;
        private List<Tag> tagList = new ArrayList<>();
        private OnItemClickListener clickListener;
        private int rid;
        boolean isHead = false;

        public SearchAdapter(Context context, int rid, List<Tag> tagList) {
            this.context = context;
            this.tagList = tagList;
            this.rid = rid;
        }

        public SearchAdapter(Context context, int rid, boolean isHead) {
            this.context = context;
            this.rid = rid;
            this.isHead = isHead;
        }

        public void setDataList(List<Tag> tagList) {
            if (tagList == null || tagList.size() <= 0)
                return;
            this.tagList = tagList;
            notifyDataSetChanged();
        }

        public void addData(Tag tag) {
            if (tagList == null) {
                return;
            }
            this.tagList.add(tag);
            notifyDataSetChanged();
        }

        public void clearList() {
            if (tagList == null)
                return;
            tagList.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (isHead)
                return tagList.size();
            else
                return tagList.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            if (position < tagList.size())
                return tagList.get(position);
            else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                if (getItemViewType(position) == 0) {
                    convertView = LayoutInflater.from(context).inflate(rid, null, false);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clickListener != null) {
                                clickListener.OnItemClick((Tag) getItem(position));
                            }
                        }
                    });
                    ((TextView) convertView).setText(((Tag) getItem(position)).getTag());
                } else if (!isHead) {
                    TextView textView = new TextView(context);
                    textView.setText("清空历史记录");
                    textView.setTextColor(Color.BLUE);
                    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(DensityUtil.dp2px(context, 6));
                    textView.setPadding(0, DensityUtil.dp2px(context, 15), 0, 50);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clearList();
                        }
                    });
                    convertView = textView;
                }
            }

            if (getItemViewType(position) == 0) {
                ((TextView) convertView).setText(((Tag) getItem(position)).getTag());
            }else {
                ((TextView) convertView).setText("清空历史记录");
            }

            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            if (!isHead)
                return 2;
            else
                return 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (!isHead && position >= tagList.size()) {
                return 1; //清空历史记录
            } else {
                return 0;
            }
        }

        public void setClickListener(OnItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public interface OnItemClickListener {
            void OnItemClick(Tag tag);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedpreferncesUtil.saveSearchedListToDisk(this, searchedList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
