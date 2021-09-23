package com.biemo.cloud.system.modular.sys.wrapper;

import com.biemo.cloud.system.modular.sys.context.QueryContext;
import com.biemo.cloud.core.base.warpper.BaseControllerWrapper;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 菜单列表结果包装
 *
 *
 * @date 2018-02-09 17:25
 */
public class MenuWrapper extends BaseControllerWrapper {

    public MenuWrapper(Map<String, Object> single) {
        super(single);
    }

    public MenuWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public MenuWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public MenuWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("appName", QueryContext.me().getAppNameById((String.valueOf(map.get("appId")))));
        map.put("parentName", QueryContext.me().getMenuNameById(String.valueOf(map.get("pid"))));
        map.put("resName", QueryContext.me().getResourceNameByCode(String.valueOf(map.get("resCode"))));
        map.put("statusName", StatusEnum.getNameByCode((Integer) map.get("status")));
    }
}
