package save;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * 文件保存、文件打开、txt文档写入
 */
public class Save {

    public static String fileSave(Component parent,String name) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(name));
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file.getAbsolutePath();
        }
        else
            return null;
    }

    /**
     * 打开文件
     * @param file 文件的路径
     * @return 返回 BufferedReader 类型
     */
    public static BufferedReader openFile(File file){
        BufferedReader in = null;
        if (!file.exists()){
            System.out.println("No File!");
            System.exit(0);
        }
        try {
            FileReader reader = new FileReader(file);
            in = new BufferedReader(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return in;
    }

    /**
     * 在.txt中保存字符串
     * @param line 要保存的字符串
     * @param file 保存的文件
     * @param flag 是否追加
     * @throws IOException IOException
     */
    public static void saveStr(String line, File file, boolean flag) throws IOException //追加文件
    {
        try {
            FileOutputStream writeData = new FileOutputStream(file,flag);
            writeData.write(line.getBytes());
        } catch (FileNotFoundException e) {
            System.out.println("写入失败!");
            e.printStackTrace();
            System.exit(0);
        }
    }

}
