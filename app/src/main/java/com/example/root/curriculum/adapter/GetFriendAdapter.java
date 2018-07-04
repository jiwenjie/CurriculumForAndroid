package com.example.root.curriculum.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

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

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击单个用户显示基本信息情况
                showMessage(item);
            }
        });

    }

    private void showMessage(PhonePerson item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("详细信息");
        String number = "";
        for (int i = 0; i < item.getNumbers().size(); i++) {
            number = item.getNumbers().get(i) + "\n";
        }
        if (!TextUtils.isEmpty(number)) {
            builder.setMessage("用户名为：" + item.getUserName() + "\n"
                    + "默认号码为：" + number);
        } else {
            builder.setMessage("用户名为：" + item.getUserName() + "\n"
                    + "默认号码为：" + item.getNumbers().get(0));
        }

        //拒绝，取消
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.setCancelable(true);
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }
}
