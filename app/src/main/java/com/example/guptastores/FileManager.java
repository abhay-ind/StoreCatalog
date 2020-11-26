package com.example.guptastores;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<productNew> getOfflineDatabase(Activity main) {
        if(main.getApplicationContext().getFileStreamPath("db.dat").exists())
        {
            try{
                FileInputStream fis=main.getApplicationContext().openFileInput("db.dat");
                ObjectInputStream ois=new ObjectInputStream(fis);
                ArrayList<productDetails> os1=(ArrayList<productDetails>) ois.readObject();
                fis.close();
                ArrayList<productNew> os=new ArrayList<>();
                for(productDetails po:os1){
                    productNew pd=new productNew(po.getproductName(),po.getcostPrice(),po.getsellingPrice(),po.getid());
                    os.add(pd);
                }
                return os;
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                FileInputStream fis=main.getApplicationContext().openFileInput("db.dat");
                ObjectInputStream ois=new ObjectInputStream(fis);
                ArrayList<productNew> os=(ArrayList<productNew>) ois.readObject();
                fis.close();
                return os;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new ArrayList<productNew>();
    }
    public static void setOfflineDatabase(Activity main,ArrayList<productNew> os){
        try {
            FileOutputStream fos=main.getApplicationContext().openFileOutput("db.dat",main.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(os);
            fos.close();
            Log.d("FILECREATED","FILE CREATED SUCCESSFULLY");
            saveDataFile(main,os);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isStoragePermissionGranted(Activity context,String permission) {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;

            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    public static void saveDataFile(Activity context,ArrayList<productNew> data) {
        String root = Environment.getExternalStorageDirectory().toString();
        if (isStoragePermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // check or ask permission
            Log.d("FILESS!!!!!!!",root);
            File myDir = new File(root, "/VijayHardwares/Data/");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String fname = "DataFile.dat";
            File file = new File(myDir, fname);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile(); // if file already exists will do nothing
                FileOutputStream out = new FileOutputStream(file);
                ObjectOutputStream oos=new ObjectOutputStream(out);
                oos.writeObject(data);

                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, new String[]{file.getName()}, null);
        }
    }
    public static ArrayList<productNew> getDataFile(Activity context, Uri filepath) {
        String root = Environment.getExternalStorageDirectory().toString();
        ArrayList<productNew> output=new ArrayList<>();
        if (isStoragePermissionGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE)) { // check or ask permission
            Log.d("FILESS!!!!!!!",root);
            try{
                FileInputStream fis=context.openFileInput("db.dat");
                ObjectInputStream ois=new ObjectInputStream(fis);
                ArrayList<productDetails> os1=(ArrayList<productDetails>) ois.readObject();
                fis.close();
                ArrayList<productNew> os=new ArrayList<>();
                for(productDetails po:os1){
                    productNew pd=new productNew(po.getproductName(),po.getcostPrice(),po.getsellingPrice(),po.getid());
                    os.add(pd);
                }
                return os;
            }catch (Exception e){
                e.printStackTrace();
                Log.d("IMPORT ERROR","old->new");
            }

            try {
                InputStream in = context.getContentResolver().openInputStream(filepath);
                ObjectInputStream ois=new ObjectInputStream(in);
                output=(ArrayList<productNew>)ois.readObject();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

//            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, new String[]{file.getName()}, null);
        }
        return output;
    }

    public static void setImport(Activity main,Integer val){
        try {
            FileOutputStream fos=main.getApplicationContext().openFileOutput("import1.dat",main.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(val);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Integer getImport(Activity main){
        if(main.getApplicationContext().getFileStreamPath("import1.dat").exists())
        {
            try{
                FileInputStream fis=main.getApplicationContext().openFileInput("import1.dat");
                ObjectInputStream ois=new ObjectInputStream(fis);
                Integer os=(Integer) ois.readObject();
                fis.close();
                return os;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return -1;
    }
}
