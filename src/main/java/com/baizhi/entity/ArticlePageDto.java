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
public class ArticlePageDto {
    private Integer page;
    private Integer total;//多少页
    private Integer records;//多少行
    private List<Article> rows;
}
