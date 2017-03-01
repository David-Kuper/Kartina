package kartina.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import kartina.api.Auction;
import kartina.view.CommonStateView;
import kartina.view.ErrorHintView;
import kartina.view.KartinaActionBar;

abstract public class SuperActivity extends AppCompatActivity implements Auction {
    private ErrorHintView mErrorHintView;
    private KartinaActionBar actionBar;
    private LinearLayout container;
    private FrameLayout contentContainer;
    private ViewGroup contentView;
    private ProgressDialog progressDialog;
    private boolean useParentActionBar = true;

    protected static final int VIEW_CONTENT = 1;
    /**
     * 显示断网
     **/
    protected static final int VIEW_WIFIFAILUER = 2;
    /**
     * 显示加载数据失败
     **/
    protected static final int VIEW_LOADFAILURE = 3;
    /**
     * 正在加载
     */
    protected static final int VIEW_LOADING = 4;
    /**
     * 服务器异常
     */
    protected static final int VIEW_SERVERERROR = 5;

    protected static final int VIEW_NODATA = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams containerLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //是否使用父类ActionBar
        if (useParentActionBar) {
            actionBar = new KartinaActionBar(this);
            actionBar.setStyle(KartinaActionBar.STYLE1);
            container.addView(actionBar);
            actionBar.setOnItemClickListener(new KartinaActionBar.OnItemClickListener() {
                @Override
                public void OnItemClick(int style, int position, View view) {
                    if (style == KartinaActionBar.STYLE1) {
                        if (KartinaActionBar.LEFT == position) {
                            finish();
                        } else if (KartinaActionBar.RIGHT == position) {

                        }
                    } else if (style == KartinaActionBar.STYLE2) {
                        if (KartinaActionBar.EDIT == position) {
                            Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                            startActivity(intent);
                        } else if (KartinaActionBar.LEFT == position) {

                        } else if (KartinaActionBar.RIGHT == position) {

                        } else if (KartinaActionBar.SPINNER == position) {

                        }
                    }

                }
            });
        }

        //内容界面
        contentContainer = new FrameLayout(this);
        //错误界面
        mErrorHintView = new ErrorHintView(this);
        FrameLayout.LayoutParams StateLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mErrorHintView.setVisibility(View.GONE);
        contentContainer.addView(mErrorHintView, StateLP);

        FrameLayout.LayoutParams frontLP = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        container.addView(contentContainer, frontLP);
        addContentView(container, containerLP);

    }

    protected void showText(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }

    public void setUseParentActionBar(boolean useParentActionBar) {
        this.useParentActionBar = useParentActionBar;
    }

    protected void setActionBarStyle(int style) {
        if (actionBar == null || useParentActionBar == false) {
            throw new NullPointerException("actionBar is null or you are not userParentActionBar,please Call setUserParentActionBar(true)");
        }
        actionBar.setStyle(style);
    }

    protected void setTitle(String title) {
        actionBar.setStyle1CenterTitle(title);
    }

    protected void addContentView(int rid) {
        contentView = (ViewGroup) LayoutInflater.from(this).inflate(rid, null, false);
        if (contentContainer != null)
            contentContainer.addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected void addView(int rid, ViewGroup.LayoutParams layoutParams) {
        View view = LayoutInflater.from(this).inflate(rid, null, false);
        if (contentView != null)
            contentView.addView(view, layoutParams);
    }

    protected KartinaActionBar getMyActionBar() {
        return actionBar;
    }

    @Override
    public void onError(CommonStateView view, int code) {
        if (view == null)
            return;
        view.setStateView(code);
    }

    @Override
    public void onSuccess(CommonStateView view) {

    }

    @Override
    public void onReLoad(CommonStateView view) {

    }

    public void showToast(String txt) {
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度条
     */
    protected void showProgressDialog(final String title, final String content){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if ( progressDialog == null) {
                    progressDialog = new ProgressDialog(SuperActivity.this);
                    progressDialog.setMessage("正在加载,请稍后...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setTitle(title);
                    progressDialog.setMessage(content);
                    progressDialog.setCancelable(true);
                }
                progressDialog.show();
            }
        });
    }
    /**
     * 隐藏进度条
     */
    protected void clearProgressDialog(){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if( progressDialog!=null ){
                    progressDialog.dismiss();
                }
            }
        });
    }

    protected void showCustomProgressDialog(String title, String content, View customView) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getBaseContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(content);
        progressDialog.setCustomTitle(customView);
        progressDialog.show();
    }

    /**
     * 隐藏输入法
     *
     * @param context
     * @param achor
     */
    public static void hideSoftInput(Context context, View achor) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(achor.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected abstract void loadData();

    /**
     * 显示加载状态界面
     *
     * @param i
     */
    protected void showLoading(int i) {
        mErrorHintView.setVisibility(View.INVISIBLE);
        contentView.setVisibility(View.INVISIBLE);


        switch (i) {
            case VIEW_CONTENT:
                mErrorHintView.hideLoading();
                contentView.setVisibility(View.VISIBLE);
                break;

            case VIEW_WIFIFAILUER:
                mErrorHintView.hideLoading();
                mErrorHintView.netError(new ErrorHintView.OperateListener() {
                    @Override
                    public void operate() {
                        showLoading(VIEW_LOADING);
                        loadData();
                    }
                });
                break;

            case VIEW_LOADFAILURE:
                mErrorHintView.hideLoading();
                mErrorHintView.loadFailure(new ErrorHintView.OperateListener() {
                    @Override
                    public void operate() {
                        showLoading(VIEW_LOADING);
                        loadData();
                    }
                });
                break;

            case VIEW_LOADING:
                mErrorHintView.loadingData();
                break;
            case VIEW_SERVERERROR:
                mErrorHintView.hideLoading();
                mErrorHintView.serverError(new ErrorHintView.OperateListener() {
                    @Override
                    public void operate() {
                        showLoading(VIEW_LOADING);
                        loadData();
                    }
                });
                break;
            case VIEW_NODATA:
                mErrorHintView.hideLoading();
                mErrorHintView.noData();
                break;
        }
    }
}
