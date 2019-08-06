package com.roger.biz.util;

import com.roger.core.utils.FieldReflectUtil;
import com.roger.core.utils.PersistentUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

public class ReqParamToOBjUtil {

    //请求参数在Url地址里面必须和实体对象的属性名称一致
    public void convertToObj(HttpServletRequest request, Object obj) {
        List<Field> fieldList = PersistentUtil.getPersistentFields(obj.getClass());
        for (Field field : fieldList) {
            Object fieldValue = request.getParameter(field.getName());
            FieldReflectUtil.setFieldValue(obj,field,fieldValue);
        }
    }
}
