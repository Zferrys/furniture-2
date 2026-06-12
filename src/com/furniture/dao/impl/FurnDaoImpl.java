package com.furniture.dao.impl;

import com.furniture.dao.BasicDAO;
import com.furniture.dao.FurnDao;
import com.furniture.entity.Furn;

import java.util.List;

/**
 * @author zph
 * @version 1.0
 */
public class FurnDaoImpl extends BasicDAO<Furn> implements FurnDao {
    @Override
    public List<Furn> queryFurns() {
        String sql = "SELECT id, name,market, price, sales, store, img_path imgPath FROM furn";
        return queryMulti(sql, Furn.class);
    }

    @Override
    public int closeFurnById(int id) {
        String sql = "DELETE FROM furn WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn(name, market, price, sales, store, img_path) VALUES(?, ?, ?, ?, ?, ?)";
        return update(sql, furn.getName(), furn.getMarket(), furn.getPrice(), furn.getSales(), furn.getStore(), furn.getImgPath());
    }

    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATE furn SET name = ?, market = ?, price = ?, sales = ?, store = ?, img_path = ? WHERE id = ?";
        return update(sql, furn.getName(), furn.getMarket(), furn.getPrice(), furn.getSales(), furn.getStore(), furn.getImgPath(), furn.getId());
    }

    @Override
    public Furn queryFurnById(int id) {
        String sql = "SELECT id, name, market, price, sales, store, img_path imgPath FROM furn WHERE id = ?";
        return querySingle(sql, Furn.class, id);
    }

    @Override
    public Integer getTotalRow() {
        String sql = "SELECT COUNT(*) FROM furn";
        return ((Number)queryScalar(sql)).intValue();
    }

    @Override
    public List<Furn> queryPageItems(int begin, int pageSize) {
        String sql = "SELECT id, name, market, price, sales, store, img_path imgPath FROM furn LIMIT ?, ?";
        return queryMulti(sql, Furn.class, begin, pageSize);
    }

    @Override
    public Integer getTotalRowByName(String name) {
        String sql = "SELECT COUNT(*) FROM furn WHERE name LIKE ?";
        return ((Number)queryScalar(sql, "%" + name + "%")).intValue();
    }

    @Override
    public List<Furn> queryPageItemsByName(int begin, int pageSize, String name) {
        String sql = "SELECT id, name, market, price, sales, store, img_path imgPath FROM furn WHERE name LIKE ? LIMIT ?, ?";
        return queryMulti(sql, Furn.class, "%" + name + "%", begin, pageSize);
    }

    @Override
    public int updateStock(int furnId, int store) {
        String sql = "UPDATE furn SET store = ? WHERE id = ?";
        return update(sql, store, furnId);
    }

    @Override
    public int updateStockAtomic(int furnId, int salesIncrement, int storeDecrement) {
        String sql = "UPDATE furn SET sales = sales + ?, store = store - ? WHERE id = ? AND store >= ?";
        return update(sql, salesIncrement, storeDecrement, furnId, storeDecrement);
    }
}
