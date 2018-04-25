package com.xh.basic.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author szq
 * @Package com.xh.basic.utils
 * @Description: mybatis generator 代码生成
 * @date 2018/4/2517:16
 */
public class MyBatisGeneratorTool {
    public static void main(String[] args){
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String genCfg = "generatorConfig.xml";
        URL url = MyBatisGeneratorTool.class.getResource(genCfg);
        //File configFile = new File(MyBatisGeneratorTool.class.getResource(genCfg).getFile());
        File configFile = new File(genCfg);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try{
            config = cp.parseConfiguration(configFile);
        }catch (IOException e){
            e.printStackTrace();
        }catch (XMLParserException e){
            e.printStackTrace();
        }

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = null;
        try{
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        }catch(InvalidConfigurationException e){
            e.printStackTrace();
        }

        try{
            myBatisGenerator.generate(null);
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
