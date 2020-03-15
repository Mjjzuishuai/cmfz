package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Role implements Serializable {
    @Id
    private String id;
    private String roleName;
    private List<Resources> resources;
}
