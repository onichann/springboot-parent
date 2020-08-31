package com.wt.activiti.configuration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author Administrator
 * @date 2020-06-12 下午 2:20
 * introduction
 */
public class BPSAutoConfigurationImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        boolean bpsClientPresent = ClassUtils.isPresent("com.eos.workflow.api.BPSServiceClientFactory", this.getClass().getClassLoader());
        return bpsClientPresent? new String[]{"com.gisinfo.activiti.configuration.BPSAutoConfiguration"}:new String[0];
    }
}
