package com.study.newbies.main.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.util.callback.CallbackManager;
import com.study.newbies.common.util.callback.CallbackType;
import com.study.newbies.common.util.callback.IGlobalCallback;
import com.study.newbies.common.view.AutoPhotoLayout;
import com.study.newbies.common.view.StarLayout;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author NewBies
 */
public class OrderCommentDelegate extends AppDelegate {

    @BindView(R2.id.customStarLayout)
    StarLayout starLayout = null;
    @BindView(R2.id.customAutoPhotoLayout)
    AutoPhotoLayout autoPhotoLayout = null;

    @OnClick(R2.id.topTvCommentCommit)
    void onClickSubmit() {
        Toast.makeText(getContext(), "评分： " + starLayout.getStarCount(), Toast.LENGTH_LONG).show();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        autoPhotoLayout.setDelegate(this);
//        CallbackManager.getInstance()
//                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
//                    @Override
//                    public void executeCallback(@Nullable Uri args) {
//                        autoPhotoLayout.onCropTarget(args);
//                    }
//                });
    }
}
