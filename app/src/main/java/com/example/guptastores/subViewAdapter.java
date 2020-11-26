package com.example.guptastores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class subViewAdapter extends RecyclerView.Adapter<subViewAdapter.subView> {
    Activity context;
    ArrayList<subType> subList;
    public subViewAdapter(Activity activity, ArrayList<subType> list){
        context=activity;
        subList=list;
    }
    @NonNull
    @Override
    public subViewAdapter.subView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new subView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull subViewAdapter.subView holder, int position) {
        subType st=subList.get(position);
        holder.name.setText(st.getType());
        holder.cp.setText(st.getCp()+"");
        holder.sp.setText(st.getSp()+"");
    }

    @Override
    public int getItemCount() {
        return subList.size();
    }
    public class subView extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView cp;
        private TextView sp;

        public subView(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            cp=itemView.findViewById(R.id.cost);
            sp=itemView.findViewById(R.id.sell);
        }
    }
}
