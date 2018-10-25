package com.study.newbies.main.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.common.ui.recycle.BaseDecoration;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;
import com.study.newbies.main.main.BottomDelegate;
import com.study.newbies.main.main.index.search.SearchDelegate;
import com.study.newbies.main.ui.refresh.RefreshHandler;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author NewBies
 * @date 2018/9/15
 */

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener{

    @BindView(R2.id.rvIndex)
    RecyclerView rvIndex = null;
    @BindView(R2.id.srlIndex)
    SwipeRefreshLayout refreshLayout = null;
    @BindView(R2.id.tbIndex)
    Toolbar toolbar = null;
    @BindView(R2.id.iconIndexScan)
    IconTextView iconIndexScan = null;
    @BindView(R2.id.etSearchView)
    AppCompatEditText searchView = null;
    @BindView(R2.id.iconIndexMessage)
    IconTextView iconIndexMessage = null;

    private RefreshHandler handler = null;

    @OnClick(R2.id.etSearchView)
    public void onSearchViewClick(){
        getParentDelegate().getSupportDelegate().start(new SearchDelegate());
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        handler = RefreshHandler.create(refreshLayout, rvIndex, new IndexDataConverter());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        handler.firstPage("index");
    }

    private void initRefreshLayout(){
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        //设置为true的话，下拉的时候球会由小变大，回去的时候会由大变小
        //第二个参数是起始高度，第三个参数是最终高度
        refreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        rvIndex.setLayoutManager(manager);
        rvIndex.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        //得到父级元素
        final BottomDelegate bottomDelegate = getParentDelegate();
        rvIndex.addOnItemTouchListener(IndexItemClickListener.create(bottomDelegate));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentDelegate().getSupportDelegate().start(new SearchDelegate());
        }
    }
}
