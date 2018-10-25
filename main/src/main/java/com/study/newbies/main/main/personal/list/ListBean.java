package com.study.newbies.main.main.personal.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.study.newbies.common.activity.AppDelegate;

import java.util.List;

/**
 *
 * @author NewBies
 * @date 2018/10/8
 */

public class ListBean implements MultiItemEntity{

    private int itemType = 0;
    private String imageUrl = null;
    private String text = null;
    private String value = null;
    private int id = 0;
    private AppDelegate delegate = null;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;

    public ListBean(int itemType, String imageUrl, String text, String value, int id, AppDelegate delegate, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.itemType = itemType;
        this.imageUrl = imageUrl;
        this.text = text;
        this.value = value;
        this.id = id;
        this.delegate = delegate;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        if(text == null){
            return "";
        }
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        if(this.value == null){
            return "";
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(AppDelegate delegate) {
        this.delegate = delegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static final class Builder{
        private int id = 0;
        private int itemType = 0;
        private String imageURl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;
        private AppDelegate delegate = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageURl(String imageURl) {
            this.imageURl = imageURl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public Builder setDelegate(AppDelegate delegate) {
            this.delegate = delegate;
            return this;
        }

        public ListBean build(){
            return new ListBean(itemType, imageURl, text, value, id, delegate, onCheckedChangeListener);
        }
    }
}
