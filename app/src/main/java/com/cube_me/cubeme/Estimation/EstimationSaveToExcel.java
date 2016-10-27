package com.cube_me.cubeme.Estimation;

/**
 * Created by FredrickCyril on 8/29/16.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.cube_me.cubeme.BaseActivity;
import com.cube_me.cubeme.R;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EstimationSaveToExcel {

    String path;
    int rowCount;
    Cell cell;
    Workbook workbook;
    CellStyle mainTitleStyle, subTitleStyle, valueStyle,subTotalStyle;
    Sheet estimationSheet;


    public void getExcel(Context context, Estimation estimation){

      //CHECK IF AVAILABLE AND NOT READ ONLY
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
        }

//        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cube/Estimations";

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cube/Estimations");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        path = dir.getAbsolutePath();
//        dir.mkdirs();

        rowCount = 0;

        workbook = new HSSFWorkbook();
//        workbook = new XSSFWorkbook();
        cell = null;

        // CELL STYLE FOR MAIN TITLE ROW
        mainTitleStyle = workbook.createCellStyle();
        mainTitleStyle.setFillForegroundColor(HSSFColor.RED.index);
        mainTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        mainTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //CELL STYLE FOR SUB TITLE ROW
        subTitleStyle = workbook.createCellStyle();
        subTitleStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);    //HSSFColor.LIME.index
        subTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        subTitleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // CELL STYLE FOR VALUE ROWS
        valueStyle = workbook.createCellStyle();
        valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // CELL STYLE FOR MAIN TITLE ROW
        subTotalStyle = workbook.createCellStyle();
        subTotalStyle.setFillForegroundColor(HSSFColor.LIME.index);
        subTotalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        subTotalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //New Sheet
        estimationSheet = null;
        estimationSheet = workbook.createSheet("Estimation");

        // Generate column headings

        // Main Title Row

        Row estimationTitle = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = estimationTitle.createCell(1);
        cell.setCellValue(estimation.estimationID);
        cell.setCellStyle(mainTitleStyle);

        // DM SECTION
        if(estimation.estimationDirectMaterialList.size()>0){
            setDMSection(estimation);
        }
        // MANPOWER SECTION
        if(estimation.manPowerList.size()>0){
            setMPSection(estimation);
        }
        // TOOLS SECTION
        if(estimation.toolsList.size()>0){
            setToolsSection(estimation);
        }
        // TRANSPORT SECTION
        if(estimation.transportList.size()>0){
            setTransportSection(estimation);
        }
        //CONSUMABLE SECTION
        if(estimation.consumableList.size()>0){
            setConsumablesSection(estimation);
        }

        //SETTING OVERHEAD
        Row overHeadRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = overHeadRow.createCell(2);
        cell.setCellValue("OverHead %");
        cell.setCellStyle(subTotalStyle);

        cell = overHeadRow.createCell(3);
        cell.setCellValue(estimation.overHeadPercentage);
        cell.setCellStyle(subTotalStyle);

        //SETTING MARGIN %
        Row marginRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = marginRow.createCell(2);
        cell.setCellValue("Margin %");
        cell.setCellStyle(subTotalStyle);

        cell = marginRow.createCell(3);
        cell.setCellValue(estimation.marginPercentage);
        cell.setCellStyle(subTotalStyle);


        // SETTING JOBS TOTAL
        rowCount++;
        Row jobTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = jobTitleRow.createCell(1);
        cell.setCellValue("JobName");
        cell.setCellStyle(mainTitleStyle);

        cell = jobTitleRow.createCell(2);
        cell.setCellValue("Quantity");
        cell.setCellStyle(mainTitleStyle);

        cell = jobTitleRow.createCell(3);
        cell.setCellValue("Total");
        cell.setCellStyle(mainTitleStyle);

        for(int i = 0 ;i<estimation.jobsList.size();i++){

            Row jobRow = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationJobs jobs = estimation.jobsList.get(i);
            cell = jobRow.createCell(1);
            cell.setCellValue(jobs.jobName);
            cell.setCellStyle(valueStyle);

            cell = jobRow.createCell(2);
            cell.setCellValue(jobs.jobQuantity);
            cell.setCellStyle(valueStyle);

            cell = jobRow.createCell(3);
            cell.setCellValue(BaseActivity.decimalFormat.format(jobs.jobTotal));
            cell.setCellStyle(subTotalStyle);
        }

        // SETTING TOTAL VALUE

        rowCount++;
        Row totalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = totalRow.createCell(2);
        cell.setCellValue("Total");
        cell.setCellStyle(mainTitleStyle);

        cell = totalRow.createCell(3);
        cell.setCellValue(BaseActivity.decimalFormat.format(estimation.grandTotal));
        cell.setCellStyle(mainTitleStyle);



        estimationSheet.setDefaultColumnWidth(2 * 5);

//        estimationSheet.setColumnWidth(0, (15 * 500));
//        estimationSheet.setColumnWidth(1, (15 * 500));
//        estimationSheet.setColumnWidth(2, (15 * 500));

        String[] tempEstId = estimation.estimationID.split("/ ");

        File file = new File(path+"/"+"EST_"+tempEstId[1]+".xls");
        FileOutputStream fos = null;


        try {
            fos = new FileOutputStream(file);
            workbook.write(fos);
            Log.w("FileUtils", "Writing file" + file);
            Toast.makeText(context, "Successfully Downloaded", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.e("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != fos)
                    fos.close();
            } catch (Exception ex) {
            }
        }

    }

    public void setDMSection(Estimation estimation){

        Row dmHeadingRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = dmHeadingRow.createCell(0);
        cell.setCellValue("Materials");
        cell.setCellStyle(mainTitleStyle);

        Row dmTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = dmTitleRow.createCell(0);
        cell.setCellValue("Job");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(1);
        cell.setCellValue("Direct Material");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(2);
        cell.setCellValue("Dimension");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(3);
        cell.setCellValue("UOM");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(4);
        cell.setCellValue("Budget");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(5);
        cell.setCellValue("Price");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(6);
        cell.setCellValue("Quantity");
        cell.setCellStyle(subTitleStyle);

        cell = dmTitleRow.createCell(7);
        cell.setCellValue("Amount");
        cell.setCellStyle(subTitleStyle);
        for(int i =0 ;i<estimation.estimationDirectMaterialList.size(); i++){

            Row dmUnitRows = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationDirectMaterial DM = estimation.estimationDirectMaterialList.get(i);
            cell = dmUnitRows.createCell(0);
            cell.setCellValue(DM.jobName);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(1);
            cell.setCellValue(DM.materialName);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(2);
            cell.setCellValue(DM.materialDimension);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(3);
            cell.setCellValue(DM.materialUOM);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(4);
            cell.setCellValue(DM.materialBudget);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(5);
            cell.setCellValue(DM.materialUnitPrice);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(6);
            cell.setCellValue(DM.materialNoOfUnits);
            cell.setCellStyle(valueStyle);

            cell = dmUnitRows.createCell(7);
            cell.setCellValue(DM.materialTotal);
            cell.setCellStyle(valueStyle);

        }

        Row dmTotalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = dmTotalRow.createCell(6);
        cell.setCellValue("SubTotal");
        cell.setCellStyle(subTotalStyle);

        cell = dmTotalRow.createCell(7);
        cell.setCellValue(estimation.directMaterialTotal);
        cell.setCellStyle(subTotalStyle);


    }

    public void setMPSection(Estimation estimation){

        Row mpHeadingRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = mpHeadingRow.createCell(0);
        cell.setCellValue("ManPower");
        cell.setCellStyle(mainTitleStyle);

        Row mpTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = mpTitleRow.createCell(0);
        cell.setCellValue("Job");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(2);
        cell.setCellValue("UOM");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(3);
        cell.setCellValue("Units");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(4);
        cell.setCellValue("No of Resources");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(5);
        cell.setCellValue("Budget");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(6);
        cell.setCellValue("Price");
        cell.setCellStyle(subTitleStyle);

        cell = mpTitleRow.createCell(7);
        cell.setCellValue("Amount");
        cell.setCellStyle(subTitleStyle);

        for(int i =0 ;i<estimation.manPowerList.size(); i++) {

            Row mpUnitRows = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationOtherMaterials MP = estimation.manPowerList.get(i);
            cell = mpUnitRows.createCell(0);
            cell.setCellValue(MP.jobName);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(1);
            cell.setCellValue(MP.otherMaterialName);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(2);
            cell.setCellValue(MP.otherMaterialUOM);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(3);
            cell.setCellValue(MP.otherMaterialDayHour);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(4);
            cell.setCellValue(MP.otherMaterialUnit);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(5);
            cell.setCellValue(MP.otherMaterialBudgetPerUnit);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(6);
            cell.setCellValue(MP.otherMaterialPricePerUnit);
            cell.setCellStyle(valueStyle);

            cell = mpUnitRows.createCell(7);
            cell.setCellValue(MP.otherMaterialTotal);
            cell.setCellStyle(valueStyle);
        }
        Row MPTotalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = MPTotalRow.createCell(6);
        cell.setCellValue("SubTotal");
        cell.setCellStyle(subTotalStyle);

        cell = MPTotalRow.createCell(7);
        cell.setCellValue(estimation.manPowerTotal);
        cell.setCellStyle(subTotalStyle);
    }

    public void setToolsSection(Estimation estimation){

        Row toolsHeadingRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = toolsHeadingRow.createCell(0);
        cell.setCellValue("Tools");
        cell.setCellStyle(mainTitleStyle);

        Row toolsTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = toolsTitleRow.createCell(0);
        cell.setCellValue("Job");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(2);
        cell.setCellValue("UOM");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(3);
        cell.setCellValue("Units");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(4);
        cell.setCellValue("No of Resources");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(5);
        cell.setCellValue("Budget");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(6);
        cell.setCellValue("Price");
        cell.setCellStyle(subTitleStyle);

        cell = toolsTitleRow.createCell(7);
        cell.setCellValue("Amount");
        cell.setCellStyle(subTitleStyle);

        for(int i =0 ;i<estimation.toolsList.size(); i++) {

            Row toolsUnitRows = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationOtherMaterials tools = estimation.toolsList.get(i);
            cell = toolsUnitRows.createCell(0);
            cell.setCellValue(tools.jobName);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(1);
            cell.setCellValue(tools.otherMaterialName);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(2);
            cell.setCellValue(tools.otherMaterialUOM);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(3);
            cell.setCellValue(tools.otherMaterialDayHour);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(4);
            cell.setCellValue(tools.otherMaterialUnit);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(5);
            cell.setCellValue(tools.otherMaterialBudgetPerUnit);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(6);
            cell.setCellValue(tools.otherMaterialPricePerUnit);
            cell.setCellStyle(valueStyle);

            cell = toolsUnitRows.createCell(7);
            cell.setCellValue(tools.otherMaterialTotal);
            cell.setCellStyle(valueStyle);
        }
        Row toolsTotalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = toolsTotalRow.createCell(6);
        cell.setCellValue("SubTotal");
        cell.setCellStyle(subTotalStyle);

        cell = toolsTotalRow.createCell(7);
        cell.setCellValue(estimation.toolsTotal);
        cell.setCellStyle(subTotalStyle);
    }

    public void setTransportSection(Estimation estimation){

        Row transportHeadingRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = transportHeadingRow.createCell(0);
        cell.setCellValue("Transport");
        cell.setCellStyle(mainTitleStyle);

        Row transportTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = transportTitleRow.createCell(0);
        cell.setCellValue("Job");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(2);
        cell.setCellValue("UOM");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(3);
        cell.setCellValue("Units");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(4);
        cell.setCellValue("No of Resources");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(5);
        cell.setCellValue("Budget");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(6);
        cell.setCellValue("Price");
        cell.setCellStyle(subTitleStyle);

        cell = transportTitleRow.createCell(7);
        cell.setCellValue("Amount");
        cell.setCellStyle(subTitleStyle);

        for(int i =0 ;i<estimation.transportList.size(); i++) {

            Row transportUnitRows = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationOtherMaterials transport = estimation.transportList.get(i);
            cell = transportUnitRows.createCell(0);
            cell.setCellValue(transport.jobName);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(1);
            cell.setCellValue(transport.otherMaterialName);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(2);
            cell.setCellValue(transport.otherMaterialUOM);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(3);
            cell.setCellValue(transport.otherMaterialDayHour);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(4);
            cell.setCellValue(transport.otherMaterialUnit);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(5);
            cell.setCellValue(transport.otherMaterialBudgetPerUnit);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(6);
            cell.setCellValue(transport.otherMaterialPricePerUnit);
            cell.setCellStyle(valueStyle);

            cell = transportUnitRows.createCell(7);
            cell.setCellValue(transport.otherMaterialTotal);
            cell.setCellStyle(valueStyle);
        }
        Row transportTotalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = transportTotalRow.createCell(6);
        cell.setCellValue("SubTotal");
        cell.setCellStyle(subTotalStyle);

        cell = transportTotalRow.createCell(7);
        cell.setCellValue(estimation.transportTotal);
        cell.setCellStyle(subTotalStyle);
    }

    public void setConsumablesSection(Estimation estimation){

        Row consumableHeadingRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = consumableHeadingRow.createCell(0);
        cell.setCellValue("consumable");
        cell.setCellStyle(mainTitleStyle);

        Row consumableTitleRow = estimationSheet.createRow(rowCount);
        rowCount++;

        cell = consumableTitleRow.createCell(0);
        cell.setCellValue("Job");
        cell.setCellStyle(subTitleStyle);

        cell = consumableTitleRow.createCell(1);
        cell.setCellValue("Name");
        cell.setCellStyle(subTitleStyle);

        cell = consumableTitleRow.createCell(2);
        cell.setCellValue("UOM");
        cell.setCellStyle(subTitleStyle);

        cell = consumableTitleRow.createCell(3);
        cell.setCellValue("Budget");
        cell.setCellStyle(subTitleStyle);

        cell = consumableTitleRow.createCell(4);
        cell.setCellValue("Unit Price");
        cell.setCellStyle(subTitleStyle);

        cell = consumableTitleRow.createCell(5);
        cell.setCellValue("Amount");
        cell.setCellStyle(subTitleStyle);

        for(int i =0 ;i<estimation.consumableList.size(); i++) {

            Row consumableUnitRows = estimationSheet.createRow(rowCount);
            rowCount++;
            EstimationConsumables consumable = estimation.consumableList.get(i);
            cell = consumableUnitRows.createCell(0);
            cell.setCellValue(consumable.jobName);
            cell.setCellStyle(valueStyle);

            cell = consumableUnitRows.createCell(1);
            cell.setCellValue(consumable.consumableName);
            cell.setCellStyle(valueStyle);

            cell = consumableUnitRows.createCell(2);
            cell.setCellValue(consumable.consumableUOM);
            cell.setCellStyle(valueStyle);

            cell = consumableUnitRows.createCell(3);
            cell.setCellValue(consumable.consumableBudgetPrice);
            cell.setCellStyle(valueStyle);

            cell = consumableUnitRows.createCell(4);
            cell.setCellValue(consumable.consumableUnitPrice);
            cell.setCellStyle(valueStyle);

            cell = consumableUnitRows.createCell(5);
            cell.setCellValue(consumable.consumableTotal);
            cell.setCellStyle(valueStyle);
        }
        Row consumableTotalRow = estimationSheet.createRow(rowCount);
        rowCount++;
        cell = consumableTotalRow.createCell(6);
        cell.setCellValue("SubTotal");
        cell.setCellStyle(subTotalStyle);

        cell = consumableTotalRow.createCell(7);
        cell.setCellValue(estimation.consumablesTotal);
        cell.setCellStyle(subTotalStyle);
    }


    public static boolean isExternalStorageReadOnly() {

        String extStorageState = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}
