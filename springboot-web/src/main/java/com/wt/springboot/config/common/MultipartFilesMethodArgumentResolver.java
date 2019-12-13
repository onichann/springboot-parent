package com.wt.springboot.config.common;

import org.springframework.core.MethodParameter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2019-12-12 下午 6:12
 * PROJECT_NAME gtyzt-sand
 * Instructions @MultipartFiles MultipartFile[] files
 */
public class MultipartFilesMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {

    public MultipartFilesMethodArgumentResolver() {
    }

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        MultipartFiles ann = (MultipartFiles)parameter.getParameterAnnotation(MultipartFiles.class);
        return ann != null ? new MultipartFilesMethodArgumentResolver.MultipartFilesNamedValueInfo(ann) : new MultipartFilesMethodArgumentResolver.MultipartFilesNamedValueInfo();
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Object arg= null;
        MultipartRequest multipartRequest = (MultipartRequest)request.getNativeRequest(MultipartRequest.class);
        if (multipartRequest != null) {
            List<MultipartFile> files = new ArrayList<>();
            MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
            if (!multiFileMap.isEmpty()) {
                for (Map.Entry<String, List<MultipartFile>> listEntry : multiFileMap.entrySet()) {
                    files.addAll(listEntry.getValue());
                }
                arg = files;
            }
        }
        return arg;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MultipartFiles.class) && MultipartResolutionDelegate.isMultipartArgument(parameter);
    }

    private static class MultipartFilesNamedValueInfo extends NamedValueInfo {
        public MultipartFilesNamedValueInfo() {
            super("", false, "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n");
        }

        public MultipartFilesNamedValueInfo(MultipartFiles annotation) {
            super("", false, "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n");
        }
    }

}
