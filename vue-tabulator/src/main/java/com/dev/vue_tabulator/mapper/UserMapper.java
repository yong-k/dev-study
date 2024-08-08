package com.dev.vue_tabulator.mapper;

import com.dev.vue_tabulator.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserVo> getAllUser();
}
