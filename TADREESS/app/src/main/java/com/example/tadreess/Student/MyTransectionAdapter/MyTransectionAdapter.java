package com.example.tadreess.Student.MyTransectionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tadreess.R;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleAdapter;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleGetSet;

import java.util.List;

public class MyTransectionAdapter extends RecyclerView.Adapter<MyTransectionAdapter.ViewHolder> {

    Context context;


    List<MyTransectionGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";



    int abc = 0;


    public MyTransectionAdapter(List<MyTransectionGetSet> getDataAdapter, Context context) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public MyTransectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytransection_adapter, parent, false);

        MyTransectionAdapter.ViewHolder viewHolder = new MyTransectionAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyTransectionAdapter.ViewHolder Viewholder, final int position) {

        final MyTransectionGetSet dataAdapterOBJ = dataAdapters.get(position);





        //Viewholder.txtRecharge.setText( dataAdapterOBJ.getTitle());
        Viewholder.txtTimeDate.setText( dataAdapterOBJ.getDatetime());
        Viewholder.txtTotalBalance.setText( dataAdapterOBJ.getAmount());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtRecharge, txtTimeDate, txtTotalBalance;




        public ViewHolder(View itemView) {


            super(itemView);
            txtRecharge = itemView.findViewById(R.id.txtRecharge);
            txtTimeDate = itemView.findViewById(R.id.txtTimeDate);
            txtTotalBalance = itemView.findViewById(R.id.txtTotalBalance);




        }
    }


}