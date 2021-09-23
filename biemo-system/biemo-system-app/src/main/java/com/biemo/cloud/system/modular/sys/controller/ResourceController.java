package com.biemo.cloud.system.modular.sys.controller;

import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.model.params.ResourceQueryParam;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.system.modular.sys.wrapper.ResourceWrapper;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源管理控制器
 *
 *
 * @Date 2019/9/26 22:51
 */
@Api(tags = "资源访问接口")
@RestController
@ApiResource(name = "资源管理", path = "/resource")
public class ResourceController {

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 获取资源列表
     *
     *
     * @Date 2019/9/26 22:51
     */
    @ApiOperation(value = "获取资源列表", response = SysResource.class)
    @GetResource(name = "获取资源列表", path = "/list")
    public ResponseData resourceList(ResourceQueryParam param) {
        Page result = this.sysResourceService.getResourceList(param);
        List<Map<String, Object>> records = result.getRecords();
        Object warp = new ResourceWrapper(records).wrap();
        result.setRecords((List) warp);
        return ResponseData.success(result);
    }

    /**
     * 获取资源下拉列表
     *
     *
     * @Date 2019/9/26 22:51
     */
    @ApiOperation("获取资源下拉列表")
    @GetResource(name = "获取资源下拉列表", path = "/getResourceSelectList")
    public ResponseData getResourceSelectList(Long appId) {

        List<Map<String, Object>> resourceSelectList = this.sysResourceService.getResourceSelectList(appId);

        //增加返回虚拟菜单的情况
        HashMap<String, Object> map = new HashMap<>();
        map.put("resCode", "");
        map.put("name", "虚拟目录(空)");
        resourceSelectList.add(0, map);

        return ResponseData.success(resourceSelectList);
    }

}
