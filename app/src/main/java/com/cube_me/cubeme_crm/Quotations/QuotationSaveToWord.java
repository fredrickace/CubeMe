package com.cube_me.cubeme_crm.Quotations;

import android.os.Environment;
import android.util.Log;

//
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;

//import static org.apache.poi.hslf.model.textproperties.TextPropCollection.TextPropType.paragraph;

/**
 * Created by FredrickCyril on 9/25/16.
 */

public class QuotationSaveToWord {

    String path;
//    XWPFDocument doc;
//    XWPFParagraph paragraph;
//    XWPFRun run;




    public void writeWord(){

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
        }
        path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Cube/Quotations";
        File dir = new File(path);
        dir.mkdirs();
//


//        doc = null;
//        try {
//            doc = new XWPFDocument();
//
//            paragraph = doc.createParagraph();
//            paragraph.setAlignment(ParagraphAlignment.LEFT);
//            run = paragraph.createRun();
//            run.setText("God is great");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        File file = new File(path+"/Test.txt");
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            doc.write(fos);
//            fos.close();
//        } catch (FileNotFoundException e) {
//            Log.e("Error","Error Writing"+file+e);
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e("Error", "Failed to save");
//            e.printStackTrace();
//        }


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
