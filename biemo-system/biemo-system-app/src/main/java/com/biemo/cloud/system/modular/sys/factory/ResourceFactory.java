package com.biemo.cloud.system.modular.sys.factory;

import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import org.springframework.beans.BeanUtils;

/**
 * 创建resource
 *
 *
 * @date 2019-05-29-14:37
 */
public class ResourceFactory {

    public static SysResource createResource(ResourceDefinition resourceDefinition) {
        SysResource resource = new SysResource();
        BeanUtils.copyProperties(resourceDefinition, resource);
        resource.setResourceId(resourceDefinition.getCode());

        if (resourceDefinition.getMenuFlag()) {
            resource.setMenuFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setMenuFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getRequiredLogin()) {
            resource.setRequiredLoginFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredLoginFlag(YesOrNotEnum.N.name());
        }

        if (resourceDefinition.getRequiredPermission()) {
            resource.setRequiredPermissionFlag(YesOrNotEnum.Y.name());
        } else {
            resource.setRequiredPermissionFlag(YesOrNotEnum.N.name());
        }

        return resource;
    }
}
