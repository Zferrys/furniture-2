package com.furniture.entity;

import java.math.BigDecimal;

/**
 * @author zph
 * @version 1.0
 */
public class Furn {
//    CREATE TABLE `furn` (
//    id INT (10) UNSIGNED NOT NULL PRIMARY KEY auto_increment,
//    `name` VARCHAR (32) NOT NULL DEFAULT "",
//            `market` VARCHAR (32) NOT NULL DEFAULT "",
//            `price` DECIMAL (10, 2),
//    `sales` INT UNSIGNED NOT NULL DEFAULT 0,
//            `store` INT UNSIGNED
//) CHARSET utf8 ENGINE innodb;
//    ALTER TABLE `furn` ADD `img_path` VARCHAR(256) NOT NULL;
    private Integer id;
    private String name;
    private String market;
    private BigDecimal price;
    private Integer sales;
    private Integer store;
    private String imgPath;
    public Furn() {
    }

    public Furn(Integer id, String name, String market, BigDecimal price, Integer sales, Integer store, String imgPath) {
        this.id = id;
        this.name = name;
        this.market = market;
        this.price = price;
        this.sales = sales;
        this.store = store;
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Furn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", market='" + market + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", store=" + store +
                ", img_path='" + imgPath + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
