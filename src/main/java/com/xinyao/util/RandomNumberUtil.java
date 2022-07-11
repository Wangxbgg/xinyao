package com.xinyao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create by liwutong on 2019/4/12
 * 随机数
 */
public class RandomNumberUtil {
    public static String getRandomNumber() {
        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*100000000L+b;
        String num=String.valueOf(l);
        return num;
    }
    /**
     * 随机生成4位纯数字字符串
     * @return
     */
    public static String getPhoneMessage() {
        int a=(int)((Math.random()*9+1)*100000);
        String num=String.valueOf(a);
        return num;
    }

    /**
     * 随机生成17位纯数字字符串
     * @return
     */
    public static String getNumberSeventeen() {
        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*1000000000L+b;
        String num=String.valueOf(l);
        return num;
    }
    //获取两个不一样的随机整数
    public static List getIntRandom(int val) {
        List list = new ArrayList();
        int i= (int) Math.random()*val;
        list.add(i);
        int j= (int) Math.random()*val;
        if(i == j){
            j= (int) Math.random()*val;
        }
        list.add(j);
        return list;
    }

    /**
     *
     * @param requMin 最小值
     * @param requMax 最大值
     * @param targetLength 获取随机数个数
     * @return
     */
    public static List<Integer> getTwoRandomNum(int requMin, int requMax, int targetLength) {
        if(requMax-requMin < 1){
            System.out.print("最小值和最大值数据有误");
            return null;
        }else if(requMax-requMin <targetLength){
            System.out.print("指定随机个数超过范围");
            return null;
        }
        int target = targetLength;
        List<Integer> list = new ArrayList<>();

        List<Integer> requList = new ArrayList<>();
        for (int i = requMin; i <= requMax; i++) {
            requList.add(i);
        }

        for (int i = 0; i < targetLength; i++) {
            // 取出一个随机角标
            int r = (int) (Math.random() * requList.size());
            list.add(requList.get(r));
            // 移除已经取过的值
            requList.remove(r);
        }

        return list;
    }

    public static void main(String[] args) {
        System.out.println(getRandomNumber());
        System.out.println(getNumberSeventeen());
        System.out.println(getNumberSeventeen().length());
        System.out.println(getPhoneMessage());
    }
}
