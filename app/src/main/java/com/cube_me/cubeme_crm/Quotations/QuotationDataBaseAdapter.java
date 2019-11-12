package com.cube_me.cubeme_crm.Quotations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cube_me.cubeme_crm.Estimation.EstimationJobs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FredrickCyril on 10/30/16.
 */

public class QuotationDataBaseAdapter {

    QuotationDataBaseHelper dataBaseHelper;
    Context context;

    public QuotationDataBaseAdapter(Context context) {
        this.context = context;
//        Toast.makeText(context, "test from Quo Adapter", Toast.LENGTH_SHORT).show();
        dataBaseHelper = new QuotationDataBaseHelper(context);
    }

    public long insertBaseDate(Quotation data){
        long result;
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(dataBaseHelper.QUOTATION_ID,data.quotationNo);
        cv.put(dataBaseHelper.CLIENT_NAME,data.quotationClient);
        cv.put(dataBaseHelper.QUOTATION_TITLE,data.quotationTitle);
        cv.put(dataBaseHelper.QUOTATION_STATUS,data.quotationStatus);
        cv.put(dataBaseHelper.QUOTATION_TOTAL,data.quotationTotal);
        cv.put(dataBaseHelper.QUOTATION_GROSS_TOTAL,data.quotationGrossTotal);
        cv.put(dataBaseHelper.QUOTATION_DISCOUNT,data.quotationDiscount);
        result = db.insert(dataBaseHelper.BASE_TABLE_NAME,null,cv);
        ContentValues jobCV = new ContentValues();
        List<EstimationJobs> jobList = data.quotationJobs;
        for(int i = 0; i<jobList.size(); i++){
            EstimationJobs job = jobList.get(i);
            jobCV.put(dataBaseHelper.QUOTATION_ID,data.quotationNo);
            jobCV.put(dataBaseHelper.JOB_NAME, job.jobName);
            jobCV.put(dataBaseHelper.JOB_UNIT, job.jobUnit);
            jobCV.put(dataBaseHelper.JOB_UNIT_PRICE, job.jobUnitPrice);
            jobCV.put(dataBaseHelper.JOB_QUANTITY, job.jobQuantity);
            jobCV.put(dataBaseHelper.JOB_TOTAL, job.jobTotal);
            result = db.insert(dataBaseHelper.JOB_TABLE_NAME,null,jobCV);
        }
        return result;
    }

    public List<Quotation> getBaseQuotation(){
        List<Quotation> data = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] columns = {dataBaseHelper.QUOTATION_ID, dataBaseHelper.CLIENT_NAME, dataBaseHelper.QUOTATION_TITLE,
                            dataBaseHelper.QUOTATION_STATUS,dataBaseHelper.QUOTATION_GROSS_TOTAL,dataBaseHelper.QUOTATION_DISCOUNT,
                            dataBaseHelper.QUOTATION_TOTAL};
        Cursor cursor = db.query(dataBaseHelper.BASE_TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            Quotation quotation = new Quotation();

            int quoIDInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_ID);
            int clientInd = cursor.getColumnIndex(dataBaseHelper.CLIENT_NAME);
            int titleInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_TITLE);
            int statusInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_STATUS);
            int grossTotalInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_GROSS_TOTAL);
            int totalInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_TOTAL);
            int discountInd = cursor.getColumnIndex(dataBaseHelper.QUOTATION_DISCOUNT);

            quotation.setQuotationNo(cursor.getString(quoIDInd));
            quotation.setQuotationClient(cursor.getString(clientInd));
            quotation.setQuotationTitle(cursor.getString(titleInd));
            quotation.setQuotationStatus(cursor.getString(statusInd));
            quotation.setQuotationGrossTotal(cursor.getFloat(grossTotalInd));
            quotation.setQuotationTotal(cursor.getFloat(totalInd));
            quotation.setQuotationDiscount(cursor.getFloat(discountInd));

            data.add(quotation);
        }

        return data;
    }

    public Quotation getQuotation(Quotation data){
        Quotation current = data;
        List<EstimationJobs> jobData = new ArrayList<>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] jobColumns = {dataBaseHelper.JOB_NAME,dataBaseHelper.JOB_QUANTITY,dataBaseHelper.JOB_UNIT,dataBaseHelper.JOB_UNIT_PRICE,
                               dataBaseHelper.JOB_TOTAL};
        Cursor jobCursor = db.query(dataBaseHelper.JOB_TABLE_NAME,jobColumns,dataBaseHelper.QUOTATION_ID +" = '" + data.quotationNo +"'",
                                    null,null,null,null);
        while (jobCursor.moveToNext()){
            EstimationJobs jobs = new EstimationJobs();
            int jobNameInd = jobCursor.getColumnIndex(dataBaseHelper.JOB_NAME);
            int jobQuantityInd = jobCursor.getColumnIndex(dataBaseHelper.JOB_QUANTITY);
            int jobUnitInd = jobCursor.getColumnIndex(dataBaseHelper.JOB_UNIT);
            int jobUnitPriceInd = jobCursor.getColumnIndex(dataBaseHelper.JOB_UNIT_PRICE);
            int jobTotalInd = jobCursor.getColumnIndex(dataBaseHelper.JOB_TOTAL);

            jobs.setJobName(jobCursor.getString(jobNameInd));
            jobs.setJobQuantity(jobCursor.getFloat(jobQuantityInd));
            jobs.setJobUnit(jobCursor.getString(jobUnitInd));
            jobs.setJobUnitPrice(jobCursor.getFloat(jobUnitPriceInd));
            jobs.setJobTotal(jobCursor.getFloat(jobTotalInd));

            jobData.add(jobs);
        }
        current.quotationJobs = jobData;

        return data;
    }

    public int deleteQuotation(String quoID){
        int result;
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] whereArgs = {quoID};
        result = db.delete(dataBaseHelper.BASE_TABLE_NAME,dataBaseHelper.QUOTATION_ID+ " =? ", whereArgs);
        result = db.delete(dataBaseHelper.JOB_TABLE_NAME, dataBaseHelper.QUOTATION_ID+ " =? ", whereArgs);

        return result;
    }



    public class QuotationDataBaseHelper extends SQLiteOpenHelper{

        Context context;
        private final static String DATABASE_NAME = "QuotationDatabase";
        private final static int DATABASE_VERSION = 3;
        private final static String UID = "_id";

        //CONSTRUCTOR
        public QuotationDataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        //QUOTATION BASE TABLE
        private final static String BASE_TABLE_NAME = "QuotationBaseTable";
        private final static String QUOTATION_ID = "QuotationID";
        private final static String CLIENT_NAME = "ClientName";
        private final static String QUOTATION_TITLE = "Title";
        private final static String QUOTATION_STATUS = "Status";
        private final static String QUOTATION_TOTAL = "QuotationTotal";
        private final static String QUOTATION_DISCOUNT = "Discount";
        private final static String DISCOUNT_TYPE = "DiscountType";
        private final static String QUOTATION_GROSS_TOTAL = "GrossTotal";
        private final static String CREATE_BASE_TABLE = "CREATE TABLE " +BASE_TABLE_NAME+ " (" +UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +QUOTATION_ID+ " TEXT, " +CLIENT_NAME+ " TEXT, " +QUOTATION_TITLE+ " TEXT, " +QUOTATION_STATUS+ " TEXT, "
                +QUOTATION_TOTAL+ " REAL, " +QUOTATION_DISCOUNT+ " REAL, " + DISCOUNT_TYPE+ " INTEGER, " +QUOTATION_GROSS_TOTAL+ " REAL);";
        private final static String DROP_BASE_TABLE = "DROP TABLE IF EXISTS " +BASE_TABLE_NAME;


        //QUOTATION JOBS TABLE
        private final static String JOB_TABLE_NAME = "QuotationJobTable";
        private final static String JOB_NAME = "JobName";
        private final static String JOB_UNIT = "JobUnit";
        private final static String JOB_UNIT_PRICE = "UnitPrice";
        private final static String JOB_QUANTITY = "JobQuantity";
        private final static String JOB_TOTAL = "JobTotal";
        private final static String CREATE_JOB_TABLE = "CREATE TABLE " + JOB_TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUOTATION_ID + " VARCHAR(255), " + JOB_NAME + " TEXT, " + JOB_UNIT + " TEXT, " +JOB_UNIT_PRICE+" REAL, "
                + JOB_QUANTITY + " REAL, " + JOB_TOTAL + " REAL);";
        private final static String DROP_JOB_TABLE = "DROP TABLE IF EXISTS " + JOB_TABLE_NAME;

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE_BASE_TABLE);
                sqLiteDatabase.execSQL(CREATE_JOB_TABLE);
//                Toast.makeText(context, "On Create", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Log.e("DB Error",e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try {
                sqLiteDatabase.execSQL(DROP_BASE_TABLE);
                sqLiteDatabase.execSQL(DROP_JOB_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Log.e("DB Error",e.toString());
            }
        }
    }
}
