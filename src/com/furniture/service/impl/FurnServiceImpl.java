package com.furniture.service.impl;

import com.furniture.dao.FurnDao;
import com.furniture.dao.impl.FurnDaoImpl;
import com.furniture.entity.Furn;
import com.furniture.entity.Page;
import com.furniture.service.FurnService;

import java.util.List;

/**
 * @author zph
 * @version 1.0
 */
public class FurnServiceImpl implements FurnService {
    private final FurnDao furnDao = new FurnDaoImpl();

    @Override
    public List<Furn> showFurns() {
        return furnDao.queryFurns();
    }

    @Override
    public int closeFurnById(int id) {
        return furnDao.closeFurnById(id);
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDao.addFurn(furn);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDao.updateFurn(furn);
    }

    @Override
    public Furn getFurnById(int id) {
        return furnDao.queryFurnById(id);
    }

    @Override
    public Integer getTotalRow() {
        return furnDao.getTotalRow();
    }

    @Override
    public Page<Furn> getPageItems(int begin, int pageSize) {
        Page<Furn> page = new Page<>();
        page.setPageNo(begin);
        page.setPageSize(pageSize);
        Integer totalRow = furnDao.getTotalRow();
        page.setTotalRow(totalRow);
        /**
         * public static final int PAGE_SIZE = 2;
         *     private int pageNo;
         *     private int pageSize;
         *     private int PageTotalCount;
         *     private int totalRow;
         *     private List<Furn> items;
         *     private String url;
         */
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount++;
        }
        page.setPageTotalCount(pageTotalCount);
        List<Furn> items = furnDao.queryPageItems((begin - 1) * pageSize, pageSize);
        page.setItems(items);
        return page;
    }

    @Override
    public Integer getTotalRowByName(String name) {
        return furnDao.getTotalRowByName(name);
    }

    @Override
    public Page<Furn> getPageItemsByName(int begin, int pageSize, String name) {
        Page<Furn> page = new Page<>();
        page.setPageNo(begin);
        page.setPageSize(pageSize);
        Integer totalRow = furnDao.getTotalRowByName(name);
        page.setTotalRow(totalRow);
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount++;
        }
        page.setPageTotalCount(pageTotalCount);
        List<Furn> items = furnDao.queryPageItemsByName((begin - 1) * pageSize, pageSize, name);
        page.setItems(items);
        return page;
    }

    @Override
    public int updateStock(int furnId, int store) {
        return furnDao.updateStock(furnId, store);
    }
}
