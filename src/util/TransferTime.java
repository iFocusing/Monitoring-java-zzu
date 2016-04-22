package util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/4/12.
 */
public class TransferTime {
    public Timestamp tansfer(String easyuiTime){
        DateFormat formatFrom = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = formatFrom.parse(easyuiTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = Timestamp.valueOf(formatTo.format(date));
        return timestamp;
    }



}


/*
*  $.fn.datebox.defaults.formatter = function(date){//对于时间格式的转换
           var y = date.getFullYear();
           var m = fullnum(date.getMonth()+1);
           var d = fullnum(date.getDate());
           return y+'-'+m+'-'+d;
       };
       function fullnum(obj){//对于月小于10格式的转换,因为Timestamp转换必须是2013-01-04这种格式
           if(Number(obj) < 10){
               return '0' + obj;
           }else{
               return obj;
           } }*/