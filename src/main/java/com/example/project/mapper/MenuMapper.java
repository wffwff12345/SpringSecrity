package com.example.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(@Param("id") Long id);
}
