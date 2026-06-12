package com.furniture.entity;

public class Admin {
//    CREATE TABLE admin(
//            id INT UNSIGNED PRIMARY KEY NOT NULL auto_increment,
//            `name` VARCHAR(32) UNIQUE NOT NULL DEFAULT "",
//            `psd` CHAR(32) NOT NULL DEFAULT ""
//     )CHARSET utf8 ENGINE innodb;
    private Integer id;
    private String name;
    private String psd;
    public Admin() {
    }
    public Admin(Integer id, String name, String psd) {
        this.id = id;
        this.name = name;
        this.psd = psd;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }
}
