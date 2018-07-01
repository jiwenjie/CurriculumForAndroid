package com.example.root.curriculum.activity;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.GetFriendAdapter;
import com.example.root.curriculum.base.BaseRequestPermissionActivity;
import com.example.root.curriculum.bean.PhonePerson;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GetFriendsActivity extends BaseRequestPermissionActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    String mCurFilter;

    //必须使用全局的Application初始化才有用， 否则会失效
    private MultipleStatusView statusView = new MultipleStatusView(App.getInstance());
    private IconFontTextView ift_return;
    private ImageView img_add;
    private IconFontTextView ift_search;
    private RecyclerView rv_list;

    private String TAG = "GetFriendActivity";

    private List<PhonePerson> personList = new ArrayList<>();
    private GetFriendAdapter adapter;

    //需要申请的权限
    private String[] needContactsPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS
    };

    //查询联系人信息的系统关键字
    private static final String[] PROJECTION = new String[] {
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_friends);

        //进行一些初始化的操作
        initViews();
        statusView.showLoading();
        requestPermission();

        ift_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加用户的操作
                ToastUtil.showToast("你点击了添加用户的操作");
            }
        });

        ift_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("你点击了搜索的操作");
            }
        });
    }

    //初始化的一些操作
    private void initViews() {
        //获取实例
        statusView = findViewById(R.id.friend_multipleStatusView);
        ift_return = findViewById(R.id.friend_return);
        img_add = findViewById(R.id.add_friend);
        ift_search = findViewById(R.id.friend_search);
        rv_list = findViewById(R.id.search_RecyclerView);

        adapter = new GetFriendAdapter(null);
        adapter.isFirstOnly(false);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);

        //statusView = new MultipleStatusView(this);
        statusView.setOnRetryClickListener(listener);
    }

    /**
     * 点击重试监听器
     */
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //点击重试的方法
            onRetry();
        }
    };

    private void requestPermission() {
        if (!mayRequestPermission(needContactsPermissions)) {
            return;
        }
        //执行访问联系人操作
        initLoader();
    }

    @Override
    public void requestPermissionResult(boolean allowPermission) {
        if (allowPermission) {
            //执行操作
            initLoader();
        }
    }

    private void initLoader() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        ToastUtil.showToast("正在读取操作");
        Uri baseUri;
        if (mCurFilter != null) {
            baseUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,
                    Uri.encode(mCurFilter));
        } else {
            baseUri = ContactsContract.Contacts.CONTENT_URI;
        }

        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

        CursorLoader loader = new CursorLoader(this, baseUri,
                PROJECTION, select, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        statusView.showContent();

        if (cursor == null) {
            Log.d(TAG, "系统联系人数据为空");
            ToastUtil.showToast("系统联系人数据为空");
//            statusView.showEmpty();
            return;
        }
        while (cursor.moveToNext()) {
            PhonePerson person = new PhonePerson();
            List<String> numberList = new ArrayList<>();

            //获取联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人的名字
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);

            if (phones != null) {
                while (phones.moveToNext()) {
                    //获取查询结果中电话号码的列表
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    numberList.add(phoneNumber);
                }
                person.setId(contactId);
                person.setUserName(name);
                person.setNumbers(numberList);
                personList.add(person);
            } else {
                person.setId(contactId);
                person.setUserName(name);
//                person.setPhoneNumber("110");
                personList.add(person);
            }
            if (phones != null) phones.close();
        }
        cursor.close();
        if (personList != null) {
            ToastUtil.showToast("数据获取成功");
            adapter.addData(personList);
        } else {
            Log.d(TAG, "获取的数据为空");
            ToastUtil.showToast("联系人数据为空");
            statusView.showEmpty();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //获取失败点击重试的方法
    private void onRetry() {
        //重新调用获取方法
        initLoader();
    }
}
