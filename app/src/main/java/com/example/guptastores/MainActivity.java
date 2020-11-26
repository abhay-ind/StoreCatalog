package com.example.guptastores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAMFragment.ItemAMListener {

    ArrayList<productNew> tempList;
    public static int ACTIVITY_CHOOSE_FILE=1812;
    RecyclerView productlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        tempList=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        if(FileManager.getImport(this)==-1)
        {
            findViewById(R.id.imp).setVisibility(View.VISIBLE);
            FileManager.setImport(this,2);
        }
        else if(FileManager.getImport(this)==1)
            findViewById(R.id.imp).setVisibility(View.GONE);
        else
            findViewById(R.id.imp).setVisibility(View.VISIBLE);
        productlist=findViewById(R.id.productList);
        productlist.setHasFixedSize(true);
        productlist.setLayoutManager(new LinearLayoutManager(this));
        tempList=FileManager.getOfflineDatabase(this);
        productlist.setAdapter(new ItemAdapter(this,tempList));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogFragment newFragment = new ItemAMFragment(1,new productNew("NULL",1,1,-1));
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(findViewById(R.id.imp).getVisibility()==View.VISIBLE) {
                    findViewById(R.id.imp).setVisibility(View.GONE);
                    FileManager.setImport(MainActivity.this,1);
                }
                else {
                    findViewById(R.id.imp).setVisibility(View.VISIBLE);
                    FileManager.setImport(MainActivity.this,2);
                }
                return true;
            }
        });



        FloatingActionButton fabSearch=findViewById(R.id.searchFab);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.searchbar).getVisibility()==View.VISIBLE) {
                    findViewById(R.id.searchbar).setVisibility(View.GONE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);

                    productlist.setAdapter(new ItemAdapter(MainActivity.this,tempList));
                }
                else
                {
                    findViewById(R.id.searchbar).setVisibility(View.VISIBLE);
                    findViewById(R.id.fab).setVisibility(View.GONE);
                    productlist.setAdapter(new ItemAdapter(MainActivity.this,new ArrayList<productNew>()));
                }
            }
        });
        EditText searchBox=findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getRelatedItems(editable.toString());
            }
        });

        Button imp=findViewById(R.id.imp);
        imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Your current data will be completely lost!!!!",Toast.LENGTH_LONG).show();
                Intent chooseFile;
                Intent intent;
                chooseFile=new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFile.setType("*/*");
                intent=Intent.createChooser(chooseFile,"Choose a file");
                startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        String path = "";
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uri = data.getData();
            String filepath = uri.getPath();
            Log.d("Path  = ", filepath);
            tempList=FileManager.getDataFile(this,uri);
            Toast.makeText(this,"Product Details updated successfully",Toast.LENGTH_LONG).show();
            productlist.setAdapter(new ItemAdapter(this,tempList));
            FileManager.setOfflineDatabase(MainActivity.this,tempList);
        }
    }

    public void getRelatedItems(String search){
        ArrayList<productNew> filtered=new ArrayList<>();
        for(productNew temp:tempList){
            if(temp.getproductName().toUpperCase().contains(search.toUpperCase())){
                filtered.add(temp);
            }
        }
        productlist.setAdapter(new ItemAdapter(this,filtered));

    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog,productNew pd) {
        long delim=(tempList.size()>0)?tempList.get(tempList.size()-1).getid():0;
        pd.setid(delim+1L);
        int flag=0;
        for(productNew t:tempList){
            if(t.getproductName().toUpperCase().equals(pd.getproductName().toUpperCase()))
            {flag=1;break;}
        }
        if(flag==0) {
            tempList.add(pd);
            productlist.setAdapter(new ItemAdapter(this,tempList));
            FileManager.setOfflineDatabase(this, tempList);
        }
        else {
            RelativeLayout outline=findViewById(R.id.outline);
            Snackbar.make(outline,"Product with similar name already in the list!!",Snackbar.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }

    @Override
    public void onDialogModifyPositiveClick(DialogFragment dialog, productNew temp, int position) {
        temp.setid(position);
        int flag=0;
        for(productNew t:tempList){
            if(t.getproductName().toUpperCase().equals(temp.getproductName().toUpperCase()) && t.getid()!=position)
            {flag=1;break;}
        }
        if(flag==0) {
            int index=-1;
            for(productNew pp:tempList){
                index++;
                if(pp.getid()==position){
                    tempList.set(index, temp);
                    break;
                }

            }



            FileManager.setOfflineDatabase(this, tempList);
            productlist.setAdapter(new ItemAdapter(this, tempList));
        }
        else
        {
            RelativeLayout outline=findViewById(R.id.outline);
            Snackbar.make(outline,"Product with a similar name already in the list!  Try again",Snackbar.LENGTH_LONG).show();
        }
            dialog.dismiss();
    }
    @Override
    public void onDialogDeletePositiveClick(DialogFragment dialog,int position) {
        productNew toBeRemoved=new productNew();
        for(productNew t:tempList){
            if(t.getid()==position)
                toBeRemoved=t;
        }
        Log.d("SIZE","Before"+tempList.size());
        tempList.remove(toBeRemoved);
        Log.d("SIZE","After"+tempList.size());
        FileManager.setOfflineDatabase(this, tempList);
        productlist.setAdapter(new ItemAdapter(this, tempList));
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();

    }
}
