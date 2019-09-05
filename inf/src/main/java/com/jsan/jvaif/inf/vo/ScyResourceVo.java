package com.jsan.jvaif.inf.vo;

import com.jsan.jvaif.inf.domain.ScyResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 资源Vo
 * @author: jcwang
 * @create: 2019-09-02 16:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ScyResourceVo extends ScyResource {
    public List<ScyResourceVo> getList() {
        // 为保证前台获取到数据在没有的时候为[]而不是null
        return list == null ? new ArrayList<>() : list;
    }

    private List<ScyResourceVo> list;


}
