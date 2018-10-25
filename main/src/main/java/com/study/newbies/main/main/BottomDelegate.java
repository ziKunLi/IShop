package com.study.newbies.main.main;

import android.graphics.Color;

import com.study.newbies.common.ui.bottom.BaseBottomDelegate;
import com.study.newbies.common.ui.bottom.BottomItemDelegate;
import com.study.newbies.common.ui.bottom.BottomTabBean;
import com.study.newbies.common.ui.bottom.ItemBuilder;
import com.study.newbies.main.main.cart.ShopCartDelegate;
import com.study.newbies.main.main.disconver.DiscoverDelegate;
import com.study.newbies.main.main.index.IndexDelegate;
import com.study.newbies.main.main.personal.PersonalDelegate;
import com.study.newbies.main.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 *
 * @author NewBies
 * @date 2018/9/15
 */
public class BottomDelegate extends BaseBottomDelegate {

    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder itemBuilder) {
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new PersonalDelegate());
        return itemBuilder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
