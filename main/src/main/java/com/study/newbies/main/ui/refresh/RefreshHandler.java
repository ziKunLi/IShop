package com.study.newbies.main.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.study.newbies.common.app.ThisApp;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.common.ui.page.PagingBean;
import com.study.newbies.common.ui.recycle.DataConverter;
import com.study.newbies.common.ui.recycle.MultipleRecyclerAdapter;
import com.study.newbies.common.util.LogUtil;

/**
 *
 * @author NewBies
 * @date 2018/9/16
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyclerAdapter adapter = null;
    private final DataConverter CONVERTER;

    public RefreshHandler(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, DataConverter converter, PagingBean pagingBean) {
        this.REFRESH_LAYOUT = refreshLayout;
        this.BEAN = pagingBean;
        this.RECYCLEVIEW = recyclerView;
        this.CONVERTER = converter;
        this.REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, DataConverter converter){
        return new RefreshHandler(refreshLayout, recyclerView, converter, new PagingBean());
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        //设置两秒的延迟来模拟网络请求
        ThisApp.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url){
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtil.v("tag", "请求成功 ： " + response);
                        final JSONObject object = JSON.parseObject(response);
                        Toast.makeText(ThisApp.getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        adapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        adapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLEVIEW);
                        RECYCLEVIEW.setAdapter(adapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    /**
     * 下拉
     */
    @Override
    public void onLoadMoreRequested() {

    }
}
