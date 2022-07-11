package com.xinyao.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xinyao.bean.common.GlobalConfig;
import com.xinyao.bean.common.TokenBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Jwt常用操作
 * @author ZhangFZ
 */
public class JWTUtil {

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(GlobalConfig.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户登录帐号
     */
    public static String getAccount() {
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户登录帐号
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static Long getUserId(){
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static Long getOrgId(){
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("orgId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getOrgName(){
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("orgName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户ID
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static TokenBean getTokenMsg(){
        TokenBean tokenBean = new TokenBean();
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return tokenBean;
            }
            tokenBean.setAccount(jwt.getClaim("account").asString());
            tokenBean.setRoleList(jwt.getClaim("roleList").asString());
            tokenBean.setUserId(jwt.getClaim("userId").asLong());
            tokenBean.setUserName(jwt.getClaim("userName").asString());
            tokenBean.setOrgSn(jwt.getClaim("orgSn").asString());
            tokenBean.setOrgId(jwt.getClaim("orgId").asLong());
            tokenBean.setOrgName(jwt.getClaim("orgName").asString());
            return tokenBean;

        } catch (JWTDecodeException e) {
            return tokenBean;
        }
    }

    public static String getUsername() {
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取经销商编码
     * @return 经销商编码
     */
    public static String getOrgSn() {
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("orgSn").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    private static DecodedJWT getjwt(){
        RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization == null){
            return null;
        }
        return JWT.decode(authorization);
    }

    /**
     * 通过Token解析出roles
     * @param token token
     * @return 角色信息
     */
    public static String getRolesByToken(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("roleList").asString();
        }catch (JWTDecodeException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取token中的role list
     * @return 角色信息
     */
    public static List<String> getRoleList(){
        try {
            DecodedJWT jwt = getjwt();
            if(jwt != null){
                String roleString = jwt.getClaim("roleList").asString();
                if(!StringUtils.isEmpty(roleString)) {
                    return Arrays.asList(StringUtils.split(roleString, ","));
                }
            }
        } catch (JWTDecodeException ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的是否开启电子用印
     */
    public static Boolean getIsOpenSeal(){
        try {
            DecodedJWT jwt = getjwt();
            if(jwt == null){
                return null;
            }
            return jwt.getClaim("isOpenSeal").asBoolean();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token是否有效
     * @param token token信息
     * @return 返回结果
     */
    public static boolean verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(GlobalConfig.SECRET))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            jwt.getClaims();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 创建token
     * @param tokenBean token保存的信息
     * @return token
     */
    public static String createToken(TokenBean tokenBean) throws UnsupportedEncodingException{
        Algorithm algorithm = Algorithm.HMAC256(GlobalConfig.SECRET);
        return JWT.create()
                .withClaim("userId", tokenBean.getUserId())
                .withClaim("userName", tokenBean.getUserName())
                .withClaim("roleList", tokenBean.getRoleList())
                .withClaim("account", tokenBean.getAccount())
                .withClaim("orgId", tokenBean.getOrgId())
                .withClaim("orgName", tokenBean.getOrgName())
                .withClaim("orgSn", tokenBean.getOrgSn())
                .sign(algorithm);
    }

    public static TokenBean getTokenMsg(String token){
        TokenBean tokenBean = new TokenBean();
        DecodedJWT jwt = JWT.decode(token);
        tokenBean.setAccount(jwt.getClaim("account").asString());
        tokenBean.setRoleList(jwt.getClaim("roleList").asString());
        tokenBean.setUserId(jwt.getClaim("userId").asLong());
        tokenBean.setUserName(jwt.getClaim("userName").asString());
        tokenBean.setOrgSn(jwt.getClaim("orgSn").asString());
        tokenBean.setOrgId(jwt.getClaim("orgId").asLong());
        tokenBean.setOrgName(jwt.getClaim("orgName").asString());
        return tokenBean;
    }
}
