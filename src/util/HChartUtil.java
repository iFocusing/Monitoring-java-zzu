package util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Data;
import bean.DataDisplay;

/**
 * Created by ShiheHuang on 2015/11/9.
 */
public class HChartUtil {
    /**
     * 将tags数组转换为字符串格式
     * TODO 使用json
     * @param tags
     * @return
     */
    public String getCurrentTags(String[] tags){
        String tagsValue="[";
        for(int i = 0; i < tags.length; i++){
            tagsValue += "'" + tags[i] + "',";
        }
        if(tagsValue.endsWith(",")){
            tagsValue = tagsValue.substring(0, tagsValue.length()-1) + "]";
        }else{
            tagsValue += "]";
        }
        return tagsValue;
    }

    /**
     * 用于hcharts历史数据显示
     * @param listList 每一个tag对应一个list，取平均值avg
     * @return
     */
    public String getHistoryData(List<List<DataDisplay>> listList){
        String data = "[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                String wireTemperature = "线表温度(°C)";
                data += "{ name:'" + wireTemperature + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getWireTemperature()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";


                String outTemperature = "室外温度(°C)";
                System.out.println("HChartsUtil"+outTemperature);
                data += "{ name:'" + outTemperature + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getWireTemperature()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String sag = "弧垂(m)";
                data += "{ name:'" + sag + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getSag()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String electricity = "电流(A)";
                data += "{ name:'" + electricity + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getElectricity()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String voltage = "电压(V)";
                data += "{ name:'" + voltage + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getVoltage()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String humidity = "湿度(RH%)";
                data += "{ name:'" + humidity + "', data:[";
                for(DataDisplay piTag : piTagList){
                    data += "" + piTag.getHumidity()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]";
        System.out.println(data);
        return data;
    }

    /**
     * 用户hcharts历史数据显示，因为时间一样，所以只取第一个
     * @param listList
     * @return
     */
    public List<String> getHistoryTimeList(List<List<DataDisplay>> listList) {
        List<String> timeList = new ArrayList<String>();
        if(listList != null && listList.size() > 0){
            for(DataDisplay piTag : listList.get(0)){
                timeList.add(piTag.getSamplingTime().toString());
            }
        }
        return timeList;
    }


    public String getHistoryDataByPids(List<List<DataDisplay>> listList) {
        String data = "[";
        String wireTemperature = "线表温度(°C)";
        data += "{ name:'" + wireTemperature + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getWireTemperature()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";


        String outTemperature = "室外温度(°C)";
        data += "{ name:'" + outTemperature + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getOutTemperature()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";


        String sag = "弧垂(m)";
        data += "{ name:'" + sag + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getSag()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";


        String electricity = "电流(A)";
        data += "{ name:'" + electricity + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getElectricity()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";

        String voltage = "电压(V)";
        data += "{ name:'" + voltage + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getVoltage()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";

        String humidity = "湿度(RH%)";
        data += "{ name:'" + humidity + "', data:[";
        for(List<DataDisplay> piTagList : listList){
            if(piTagList != null && piTagList.size() > 0){
                data += "" +piTagList.get(piTagList.size()-1).getHumidity()+",";
            }

        }
        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]},";






        // 去掉最后的','
        if(data.length() > 0){
            data = data.substring(0, data.length()-1);
        }
        System.out.println(data);
        data=data+"]";
        return data;
    }



    public List<String> getPidList(List<List<DataDisplay>> listList) {
        List<String> pidList=new ArrayList<String>();
        if(listList != null && listList.size() > 0){
            for(List<DataDisplay> dataList : listList ){
                pidList.add(dataList.get(0).getPid()+"");
            }
        }
        return pidList;
    }




    /**
     * 用户hcharts实时数据显示，动态更新数据
     * @param displayList
     * @return
     */
//    public String getCurrentData(Date time, double value){
//        return time == null ? "null" : time.getTime() + "," + value;
//    }

    public String getCurrentData(List<DataDisplay> displayList) {
        String data = "[";
            if(displayList != null && displayList.size() > 0){
                String wireTemperature = "线表温度(°C)";
                data += "{ name:'" + wireTemperature + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getWireTemperature()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String outTemperature = "室外温度(°C)";
                System.out.println("HChartsUtil"+outTemperature);
                data += "{ name:'" + outTemperature + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getWireTemperature()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String sag = "弧垂(m)";
                data += "{ name:'" + sag + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getSag()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String electricity = "电流(A)";
                data += "{ name:'" + electricity + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getElectricity()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String voltage = "电压(V)";
                data += "{ name:'" + voltage + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getVoltage()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

                String humidity = "湿度(RH%)";
                data += "{ name:'" + humidity + "', data:[";
                for(DataDisplay piTag : displayList){
                    data += "" + piTag.getHumidity()+",";
                }
                // 去掉最后的','
                if(data.endsWith(",")){
                    data = data.substring(0, data.length()-1);
                }
                data += "]},";

            }
        // 去掉最后的','
        if(data.endsWith(",")){
            data = data.substring(0, data.length()-1);
        }
        data=data+"]";
        System.out.println("HCharUtil"+data);
        return data;
    }



    //一个点的数据时间list
    public List<String> getCurrentTimeList(List<DataDisplay> displayList) {
        List<String> timeList = new ArrayList<String>();
        if(displayList != null && displayList.size() > 0){
            for(DataDisplay dataDisplay : displayList){
                timeList.add(dataDisplay.getSamplingTime().toString());
            }
        }
        return timeList;
    }
}
