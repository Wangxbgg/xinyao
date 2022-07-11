package com.xinyao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Description:
 * @author: Tony.Zhang
 * @Date: 2017-03-16 14:10
 **/
public class NumberUtils {
    private static final Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    private static DecimalFormat df_2 = new DecimalFormat("0.00");

    /**
     * 从 Obj 中得到 非空的 Double值
     * @param val
     * @return null 返回 0d；其他返回 Double值
     */
    public static Double treatNullOnDouble(Object val) {
        if (val == null) {
            return 0d;
        }
        return new Double(val.toString());
    }

    /**
     * 只有数字才返回对应的String类型，其他的返回 "0"
     * @param obj
     * @return
     */
    public static String treatNotNumber(Object obj){
        return "0";
    }

    /**
     * 将 Obj 转化为 Double 型 或 为 null
     * @param value
     * @return null 返回 null ， 其他返回 Double
     */
    public static Double parseDoubleByString(Object value){
        if (StringUtils.isNullOrBlank(value)) {
            return null;
        }
        return Double.parseDouble(value.toString());
    }

    /**
     * 判断字符串是否是浮点型数字
     * @param value
     * @return
     */
    public static boolean isDouble(Object value){
        if(value == null) {
            return false;
        } else {
            String sValue = value.toString();
            int sz = sValue.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(sValue.charAt(i)) && sValue.charAt(i) != '.') {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNumeric(Object value){
        if (value == null) {
            return true;
        }

        try{
           Double.valueOf(value.toString()).intValue();
           return true;
        }catch(Exception exc){
            return false;
        }
        // return org.apache.commons.lang3.StringUtils.isNumeric(value.toString());
    }

    public static int roundInt(String value) {
        if (StringUtils.isNotNullOrBlank(value)) {
            return roundInt(Double.parseDouble(value.toString()));
        } else {
            return 0;
        }
    }

    public static boolean isNumberOrNull(Object value){
        if (value == null) {
            return true;
        }

        return isDouble(value);
    }

    public static int ceilInt(double value){
        try {
            if(! Double.isInfinite(value)) {
                BigDecimal bg = new BigDecimal(Math.ceil(value)).setScale(0, RoundingMode.HALF_UP);
                return bg.intValue();
            }else{
                return 0;
            }
        }catch(Exception exc){
            logger.warn("值=" + value, exc);

            return 0;
        }

    }

    public static int roundInt(double value){
        try {
            if(! Double.isInfinite(value)) {
                BigDecimal bg = new BigDecimal(value).setScale(0, RoundingMode.HALF_UP);
                return bg.intValue();
            }else{
                return 0;
            }
        }catch(Exception exc){
            logger.warn("值=" + value, exc);

            return 0;
        }

    }

    public static Integer getIntegerValue(Object value){
        if (value == null || value.toString().length() == 0) {
            return null;
        }
        if (value.toString().contains(".")) {
            return Integer.valueOf(value.toString().substring(0, value.toString().indexOf(".")));
        }
        return Integer.valueOf(value.toString());
    }

    public static String format(double value, int precious){
        return String.valueOf(round(value, precious));
    }

    public static String format(Object value){
        if(value == null || value.toString().length() == 0){
            return null;
        }
        return df_2.format(Double.valueOf(value.toString()));
    }

    public static double round(String value, int precious){
        try {
            if(StringUtils.isNullOrBlank(value)) {
                return 0;
            }

            return round(Double.valueOf(value), precious);
        }catch(Exception exc){
            logger.warn("值=" + value + ", 精度=" + precious, exc);

            return 0.0;
        }
    }

    public static double round(double value, int precious){
        try {
            if(! Double.isInfinite(value)) {
                BigDecimal bg = new BigDecimal(value).setScale(precious, RoundingMode.HALF_UP);

                return bg.doubleValue();
            }else{
                return 0.0;
            }
        }catch(Exception exc){
            logger.warn("值=" + value + ", 精度=" + precious, exc);

            return 0.0;
        }
    }

    /**
     * 获取BigDecimal值
     * @param values
     * @return
     */
    public static BigDecimal getBigDecimalValue(String values){
        BigDecimal reValue = new BigDecimal(0.00);
        if(StringUtils.isNullOrBlank(values)){
            return reValue;
        }
        reValue = BigDecimal.valueOf(Double.parseDouble(values));
        return reValue;
    }

    public static BigDecimal getBigDecimalValue(Object values){
        if(values == null){
            return BigDecimal.ZERO;
        }
        return getBigDecimalValue(values.toString());
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1,BigDecimal v2,int scale){
        if(scale<0){
            scale = 0;
        }
        return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2){
        BigDecimal value = new BigDecimal(0.00);
        if( null == v1 || v1.compareTo(value) !=1){
            return value;
        }
        return v1.subtract(v2);
    }


    public static BigDecimal add(Object v1,Object v2){
        return add(getBigDecimalValue(v1),getBigDecimalValue(v2));
    }


    /**
     * 提供加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1,BigDecimal v2){
        BigDecimal value = v2;
        if( null == v1){
            return value;
        }
        return v1.add(v2);
    }


    /**
     * 提供乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2){
        BigDecimal value = new BigDecimal(0.00);
        if( null == v1 || v1.compareTo(value) !=1){
            return value;
        }
        return v1.multiply(v2);
    }

    /**
     * 提供乘法运算。
     * @param o1 被乘数
     * @param o2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(Object o1,Object o2){
        BigDecimal v1 = getBigDecimalValue(o1);
        BigDecimal v2 = getBigDecimalValue(o2);
        return mul(v1, v2);
    }

    private final static String[] STR_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    // 整数单位
    private final static String[] STR_UNIT = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };
    // 小数单位
    private final static String[] STR_UNIT2 = { "角", "分"};

    /**
     * 处理的最大数字达千万亿位 精确到分
     * @return
     */
    public static String digitUppercase(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
            return "零";
        }

        String num = bigDecimal.toString();
        return convert(num);
    }

    /**
     * 获取整数部分
     */
    public static String getInteger(String num) {
        // 判断是否包含小数点
        if (num.indexOf(".") != -1) {
            num = num.substring(0, num.indexOf("."));
        }
        // 反转字符串
        num = new StringBuffer(num).reverse().toString();
        // 创建一个StringBuffer对象
        StringBuffer temp = new StringBuffer();
        // 加入单位
        for (int i = 0; i < num.length(); i++) {
            temp.append(STR_UNIT[i]);
            temp.append(STR_NUMBER[num.charAt(i) - 48]);
        }
        // 反转字符串
        num = temp.reverse().toString();
        // 替换字符串的字符
        num = numReplace(num, "零拾", "零");
        num = numReplace(num, "零佰", "零");
        num = numReplace(num, "零仟", "零");
        num = numReplace(num, "零万", "万");
        num = numReplace(num, "零亿", "亿");
        num = numReplace(num, "零零", "零");
        num = numReplace(num, "亿万", "亿");
        // 如果字符串以零结尾将其除去
        if (num.lastIndexOf("零") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    /**
     * 获取小数部分
     */
    public static String getDecimal(String num) {
        // 判断是否包含小数点
        if (num.indexOf(".") == -1) {
            return "";
        }
        num = num.substring(num.length()-2);
        // 创建一个StringBuffer对象
        StringBuffer temp = new StringBuffer();
        // 加入单位
        for (int i = 0; i < num.length(); i++) {
            temp.append(STR_NUMBER[num.charAt(i) - 48]).append(STR_UNIT2[i]);
        }
        num = temp.toString();
        num = numReplace(num, "零角", "零");
        num = numReplace(num, "零分", "零");
        num = numReplace(num, "零厘", "零");
        num = numReplace(num, "零零", "零");
        // 如果字符串以零结尾将其除去
        if (num.lastIndexOf("零") == num.length() - 1) {
            num = num.substring(0, num.length() - 1);
        }
        return num;
    }

    /**
     * 替换字符串中内容
     */
    public static String numReplace(String num, String oldStr, String newStr) {
        while (true) {
            // 判断字符串中是否包含指定字符
            if (num.indexOf(oldStr) == -1) {
                break;
            }
            // 替换字符串
            num = num.replaceAll(oldStr, newStr);
        }
        // 返回替换后的字符串
        return num;
    }

    /**
     * 金额转换
     */
    public static String convert(String d) {
        // 判断是否包含小数点
        if (d.indexOf(".") != -1) {
            String num = d.substring(0, d.indexOf("."));
            // 整数部分大于12不能转换
            if (num.length() > 12) {
                System.out.println("数字太大，不能完成转换！");
                return "";
            }
        }
        // 小数点
        String point = "";
        if (!"00".equals(d.substring(d.length()-2))) {
            point = "元";
        } else {
            point = "元整";
        }
        // 转换结果
        return getInteger(d) + point + getDecimal(d);
    }

    /**
     *
     * @param o
     * @return
     */
    public static double parseDouble(Object o) {
        if(o == null){
            return 0d;
        }
        return Double.parseDouble(o.toString());
    }

    public static int intValue(String value){
        if(value == null || value.trim().toString().equals("")){
            return 0;
        }

        return Double.valueOf(value).intValue();
    }

    /**
     * 设置Bigdecimal 四舍五入 保留2位小数
     * @param value 需转换值
     * @return 转换后值
     */
    public static BigDecimal setScale(BigDecimal value) {
        if (StringUtils.isNullOrBlank(value)) {
            return new BigDecimal(0);
        }
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
