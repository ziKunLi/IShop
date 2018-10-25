package com.study.newbies.main.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.ui.recycle.MultipleItemEntity;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;
import com.study.newbies.main.main.sort.SortDelegate;

import java.util.List;

import butterknife.BindView;

/**
 *
 * @author NewBies
 * @date 2018/9/18
 */

public class VerticalListDelegate extends AppDelegate {

    @BindView(R2.id.rvVerticalMenuList)
    RecyclerView recyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecycleView();
    }

    private void initRecycleView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        recyclerView.setItemAnimator(null);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data= new VerticalListDataConverter().setJsonData(response).convert();
                        final SortDelegate delegate = getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, delegate);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
