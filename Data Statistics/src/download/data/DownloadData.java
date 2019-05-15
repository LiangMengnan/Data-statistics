package download.data;

import save.Save;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用于下载目标网址的后台数据
 * 这些数据会被放入指定的路径
 * 保存在.txt文本文档中
 */
public class DownloadData {
    //网址
    private String website;
    //字节码
    private String encoding;
    //路径
    private String route;

    public DownloadData(String website, String encoding,String route) {
        this.website = website;
        this.encoding = encoding;
        this.route = route;
    }

    public void getData() throws Exception {
        System.out.println("提取网站后台数据中......");
        String str;
        File file = new File(route);
        // 根据链接（字符串格式），生成一个URL对象
        URL url = new URL(website);
        // 打开URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        //打开流
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),encoding));
        //文件初始化
        Save.saveStr("",file,false);
        //开始保存
        while ((str = reader.readLine())!= null) {
            Save.saveStr(str,file,true);
            Save.saveStr("\r\n",file,true);
        }
    }

}