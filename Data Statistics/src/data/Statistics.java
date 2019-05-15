package data;

import save.Save;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Statistics {

    private String compare;
    private String type;
    private String month;
    public List<Data[][]> getData = new ArrayList<>();
    private Data monthData[][] = new Data[10][12];
    private Data yearData[][] = new Data[10][19];

    /**
     * 获取信息
     * <p>
     * //   * @param year 处理数据的年份
     * //   * @param type 处理的类型
     */
    public void getMonMessage(String year, String type) {
        compare = "Date.UTC[(]" + year;
        this.type = type;
    }

    public void getYearMessage(String type, String month) {
        this.month = month;
        this.type = type;
    }
    /**
     * 获取信息
     */
    public void getData() {
        Pattern m;
        if (type.equals("以月为单位")){
            m = Pattern.compile(compare);
            get(m);
            sort(monthData);
        }
        if (type.equals("以年为单位")){
            double sum = 0;
            int temp;
            int p = 0;int q = 0;
            for (int year = 2001; year <=2019;year ++){
                m = Pattern.compile("Date.UTC[(]" + year);
                get(m);
                for (Data[] i : monthData){
                    yearData[p][q] = new Data();
                    yearData[p][q].setLanguage(i[0].getLanguage());
                    if (month.equals("")){
                        for (Data j : i){
                            sum = sum + j.getData();
                        }
                        yearData[p][q].setData(sum/12);
                        sum = 0;
                    }
                    else{
                        temp = Integer.parseInt(month);
                        sum = i[temp - 1].getData();
                        yearData[p][q].setData(sum);
                        sum = 0;
                    }
                    p ++;
                    if (p >= yearData.length){
                        p = 0;
                        q ++;
                    }
                }
            }
            sort(yearData);
        }
//        prints(yearData);
    }

    /**
     * 处理文件数据
     * @param dataNeed year
     */
    private void get(Pattern dataNeed) {
        try {
            String redStr;
            String language;
            Pattern nameNeed = Pattern.compile("'\\w++(\\W{0,2})?( \\w++)?( \\W)?(\\w++)?'");
            Pattern rate = Pattern.compile("\\d+" + "." + "\\d{3}");
            Pattern monthNeed = Pattern.compile("\\s\\d++");
            Matcher matcher1;//匹配数据
            Matcher matcher2;//匹配月份
            Matcher matcher3;//匹配使用率
            Matcher matcher4;//匹配语言名字
            BufferedReader in = Save.openFile(new File("D:\\WorkSpace\\Java\\文件\\sData.txt"));
            redStr = in.readLine();
            String point = "";
            int i = 0;int j = 0;
            boolean flag = true;
            //
            while (redStr != null) {
                if (point.length() > 17 && redStr.length() > 17){
                    if ((point.substring(0,17)).equals(redStr.substring(0,17))){
                        point = redStr;
                        redStr = in.readLine();
                        continue;
                    }
                }
                matcher1 = dataNeed.matcher(redStr);
                if (matcher1.find()) {
                    flag = true;
                    matcher2 = monthNeed.matcher(redStr);
                    //匹配月份 如果月份不相等，continue
                    if (matcher2.find()) {
                        int length = matcher2.group().length();
                        if (!(monthData[i][j].getMonth()).equals(matcher2.group().substring(1, length))){
                            j++;
                            if (j >= monthData[i].length) {
                                j = 0;
                                i++;
                            }
                            continue;
                        }
                    }
                    matcher3 = rate.matcher(redStr);
                    //如果月份匹配，就放入这个月的数据。
                    if (matcher3.find()){
                        monthData[i][j].setData(Double.parseDouble(matcher3.group()));
                        j++;
                        if (j >= monthData[i].length) {
                            j = 0;
                            i ++;
                        }
                    }
                }
                else {
                    matcher4 = nameNeed.matcher(redStr);
                    if (matcher4.find()){
                        int length = matcher4.group().length();
                        language = matcher4.group().substring(1,length - 1);
                        if (!flag){
                            i ++;
                        }
                        //处理类似于2019年的情况
                        if (j != 0){
                            i ++;
                            j = 0;
                        }
                        for (int p = 0;p < monthData[i].length;p ++){
                            monthData[i][p] = new Data();
                            monthData[i][p].setLanguage(language);
                            monthData[i][p].setMonth(String.valueOf(p));
                            monthData[i][p].setData(0);
                        }
                        flag = false;
                    }

                }
                point = redStr;
                redStr = in.readLine();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 排序
     */
    private void sort(Data[][] data) {
        for (int j = 0; j < data[0].length; j++) {
            Data x;
            for (int i = 0; i < data.length - 1; i++) {
                int k = i;
                for (int q = i + 1; q < data.length; q++) {
                    if (data[k][j].getData() < data[q][j].getData())
                        k = q;
                }
                if (k != i) {
                    x = data[k][j];
                    data[k][j] = data[i][j];
                    data[i][j] = x;
                }
            }
        }
        getData.add(data);
        prints(data);
    }

     private void prints (Data[][]data){
         for (Data[] i : data) {
            for (Data j : i) {
                if (j.getLanguage().length() >= 3)
                    System.out.print(j.getLanguage().substring(0, 3) + " ");
                else {
                    if (j.getLanguage().length() == 2)
                        System.out.print(j.getLanguage() + "  ");
                    else
                        System.out.print(j.getLanguage() + "   ");
                }
            }
            System.out.println();
         }

        System.out.print("\n");
        DecimalFormat a = new DecimalFormat("00.000");
        for (Data[] i : data){
            for (Data j : i){
                System.out.print(a.format(j.getData())+"  ");
            }
            System.out.println();
        }
     }
}
