package com.study.newbies.common.callback;

import android.view.View;

/**
 *  对于一些特殊的组件，比如Spanner可能有子组件被选中，所以这个多了一个子组件被选中的回调方法
 * @author NewBies
 * @date 2018/8/27
 */
public interface ItemSelectListener extends ItemClickListener{
    void onItemSelected(View v, int positionX, int positionY, int index);
}
