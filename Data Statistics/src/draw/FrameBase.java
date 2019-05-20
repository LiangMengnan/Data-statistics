package draw;

import Processing.strings.ProStr;
import download.data.DownloadData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 主窗体
 * 窗体控件
 */
public class FrameBase extends JFrame{
    private JComboBox<String> chooseLanguage;
    private JComboBox<String> chooseYear;
    private JComboBox<String> chooseType;
    private JComboBox<String> chooseDataType;
    private JComboBox<String> chooseMonth;
    private JButton button0;
    private String language;
    private String year;
    private String type;
    private String dataType;
    private String month;

    public static void main(String[] args){
        String website = "https://www.tiobe.com/tiobe-index/";
        String encoding = "UTF-8";
        String route = "D:\\WorkSpace\\Java\\文件\\webData.txt";
        DownloadData downloadData = new DownloadData(website,encoding,route);
        try {
            downloadData.getData();
        }catch (Exception e){
            System.out.println("获取失败，请检查网络和网址。");
            System.exit(0);
        }
        ProStr proStr = new ProStr();
        proStr.datPro();
        new FrameBase();
    }

    /**
     * 构造方法
     */
    private FrameBase(){
        super("数据图表");
        this.setSize(1350,750);
        Panel panel = new Panel();
        paintUI(panel);
        getContentPane().add(panel);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * 添加控件
     * @param panel JPanel
     */
    private void paintUI(Panel panel) {
        //选择年份
        JLabel label0 = new JLabel();
        label0.setForeground(Color.black);
        label0.setText("选择年份");
        label0.setBounds(this.getWidth() - 210, 80, 80, 20);
        label0.setVisible(false);
        panel.add(label0);

        //选择语言
        JLabel label1 = new JLabel();
        label1.setForeground(Color.black);
        label1.setText("选择语言");
        label1.setBounds(this.getWidth() - 210, 32, 80, 20);
        panel.add(label1);

        //选择类型
        JLabel label2 = new JLabel();
        label2.setForeground(Color.black);
        label2.setText("单位类型");
        label2.setBounds(this.getWidth() - 210, 55, 80, 20);
        panel.add(label2);

        //选择数据类型
        JLabel label3 = new JLabel();
        label3.setForeground(Color.black);
        label3.setText("数据类型");
        label3.setBounds(this.getWidth() - 210, 78, 80, 20);
        label3.setVisible(false);
        panel.add(label3);

        //选择月份
        JLabel label4 = new JLabel();
        label4.setForeground(Color.black);
        label4.setText("选择月份");
        label4.setBounds(this.getWidth() - 210, 102, 80, 20);
        label4.setVisible(false);
        panel.add(label4);

        //语言下拉列表框
        String[] Language = {"请选择...", "All Language", "Java", "C", "C++", "Python", "Visual Basic .NET", "C#", "JavaScript", "SQL", "PHP", "Assembly language"};
        chooseLanguage = new JComboBox<>(Language);
        chooseLanguage.setBounds(this.getWidth() - 150, 30, 115, 20);
        chooseLanguage.setMaximumRowCount(5);
        panel.add(chooseLanguage);
        chooseLanguage.addItemListener(event -> {
            button0.setVisible(false);
            this.update(this.getGraphics());
            panel.getYearMassage("", "","");
            panel.repaint();
            if (event.getStateChange() == ItemEvent.SELECTED) {
                language = (String) chooseLanguage.getSelectedItem();
            }
        });

        //类型下拉列表框
        String[] Type = {"请选择...", "以年为单位", "以月为单位"};
        chooseType = new JComboBox<>(Type);
        chooseType.setBounds(this.getWidth() - 150, 55, 115, 20);
        panel.add(chooseType);
        chooseType.addItemListener(event -> {
            button0.setVisible(false);
            this.update(this.getGraphics());
            if (event.getStateChange() == ItemEvent.SELECTED) {
                panel.getYearMassage("", "","");
                panel.repaint();
                type = (String) chooseType.getSelectedItem();
                if (type != null) {
                    if (type.equals("以月为单位")) {
                        label0.setVisible(true);
                        chooseYear.setVisible(true);
                        label4.setVisible(false);
                        chooseMonth.setVisible(false);
                    } else {
                        label0.setVisible(false);
                        chooseYear.setVisible(false);
                    }
                    if (type.equals("以年为单位")){
                        label3.setVisible(true);
                        chooseDataType.setVisible(true);
                    }else{
                        label3.setVisible(false);
                        chooseDataType.setVisible(false);
                    }
                }
                this.update(this.getGraphics());
            }
        });

        //年份下拉列表框
        String[] Year = {"请选择...", "2001年", "2002年", "2003年", "2004年", "2005年", "2006年", "2007年", "2008年", "2009年",
                "2010年", "2011年", "2012年", "2013年", "2014年", "2015年", "2016年", "2017年", "2018年", "2019年"};
        chooseYear = new JComboBox<>(Year);
        chooseYear.setBounds(this.getWidth() - 150, 80, 85, 20);
        chooseYear.setMaximumRowCount(5);
        chooseYear.setVisible(false);
        panel.add(chooseYear);
        chooseYear.addItemListener(event -> {
            button0.setVisible(false);
            this.update(this.getGraphics());
            panel.getMonMassage("", "", "");
            panel.repaint();
            if (event.getStateChange() == ItemEvent.SELECTED) {
                if (chooseYear.getSelectedItem()!= null){
                    year = ((String) chooseYear.getSelectedItem()).substring(0,4);
                }
            }
        });

        //选择月份下拉列表框
        String[] Month = {"请选择...", "1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        chooseMonth = new JComboBox<>(Month);
        chooseMonth.setBounds(this.getWidth() - 150, 105, 85, 20);
        chooseMonth.setMaximumRowCount(5);
        chooseMonth.setVisible(false);
        panel.add(chooseMonth);
        chooseMonth.addItemListener(event -> {
            button0.setVisible(false);
            this.update(this.getGraphics());
            panel.getMonMassage("", "", "");
            panel.repaint();
            if (event.getStateChange() == ItemEvent.SELECTED) {
                if (chooseMonth.getSelectedItem()!= null){
                    month = (String) chooseMonth.getSelectedItem();
                    if (month.length() == 2){
                        month = ((String) chooseMonth.getSelectedItem()).substring(0,1);
                    }
                    if (month.length() ==3){
                        month = ((String) chooseMonth.getSelectedItem()).substring(0,2);
                    }
                }
            }
        });

        //数据类型下拉列表框
        String[] DataType = {"请选择...", "每年平均使用率","每年单月使用率"};
        chooseDataType = new JComboBox<>(DataType);
        chooseDataType.setBounds(this.getWidth() - 150, 80, 115, 18);
        chooseDataType.setMaximumRowCount(5);
        chooseDataType.setVisible(false);
        panel.add(chooseDataType);
        chooseDataType.addItemListener(event -> {
            button0.setVisible(false);
            this.update(this.getGraphics());
            panel.getMonMassage("","","");
            panel.repaint();
            if (event.getStateChange() == ItemEvent.SELECTED) {
                if (chooseDataType.getSelectedItem()!= null){
                    dataType = ((String) chooseDataType.getSelectedItem());
                    if (dataType.equals("每年单月使用率")){
                        label4.setVisible(true);
                        chooseMonth.setVisible(true);
                    }else{
                        label4.setVisible(false);
                        chooseMonth.setVisible(false);
                    }
                }
            }
        });

        //保存按钮
        button0 = new JButton();
        button0.setMargin(new Insets(0,0,0,0));
        button0.setText("保存");
        button0.setBounds(this.getWidth() - 89, 130, 59, 18);
        button0.addActionListener(event -> {
            if ((!type.equals("")) && (!language.equals(""))){
                panel.save(this);
            }
        });
        button0.setVisible(false);
        panel.add(button0);

        //绘制按钮
        JButton button1 = new JButton();
        button1.setText("绘制");
        button1.setMargin(new Insets(0, 0, 0, 0));
        button1.setBounds(this.getWidth() - 150, 130, 59, 18);
        panel.add(button1);
        button1.addActionListener(event -> {
            if (type.equals("以月为单位")) {
                if (year != null && language != null) {
                    button0.setVisible(true);
                    this.update(this.getGraphics());
                    panel.getMonMassage(language,year,type);
                    panel.repaint();
                    System.out.println(language);
                    System.out.println(year);
                }
            }
            if (type.equals("以年为单位")) {
                if (dataType != null){
                    if (language != null && dataType.equals("每年平均使用率")) {
                        button0.setVisible(true);
                        this.update(this.getGraphics());
                        panel.getYearMassage(language,type,"");
                        panel.repaint();
                    }
                    if (language != null && dataType.equals("每年单月使用率")){
                        if (month != null){
                            button0.setVisible(true);
                            this.update(this.getGraphics());
                            panel.getYearMassage(language,type,month);
                            panel.repaint();
                        }
                    }
                }
            }
        });
    }
}