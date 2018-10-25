package com.study.newbies.common.ui;

import com.google.auto.value.AutoValue;

/**
 *
 * @author NewBies
 * @date 2018/9/18
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue){
        return  new AutoValue_RgbValue(red, green, blue);
    }
}
