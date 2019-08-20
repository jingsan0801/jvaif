package com.jsan.jvaif.inf.constant;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 存放数据字典的常量
 * @description:
 * @author: Stone
 * @create: 2019-08-19 15:59
 **/
@Getter
@ToString
public enum DicItemEnum {

    /**
     * 有效状态
     */
    status_user_valid("status_user", "1", "有效", 1),

    status_user_invalid("status_user", "0", "无效", 2),

    /**
     * 状态
     */
    status_faYao_yiFaYao("status_faYao", "1", "已发药", 2),

    status_faYao_yiTuiYao("status_faYao", "2", "已退药", 3),

    status_faYao_weiFaYao("status_faYao", "0", "未发药", 1);

    /**
     * @param dicCode 字典编码
     * @return
     */
    static Map<String, String> getItemMap(String dicCode) {
        List<DicItemEnum> rsList = new ArrayList<>();
        for (DicItemEnum itemEnum : DicItemEnum.values()) {
            if (dicCode.equals(itemEnum.dicCode)) {
                rsList.add(itemEnum);
            }
        }
        rsList.sort(Comparator.comparing(DicItemEnum::getOrder));
        return rsList.stream()
            .collect(Collectors.toMap(dicItemEnum -> dicItemEnum.key, dicItemEnum -> dicItemEnum.value));
    }

    /**
     * 字典编码
     */
    private String dicCode;
    /**
     * 字典项key
     */
    private String key;
    /**
     * 字典项value
     */
    private String value;

    /**
     * 排序
     */
    private int order;

    DicItemEnum(String dicCode, String key, String value, int order) {
        this.dicCode = dicCode;
        this.key = key;
        this.value = value;
        this.order = order;
    }
}

