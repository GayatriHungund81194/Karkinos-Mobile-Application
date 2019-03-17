package karkinos.gayatri.com.kankinos;

import android.app.Activity;

public class GlobalData extends Activity {


    private static GlobalData textMessage;

    // Global variable
    private String data;

    // Restrict the constructor from being instantiated
    private GlobalData(){}

    public void setData(String d){
        this.data=d;
    }
    public String getData(){
        return this.data;
    }

    public static synchronized GlobalData getInstance(){
        if(textMessage==null){
            textMessage=new GlobalData();
        }
        return textMessage;
    }
}

