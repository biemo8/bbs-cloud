package com.biemo.cloud.system.modular.sys.wrapper;

import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.core.base.warpper.BaseControllerWrapper;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 资源列表结果包装
 *
 *
 * @date 2018-02-09 17:25
 */
public class ResourceWrapper extends BaseControllerWrapper {

    public ResourceWrapper(Map<String, Object> single) {
        super(single);
    }

    public ResourceWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ResourceWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ResourceWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Object menuFlag = map.get("menuFlag");
        if (menuFlag != null) {
            if (YesOrNotEnum.Y.name().equalsIgnoreCase(menuFlag.toString())) {
                map.put("menuFlagName", YesOrNotEnum.Y.getDesc());
            } else {
                map.put("menuFlagName", YesOrNotEnum.N.getDesc());
            }
        }

        Object requiredLoginFlag = map.get("requiredLoginFlag");
        if (requiredLoginFlag != null) {
            if (YesOrNotEnum.Y.name().equalsIgnoreCase(requiredLoginFlag.toString())) {
                map.put("requiredLoginFlagName", YesOrNotEnum.Y.getDesc());
            } else {
                map.put("requiredLoginFlagName", YesOrNotEnum.N.getDesc());
            }
        }

        Object requiredPermissionFlag = map.get("requiredPermissionFlag");
        if (requiredLoginFlag != null) {
            if (YesOrNotEnum.Y.name().equalsIgnoreCase(requiredPermissionFlag.toString())) {
                map.put("requiredPermissionFlagName", YesOrNotEnum.Y.getDesc());
            } else {
                map.put("requiredPermissionFlagName", YesOrNotEnum.N.getDesc());
            }
        }

        String modularName = (String) map.get("modularName");
        String modularCode = (String) map.get("modularCode");
        if (StrUtil.isNotBlank(modularName) && StrUtil.isNotBlank(modularCode)) {
            map.put("modular", "" + modularCode + "(" + modularName + ")");
        }
    }

}
