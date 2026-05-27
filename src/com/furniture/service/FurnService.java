package com.furniture.service;

import com.furniture.entity.Furn;
import com.furniture.entity.Page;

import java.util.List;

/**
 * @author zph
 * @version 1.0
 */
public interface FurnService {
    public List<Furn> showFurns();
    public int closeFurnById(int id);
    public int addFurn(Furn furn);
    public int updateFurn(Furn furn);
    public Furn getFurnById(int id);
    public Integer getTotalRow();
    public Page<Furn> getPageItems(int begin, int pageSize);
    public Integer getTotalRowByName(String name);
    public Page<Furn> getPageItemsByName(int begin, int pageSize, String name);
    public int updateStock(int furnId, int store);
}
