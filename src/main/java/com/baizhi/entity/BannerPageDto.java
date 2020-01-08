package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class BannerPageDto {
    private Integer page;//当前页
    private Integer total;//总页数
    private Integer records;//总行数
    private List<Banner> rows;//分页查询结果
}
