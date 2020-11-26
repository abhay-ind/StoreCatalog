package com.example.guptastores;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.guptastores.R;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemView> {
    private Activity context;
    private ArrayList<productNew> db;
    private ArrayList<productNew> dbSorted;
    private FragmentManager fragMan;
    public ItemAdapter(@NonNull Activity context, ArrayList<productNew> db) {
        this.context=context;
        this.db=db;
        this.dbSorted=new ArrayList<productNew>(db);
        Collections.sort(dbSorted,new ItemComparator());
        this.fragMan=((FragmentActivity)context).getSupportFragmentManager();
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_display,parent,false);
        return new ItemView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemView holder, final int position) {
        holder.name.setText(dbSorted.get(position).getproductName());
        holder.cp.setText(dbSorted.get(position).getcostPrice()+"");
        holder.sp.setText(dbSorted.get(position).getsellingPrice()+"");
        holder.slNo.setText((position+1)+".");
        if(dbSorted.get(position).anySubType())
        {
            holder.name.setTextColor(Color.rgb(112,48,160));
            holder.cp.setText("");
            holder.sp.setText("");
        }
        else {
            holder.name.setTextColor(Color.rgb(138,138,138));
        }


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogFragment v=new ItemAMFragment(2,dbSorted.get(position));
                v.show(fragMan,"modify");
                return false;
            }
        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogFragment v=new ItemAMFragment(2,dbSorted.get(position));
                v.show(fragMan,"modify");
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbSorted.get(position).anySubType()){
                    if(holder.rv.getVisibility()==View.GONE)
                    {holder.rv.setVisibility(View.VISIBLE);
                    holder.rv.setAdapter(new subViewAdapter(context,dbSorted.get(position).getallType()));
                    }
                    else
                        holder.rv.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dbSorted.size();
//        return 0;
    }
    public class ItemView extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView cp;
        private TextView sp;
        private RecyclerView rv;
        private CardView cv;
        private TextView slNo;
        public ItemView(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            cp=itemView.findViewById(R.id.cost);
            sp=itemView.findViewById(R.id.sell);
            rv=itemView.findViewById(R.id.subProdList);
            rv.setLeft(0);
            rv.setRight(0);
            cv=itemView.findViewById(R.id.mainLayout);
            slNo=itemView.findViewById(R.id.SlNo);
            rv.setHasFixedSize(true);
            rv.setClickable(false);
            rv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
