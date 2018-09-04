package com.ddian.youfan.common.intercepter;

import com.ddian.youfan.common.Constants.CommonConstants;
import com.ddian.youfan.common.context.FilterContextHandler;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignIntercepter implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(CommonConstants.CONTEXT_TOKEN, FilterContextHandler.getToken());
    }
}