package com.roger.core.db;

import org.springframework.util.StringUtils;

public class DynamicOgnlExpressionValidate {

    /**
     * 非空校验
     * <p>
     * 使用方法：
     *
     * <if test="name !=null and  name != ''">
     *
     * </if>
     * 等价于
     * <if test="@com.roger.core.db.DynamicOgnlExpressionValidate@.isNotEmpty()">
     *
     * </if>
     *
     * @param source
     * @return
     */
    public static boolean isNotEmpty(String source) {
        return StringUtils.hasText(source);
    }

}
