package com.multimysql.demo.mapper.tiku;

import com.multimysql.demo.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CategoryMapper {
    Category selectOneCategory(String Category_id);
}
