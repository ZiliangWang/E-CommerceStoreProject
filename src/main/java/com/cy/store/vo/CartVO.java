package com.cy.store.vo;

import java.io.Serializable;
import java.util.Objects;

public class CartVO implements Serializable {
    private Integer cid;
    private Integer pid;
    private Integer uid;
    private Long price;
    private Integer num;
    private String title;
    private String image;
    private Long realPrice;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartVO)) return false;
        CartVO cartVO = (CartVO) o;
        return Objects.equals(getCid(), cartVO.getCid()) && Objects.equals(getPid(), cartVO.getPid()) && Objects.equals(getUid(), cartVO.getUid()) && Objects.equals(getPrice(), cartVO.getPrice()) && Objects.equals(getNum(), cartVO.getNum()) && Objects.equals(getTitle(), cartVO.getTitle()) && Objects.equals(getImage(), cartVO.getImage()) && Objects.equals(getRealPrice(), cartVO.getRealPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCid(), getPid(), getUid(), getPrice(), getNum(), getTitle(), getImage(), getRealPrice());
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "cid=" + cid +
                ", pid=" + pid +
                ", uid=" + uid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", realPrice=" + realPrice +
                '}';
    }
}
