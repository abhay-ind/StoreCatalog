package com.example.guptastores;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subTypeAdapter extends RecyclerView.Adapter<subTypeAdapter.subView> {
    Activity context;
    ArrayList<subType> subList;
    public subTypeAdapter(Activity c, ArrayList<subType> list){
        context=c;
        subList=list;
    }

    @NonNull
    @Override
    public subTypeAdapter.subView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.subtype,parent,false);
        return new subView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final subTypeAdapter.subView holder, final int position) {

        if(subList.get(position).getType().length()!=0 ||subList.get(position).getCp()>0 ||subList.get(position).getSp()>0)
        {
            holder.name.setText(subList.get(position).getType());
            holder.cp.setText(""+subList.get(position).getCp());
            holder.sp.setText(""+subList.get(position).getSp());
        }
        holder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float cp,sp;
                if(holder.cp.getText().toString().trim().length()==0)
                    cp=-1;
                else
                    cp=Float.parseFloat(holder.cp.getText().toString());
                if(holder.sp.getText().toString().trim().length()==0)
                    sp=-1;
                else
                    sp=Float.parseFloat(holder.sp.getText().toString());
                subType temp=new subType(holder.name.getText().toString(),cp,sp);
                subList.set(position,temp);
            }
        });
        holder.cp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float cp,sp;
                if(holder.cp.getText().toString().trim().length()==0)
                    cp=-1;
                else
                    cp=Float.parseFloat(holder.cp.getText().toString());
                if(holder.sp.getText().toString().trim().length()==0)
                    sp=-1;
                else
                    sp=Float.parseFloat(holder.sp.getText().toString());
                subType temp=new subType(holder.name.getText().toString(),cp,sp);
                subList.set(position,temp);
            }
        });
        holder.sp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float cp,sp;
                if(holder.cp.getText().toString().trim().length()==0)
                    cp=-1;
                else
                    cp=Float.parseFloat(holder.cp.getText().toString());
                if(holder.sp.getText().toString().trim().length()==0)
                    sp=-1;
                else
                    sp=Float.parseFloat(holder.sp.getText().toString());
                subType temp=new subType(holder.name.getText().toString(),cp,sp);
                subList.set(position,temp);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subList.size();
    }
    public class subView extends RecyclerView.ViewHolder{
        private EditText name;
        private EditText cp;
        private EditText sp;
        private CardView cv;

        public subView(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.subName);
            cp=itemView.findViewById(R.id.subCP);
            sp=itemView.findViewById(R.id.subSP);
            cv=itemView.findViewById(R.id.details);

        }
    }
}
