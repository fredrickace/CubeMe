package com.cube_me.cubeme.Estimation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FredrickCyril on 8/22/16.
 */
public class EstimationDatabaseAdapter {

    EstimationDatabaseHelper databaseHelper;
    Context context;

    public EstimationDatabaseAdapter(Context context) {

        databaseHelper = new EstimationDatabaseHelper(context);
        this.context = context;
    }

    // SET AND RETRIEVE BASE TABLE
    public long insertBaseData(Estimation data) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues baseContentValues = new ContentValues();
        baseContentValues.put(databaseHelper.ESTIMATION_ID, data.estimationID);
        baseContentValues.put(databaseHelper.CLIENT, data.clientAccount);
        baseContentValues.put(databaseHelper.OVERHEAD_PERCENT, data.overHeadPercentage);
        baseContentValues.put(databaseHelper.MARGIN_PERCENT, data.marginPercentage);
        baseContentValues.put(databaseHelper.TOTAL, data.grandTotal);
        baseContentValues.put(databaseHelper.DM_OATOTAL, data.directMaterialTotal);
        baseContentValues.put(databaseHelper.MP_OATOTAL, data.manPowerTotal);
        baseContentValues.put(databaseHelper.TOOLS_OATOTAL, data.toolsTotal);
        baseContentValues.put(databaseHelper.TRANPSORT_OATOTAL, data.transportTotal);
        baseContentValues.put(databaseHelper.CONSUMABLES_OATOTAL, data.consumablesTotal);
        baseContentValues.put(databaseHelper.CREATE_DATE, data.estimationCreateDate);
        baseContentValues.put(databaseHelper.EDIT_DATE, data.estimationLastEditDate);
        baseContentValues.put(databaseHelper.CREATE_TIME, data.estimationCreateTime);
        baseContentValues.put(databaseHelper.EDIT_TIME, data.estimationLastEditTime);

        long resultKey = db.insert(databaseHelper.TABLE_NAME, null, baseContentValues);
        return resultKey;
    }

    public List<Estimation> getBaseData() {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<Estimation> data = new ArrayList<>();

        String[] columns = {databaseHelper.ESTIMATION_ID,databaseHelper.CLIENT,databaseHelper.TOTAL};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, null, null, null, null, null);
//        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            Estimation estimationObject = new Estimation();

            int estIDCursorId = cursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int clientNameId = cursor.getColumnIndex(databaseHelper.CLIENT);
            int totalId = cursor.getColumnIndex(databaseHelper.TOTAL);

            estimationObject.setEstimationID(cursor.getString(estIDCursorId));
            estimationObject.setClientAccount(cursor.getString(clientNameId));
            estimationObject.setGrandTotal(cursor.getFloat(totalId));

            data.add(estimationObject);
//            buffer.append(SerialId+" "+ EstID+ "\n");
        }
        return data;
    }

    public List<Estimation> getEstimationForInquiry(String EstId) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<Estimation> data = new ArrayList<>();


        String[] columns = {databaseHelper.ESTIMATION_ID,databaseHelper.CLIENT,databaseHelper.TOTAL};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, databaseHelper.ESTIMATION_ID + " = '" + EstId + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            Estimation estimationObject = new Estimation();
            int estIDCursorId = cursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int clientNameId = cursor.getColumnIndex(databaseHelper.CLIENT);
            int totalId = cursor.getColumnIndex(databaseHelper.TOTAL);

            estimationObject.setEstimationID(cursor.getString(estIDCursorId));
            estimationObject.setClientAccount(cursor.getString(clientNameId));
            estimationObject.setGrandTotal(cursor.getFloat(totalId));

            data.add(estimationObject);

        }
        return data;
    }

    public Estimation getEstimationBase(String estID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Estimation data = new Estimation();
        String[] columns = {databaseHelper.ESTIMATION_ID, databaseHelper.CLIENT, databaseHelper.OVERHEAD_PERCENT, databaseHelper.MARGIN_PERCENT,
                databaseHelper.TOTAL, databaseHelper.DM_OATOTAL, databaseHelper.MP_OATOTAL, databaseHelper.TOOLS_OATOTAL,
                databaseHelper.TRANPSORT_OATOTAL, databaseHelper.CONSUMABLES_OATOTAL, databaseHelper.CREATE_DATE, databaseHelper.EDIT_DATE,
                databaseHelper.CREATE_TIME, databaseHelper.EDIT_TIME};

        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, databaseHelper.ESTIMATION_ID + " = '" + estID + "'", null, null, null, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int estIDInd = cursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int clientInd = cursor.getColumnIndex(databaseHelper.CLIENT);
            int overHeadInd = cursor.getColumnIndex(databaseHelper.OVERHEAD_PERCENT);
            int marginInd = cursor.getColumnIndex(databaseHelper.MARGIN_PERCENT);
            int totalInd = cursor.getColumnIndex(databaseHelper.TOTAL);
            int DMTotalInd = cursor.getColumnIndex(databaseHelper.DM_OATOTAL);
            int MPTotalInd = cursor.getColumnIndex(databaseHelper.MP_OATOTAL);
            int toolsTotalInd = cursor.getColumnIndex(databaseHelper.TOOLS_OATOTAL);
            int transportTotalInd = cursor.getColumnIndex(databaseHelper.TRANPSORT_OATOTAL);
            int consumableTotalInd = cursor.getColumnIndex(databaseHelper.CONSUMABLES_OATOTAL);
            int createDateInd = cursor.getColumnIndex(databaseHelper.CREATE_DATE);
            int editDateInd = cursor.getColumnIndex(databaseHelper.EDIT_DATE);
            int createTimeInd = cursor.getColumnIndex(databaseHelper.CREATE_TIME);
            int editTimeInd = cursor.getColumnIndex(databaseHelper.EDIT_TIME);

            data.setEstimationID(cursor.getString(estIDInd));
            data.setClientAccount(cursor.getString(clientInd));
            data.setOverHeadPercentage(cursor.getFloat(overHeadInd));
            data.setMarginPercentage(cursor.getFloat(marginInd));
            data.setGrandTotal(cursor.getFloat(totalInd));
            data.setDirectMaterialTotal(cursor.getFloat(DMTotalInd));
            data.setManPowerTotal(cursor.getFloat(MPTotalInd));
            data.setToolsTotal(cursor.getFloat(toolsTotalInd));
            data.setTransportTotal(cursor.getFloat(transportTotalInd));
            data.setConsumablesTotal(cursor.getFloat(consumableTotalInd));
            data.setEstimationCreateDate(cursor.getString(createDateInd));
            data.setEstimationLastEditDate(cursor.getString(editDateInd));
            data.setEstimationCreateTime(cursor.getString(createTimeInd));
            data.setEstimationLastEditTime(cursor.getString(editTimeInd));
        }


        return data;
    }

    public List<Estimation> getEstimationBaseAll(String estID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<Estimation> estimationList = new ArrayList<>();
        Estimation data;
        String[] columns = {databaseHelper.ESTIMATION_ID, databaseHelper.CLIENT, databaseHelper.OVERHEAD_PERCENT, databaseHelper.MARGIN_PERCENT,
                databaseHelper.TOTAL, databaseHelper.DM_OATOTAL, databaseHelper.MP_OATOTAL, databaseHelper.TOOLS_OATOTAL,
                databaseHelper.TRANPSORT_OATOTAL, databaseHelper.CONSUMABLES_OATOTAL, databaseHelper.CREATE_DATE, databaseHelper.EDIT_DATE,
                databaseHelper.CREATE_TIME, databaseHelper.EDIT_TIME};

        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, databaseHelper.ESTIMATION_ID + " LIKE '%" + estID + "%'", null, null, null, null);

        while (cursor.moveToNext()) {
            data = new Estimation();
            int estIDInd = cursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int clientInd = cursor.getColumnIndex(databaseHelper.CLIENT);
            int overHeadInd = cursor.getColumnIndex(databaseHelper.OVERHEAD_PERCENT);
            int marginInd = cursor.getColumnIndex(databaseHelper.MARGIN_PERCENT);
            int totalInd = cursor.getColumnIndex(databaseHelper.TOTAL);
            int DMTotalInd = cursor.getColumnIndex(databaseHelper.DM_OATOTAL);
            int MPTotalInd = cursor.getColumnIndex(databaseHelper.MP_OATOTAL);
            int toolsTotalInd = cursor.getColumnIndex(databaseHelper.TOOLS_OATOTAL);
            int transportTotalInd = cursor.getColumnIndex(databaseHelper.TRANPSORT_OATOTAL);
            int consumableTotalInd = cursor.getColumnIndex(databaseHelper.CONSUMABLES_OATOTAL);
            int createDateInd = cursor.getColumnIndex(databaseHelper.CREATE_DATE);
            int editDateInd = cursor.getColumnIndex(databaseHelper.EDIT_DATE);
            int createTimeInd = cursor.getColumnIndex(databaseHelper.CREATE_TIME);
            int editTimeInd = cursor.getColumnIndex(databaseHelper.EDIT_TIME);

            data.setEstimationID(cursor.getString(estIDInd));
            data.setClientAccount(cursor.getString(clientInd));
            data.setOverHeadPercentage(cursor.getFloat(overHeadInd));
            data.setMarginPercentage(cursor.getFloat(marginInd));
            data.setGrandTotal(cursor.getFloat(totalInd));
            data.setDirectMaterialTotal(cursor.getFloat(DMTotalInd));
            data.setManPowerTotal(cursor.getFloat(MPTotalInd));
            data.setToolsTotal(cursor.getFloat(toolsTotalInd));
            data.setTransportTotal(cursor.getFloat(transportTotalInd));
            data.setConsumablesTotal(cursor.getFloat(consumableTotalInd));
            data.setEstimationCreateDate(cursor.getString(createDateInd));
            data.setEstimationLastEditDate(cursor.getString(editDateInd));
            data.setEstimationCreateTime(cursor.getString(createTimeInd));
            data.setEstimationLastEditTime(cursor.getString(editTimeInd));

            estimationList.add(data);
        }


        return estimationList;
    }
    //END OF BASE TABLE
    //SET AND RETRIEVE JOBTABLE

    public long insertJobTable(List<EstimationJobs> jobData, String estimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        EstimationJobs estimationJobs;
        long jobResultKey = 0;
        for (int i = 0; i < jobData.size(); i++) {
            estimationJobs = jobData.get(i);
            ContentValues jobContentValues = new ContentValues();
            jobContentValues.put(databaseHelper.ESTIMATION_ID, estimationID);
            jobContentValues.put(databaseHelper.JOB_NAME, estimationJobs.jobName);
            jobContentValues.put(databaseHelper.JOB_UNIT, estimationJobs.jobUnit);
            jobContentValues.put(databaseHelper.JOB_QUANTITY, estimationJobs.jobQuantity);
            jobContentValues.put(databaseHelper.JOB_TOTAL, estimationJobs.jobTotal);
            jobResultKey = db.insert(databaseHelper.JOB_TABLE_NAME, null, jobContentValues);

        }
        return jobResultKey;
    }

    public List<EstimationJobs> getJobData(String estimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        EstimationJobs estimationJobs;
        List<EstimationJobs> jobsList = new ArrayList<>();
        String[] jobColumns = {databaseHelper.JOB_NAME, databaseHelper.JOB_UNIT,
                databaseHelper.JOB_QUANTITY, databaseHelper.JOB_TOTAL};
        Cursor jobCursor = db.query(databaseHelper.JOB_TABLE_NAME, jobColumns, databaseHelper.ESTIMATION_ID + " = '" + estimationID + "'",
                null, null, null, null);

        while (jobCursor.moveToNext()) {
            estimationJobs = new EstimationJobs();
            int jobNameIndId = jobCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int jobUnitIntId = jobCursor.getColumnIndex(databaseHelper.JOB_UNIT);
            int jobQuantityIntID = jobCursor.getColumnIndex(databaseHelper.JOB_QUANTITY);
            int jobTotalIntID = jobCursor.getColumnIndex(databaseHelper.JOB_TOTAL);

            estimationJobs.setJobName(jobCursor.getString(jobNameIndId));
            estimationJobs.setJobUnit(jobCursor.getString(jobUnitIntId));
            estimationJobs.setJobQuantity(jobCursor.getFloat(jobQuantityIntID));
            estimationJobs.setJobTotal(jobCursor.getFloat(jobTotalIntID));

            jobsList.add(estimationJobs);
        }

        return jobsList;
    }

    //END OF JOBS TABLE
    // SET AND RETRIEVE DMTABLE
    public long insertDMData(List<EstimationDirectMaterial> data, String EstimationID) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long DMResultKey = 0;

        for (int i = 0; i < data.size(); i++) {
            EstimationDirectMaterial EDM = data.get(i);

            ContentValues DMContentValues = new ContentValues();
            DMContentValues.put(databaseHelper.ESTIMATION_ID, EstimationID);
            DMContentValues.put(databaseHelper.JOB_NAME, EDM.jobName);
            DMContentValues.put(databaseHelper.DM_ITEM_NAME, EDM.materialName);
            DMContentValues.put(databaseHelper.DM_DIMENSION, EDM.materialDimension);
            DMContentValues.put(databaseHelper.DM_UOM, EDM.materialUOM);
            DMContentValues.put(databaseHelper.DM_UNIT_PRICE, EDM.materialUnitPrice);
            DMContentValues.put(databaseHelper.DM_NO_OF_UNITS, EDM.materialNoOfUnits);
            DMContentValues.put(databaseHelper.DM_TOTAL, EDM.materialTotal);
            DMContentValues.put(databaseHelper.DM_BUDGET_PRICE, EDM.materialBudget);

            DMResultKey = db.insert(databaseHelper.DM_TABLE_NAME, null, DMContentValues);

        }

        return DMResultKey;
    }

    public List<EstimationDirectMaterial> getDMData(String EstimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<EstimationDirectMaterial> data = new ArrayList<>();
        EstimationDirectMaterial EDM;
        String[] DMcolumns = {databaseHelper.ESTIMATION_ID, databaseHelper.JOB_NAME,
                databaseHelper.DM_ITEM_NAME, databaseHelper.DM_DIMENSION, databaseHelper.DM_UOM,
                databaseHelper.DM_UNIT_PRICE, databaseHelper.DM_BUDGET_PRICE, databaseHelper.DM_NO_OF_UNITS, databaseHelper.DM_TOTAL};

        Cursor DMCursor = db.query(databaseHelper.DM_TABLE_NAME, DMcolumns, databaseHelper.ESTIMATION_ID + " = '" + EstimationID + "'",
                null, null, null, null);

        while (DMCursor.moveToNext()) {

            EDM = new EstimationDirectMaterial();

            int estIndID = DMCursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int jodNameIndID = DMCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int itemNameIndID = DMCursor.getColumnIndex(databaseHelper.DM_ITEM_NAME);
            int dimensionIndID = DMCursor.getColumnIndex(databaseHelper.DM_DIMENSION);
            int UOMIndID = DMCursor.getColumnIndex(databaseHelper.DM_UOM);
            int unitPriceIndID = DMCursor.getColumnIndex(databaseHelper.DM_UNIT_PRICE);
            int budgetPriceIndID = DMCursor.getColumnIndex(databaseHelper.DM_BUDGET_PRICE);
            int noOfUnitsIndID = DMCursor.getColumnIndex(databaseHelper.DM_NO_OF_UNITS);
            int totalIndID = DMCursor.getColumnIndex(databaseHelper.DM_TOTAL);

            EDM.setJobName(DMCursor.getString(jodNameIndID));
            EDM.setMaterialName(DMCursor.getString(itemNameIndID));
            EDM.setMaterialDimension(DMCursor.getString(dimensionIndID));
            EDM.setMaterialUOM(DMCursor.getString(UOMIndID));
            EDM.setMaterialUnitPrice(DMCursor.getFloat(unitPriceIndID));
            EDM.setMaterialNoOfUnits(DMCursor.getFloat(noOfUnitsIndID));
            EDM.setMaterialTotal(DMCursor.getFloat(totalIndID));
            EDM.setMaterialBudget(DMCursor.getFloat(budgetPriceIndID));

            data.add(EDM);
        }

        return data;
    }

    // END OF DM TABLE
    // SET AND RETRIEVE MANPOWER TABLE
    public long insertMPTable(List<EstimationOtherMaterials> data, String estimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        EstimationOtherMaterials EOM;
        long mpResultKey = 0;
        for (int i = 0; i < data.size(); i++) {

            EOM = data.get(i);
            ContentValues mpContentValues = new ContentValues();
            mpContentValues.put(databaseHelper.ESTIMATION_ID, estimationID);
            mpContentValues.put(databaseHelper.JOB_NAME, EOM.jobName);
            mpContentValues.put(databaseHelper.MP_NAME, EOM.otherMaterialName);
            mpContentValues.put(databaseHelper.MP_UOM, EOM.otherMaterialUOM);
            mpContentValues.put(databaseHelper.MP_UNITS, EOM.otherMaterialDayHour);
            mpContentValues.put(databaseHelper.MP_RESOURCES, EOM.otherMaterialUnit);
            mpContentValues.put(databaseHelper.MP_PRICE_UNIT, EOM.otherMaterialPricePerUnit);
            mpContentValues.put(databaseHelper.MP_TOTAL, EOM.otherMaterialTotal);
            mpContentValues.put(databaseHelper.MP_BUDGET_UNIT, EOM.otherMaterialBudgetPerUnit);

            mpResultKey = db.insert(databaseHelper.MP_TABLE_NAME, null, mpContentValues);
        }
        return mpResultKey;
    }

    public List<EstimationOtherMaterials> getMPTable(String estID) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<EstimationOtherMaterials> data = new ArrayList<>();
        EstimationOtherMaterials MP;
        String[] mpColumns = {databaseHelper.ESTIMATION_ID, databaseHelper.JOB_NAME, databaseHelper.MP_NAME, databaseHelper.MP_UOM,
                databaseHelper.MP_UNITS, databaseHelper.MP_RESOURCES, databaseHelper.MP_PRICE_UNIT,
                databaseHelper.MP_TOTAL,databaseHelper.MP_BUDGET_UNIT};
        Cursor mpCursor = db.query(databaseHelper.MP_TABLE_NAME, mpColumns, databaseHelper.ESTIMATION_ID + " = '" + estID + "'",
                null, null, null, null);
        while (mpCursor.moveToNext()) {

            MP = new EstimationOtherMaterials();
            int mpJobIndex = mpCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int mpNameIndex = mpCursor.getColumnIndex(databaseHelper.MP_NAME);
            int mpUOMIndex = mpCursor.getColumnIndex(databaseHelper.MP_UOM);
            int mpDayIndex = mpCursor.getColumnIndex(databaseHelper.MP_UNITS);
            int mpHoursIndex = mpCursor.getColumnIndex(databaseHelper.MP_RESOURCES);
            int mpPriceHourIndex = mpCursor.getColumnIndex(databaseHelper.MP_PRICE_UNIT);
            int mpTotalIndex = mpCursor.getColumnIndex(databaseHelper.MP_TOTAL);
            int mpBudgetIndex = mpCursor.getColumnIndex(databaseHelper.MP_BUDGET_UNIT);

            MP.setJobName(mpCursor.getString(mpJobIndex));
            MP.setOtherMaterialName(mpCursor.getString(mpNameIndex));
            MP.setOtherMaterialUOM(mpCursor.getString(mpUOMIndex));
            MP.setOtherMaterialDayHour(mpCursor.getFloat(mpDayIndex));
            MP.setOtherMaterialUnit(mpCursor.getFloat(mpHoursIndex));
            MP.setOtherMaterialPricePerUnit(mpCursor.getFloat(mpPriceHourIndex));
            MP.setOtherMaterialTotal(mpCursor.getFloat(mpTotalIndex));
            MP.setOtherMaterialBudgetPerUnit(mpCursor.getFloat(mpBudgetIndex));

            data.add(MP);

        }
        return data;
    }
    // END OF MANPOWER TABLE
    // START OF TOOLS TABLE SECTION

    public long insertToolsTable(List<EstimationOtherMaterials> data, String estimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        EstimationOtherMaterials toolsEOM;
        long toolsResultKey = 0;
        for (int i = 0; i < data.size(); i++) {

            toolsEOM = data.get(i);
            ContentValues toolsContentValues = new ContentValues();
            toolsContentValues.put(databaseHelper.ESTIMATION_ID, estimationID);
            toolsContentValues.put(databaseHelper.JOB_NAME, toolsEOM.jobName);
            toolsContentValues.put(databaseHelper.TOOLS_NAME, toolsEOM.otherMaterialName);
            toolsContentValues.put(databaseHelper.TOOLS_UOM, toolsEOM.otherMaterialUOM);
            toolsContentValues.put(databaseHelper.TOOLS_UNIT, toolsEOM.otherMaterialDayHour);
            toolsContentValues.put(databaseHelper.TOOLS_RESOURCES, toolsEOM.otherMaterialUnit);
            toolsContentValues.put(databaseHelper.TOOLS_PRICE_UNIT, toolsEOM.otherMaterialPricePerUnit);
            toolsContentValues.put(databaseHelper.TOOLS_TOTAL, toolsEOM.otherMaterialTotal);
            toolsContentValues.put(databaseHelper.TOOLS_BUDGET_UNIT,toolsEOM.otherMaterialBudgetPerUnit);

            db.insert(databaseHelper.TOOLS_TABLE_NAME, null, toolsContentValues);
        }
        return toolsResultKey;
    }

    public List<EstimationOtherMaterials> getToolsTable(String estID) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<EstimationOtherMaterials> data = new ArrayList<>();
        EstimationOtherMaterials toolsEOM;
        String[] toolsColumns = {databaseHelper.ESTIMATION_ID, databaseHelper.JOB_NAME, databaseHelper.TOOLS_NAME, databaseHelper.TOOLS_UOM,
                databaseHelper.TOOLS_UNIT, databaseHelper.TOOLS_RESOURCES, databaseHelper.TOOLS_PRICE_UNIT,
                databaseHelper.TOOLS_TOTAL,databaseHelper.TOOLS_BUDGET_UNIT};
        Cursor toolsCursor = db.query(databaseHelper.TOOLS_TABLE_NAME, toolsColumns, databaseHelper.ESTIMATION_ID + " = '" + estID + "'",
                null, null, null, null);
        while (toolsCursor.moveToNext()) {

            toolsEOM = new EstimationOtherMaterials();
            int toolsJobIndex = toolsCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int toolsNameIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_NAME);
            int toolsUOMIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_UOM);
            int toolsDayIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_UNIT);
            int toolsHoursIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_RESOURCES);
            int toolsPriceHourIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_PRICE_UNIT);
            int toolsTotalIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_TOTAL);
            int toolsBudgetIndex = toolsCursor.getColumnIndex(databaseHelper.TOOLS_BUDGET_UNIT);

            toolsEOM.setJobName(toolsCursor.getString(toolsJobIndex));
            toolsEOM.setOtherMaterialName(toolsCursor.getString(toolsNameIndex));
            toolsEOM.setOtherMaterialUOM(toolsCursor.getString(toolsUOMIndex));
            toolsEOM.setOtherMaterialDayHour(toolsCursor.getFloat(toolsDayIndex));
            toolsEOM.setOtherMaterialUnit(toolsCursor.getFloat(toolsHoursIndex));
            toolsEOM.setOtherMaterialPricePerUnit(toolsCursor.getFloat(toolsPriceHourIndex));
            toolsEOM.setOtherMaterialTotal(toolsCursor.getFloat(toolsTotalIndex));
            toolsEOM.setOtherMaterialBudgetPerUnit(toolsCursor.getFloat(toolsBudgetIndex));

            data.add(toolsEOM);

        }
        return data;
    }
    // END OF TOOLS SECTION
    // START OF TRANSPORT SECTION


    public long insertTransportTable(List<EstimationOtherMaterials> data, String estimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        EstimationOtherMaterials transportEOM;
        long transportResultKey = 0;
        for (int i = 0; i < data.size(); i++) {

            transportEOM = data.get(i);
            ContentValues transportConttentValues = new ContentValues();
            transportConttentValues.put(databaseHelper.ESTIMATION_ID, estimationID);
            transportConttentValues.put(databaseHelper.JOB_NAME, transportEOM.jobName);
            transportConttentValues.put(databaseHelper.TRANSPORT_NAME, transportEOM.otherMaterialName);
            transportConttentValues.put(databaseHelper.TRANSPORT_UOM, transportEOM.otherMaterialUOM);
            transportConttentValues.put(databaseHelper.TRANSPORT_UNIT, transportEOM.otherMaterialDayHour);
            transportConttentValues.put(databaseHelper.TRANSPORT_RESOURCES, transportEOM.otherMaterialUnit);
            transportConttentValues.put(databaseHelper.TRANSPORT_PRICE_UNIT, transportEOM.otherMaterialPricePerUnit);
            transportConttentValues.put(databaseHelper.TRANSPORT_TOTAL, transportEOM.otherMaterialTotal);
            transportConttentValues.put(databaseHelper.TRANSPORT_BUDGET_UNIT,transportEOM.otherMaterialBudgetPerUnit);

            db.insert(databaseHelper.TRANSPORT_TABLE_NAME, null, transportConttentValues);
        }
        return transportResultKey;
    }

    public List<EstimationOtherMaterials> getTransportTable(String estID) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<EstimationOtherMaterials> data = new ArrayList<>();
        EstimationOtherMaterials transportEOM;
        String[] transportColumns = {databaseHelper.ESTIMATION_ID, databaseHelper.JOB_NAME, databaseHelper.TRANSPORT_NAME, databaseHelper.TRANSPORT_UOM,
                databaseHelper.TRANSPORT_UNIT, databaseHelper.TRANSPORT_RESOURCES,
                databaseHelper.TRANSPORT_PRICE_UNIT, databaseHelper.TRANSPORT_TOTAL,databaseHelper.TRANSPORT_BUDGET_UNIT};
        Cursor transportCursor = db.query(databaseHelper.TRANSPORT_TABLE_NAME, transportColumns, databaseHelper.ESTIMATION_ID + " = '" + estID + "'",
                null, null, null, null);
        while (transportCursor.moveToNext()) {

            transportEOM = new EstimationOtherMaterials();
            int transportJobIndex = transportCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int transportNameIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_NAME);
            int transportUOMIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_UOM);
            int transportDayIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_UNIT);
            int transportHoursIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_RESOURCES);
            int transportPriceTripIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_PRICE_UNIT);
            int transportTotalIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_TOTAL);
            int transportBudgetIndex = transportCursor.getColumnIndex(databaseHelper.TRANSPORT_BUDGET_UNIT);

            transportEOM.setJobName(transportCursor.getString(transportJobIndex));
            transportEOM.setOtherMaterialName(transportCursor.getString(transportNameIndex));
            transportEOM.setOtherMaterialUOM(transportCursor.getString(transportUOMIndex));
            transportEOM.setOtherMaterialDayHour(transportCursor.getFloat(transportDayIndex));
            transportEOM.setOtherMaterialUnit(transportCursor.getFloat(transportHoursIndex));
            transportEOM.setOtherMaterialPricePerUnit(transportCursor.getFloat(transportPriceTripIndex));
            transportEOM.setOtherMaterialTotal(transportCursor.getFloat(transportTotalIndex));
            transportEOM.setOtherMaterialBudgetPerUnit(transportCursor.getFloat(transportBudgetIndex));

            data.add(transportEOM);

        }
        return data;
    }
    // END OF TRANSPORT SECTION
    // START OF CONSUMABLES SECTION

    public long insertConsumableData(List<EstimationConsumables> data, String EstimationID) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long consumableResultKey = 0;

        for (int i = 0; i < data.size(); i++) {
            EstimationConsumables consumable = data.get(i);

            ContentValues consumableContentValues = new ContentValues();
            consumableContentValues.put(databaseHelper.ESTIMATION_ID, EstimationID);
            consumableContentValues.put(databaseHelper.JOB_NAME, consumable.jobName);
            consumableContentValues.put(databaseHelper.CONSUMABLE_NAME, consumable.consumableName);
            consumableContentValues.put(databaseHelper.CONSUMABLE_UOM, consumable.consumableUOM);
            consumableContentValues.put(databaseHelper.CONSUMABLE_UNIT_PRICE, consumable.consumableUnitPrice);
            consumableContentValues.put(databaseHelper.CONSUMABLE_NO_OF_UNITS, consumable.consumableNoOfUnits);
            consumableContentValues.put(databaseHelper.CONSUMABLE_TOTAL, consumable.consumableTotal);
            consumableContentValues.put(databaseHelper.CONSUMABLE_BUDGET_PRICE,consumable.consumableBudgetPrice);

            consumableResultKey = db.insert(databaseHelper.CONSUMABLE_TABLE_NAME, null, consumableContentValues);

        }

        return consumableResultKey;
    }

    public List<EstimationConsumables> getConsumableData(String EstimationID) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<EstimationConsumables> data = new ArrayList<>();
        EstimationConsumables consumable;
        String[] consumableColumns = {databaseHelper.ESTIMATION_ID, databaseHelper.JOB_NAME,
                databaseHelper.CONSUMABLE_NAME, databaseHelper.CONSUMABLE_UOM,
                databaseHelper.CONSUMABLE_UNIT_PRICE, databaseHelper.CONSUMABLE_NO_OF_UNITS, databaseHelper.CONSUMABLE_TOTAL,
                databaseHelper.CONSUMABLE_BUDGET_PRICE};

        Cursor consumableCursor = db.query(databaseHelper.CONSUMABLE_TABLE_NAME, consumableColumns, databaseHelper.ESTIMATION_ID + " = '" + EstimationID + "'",
                null, null, null, null);

        while (consumableCursor.moveToNext()) {

            consumable = new EstimationConsumables();

            int estIndID = consumableCursor.getColumnIndex(databaseHelper.ESTIMATION_ID);
            int jodNameIndID = consumableCursor.getColumnIndex(databaseHelper.JOB_NAME);
            int itemNameIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_NAME);
            int UOMIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_UOM);
            int unitPriceIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_UNIT_PRICE);
            int noOfUnitsIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_NO_OF_UNITS);
            int totalIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_TOTAL);
            int budgetIndID = consumableCursor.getColumnIndex(databaseHelper.CONSUMABLE_BUDGET_PRICE);

            consumable.setJobName(consumableCursor.getString(jodNameIndID));
            consumable.setConsumableName(consumableCursor.getString(itemNameIndID));
            consumable.setConsumableUOM(consumableCursor.getString(UOMIndID));
            consumable.setConsumableUnitPrice(consumableCursor.getFloat(unitPriceIndID));
            consumable.setConsumableNoOfUnits(consumableCursor.getFloat(noOfUnitsIndID));
            consumable.setConsumableTotal(consumableCursor.getFloat(totalIndID));
            consumable.setConsumableBudgetPrice(consumableCursor.getFloat(budgetIndID));

            data.add(consumable);
        }

        return data;
    }

    // END OF CONSUMABLES SECTION
    // DELETE ALL TABLES

    public int deleteEstimation(String EstimationID) {
        int count;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = {EstimationID};
        count = db.delete(databaseHelper.TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.JOB_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.DM_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.MP_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.TOOLS_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.TRANSPORT_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);
        count = db.delete(databaseHelper.CONSUMABLE_TABLE_NAME, databaseHelper.ESTIMATION_ID + " =? ", whereArgs);

        return count;
    }


    class EstimationDatabaseHelper extends SQLiteOpenHelper {
        Context context;

        private final static String DATABASE_NAME = "EstimationDatabase";
        private final static String UID = "_id";
        private final static int DATABASE_VERSION = 4;

        //BASE TABLE
        private final static String TABLE_NAME = "EstimationBaseTable";
        private final static String ESTIMATION_ID = "EstimationID";
        private final static String CLIENT = "ClientName";
        private final static String OVERHEAD_PERCENT = "OverHeadPercent";
        private final static String MARGIN_PERCENT = "MarginPercent";
        private final static String DM_OATOTAL = "DMTotal";
        private final static String MP_OATOTAL = "MPTotal";
        private final static String TOOLS_OATOTAL = "ToolsTotal";
        private final static String TRANPSORT_OATOTAL = "TrasnportTotal";
        private final static String CONSUMABLES_OATOTAL = "ConsumablesTotal";
        private final static String TOTAL = "Total";
        private final static String CREATE_DATE = "CreateDate";
        private final static String EDIT_DATE = "EditDate";
        private final static String CREATE_TIME = "CreateTime";
        private final static String EDIT_TIME = "EditTime";
        private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + CLIENT + " TEXT, " + OVERHEAD_PERCENT + " REAL, " + MARGIN_PERCENT + " REAL, " + DM_OATOTAL + " REAL, "
                + MP_OATOTAL + " REAL, " + TOOLS_OATOTAL + " REAL, " + TRANPSORT_OATOTAL + " REAL, " + CONSUMABLES_OATOTAL + " REAL, " + TOTAL + " REAL, "
                + CREATE_DATE + " TEXT, " + EDIT_DATE + " TEXT, " + CREATE_TIME + " TEXT, " + EDIT_TIME + " TEXT);";
        private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        // JOBS TABLE
        private final static String JOB_TABLE_NAME = "JobTable";
        private final static String JOB_NAME = "JobName";
        private final static String JOB_UNIT = "JobUnit";
        private final static String JOB_QUANTITY = "JobQuantity";
        private final static String JOB_TOTAL = "JobTotal";
        private final static String CREATE_JOB_TABLE = "CREATE TABLE " + JOB_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + JOB_UNIT + " TEXT, " + JOB_QUANTITY + " REAL, " + JOB_TOTAL + " REAL);";
        private final static String DROP_JOB_TABLE = "DROP TABLE IF EXISTS " + JOB_TABLE_NAME;


        // DIRECT MATERIALS TABLE
        private final static String DM_TABLE_NAME = "DMTable";
        private final static String DM_ITEM_NAME = "ItemName";
        private final static String DM_DIMENSION = "Dimension";
        private final static String DM_UOM = "UOM";
        private final static String DM_UNIT_PRICE = "UnitPrice";
        private final static String DM_BUDGET_PRICE = "DMBudgetPrice";
        private final static String DM_NO_OF_UNITS = "NoOfUnits";
        private final static String DM_TOTAL = "TotalPrice";
        private final static String CREATE_DM_TABLE = "CREATE TABLE " + DM_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + DM_ITEM_NAME + " TEXT, " + DM_DIMENSION + " TEXT, " + DM_UOM + " TEXT, "
                + DM_BUDGET_PRICE + " REAL, " + DM_UNIT_PRICE + " REAL, " + DM_NO_OF_UNITS + " REAL, " + DM_TOTAL + " REAL);";
        private final static String DROP_DM_TABLE = "DROP TABLE IF EXISTS " + DM_TABLE_NAME;


        // MANPOWER TABLE   ABBR--MP
        private static final String MP_TABLE_NAME = "MPTable";
        private static final String MP_NAME = "MPName";
        private static final String MP_UOM = "MPUOM";
        private static final String MP_UNITS = "MPDay";
        private static final String MP_RESOURCES = "MPResource";
        private static final String MP_PRICE_UNIT = "MPPrice";
        private static final String MP_BUDGET_UNIT = "MPBudget";
        private static final String MP_TOTAL = "MPTotal";
        private static final String CREATE_MP_TABLE = "CREATE TABLE " + MP_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + MP_NAME + " TEXT, " + MP_UOM + " TEXT, " + MP_UNITS + " REAL, " + MP_RESOURCES + " REAL, "
                + MP_PRICE_UNIT + " REAL, " + MP_BUDGET_UNIT + " REAL, " + MP_TOTAL + " REAL);";
        private final static String DROP_MP_TABLE = "DROP TABLE IF EXISTS " + MP_TABLE_NAME;


        // TOOLS TABLE
        private static final String TOOLS_TABLE_NAME = "ToolsTable";
        private static final String TOOLS_NAME = "ToolsName";
        private static final String TOOLS_UOM = "ToolsUOM";
        private static final String TOOLS_UNIT = "ToolsUnit";
        private static final String TOOLS_RESOURCES = "ToolsResources";
        private static final String TOOLS_PRICE_UNIT = "ToolsPrice";
        private static final String TOOLS_BUDGET_UNIT = "ToolsBudget";
        private static final String TOOLS_TOTAL = "ToolsTotal";
        private static final String CREATE_TOOLS_TABLE = "CREATE TABLE " + TOOLS_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + TOOLS_NAME + " TEXT, " + TOOLS_UOM + " TEXT, " + TOOLS_UNIT + " REAL, " + TOOLS_RESOURCES + " REAL, "
                + TOOLS_PRICE_UNIT + " REAL, " + TOOLS_BUDGET_UNIT + " REAL, " + TOOLS_TOTAL + " REAL);";
        private final static String DROP_TOOLS_TABLE = "DROP TABLE IF EXISTS " + TOOLS_TABLE_NAME;


        // TRANSPORT TABLE
        private static final String TRANSPORT_TABLE_NAME = "TransportTable";
        private static final String TRANSPORT_NAME = "TransportName";
        private static final String TRANSPORT_UOM = "TransportUOM";
        private static final String TRANSPORT_UNIT = "TransportUnit";
        private static final String TRANSPORT_RESOURCES = "TransportResources";
        private static final String TRANSPORT_PRICE_UNIT = "TransportPrice";
        private static final String TRANSPORT_BUDGET_UNIT = "TransportBudget";
        private static final String TRANSPORT_TOTAL = "TransportTotal";
        private static final String CREATE_TRANSPORT_TABLE = "CREATE TABLE " + TRANSPORT_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + TRANSPORT_NAME + " TEXT, " + TRANSPORT_UOM + " TEXT, " + TRANSPORT_UNIT + " REAL, " + TRANSPORT_RESOURCES + " REAL, "
                + TRANSPORT_BUDGET_UNIT + " REAL, " + TRANSPORT_PRICE_UNIT + " REAL, " + TRANSPORT_TOTAL + " REAL);";
        private final static String DROP_TRANSPORT_TABLE = "DROP TABLE IF EXISTS " + TRANSPORT_TABLE_NAME;

        // CONSUMABLES TABLE
        private final static String CONSUMABLE_TABLE_NAME = "ConsumablesTable";
        private final static String CONSUMABLE_NAME = "ItemName";
        private final static String CONSUMABLE_UOM = "UOM";
        private final static String CONSUMABLE_UNIT_PRICE = "UnitPrice";
        private static final String CONSUMABLE_BUDGET_PRICE = "BudgetPrice";
        private final static String CONSUMABLE_NO_OF_UNITS = "NoOfUnits";
        private final static String CONSUMABLE_TOTAL = "TotalPrice";
        private final static String CREATE_CONSUMABLE_TABLE = "CREATE TABLE " + CONSUMABLE_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ESTIMATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + CONSUMABLE_NAME + " TEXT, " + CONSUMABLE_UOM + " TEXT, "
                + CONSUMABLE_UNIT_PRICE + " REAL, " + CONSUMABLE_BUDGET_PRICE + " REAL, " + CONSUMABLE_NO_OF_UNITS + " REAL, " + CONSUMABLE_TOTAL + " REAL);";
        private final static String DROP_CONSUMABLE_TABLE = "DROP TABLE IF EXISTS " + CONSUMABLE_TABLE_NAME;


        // CONSTRUCTOR
        public EstimationDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
                sqLiteDatabase.execSQL(CREATE_DM_TABLE);
                sqLiteDatabase.execSQL(CREATE_JOB_TABLE);
                sqLiteDatabase.execSQL(CREATE_MP_TABLE);
                sqLiteDatabase.execSQL(CREATE_TOOLS_TABLE);
                sqLiteDatabase.execSQL(CREATE_TRANSPORT_TABLE);
                sqLiteDatabase.execSQL(CREATE_CONSUMABLE_TABLE);

            } catch (SQLException e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                Log.e("DB Error", e.toString());
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                sqLiteDatabase.execSQL(DROP_DM_TABLE);
                sqLiteDatabase.execSQL(DROP_JOB_TABLE);
                sqLiteDatabase.execSQL(DROP_MP_TABLE);
                sqLiteDatabase.execSQL(DROP_TOOLS_TABLE);
                sqLiteDatabase.execSQL(DROP_TRANSPORT_TABLE);
                sqLiteDatabase.execSQL(DROP_CONSUMABLE_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

}
