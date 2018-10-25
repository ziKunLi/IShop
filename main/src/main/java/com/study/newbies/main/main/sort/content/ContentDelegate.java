package com.study.newbies.main.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.study.newbies.common.activity.AppDelegate;
import com.study.newbies.common.net.RestClient;
import com.study.newbies.common.net.callback.ISuccess;
import com.study.newbies.main.R;
import com.study.newbies.main.R2;

import java.util.List;

import butterknife.BindView;

/**
 *
 * @author NewBies
 * @date 2018/9/18
 */

public class ContentDelegate extends AppDelegate {

    @BindView(R2.id.rvListContent)
    RecyclerView recyclerView;

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int contentId = -1;
    private List<SectionBean> data = null;

    public static ContentDelegate newInstance(int contentId){
        final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        final ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args != null){
            contentId = args.getInt(ARG_CONTENT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //瀑布流
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initData(){
        RestClient.builder()
                .url("sortContentList")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        data = new SectionDataConverter().convert(response);
                        final SectionAdapter sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.item_section_header, data);
                        recyclerView.setAdapter(sectionAdapter);
                    }
                })
                .build()
                .get();
    }
}
