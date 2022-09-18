package com.liuzhao;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.Set;

/**
 * @ author Liuzhao
 * @ date 2022/9/17
 */
public class Rename {
    static int count = 0;

    static public void rename(String sor, String beginName) {
        File file = new File(sor);
        if (file.exists()) {
            File[] files = file.listFiles();

            assert files != null;
            for (File aFile : files) {
                count++;
                if (!aFile.isDirectory()) {
                    String path = aFile.getAbsolutePath();
                    //进行字符串的拼接 源文件夹路径 + 名称前缀 + 变化数字 + 后缀
                    String tempName = sor + "\\" + beginName + count + path.substring(path.lastIndexOf("."));
                    //如果更名失败加大名称变化重新更名
                    while (!aFile.renameTo(new File(tempName))) {
                        count++;
                        tempName = sor + "\\" + beginName + count + path.substring(path.lastIndexOf("."));
                    }
                }

            }
        }
    }

    static public void rename(String sor) throws Exception {
        File file = new File(sor);
        if (file.exists()) {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/mapper.properties"));
            Set<Object> objects = properties.keySet();
            File[] files = file.listFiles();
            assert files != null;
            for (File aFile : files) {
                if (!aFile.isDirectory()) {
                    String name = aFile.getName();
//                        String tempName = sor + "\\" + count + path.substring(path.lastIndexOf("."));
                    for (Object object : objects) {
                        String object1 = (String) object;
                        if (object1.matches("\\w*"+name.substring(0, name.lastIndexOf(".")) +"\\w*" ) ) {
                            // 源路径名 + \\ + 学号 + 姓名 + 后缀
                            String tempName = sor + "\\" + object1 +properties.getProperty(object1) +  name.substring(name.lastIndexOf("."));
                            System.out.println(tempName);
                            //如果更名失败加大名称变化重新更名
                            if(!aFile.renameTo(new File(tempName))) {
                                return;
                            }
                        }
                    }

                }
            }
        }
    }
}
