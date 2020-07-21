package com.multimysql.demo.mapper.store;

import com.multimysql.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsMapper {
    Goods selectOneGoods(Long goodsId);
    List<Goods> selectAllGoods();
}
