package smq.study;

import smq.study.anno.Column;

import java.util.Date;

public class Parent {
    private Date createTime;
    private Date updateTime;
    @Column(len = 1)
    private Integer state;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
