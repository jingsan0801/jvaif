package com.jsan.jvaif.inf.dto;

import com.jsan.jvaif.inf.domain.ScyResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 资源dto
 * @author: jcwang
 * @create: 2019-09-02 16:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ScyResourceDTO extends ScyResource {
    public List<ScyResourceDTO> getList() {
        // 为保证前台获取到数据在没有的时候为[]而不是null
        return list == null ? new ArrayList<>() : list;
    }

    private List<ScyResourceDTO> list;


}
