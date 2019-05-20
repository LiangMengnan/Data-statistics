package draw;

import data.*;
import save.Save;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 绘图面板
 */
class Panel extends JPanel {
    private int width;
    private int height;
    private int xUnitValue;
    private int yUnitValue;
    private String language;
    private String year;
    private String type = "";
    private String month = "";
    private Data data[][];
    /**
     * 构造方法
     */
    Panel(){
        this.setLayout(null);
    }

    /**
     * 获取以月为单位信息
     * @param language 语言类型
     * @param year 数据年份
     * @param type 绘制类型
     */
    void getMonMassage(String language,String year,String type){
        this.language = language;
        this.year = year;
        this.type = type;
    }

    /**
     * @param language 语言类型
     * @param type 绘制类型
     * @param month 月份
     */
    void getYearMassage(String language, String type, String month){
        this.language = language;
        this.type = type;
        this.month = month;
    }

    /**
     *自由绘制
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.white);
        this.width = this.getWidth();
        this.height = this.getHeight();
        this.xUnitValue = width / 13;
        this.yUnitValue = height / 14;
        if (!type.equals("")){
            g.setColor(Color.black);
            this.drawCoordinates(g);
            Statistics statistics = new Statistics();
            if (type.equals("以月为单位")){
                statistics.getMonMessage(year,type);
            }
            if (type.equals("以年为单位")){
                if (month.equals("")){
                    statistics.getYearMessage(type,"");
                }else{
                    statistics.getYearMessage(type,month);
                }
            }
            statistics.getData();
            data = statistics.getData.get(0);
            if(language.equals("All Language")){
                drawAllData(g);
                title(g);
            }
            else{
                g.setColor(Color.red);
                drawData(g);
                title(g);
            }
        }
    }

    /**
     * 绘制坐标轴
     */
    private void drawCoordinates(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3.0f));
        String s;
        int temp = 1;
        //原点坐标
        final int x0 = width/14;
        final int y0 = height - height/10;
        //x轴
        g2.drawLine(x0, y0, width - x0, y0);
        g2.drawLine(width - x0, y0 , width - x0 - 15, y0 - 10);
        g2.drawLine(width - x0, y0 , width - x0 - 15, y0 + 10);
        if(year!=null && type.equals("以月为单位")){
            g.drawString(language + "语言" + year + "年排名",width - x0 - 80,y0+40);
        }
        if(type.equals("以年为单位")){
            g.drawString("年  份",width - x0 - 15,y0 + 25);
        }
        //y轴
        g2.drawLine(x0 , y0 , x0 , 2 * yUnitValue);
        g2.drawLine(x0, 2 * yUnitValue, x0 - 10, 2 * yUnitValue + 15);
        g2.drawLine(x0, 2 * yUnitValue, x0 + 10, 2 * yUnitValue + 15);
        g.drawString("排",x0-25, 2 * yUnitValue + 10);
        g.drawString("名",x0-25, 2 * yUnitValue + 25);

        if (type.equals("以月为单位")){
            //创建X轴单位线
            for (int i = 0; i < 12; i++) {
                g2.drawLine(x0 + i * xUnitValue, y0, x0 + i * xUnitValue, y0 - 5);
                s = String.valueOf(temp);
                g2.drawString(s,x0 + i * xUnitValue - 5, y0 + 15);
                temp ++;
            }
        }
        if (type.equals("以年为单位")){
            //创建X轴单位线
            int x = 62;
            for (int i = 0; i < 19; i++) {
                g2.drawLine(x0 + i * x, y0, x0 + i * x, y0 - 5);
                s = String.valueOf(2000 + temp);
                g2.drawString(s,x0 + i * x - 15, y0 + 15);
                temp ++;
            }
        }
        //创建Y轴单位线
        for (int i = 1; i < 11; i++) {
            for (int j = x0;j < x0 + 11 * xUnitValue;j = j + 25){
                g2.drawLine(j, y0 - i * yUnitValue, j + 15, y0 - i * yUnitValue);
            }
            temp = 11 - i;
            if (temp>0)
                g2.drawString(String.valueOf(temp),x0-18, y0 - i * yUnitValue+5);
        }
    }

    /**
     * 绘制单个语言信息
     */
    private void drawData(Graphics g) {
        boolean flag1 = true;
        double flag2 = 0;
        int temp0, temp1 = 0;
        int x1 = width / 14;
        g.setFont(new Font("宋体", Font.BOLD, 15));
        if (type.equals("以年为单位")) {
            xUnitValue = 62;
        }
        X:for (temp0 = 0;temp0 < data[0].length; temp0++){
            for (temp1 = 0; temp1 < data.length; temp1++) {
                if (language.equals(data[temp1][temp0].getLanguage()))
                    if (data[temp1][temp0].getData() != 0) {
                        flag2 = data[temp1][temp0].getData();
                        break X;
                    }
            }
            x1 = x1 + xUnitValue;
        }
        int i;
        int y1 = height - height / 10 - (11 - (temp1 + 1)) * yUnitValue;
        int x2 = 0;
        int y2 = 0;
        X:for (int j = temp0 + 1; j < data[0].length; j++) {
            for (i = 0; i < data.length; i++) {
                if (data[i][j].getData() == 0) {
                    x1 += xUnitValue;
                    flag2 = data[i][j].getData();
                    continue X;
                }
                if (language.equals(data[i][j].getLanguage())) {
                    break;
                }
            }
            x2 = x1 + xUnitValue;
            y2 = y1 + (i - temp1) * yUnitValue;
            if (i == data.length){
                i = i - 1;
            }
            if (flag2 != 0){
                g.drawLine(x1,y1,x2,y2);
            }
            x1 = x2;
            y1 = y2;
            temp1 = i;
            flag1 = false;
            flag2 = data[i][j].getData();
        }
        if (!flag1){
            g.drawString(language,x2 + 10 , y2 + 15);
        }
    }

    /**
     * 绘制全部语言信息
     */
    private void drawAllData(Graphics g){
        g.setColor(Color.gray);
        language = "Java";
        drawData(g);
        g.setColor(Color.cyan);
        language = "C";
        drawData(g);
        g.setColor(Color.green);
        language = "C++";
        drawData(g);
        g.setColor(Color.blue);
        language = "Python";
        drawData(g);
        g.setColor(Color.black);
        language = "Visual Basic .NET";
        drawData(g);
        g.setColor(Color.red);
        language = "C#";
        drawData(g);
        g.setColor(Color.magenta);
        language = "JavaScript";
        drawData(g);
        g.setColor(Color.yellow);
        language = "SQL";
        drawData(g);
        g.setColor(Color.pink);
        language = "PHP";
        drawData(g);
        g.setColor(Color.lightGray);
        language = "Assembly language";
        drawData(g);
        language = "All Language";
    }

    /**
     * 保存JPanel图像
     */
    void save(FrameBase parent){
        String name = "";
        if (type.equals("以月为单位")){
            name = year + language + "语言排名.jpg";
        }
        if (type.equals("以年为单位")){
            if (month.equals("")){
                name = "2001年 - 2019年" + language + "语言平均排名.jpg";
            }
            else{
                name = "2001年 - 2019年"+ language + "语言" + month + "月份排名.jpg";
            }
        }
        String path = Save.fileSave(parent,name);
        if (path != null){
            if (!(path.length() - 4 == path.lastIndexOf(".jpg"))){
                path = path + ".jpg";
            }
            try {
                File file = new File(path);
                if (file.exists()){
                    for (int i = 1;i < 1000;i ++){
                        path = path.substring(0,path.length() - 4);
                        path = path + i + ".jpg";
                        System.out.println(path);
                        file = new File(path);
                        if (!file.exists()){
                            break;
                        }
                    }
                }
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                this.paintComponent(graphics);
                graphics.dispose();
                ImageIO.write(image, "jpeg", file);
                System.out.println(file);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 绘制标题
     */
    private void title(Graphics g){
        g.setColor(Color.black);
        String s = "";
        g.setFont(new Font("宋体",Font.BOLD,40));
        if (type.equals("以月为单位")){
            s = year + "年" + language + "语言每月排名";
        }
        if (type.equals("以年为单位")){
            if (month.equals("")){
                s = "2001-2019年"+ language +"语言平均使用率排名";
            }
            else{
                s = "2001-2019年" + month + "月份" + language + "语言使用率排名";
            }
        }
        int strWidth = g.getFontMetrics().stringWidth(s);
        g.drawString(s,width/2 - strWidth/2,yUnitValue * 2 - 20);
    }
}

