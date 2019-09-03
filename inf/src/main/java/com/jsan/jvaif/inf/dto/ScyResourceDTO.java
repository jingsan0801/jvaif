package com.jsan.jvaif.inf.dto;

import com.jsan.jvaif.inf.domain.ScyResource;
import lombok.*;

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
    private List<ScyResourceDTO> sub;

}
