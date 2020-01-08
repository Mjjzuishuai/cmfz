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
public class AlbumPageDto {
    private Integer page;//当前页
    private Integer total;//多少页
    private Integer records;//多少行
    private List<Album> rows;//分页查询结果
}
