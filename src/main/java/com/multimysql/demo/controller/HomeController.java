package com.multimysql.demo.controller;

import com.multimysql.demo.mapper.store.GoodsMapper;
import com.multimysql.demo.mapper.tiku.CategoryMapper;
import com.multimysql.demo.pojo.Category;
import com.multimysql.demo.pojo.Goods;

import javax.annotation.Resource;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.SQLException;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private HikariDataSource storeDataSource;

    @Resource
    private HikariDataSource tikuDataSource;

    //商品详情 参数:商品id
    @GetMapping("/goodsone")
    @ResponseBody
    public String goodsInfo(@RequestParam(value="goodsid",required = true,defaultValue = "0") Long goodsId) throws SQLException {
        System.out.println("------goodsInfo begin");
        Goods goods = goodsMapper.selectOneGoods(goodsId);
        Category category=categoryMapper.selectOneCategory("4");
        return "goods:"+goods.toString()+";category:"+category.toString();
    }

    //显示统计信息
    @GetMapping("/stats")
    @ResponseBody
    public Object stats() throws SQLException {

        Connection connection = storeDataSource.getConnection();
        connection.close();
        HikariPoolMXBean storePool = storeDataSource.getHikariPoolMXBean();
        int active = storePool.getActiveConnections();
        int total = storePool.getTotalConnections();
        int idle = storePool.getIdleConnections();
        int theadsAwaitting = storePool.getThreadsAwaitingConnection();
        int maximumPoolsize = storeDataSource.getMaximumPoolSize();
        int minimumIdle = storeDataSource.getMinimumIdle();
        String poolName = storeDataSource.getPoolName();
        long connTimeout = storeDataSource.getConnectionTimeout();
        long idleTimeout = storeDataSource.getIdleTimeout();
        long maxLifetime = storeDataSource.getMaxLifetime();

        String status = "store pool:<br/>";
        status += "poolName:" + poolName+"<br/>";
        status += "active:" + active+"<br/>";
        status += "total:" + total+"<br/>";
        status += "idle:" + idle+"<br/>";
        status += "theadsAwaitting:" + theadsAwaitting+"<br/>";
        status += "maximumPoolsize:" + maximumPoolsize+"<br/>";
        status += "minimumIdle:" + minimumIdle+"<br/>";
        status += "connTimeout:" + connTimeout+"<br/>";
        status += "idleTimeout:" + idleTimeout+"<br/>";
        status += "maxLifetime:" + maxLifetime+"<br/>";

        Connection tikuConnection = tikuDataSource.getConnection();
        tikuConnection.close();
        HikariPoolMXBean tikuPool = tikuDataSource.getHikariPoolMXBean();
        int active2 = tikuPool.getActiveConnections();
        int total2 = tikuPool.getTotalConnections();
        int idle2 = tikuPool.getIdleConnections();
        int theadsAwaitting2 = tikuPool.getThreadsAwaitingConnection();
        int maximumPoolsize2 = tikuDataSource.getMaximumPoolSize();
        int minimumIdle2 = tikuDataSource.getMinimumIdle();
        String poolName2 = tikuDataSource.getPoolName();
        long connTimeout2 = tikuDataSource.getConnectionTimeout();
        long idleTimeout2 = tikuDataSource.getIdleTimeout();
        long maxLifetime2 = tikuDataSource.getMaxLifetime();

        status += "tiku pool:<br/>";
        status += "poolName:" + poolName2+"<br/>";
        status += "active:" + active2+"<br/>";
        status += "total:" + total2+"<br/>";
        status += "idle:" + idle2+"<br/>";
        status += "theadsAwaitting:" + theadsAwaitting2+"<br/>";
        status += "maximumPoolsize:" + maximumPoolsize2+"<br/>";
        status += "minimumIdle:" + minimumIdle2+"<br/>";
        status += "connTimeout:" + connTimeout2+"<br/>";
        status += "idleTimeout:" + idleTimeout2+"<br/>";
        status += "maxLifetime:" + maxLifetime2+"<br/>";

         return status;
    }

}

