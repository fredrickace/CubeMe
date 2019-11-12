package com.cube_me.cubeme_crm.PurchaseOrder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cube_me.cubeme_crm.AttachmentListRecyclerAdapter;
import com.cube_me.cubeme_crm.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderNew extends AppCompatActivity implements AttachmentListRecyclerAdapter.AttachmentRecyclerCommunicator {

    Toolbar toolbar;
    PurchaseOrder purchaseOrder;
    TextView poNumberTV;
    TextView poClientNameTV;
    EditText poDescET;
    TextView poCreateDateTV;
    TextView poCreateTimeTV;
    TextView poEditDateTV;
    TextView poEditTimeTV;
    RecyclerView attachmentRecycler;
    AttachmentListRecyclerAdapter attachmentListRecyclerAdapter;
    List<String> attachmentNameList;
    List<Uri> attachmentList;
    Context context;
    Button addAttachmentButton;
    public final static int PICK_FILE = 20;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_order_new);

        //INIT TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("New PO");

        //BASIC INIT
        purchaseOrder = new PurchaseOrder();
        poNumberTV = (TextView) findViewById(R.id.poNew_poNoTV);
        poClientNameTV = (TextView) findViewById(R.id.poNew_ClientNameTV);
        poDescET = (EditText) findViewById(R.id.poNew_descET);
        poCreateDateTV = (TextView) findViewById(R.id.poNew_CreateDateTV);
        poCreateTimeTV = (TextView) findViewById(R.id.poNew_CreateTimeTV);
        poEditDateTV = (TextView) findViewById(R.id.poNew_EditDateTV);
        poEditTimeTV = (TextView) findViewById(R.id.poNew_EditTimeTV);
        addAttachmentButton = (Button) findViewById(R.id.poNew_addAttachementsButton);
        attachmentRecycler = (RecyclerView) findViewById(R.id.poNew_attachmentRecycler);
        attachmentNameList = new ArrayList<>();
        attachmentList = new ArrayList<>();
        context = getApplicationContext();

        //SETTING UP THE RECYCLER ADAPTER
        attachmentListRecyclerAdapter = new AttachmentListRecyclerAdapter(attachmentNameList,context,this);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(context));
        attachmentRecycler.setAdapter(attachmentListRecyclerAdapter);

        //SETTING UP THE ADD ATTACHMENT
        addAttachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFile();
            }
        });

    }

    //INTENT TO OPEN THE FILES FROM DEVICE
    private void pickFile() {
        Intent attachmentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        attachmentIntent.setType("*/*");
        startActivityForResult(attachmentIntent,PICK_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_FILE && resultCode == RESULT_OK && data != null){

            Uri selectedFile = data.getData();
            attachmentList.add(selectedFile);
            Log.i("Attachment List Size",String.valueOf(attachmentList.size()));
            String fileName = null;
            String fileSize = null;
            String scheme = selectedFile.getScheme();
            if(scheme.equals("file")){
                fileName = selectedFile.getLastPathSegment();
            } else if(scheme.equals("content")){
                Cursor cursor = getApplicationContext().getContentResolver().query(selectedFile,null,null,null,null);
                if(cursor != null && cursor.getCount() != 0){
                    int fileNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int fileSizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                    cursor.moveToFirst();
                    fileName = cursor.getString(fileNameIndex);
                    fileSize = cursor.getString(fileSizeIndex);
                    attachmentNameList.add(fileName);
                    updateAttachmentRecycler();
                }
                Log.i("Attachment Name", fileName);
                Log.i("Attachment Size",fileSize);
            }
        }
    }

    public void updateAttachmentRecycler(){
        attachmentListRecyclerAdapter = new AttachmentListRecyclerAdapter(attachmentNameList,context,this);
        attachmentRecycler.setLayoutManager(new LinearLayoutManager(context));
        attachmentRecycler.setAdapter(attachmentListRecyclerAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deleteAttachedPosition(int position) {
        attachmentNameList.remove(position);
        attachmentList.remove(position);
        updateAttachmentRecycler();
        Log.i("Attachment List Size",String.valueOf(attachmentList.size()));
    }


}
