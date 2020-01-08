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
public class ChapterDto {
    private Integer page;//当前页
    private Integer total;//多少页
    private Integer records;//多少行数据
    private List<Chapter> rows;
}
