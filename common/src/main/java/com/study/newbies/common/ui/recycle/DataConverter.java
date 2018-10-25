package com.study.newbies.common.ui.recycle;

import com.study.newbies.common.util.StringUtil;

import java.util.ArrayList;

/**
 *
 * @author NewBies
 * @date 2018/9/16
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String jsonData = null;

    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.jsonData = json;
        return this;
    }

    protected String getJsonData(){
        if(StringUtil.isNull(this.jsonData)){
            throw new NullPointerException("json 数据为空");
        }
        return this.jsonData;
    }

}
