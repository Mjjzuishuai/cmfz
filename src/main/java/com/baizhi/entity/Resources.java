package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resources implements Serializable {
    @Id
    private String id;
    private String resourcesName;
}
