package com.ddian.youfan.zuul.filter;


import com.ddian.youfan.zuul.prc.admin.MenuService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

public class AccessFilter extends ZuulFilter {
    @Autowired
    MenuService menuService;


    private String ignorePath = "/api-admin/login,/api-admin/logout,/api-gmap-mobile/App/checkAppVersion,/api-gmap-mobile/QL/pay,/api-tracing/file/upload,/api-tracing/file,/api-tracing/app,/api-tracing/traceBack";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }


    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        if (isStartWith(requestUri)) {
            return null;
        }
        String accessToken = request.getHeader(CommonConstants.CONTEXT_TOKEN);
        if (null == accessToken) {
            setFailedRequest(R.error401(), 200);
            return null;
        }
        try {
            UserToken userToken = JwtUtils.getInfoFromToken(accessToken);
        } catch (Exception e) {
            setFailedRequest(R.error401(), 200);
            return null;
        }
        FilterContextHandler.setToken(accessToken);
        if (!havePermission(request)) {
            setFailedRequest(R.error403(), 200);
            return null;
        }
        Set<String> headers = (Set<String>) ctx.get("ignoredHeaders");
        headers.remove("authorization");
        return null;
    }

    private void setFailedRequest(Object body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        HttpServletResponse response = ctx.getResponse();
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JsonUtils.toJson(body));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.setSendZuulResponse(false);
    }

    private boolean havePermission(HttpServletRequest request) {
        String currentURL = request.getRequestURI();
        List<MenuDTO> menuDTOS = menuService.userMenus();
        for (MenuDTO menuDTO : menuDTOS) {
            if (currentURL != null && null != menuDTO.getUrl() && currentURL.startsWith(menuDTO.getUrl())) {
                return true;
            }
        }
        return false;
    }

    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : ignorePath.split(",")) {

            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }
}
