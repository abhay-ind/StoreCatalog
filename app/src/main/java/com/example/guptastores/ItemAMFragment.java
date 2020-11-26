package com.example.guptastores;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemAMFragment extends DialogFragment {
    int operation;productNew position;
    ArrayList<subType> list;

    public ItemAMFragment(int addOrModify,productNew pos) {
        super();
        this.operation=addOrModify;
        this.position=pos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final View promptView=inflater.inflate(R.layout.additem,container,false);
        Button addModify=promptView.findViewById(R.id.addButton);
        final CheckBox addSubType=promptView.findViewById(R.id.radioSub);
        final LinearLayout single=promptView.findViewById(R.id.singleType);
        final LinearLayout multiple=promptView.findViewById(R.id.multiple);
        final Button addRow=promptView.findViewById(R.id.addRow);
        Button delete=promptView.findViewById(R.id.deleteButton);
        addSubType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked()){
                    if(operation==1)
                    {
                        list=new ArrayList<>();
                    }
                    else{
                        list=position.getallType();
                    }
                    single.setVisibility(View.GONE);
                    multiple.setVisibility(View.VISIBLE);
                    final RecyclerView subTypeList=promptView.findViewById(R.id.subList);
                    subTypeList.setHasFixedSize(true);
                    subTypeList.setLayoutManager(new LinearLayoutManager(promptView.getContext()));

                    subTypeList.setAdapter(new subTypeAdapter(ItemAMFragment.this.getActivity(),list));
                    addRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            list.add(new subType("",0,0));
                            subTypeList.setAdapter(new subTypeAdapter(ItemAMFragment.this.getActivity(),list));
                        }
                    });
                }
                else
                {
                    single.setVisibility(View.VISIBLE);
                    multiple.setVisibility(View.GONE);
                }
            }
        });

        if(operation==1)
            addModify.setText("Add");
        else
        {
            delete.setVisibility(View.VISIBLE);
            addModify.setText("Modify");
        EditText n = promptView.findViewById(R.id.pName);
        EditText c = promptView.findViewById(R.id.pCP);
        EditText s = promptView.findViewById(R.id.pSP);
        n.setText(position.getproductName());
        c.setText(position.getcostPrice()+"");
        s.setText(position.getsellingPrice()+"");
        if(position.getallType().size()>0)
            addSubType.setChecked(true);
        else
            addSubType.setChecked(false);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDialogDeletePositiveClick(ItemAMFragment.this, (int) (position.getid()));
            }
        });
        promptView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addSubType.isChecked()) {
                    EditText name = promptView.findViewById(R.id.pName);

                    ArrayList<subType> invalidItems=new ArrayList<>();
                    for(int i=0;i<list.size();i++){
                        subType a=list.get(i);
                        if(a.getType().length()==0||a.getCp()<=0||a.getSp()<=0)
                        {
                            invalidItems.add(a);
                        }
                    }
                    list.removeAll(invalidItems);
                    productNew p = new productNew(name.getText().toString(), list, 0);
                    if (operation == 1)
                        listener.onDialogPositiveClick(ItemAMFragment.this, p);
                    else
                        listener.onDialogModifyPositiveClick(ItemAMFragment.this, p, (int) (position.getid()));
                } else {
                EditText name = promptView.findViewById(R.id.pName);
                EditText cp = promptView.findViewById(R.id.pCP);
                EditText sp = promptView.findViewById(R.id.pSP);
                String error = "";
                if (name.getText().toString().length() == 0)
                    error += " | Product Name ";
                if (cp.getText().toString().length() == 0)
                    error += " | Cost Price ";
                if (sp.getText().toString().length() == 0)
                    error += " | Selling Price ";

                if (error.length() == 0) {
                    float c = Float.parseFloat(cp.getText().toString());
                    float s = Float.parseFloat(sp.getText().toString());
                    productNew p = new productNew(name.getText().toString(), c, s, 0);
                    if (operation == 1)
                        listener.onDialogPositiveClick(ItemAMFragment.this, p);
                    else
                        listener.onDialogModifyPositiveClick(ItemAMFragment.this, p, (int) (position.getid()));
                } else {
                    Toast.makeText(view.getContext(), "Please enter: " + error, Toast.LENGTH_LONG).show();
                }
            }
            }
        });
        promptView.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDialogNegativeClick(ItemAMFragment.this);
            }
        });
        return promptView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    public interface ItemAMListener{
        public void onDialogPositiveClick(DialogFragment dialog,productNew temp);
        public void onDialogNegativeClick(DialogFragment dialog);
        public void onDialogModifyPositiveClick(DialogFragment dialog,productNew temp,int pos);
        public void onDialogDeletePositiveClick(DialogFragment dialog, int position);
    }

    // Use this instance of the interface to deliver action events
    ItemAMListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ItemAMListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement ItemAMListener");
        }
    }
}
