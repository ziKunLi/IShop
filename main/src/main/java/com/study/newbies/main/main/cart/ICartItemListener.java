package com.study.newbies.main.main.cart;

/**
 *
 * @author NewBies
 * @date 2018/10/6
 */

public interface ICartItemListener {
    void onItemClick(double itemTotalPrice);
    void onItemSelectChanged(boolean isSelect);
}
