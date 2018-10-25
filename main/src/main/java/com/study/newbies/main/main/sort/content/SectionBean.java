package com.study.newbies.main.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 *
 * @author NewBies
 * @date 2018/9/19
 */

public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    private boolean isMore = false;
    private int id = -1;

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean getIsMore() {

        return isMore;
    }

    public void setIsMore(boolean more) {
        isMore = more;
    }
}
