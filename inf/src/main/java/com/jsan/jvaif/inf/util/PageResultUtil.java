package com.jsan.jvaif.inf.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jsan.jvaif.inf.constant.DicItemEnum;
import com.jsan.jvaif.inf.constant.ResultEnum;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.inf.vo.sys.PageResult;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-09-05 18:15
 **/
public class PageResultUtil {

    public static PageResult success(IPage pageObj) {
        PageResult prs = new PageResult();
        prs.setCode(ResultEnum.pub_success.getCode());
        prs.setMsg(ResultEnum.pub_success.getMsg());
        prs.setCount(pageObj.getTotal());
        prs.setData(pageObj.getRecords());
        return prs;
    }
}
