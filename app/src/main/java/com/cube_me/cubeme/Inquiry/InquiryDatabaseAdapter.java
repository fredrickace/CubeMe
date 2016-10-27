package com.cube_me.cubeme.Inquiry;

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
 * Created by FredrickCyril on 9/14/16.
 */
public class InquiryDatabaseAdapter {
    Context context;
    InquiryDatabaseHelper databaseHelper;

    public InquiryDatabaseAdapter(Context context) {

        databaseHelper = new InquiryDatabaseHelper(context);
        this.context = context;
    }

    public long saveInquiry(Inquiry inquiry) {
        long resultKey = 0;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues inquiryCV = new ContentValues();

        inquiryCV.put(databaseHelper.ACCOUNT_NAME, inquiry.inquiryCompanyName);
        inquiryCV.put(databaseHelper.INQUIRY_ID, inquiry.inquiryID);
        inquiryCV.put(databaseHelper.SUBJECT, inquiry.inquirySubject);
        inquiryCV.put(databaseHelper.DESCRIPTION, inquiry.inquiryDescription);
        inquiryCV.put(databaseHelper.DEPARTMENT, inquiry.inquiryDepartment);
        inquiryCV.put(databaseHelper.PROJECT_TYPE, inquiry.projectType);
        inquiryCV.put(databaseHelper.STATUS, inquiry.inquiryStatus);
        inquiryCV.put(databaseHelper.CONTACT_PERSON, inquiry.inquiryContactPerson);
        inquiryCV.put(databaseHelper.CONTACT_PERSON_DESIGNATION,inquiry.contactPersonDesignation);
        inquiryCV.put(databaseHelper.CONTACT_NUMBER, inquiry.inquiryContactNumber);
        inquiryCV.put(databaseHelper.EMAIL_ID, inquiry.inquiryEmailID);
        inquiryCV.put(databaseHelper.ASSIGN_TO, inquiry.inquiryAssignTo);
        inquiryCV.put(databaseHelper.CREATE_DATE,inquiry.inquiryCreateDate);
        inquiryCV.put(databaseHelper.EDIT_DATE,inquiry.inquiryLastEditDate);
        inquiryCV.put(databaseHelper.CREATE_TIME,inquiry.inquiryCreateTime);
        inquiryCV.put(databaseHelper.EDIT_TIME,inquiry.inquiryLastEditTime);
        inquiryCV.put(databaseHelper.CONSULTANT, inquiry.consultant);
        inquiryCV.put(databaseHelper.MAIN_CONTRACTOR, inquiry.mainContractor);
        inquiryCV.put(databaseHelper.SUB_CONTRACTOR, inquiry.subContractor);

        resultKey = db.insert(databaseHelper.TABLE_NAME, null, inquiryCV);
        return resultKey;
    }

    public long deleteInquiry(String inqID) {
        long deleteResultKey = 0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs = {inqID};

        deleteResultKey = db.delete(databaseHelper.TABLE_NAME,databaseHelper.INQUIRY_ID+" =? ", whereArgs);
        return deleteResultKey;
    }

    public List<Inquiry> getAllInquiries(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<Inquiry> data = new ArrayList<>();

        String[] columns = {databaseHelper.INQUIRY_ID, databaseHelper.ACCOUNT_NAME, databaseHelper.SUBJECT, databaseHelper.DESCRIPTION,
                databaseHelper.DEPARTMENT, databaseHelper.PROJECT_TYPE, databaseHelper.STATUS, databaseHelper.CONTACT_PERSON,
                databaseHelper.CONTACT_PERSON_DESIGNATION, databaseHelper.CONTACT_NUMBER, databaseHelper.EMAIL_ID,
                databaseHelper.ASSIGN_TO, databaseHelper.CREATE_DATE, databaseHelper.EDIT_DATE, databaseHelper.CREATE_TIME,
                databaseHelper.EDIT_TIME, databaseHelper.CONSULTANT, databaseHelper.MAIN_CONTRACTOR, databaseHelper.SUB_CONTRACTOR};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            Inquiry inquiry = new Inquiry();

            int inqIdInd = cursor.getColumnIndex(databaseHelper.INQUIRY_ID);
            int accountNameInd = cursor.getColumnIndex(databaseHelper.ACCOUNT_NAME);
            int subjectInd = cursor.getColumnIndex(databaseHelper.SUBJECT);
            int descInd = cursor.getColumnIndex(databaseHelper.DESCRIPTION);
            int deptInd = cursor.getColumnIndex(databaseHelper.DEPARTMENT);
            int prjTypeInd = cursor.getColumnIndex(databaseHelper.PROJECT_TYPE);
            int statusInd = cursor.getColumnIndex(databaseHelper.STATUS);
            int contactPersonInd = cursor.getColumnIndex(databaseHelper.CONTACT_PERSON);
            int contactDesgInd = cursor.getColumnIndex(databaseHelper.CONTACT_PERSON_DESIGNATION);
            int contactNoInd = cursor.getColumnIndex(databaseHelper.CONTACT_NUMBER);
            int emailIDInd = cursor.getColumnIndex(databaseHelper.EMAIL_ID);
            int assignTOInd = cursor.getColumnIndex(databaseHelper.ASSIGN_TO);
            int createDateInd = cursor.getColumnIndex(databaseHelper.CREATE_DATE);
            int editDateInd = cursor.getColumnIndex(databaseHelper.EDIT_DATE);
            int createTimeInd = cursor.getColumnIndex(databaseHelper.CREATE_TIME);
            int editTimeInd = cursor.getColumnIndex(databaseHelper.EDIT_TIME);
            int consultantInd = cursor.getColumnIndex(databaseHelper.CONSULTANT);
            int mainContInd = cursor.getColumnIndex(databaseHelper.MAIN_CONTRACTOR);
            int subContInd = cursor.getColumnIndex(databaseHelper.SUB_CONTRACTOR);

            inquiry.setInquiryID(cursor.getString(inqIdInd));
            inquiry.setInquiryCompanyName(cursor.getString(accountNameInd));
            inquiry.setInquirySubject(cursor.getString(subjectInd));
            inquiry.setInquiryDescription(cursor.getString(descInd));
            inquiry.setInquiryDepartment(cursor.getString(deptInd));
            inquiry.setProjectType(cursor.getString(prjTypeInd));
            inquiry.setInquiryStatus(cursor.getString(statusInd));
            inquiry.setInquiryContactPerson(cursor.getString(contactPersonInd));
            inquiry.setContactPersonDesignation(cursor.getString(contactDesgInd));
            inquiry.setInquiryContactNumber(cursor.getString(contactNoInd));
            inquiry.setInquiryEmailID(cursor.getString(emailIDInd));
            inquiry.setInquiryAssignTo(cursor.getString(assignTOInd));
            inquiry.setInquiryCreateDate(cursor.getString(createDateInd));
            inquiry.setInquiryLastEditDate(cursor.getString(editDateInd));
            inquiry.setInquiryCreateTime(cursor.getString(createTimeInd));
            inquiry.setInquiryLastEditTime(cursor.getString(editTimeInd));
            inquiry.setConsultant(cursor.getString(consultantInd));
            inquiry.setMainContractor(cursor.getString(mainContInd));
            inquiry.setSubContractor(cursor.getString(subContInd));

            data.add(inquiry);

        }
        return data;
    }
    public Inquiry getInquiry(String inqID){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Inquiry inquiry = new Inquiry();

        String[] columns = {databaseHelper.INQUIRY_ID, databaseHelper.ACCOUNT_NAME, databaseHelper.SUBJECT, databaseHelper.DESCRIPTION,
                databaseHelper.DEPARTMENT, databaseHelper.PROJECT_TYPE, databaseHelper.STATUS, databaseHelper.CONTACT_PERSON,
                databaseHelper.CONTACT_PERSON_DESIGNATION, databaseHelper.CONTACT_NUMBER, databaseHelper.EMAIL_ID,
                databaseHelper.ASSIGN_TO, databaseHelper.CREATE_DATE, databaseHelper.EDIT_DATE, databaseHelper.CREATE_TIME,
                databaseHelper.EDIT_TIME, databaseHelper.CONSULTANT, databaseHelper.MAIN_CONTRACTOR, databaseHelper.SUB_CONTRACTOR};

        Cursor cursor = db.query(databaseHelper.TABLE_NAME,columns,databaseHelper.INQUIRY_ID+" = '"+inqID+"'",null,null,null,null);
        while (cursor.moveToNext()){
            inquiry = new Inquiry();

            int inqIdInd = cursor.getColumnIndex(databaseHelper.INQUIRY_ID);
            int accountNameInd = cursor.getColumnIndex(databaseHelper.ACCOUNT_NAME);
            int subjectInd = cursor.getColumnIndex(databaseHelper.SUBJECT);
            int descInd = cursor.getColumnIndex(databaseHelper.DESCRIPTION);
            int deptInd = cursor.getColumnIndex(databaseHelper.DEPARTMENT);
            int prjTypeInd = cursor.getColumnIndex(databaseHelper.PROJECT_TYPE);
            int statusInd = cursor.getColumnIndex(databaseHelper.STATUS);
            int contactPersonInd = cursor.getColumnIndex(databaseHelper.CONTACT_PERSON);
            int contactDesgInd = cursor.getColumnIndex(databaseHelper.CONTACT_PERSON_DESIGNATION);
            int contactNoInd = cursor.getColumnIndex(databaseHelper.CONTACT_NUMBER);
            int emailIDInd = cursor.getColumnIndex(databaseHelper.EMAIL_ID);
            int assignTOInd = cursor.getColumnIndex(databaseHelper.ASSIGN_TO);
            int createDateInd = cursor.getColumnIndex(databaseHelper.CREATE_DATE);
            int editDateInd = cursor.getColumnIndex(databaseHelper.EDIT_DATE);
            int createTimeInd = cursor.getColumnIndex(databaseHelper.CREATE_TIME);
            int editTimeInd = cursor.getColumnIndex(databaseHelper.EDIT_TIME);
            int consultantInd = cursor.getColumnIndex(databaseHelper.CONSULTANT);
            int mainContInd = cursor.getColumnIndex(databaseHelper.MAIN_CONTRACTOR);
            int subContInd = cursor.getColumnIndex(databaseHelper.SUB_CONTRACTOR);

            inquiry.setInquiryID(cursor.getString(inqIdInd));
            inquiry.setInquiryCompanyName(cursor.getString(accountNameInd));
            inquiry.setInquirySubject(cursor.getString(subjectInd));
            inquiry.setInquiryDescription(cursor.getString(descInd));
            inquiry.setInquiryDepartment(cursor.getString(deptInd));
            inquiry.setProjectType(cursor.getString(prjTypeInd));
            inquiry.setInquiryStatus(cursor.getString(statusInd));
            inquiry.setInquiryContactPerson(cursor.getString(contactPersonInd));
            inquiry.setContactPersonDesignation(cursor.getString(contactDesgInd));
            inquiry.setInquiryContactNumber(cursor.getString(contactNoInd));
            inquiry.setInquiryEmailID(cursor.getString(emailIDInd));
            inquiry.setInquiryAssignTo(cursor.getString(assignTOInd));
            inquiry.setInquiryCreateDate(cursor.getString(createDateInd));
            inquiry.setInquiryLastEditDate(cursor.getString(editDateInd));
            inquiry.setInquiryCreateTime(cursor.getString(createTimeInd));
            inquiry.setInquiryLastEditTime(cursor.getString(editTimeInd));
            inquiry.setConsultant(cursor.getString(consultantInd));
            inquiry.setMainContractor(cursor.getString(mainContInd));
            inquiry.setSubContractor(cursor.getString(subContInd));


        }
        return inquiry;
    }
    public class InquiryDatabaseHelper extends SQLiteOpenHelper {

        Context context;
        private final static String DATABASE_NAME = "InquiryDatabase";
        private final static int DATABASE_VERSION = 3;
        private final static String UID = "_id";

        //INQUIRY TABLE
        private final static String TABLE_NAME = "InquiryTable";
        private final static String INQUIRY_ID = "InquiryID";
        private final static String ACCOUNT_NAME = "AccountName";
        private final static String SUBJECT = "Subject";
        private final static String DESCRIPTION = "Description";
        private final static String DEPARTMENT = "Department";
        private final static String PROJECT_TYPE = "ProjectType";
        private final static String STATUS = "Status";
        private final static String CONTACT_PERSON = "ContactPerson";
        private final static String CONTACT_PERSON_DESIGNATION = "ContactDesignation";
        private final static String CONTACT_NUMBER = "ContactNumber";
        private final static String EMAIL_ID = "Email";
        private final static String ASSIGN_TO = "AssignTo";
        private final static String CREATE_DATE = "CreateDate";
        private final static String EDIT_DATE = "EditDate";
        private final static String CREATE_TIME = "CreateTime";
        private final static String EDIT_TIME = "EditTime";
        private final static String CONSULTANT = "Consultant";
        private final static String MAIN_CONTRACTOR = "MainContractor";
        private final static String SUB_CONTRACTOR = "SubContractor";
        private final static String CREATE_INQ_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INQUIRY_ID + " TEXT, " + ACCOUNT_NAME + " TEXT, " + SUBJECT + " TEXT, " + DESCRIPTION + " VARCHAR(255), " + DEPARTMENT + " TEXT, "
                + STATUS + " TEXT, " + CONTACT_NUMBER + " TEXT, " + CONTACT_PERSON + " TEXT, " + CONTACT_PERSON_DESIGNATION + " TEXT, "
                + EMAIL_ID + " TEXT, " + ASSIGN_TO + " TEXT, " + PROJECT_TYPE + " TEXT, "+ CREATE_DATE+" TEXT, "
                + EDIT_DATE +" TEXT, " + CREATE_TIME + " TEXT, "+ EDIT_TIME +" TEXT, " + CONSULTANT + " TEXT, "
                + MAIN_CONTRACTOR + " TEXT, "+ SUB_CONTRACTOR + " TEXT);";
        private final static String DROP_INQ_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        //CONSTRUCTOR
        public InquiryDatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Toast.makeText(context, "OnCreate DB", Toast.LENGTH_SHORT).show();

            try {
                sqLiteDatabase.execSQL(CREATE_INQ_TABLE);
            } catch (SQLException e) {
                Log.e("DB ERROR", e.toString());
                Toast.makeText(context, e.toString() , Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            Toast.makeText(context, "OnUpgrade DB", Toast.LENGTH_SHORT).show();
            try {
                sqLiteDatabase.execSQL(DROP_INQ_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Log.e("DB ERROR", e.toString());
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }


        }
    }
}
