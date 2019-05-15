package data;

public class Data{

    private String language;
    private double data;
    private String month;

    /**
     * month
     */
    String getMonth() {
        return month;
    }

    void setMonth(String month) {
        this.month = month;
    }

    /**
     *language
     */
    public String getLanguage() {
        return language;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    /**
     *data
     */
    public double getData() {
        return data;
    }

    void setData(double data) {
        this.data = data;
    }
}