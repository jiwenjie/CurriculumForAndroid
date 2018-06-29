package com.example.root.curriculum.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.R;
import com.example.root.curriculum.bean.PhonePerson;

import java.util.List;

public class GetFriendAdapter extends BaseQuickAdapter<PhonePerson, BaseViewHolder> {

    public GetFriendAdapter(List<PhonePerson> list) {
        super(R.layout.item_friends_part, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PhonePerson item) {
        helper.setText(R.id.friends_title, item.getUserName());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    Log.d("GetFriendAdapter", "=================================================进入Adapter");
//                    Log.d("GetFriendAdapter", "=================================================mingzi " +
//                            "Adapter" + item.getUserName());
//                }
//            }
//        }).start();
    }
}
