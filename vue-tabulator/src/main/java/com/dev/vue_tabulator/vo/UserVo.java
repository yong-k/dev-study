package com.dev.vue_tabulator.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo {
    private String id;
    private String name;
    private Date date;
    private Integer quantity;
    private Integer price;
}
