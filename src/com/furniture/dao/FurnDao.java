package com.furniture.dao;

import com.furniture.entity.Furn;

import java.util.List;

/**
 * @author zph
 * @version 1.0
 */
public interface FurnDao {
    public List<Furn> queryFurns();
    public int closeFurnById(int id);
    public int addFurn(Furn furn);
    public int updateFurn(Furn furn);
    public Furn queryFurnById(int id);
    public Integer getTotalRow();
    public List<Furn> queryPageItems(int begin, int pageSize);

    public Integer getTotalRowByName(String name);
    public List<Furn> queryPageItemsByName(int begin, int pageSize, String name);

    public int updateStock(int furnId, int store);

}
