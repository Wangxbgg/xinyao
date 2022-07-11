package com.xinyao.filter;

import com.xinyao.util.JWTUtil;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    public boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization == null){
            return false;
        }

        String roles= JWTUtil.getRolesByToken(authorization);
        //Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) mappedValue;
        //如果没有角色限制，直接放行
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String aRolesArray : rolesArray) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (roles!= null && roles.contains(aRolesArray)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //处理跨域问题，跨域的请求首先会发一个options类型的请求
        if (servletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        boolean isAccess = isAccessAllowed(request, response, mappedValue);

        if (isAccess){
            return true;
        }
        servletResponse.setCharacterEncoding("UTF-8");
        //Subject subject = getSubject(request, response);
        PrintWriter printWriter = servletResponse.getWriter();
        servletResponse.setContentType("application/json;charset=UTF-8");
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setHeader("Vary", "Origin");
        String respStr;

        respStr = "you have not right to access";


//        printWriter.write(new Gson().toJson(R.result(ErrorEnum.ERROR_401.getErrorCode(),ErrorEnum.ERROR_401.getErrorMsg(),"")));
        printWriter.flush();
        servletResponse.setHeader("content-Length", respStr.getBytes().length + "");
        return false;
    }
}
