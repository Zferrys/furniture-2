package com.furniture.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class Order {
//    CREATE TABLE `order` (
//            `id` VARCHAR (64) PRIMARY KEY,
//  `create_time` DATETIME NOT NULL,
//            `price` DECIMAL (10, 2) NOT NULL,
//  `status` TINYINT NOT NULL, -- 状态0 未发货，1已发货，2已结账
//  `member_id` INT NOT NULL -- 订单属于哪个会员
//) CHARSET utf8 ENGINE innodb;
    private String id;
    private Date createTime;
    private BigDecimal price;
    private Integer status;
    private Integer memberId;
    public Order() {
    }

    public Order(String id, Date createTime, BigDecimal price, Integer status, Integer memberId) {
        this.id = id;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
