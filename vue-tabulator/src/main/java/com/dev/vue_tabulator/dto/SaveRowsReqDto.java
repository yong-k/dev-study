package com.dev.vue_tabulator.dto;

import com.dev.vue_tabulator.vo.UserVo;
import lombok.Data;

import java.util.List;

@Data
public class SaveRowsReqDto {
    private List<UserVo> editedRows;
    private List<String> deletedRows;
}
