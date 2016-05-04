package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DataDisplay;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
/**
 * Created by huojingjing on 16/5/4.
 */
public class ExportExcelUtil {

        public File createExcelFile() throws IOException, URISyntaxException {
            String path = ExportExcelUtil.class.getResource("/").toURI().getPath();
            String fileName = new File(path).getParentFile().getParentFile().getCanonicalPath();
            File upload = new File(fileName + "\\upload");
            if (!upload.exists()) {
                upload.mkdir();
            }
            fileName += "\\upload\\" + UUID.randomUUID().toString().replace("-", "") + ".xls";
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            System.out.println(file.getAbsolutePath());
            return file;
        }


        public void exportDataToExcel(File file, List<String> lList, List<List<DataDisplay>> allPiList) throws IOException, WriteException, ParseException {
            System.out.println("file:" + file.getAbsolutePath());
            OutputStream os = new FileOutputStream(file);
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            for (int i = 0; i < lList.size(); i++) {
                List<DataDisplay> piList = allPiList.get(i);
                WritableSheet writableSheet = wwb.createSheet("ʵ����" + lList.get(i), i);


                Label pidTitle = new Label(1, 0, "线杆编号");
                Label samplingTimeTitle = new Label(2, 0, "收集时间");
                Label outTemperatureTitle = new Label(3, 0, "室外温度");
                Label wireTemperatureTitle = new Label(4, 0, "线表温度");
                Label sagTitle = new Label(5, 0, "弧垂 ");
                Label electricityTitle = new Label(6, 0, "电流");
                Label voltageTitle = new Label(7, 0, "电压");
                Label humidityTitle = new Label(8, 0, "湿度");
                Label nidTitle = new Label(9, 0, "节点序号");
                Label locationTitle = new Label(10, 0, "线杆位置");
                Label nameTitle = new Label(11, 0, "线路名称");
                Label sourceTitle = new Label(12, 0, "节点组属地址");

                writableSheet.addCell(pidTitle);
                writableSheet.addCell(samplingTimeTitle);
                writableSheet.addCell(outTemperatureTitle);
                writableSheet.addCell(wireTemperatureTitle);
                writableSheet.addCell(sagTitle);
                writableSheet.addCell(electricityTitle);
                writableSheet.addCell(voltageTitle);
                writableSheet.addCell(humidityTitle);
                writableSheet.addCell(nidTitle);
                writableSheet.addCell(locationTitle);
                writableSheet.addCell(nameTitle);
                writableSheet.addCell(sourceTitle);
                NumberFormat valueFormat = new NumberFormat("#.####");
                WritableCellFormat valueCellFormat = new WritableCellFormat(valueFormat);
                // DateUtil dateUtil = new DateUtil();
                //  DateFormat dateFormat = new DateFormat("yyyy-MM-dd HH");
                // WritableCellFormat timeCellFormat = new WritableCellFormat(dateFormat);
                for (int j = 0; j < piList.size(); j++) {
                    DataDisplay piTag = piList.get(j);
                    int r = j + 1;
                    //System.out.println(r);
                    // ���
                    Number number = new Number(0, r, r);
                    Label pid = new Label(1, r, piTag.getPid()+"");
                    Label samplingTime = new Label(2, r, piTag.getSamplingTime());
                    Number outTemperature = new Number(3, r, piTag.getOutTemperature(), valueCellFormat);
                    Number wireTemperature = new Number(4, r, piTag.getWireTemperature(), valueCellFormat);
                    Number sag = new Number(5, r, piTag.getSag(), valueCellFormat);
                    Number electricity = new Number(6, r, piTag.getElectricity(), valueCellFormat);
                    Number voltage = new Number(7, r, piTag.getVoltage(), valueCellFormat);
                    Number humidity = new Number(8, r, piTag.getHumidity(), valueCellFormat);
                    Number nid = new Number(9, r, piTag.getNid(), valueCellFormat);
                    Number location = new Number(10, r, piTag.getLocation(), valueCellFormat);
                    Label name = new Label(11, r, piTag.getName());
                    Label source = new Label(12, r, piTag.getSource());

                    writableSheet.addCell(number);
                    writableSheet.addCell(pid);
                    writableSheet.addCell(samplingTime);
                    writableSheet.addCell(outTemperature);

                    writableSheet.addCell(wireTemperature);
                    writableSheet.addCell(sag);
                    writableSheet.addCell(electricity);
                    writableSheet.addCell(voltage);
                    writableSheet.addCell(humidity);
                    writableSheet.addCell(nid);
                    writableSheet.addCell(location);
                    writableSheet.addCell(name);
                    writableSheet.addCell(source);
                }
            }
            wwb.write();
            os.flush();
            if (wwb != null)
                wwb.close();
            if (os != null)
                os.close();
        }

        public HttpServletResponse download(File file, HttpServletResponse response) {
            try {
                String path = file.getAbsolutePath();
                System.out.println("download:"+path);
                String filename = file.getName();
                InputStream fis = new BufferedInputStream(new FileInputStream(path));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // ���response
                response.reset();
                // ����response��Header
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return response;
        }

}