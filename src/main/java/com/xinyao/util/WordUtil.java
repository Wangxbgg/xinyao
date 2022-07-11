package com.xinyao.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ZhangFZ
 * @date 2020/9/11 16:40
 **/
public class WordUtil {

    private final Configuration configuration;

    public WordUtil(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public void createWord(Map<String,Object> dataMap, String templateName, String fileName){
        //模板文件所在路径
        configuration.setClassForTemplateLoading(this.getClass(), "/template/voice");
        Template t = null;
        try {
            //获取模板文件
            t = configuration.getTemplate(templateName, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //导出文件
        File outFile = new File(fileName);
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            if (t != null) {
                //将填充数据填入模板文件并输出到目标文件
                t.process(dataMap, out);
            }
            out.close();
        } catch (IOException | TemplateException e1) {
            e1.printStackTrace();
        }
    }
}
