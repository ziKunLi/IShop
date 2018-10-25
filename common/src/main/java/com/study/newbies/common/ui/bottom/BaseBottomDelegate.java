package com.study.newbies.common.ui.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.study.newbies.common.R;
import com.study.newbies.common.R2;
import com.study.newbies.common.activity.AppDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 *
 * @author NewBies
 * @date 2018/9/15
 */

public abstract class BaseBottomDelegate extends AppDelegate implements View.OnClickListener, View.OnKeyListener{

    @BindView(R2.id.bottomBar)
    LinearLayoutCompat bottomBar = null;

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATE = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEAN = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int currentDelegate = 0;
    private int indexDelegate = 0;
    private int clickColor = Color.RED;

    private long currentTime = 0;
    private static final int EXIT_TIME = 2000;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder itemBuilder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indexDelegate = setIndexDelegate();
        if(setClickedColor() != 0){
            clickColor = setClickedColor();
        }
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for(Map.Entry<BottomTabBean, BottomItemDelegate> item: ITEMS.entrySet()){
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEAN.add(key);
            ITEM_DELEGATE.add(value);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        //其实并不推荐i < ITEMS.size()这种写法，其实每一次循环都要运行一次ITEMS.size()
//        for(int i = 0; i < ITEMS.size(); i++){
//
//        }
        for(int i = 0; i < size; i++){
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, bottomBar);
            final RelativeLayout item = (RelativeLayout)bottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
            final BottomTabBean bean = TAB_BEAN.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            //如果这个是当前点击到的，那么设置其被点击到
            if(i == indexDelegate){
                itemIcon.setTextColor(clickColor);
            }
        }

        final SupportFragment[] delegateArray = ITEM_DELEGATE.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottomBarDelegateContainer,indexDelegate,  delegateArray);

    }

    private void resetColor(){
        final int count = bottomBar.getChildCount();
        for(int i = 0; i < count; i++){
            final RelativeLayout item = (RelativeLayout) bottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(clickColor);
        final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
        itemTitle.setTextColor(clickColor);
        showHideFragment(ITEM_DELEGATE.get(tag), ITEM_DELEGATE.get(currentDelegate));
        //注意先后顺序
        currentDelegate = tag;
    }

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if(rootView != null){
            //这样设置了才会是OnKeyListener生效，否者一点效果都没有（别问我为什么，我也不知道）
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN){
            if(System.currentTimeMillis() - currentTime > EXIT_TIME ){
                showToast("双击退出");
                currentTime = System.currentTimeMillis();
            }
            else{
                _mActivity.finish();
            }
            return true;
        }
        return false;
    }
}
