package com.tech.jvm;

import com.tech.tomcat.chapter2.Constants;

import java.io.File;
import java.net.URL;

public class SystemPath {

    /**
     * 获取当前工程路径
     * path1:D:\myCode\tech-fearless\ssm-source
     */
    public void path1(){
        String path = System.getProperty("user.dir");
        System.out.println("path1:" + path);
    }

    /**
     * 获取当前类的所在工程路径;
     * path2:/D:/myCode/tech-fearless/ssm-source/target/test-classes/
     */
    public void path2(){
        String path = this.getClass().getResource("/").getPath();
        System.out.println("path2:" + path);
    }

    /**
     * 获取当前类的绝对路径；
     * path3:/D:/myCode/tech-fearless/ssm-source/target/test-classes/com/tech/jvm/
     */
    public void path3(){
        String path = this.getClass().getResource("").getPath();
        System.out.println("path3:" + path);
    }

    public void path4(){
        try {
            String path = Constants.WEB_ROOT;
            File classPath = new File(Constants.WEB_ROOT);
            System.out.println("classPath:"+classPath+"\n");
            String host = classPath.getCanonicalPath() + File.separator;
            System.out.println("host:" + host+"\n");
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            System.out.println("repository:" + repository+"\n");
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
