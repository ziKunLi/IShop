package com.study.newbies.common.emun;

/**
 * 性别枚举
 * @author NewBies
 * @date 2018/8/27
 */

public enum Sex {
    Man(0),
    Woman(1);

    private final int value;

    Sex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
