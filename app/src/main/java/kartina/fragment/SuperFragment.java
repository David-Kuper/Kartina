package kartina.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import kartina.R;
import kartina.view.ErrorHintView;


public abstract class SuperFragment extends Fragment {
    private static final String TAG = "SuperFragment";
    private View contentView;
    private OnFloatButtonListener mListener;
    private ErrorHintView mErrorHintView;
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

    public SuperFragment() {
        Log.e("SuperFragment", getClass().getName() + "   call SuperFragment()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = getContentView();
        FrameLayout view = (FrameLayout) inflater.inflate(R.layout.fragment_super, container, false);
        mErrorHintView = (ErrorHintView) view.findViewById(R.id.hintView);
        view.addView(contentView);
        return view;
    }

    protected abstract View getContentView();

    /**
     * 在onCreateView方法之后 onRestore之前启动,每次，可以做一些框架层的检测
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, this.getClass().getName() + "  call onResume()");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, getClass().getName() + "   onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, getClass().getName() + "   onDetach()");
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, getClass().getName() + "   onDestroyView()");
    }

    /**
     * 显示正在加载界面
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
    public void showToast(String txt){
        Toast.makeText(getContext(),txt,Toast.LENGTH_SHORT).show();
    }
    protected abstract void loadData();

    public interface OnFloatButtonListener {
        void onClick(Uri uri);
    }

}
