package com.xinyao.util;

/**
 * Created by Tony.Zhang on 2017/2/22.
 */

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:String工具类
 * @author: Tony.Zhang
 * @Date: 2017-02-22 20:13
 **/
public class StringUtils {
    public static String null2String(Object value){
        return value == null ? "" : value.toString();
    }

    public static String trim(String var){
        if(var == null) return null;

        return var.trim();
    }



    public static BigDecimal getBigDecimalValue(String values){
        BigDecimal reValue = new BigDecimal(0.00);
        if(StringUtils.isNullOrBlank(values)){
            return reValue;
        }
        reValue = BigDecimal.valueOf(Double.parseDouble(values));
        return reValue;
    }

    public static String getMapKeys(Map<String, String> map, String val, String regex) {
        String keys = "";
        boolean b = false;
        String[] str = val.split(regex);
        if (str.length > 0) {
            String[] var9 = str;
            int var8 = str.length;
            for(int var7 = 0; var7 < var8; ++var7) {
                String value = var9[var7];
                Iterator var11 = map.entrySet().iterator();
                while(var11.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry)var11.next();
                    if (value.equals(entry.getValue())) {
                        if (b) {
                            keys = keys + ",";
                        }
                        keys = keys + (String)entry.getKey();
                        b = true;
                    }
                }
            }
        }
        return keys;
    }

    /**
     * @author liwutong
     * 字符串补位
     * @param w 补位数
     * @param v 补位值
     * @return
     */
    public static String stringComplementNumber(Integer w, Integer v){
        return  String.format("%"+w+"d", v).replace(" ", "0");
    }

    /**
     * 校验对应字符串为哪种格式
     */
    public static String isFormat(String value){
        String result = "";

        if(StringUtils.isPhone(value)){
            result = "phone";
        }else if(StringUtils.isIdcard(value)){
            result = "idcard";
        }else if(StringUtils.isEmail(value)){
            result = "email";
        }
        return result;
    }

    /**
     * 验证是否为有效手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 验证是否为有效邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isMatch = m.matches();
        return isMatch;
    }

    /**
     * 验证是否为有效身份证号
     * @param idcard
     * @return
     */
    public static boolean isIdcard(String idcard){
        String regex = "\\d{15}(\\d{2}[0-9xX])?";
        if (idcard.length() != 18) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(idcard);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 字符串相等匹配（区分大小写）
     * @param val1
     * @param val2
     * @return
     */
    public static boolean equals(String val1, String val2){
        if(isNullOrBlank(val1) && isNullOrBlank(val2)){
            return true;
        }

        if(isNullOrBlank(val1) || isNullOrBlank(val2)){
            return false;
        }

        return val1.trim().equals(val2.trim());
    }

    /**
     * 字符串相等匹配（不区分大小写）
     * @param val1
     * @param val2
     * @return
     */
    public static boolean equalsIgnoreCase(String val1, String val2){
        if(isNullOrBlank(val1) && isNullOrBlank(val2)){
            return true;
        }

        if(isNullOrBlank(val1) || isNullOrBlank(val2)){
            return false;
        }

        return val1.trim().equalsIgnoreCase(val2.trim());
    }

    public static boolean isNotNullOrBlank(Object value){
        if(value == null) return false;

        return ! (value.toString().trim().equals("") || value.toString().trim().equalsIgnoreCase("null"));
    }

    /**
     * 非空判断若为空返回true
     * @param value
     * @return
     */
    public static boolean isNullOrBlank(Object value){
        if(value == null) return true;

        return value.toString().trim().equals("") || value.toString().trim().equalsIgnoreCase("null") || value.toString().trim().equals("undefined");
    }

    /**
     * 转换空字符串为null
     * @param value
     * @return
     */
    public static String convertNull(String value) {
        return StringUtils.isNullOrBlank(value)?null: value;
    }

    /**
     * 非空或者非法参数返回true
     * 此方法用于一体机保存接口
     * @param value
     * @return
     */
    public static boolean isNullOrBlankOrIllegal(Object value){
        if(value == null) return true;

        return value.toString().trim().equals("") || value.toString().trim().equals("+") || value.toString().trim().equals("-") || value.toString().trim().equalsIgnoreCase("null") || value.toString().trim().equals("undefined");
    }

    /**
     * 是否是整数数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(isNullOrBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 是否是正数数字（不带加号）
     * @param str
     * @return
     */
    public static boolean isPositiveNumber(String str) {
        if (isNullOrBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+.?[0-9]*");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 动态检测参数是否为空
     * @param param
     * @return
     */
    public static boolean validataStrIsNull(String ...param){
        Integer nullNum=0;
        for(int i=0;i<param.length;i++){
            if (null==param[i] || ("").equals(param[i]) || ("null").equalsIgnoreCase(param[i])
                    || param[i].trim().isEmpty()){
                nullNum = nullNum +1;
            }
        }

        if (nullNum ==param.length){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 货币统一格式化入口
     * @param money
     * @return
     */
    public static String currencyFormat(Float money){
        return  new DecimalFormat("###,###,###.##").format(money );
    }

    /**
     * 格式化 Float数据
     * @param scale
     * @param roundingMode
     * @param ft
     * @return
     */
    public static float formatFloat(int scale,int roundingMode,Float ft){
        BigDecimal   bd  =   new BigDecimal((double)ft);
        bd   =  bd.setScale(scale,roundingMode);
        ft   =  bd.floatValue();
        return ft;
    }

    public static List<String> tokenize(String value, String seperator){
        List<String> values = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(value, seperator);
        while(st.hasMoreElements()){
            values.add(st.nextElement().toString());
        }

        return values;
    }


    public static String lpad(String value, String c, int length) {
        StringBuffer sb = null;
        if (value == null) {
            sb = new StringBuffer("1");
        } else {
            sb = new StringBuffer(value);
        }

        while (sb.length() <= length) {
            sb.insert(0, c);
        }

        return sb.toString();
    }

    /**
     * 对象转为字符串类型
     * @param o
     * @return
     */
    public static String valueOf(Object o) {
        return o == null ? null : o.toString();
    }

}
