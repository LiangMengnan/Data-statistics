package Processing.strings;

import save.Save;
import java.io.BufferedReader;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理
 * 利用正则表达式提取目标.txt文件中的数据
 * 所提取的文件放入.txt文档中
 */
public class ProStr {

    /**
     * data processing
     */
    public void datPro(){
        File file;
        BufferedReader in;
        file = new File("D:\\WorkSpace\\Java\\文件\\webData.txt");
        in = Save.openFile(file);
        file = new File("D:\\WorkSpace\\Java\\文件\\sData.txt");
        if (in != null){
            //正则表达式匹配数据
            Pattern dataCompile = Pattern.compile("Date.UTC[(]\\d{4}, \\d{1,2}, \\d{1,2}[)], \\d{1,2}.\\d{3}|name : '\\w++(\\W{0,2})?( \\w++)?( \\W)?(\\w++)?',data");
            String s;
            try {
                System.out.println("正在处理数据......");
                //初始化文件，清楚文件全部内容
                Save.saveStr("",file,false);
                //写入文件
                while ((s = in.readLine())!=null){
                    Matcher dataNeed = dataCompile.matcher(s);
                    while (dataNeed.find()){
                        Save.saveStr(dataNeed.group(),file,true);
                        Save.saveStr("\r\n",file,true);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
