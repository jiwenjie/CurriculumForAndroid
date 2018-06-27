package com.example.root.curriculum.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.App;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView, RxNetManager {

    protected T mPresenter;

    private ProgressDialog dialog;
    protected Context mContext;
    //缓存Fragment view
    private View mRootView;
    private boolean mIsMulti = false;

    protected String fragmentTitle;             //fragment标题
    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载
    protected LayoutInflater inflater;

    private OnRetryListener listener = new OnRetryListener();
    /**
     * 多种状态的 View 的切换
     */
    protected MultipleStatusView mLayoutStatusView;

    //用于整体管理 disable （RxJava）
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(attachLayoutRes(), null);
            //NineGridView.setImageLoader(new GlideImageLoader());
            ButterKnife.bind(this, mRootView);
            initViews();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        //多种状态切换的view 重试点击事件
        mLayoutStatusView.setOnClickListener(listener);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }

    /**
     * 点击重试监听器
     */
    public class OnRetryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onRetry();
        }
    }

    abstract void onRetry();

    @Override
    public void dispose(Disposable disposable) {
        disposables.remove(disposable);
    }

    @Override
    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void showLoading() {

        if (dialog != null && dialog.isShowing()) {
            dialog = new ProgressDialog(App.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("请求网络中");
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 打卡软键盘
     */
    public void openKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public void closeKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    public String getTitle() {
        return TextUtils.isEmpty(fragmentTitle) ? "" : fragmentTitle;
    }

    public void setTitle(String title) {
        fragmentTitle = title;
    }

    /**
     * 绑定布局文件
     * @return  布局文件ID
     */
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        ((BaseActivity)getActivity()).initToolBar(toolbar, homeAsUpEnabled, title);
    }


}
