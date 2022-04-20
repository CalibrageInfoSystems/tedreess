package com.example.tadreess.Student.MyScheduleAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.ClassDetailsActivity;
import com.example.tadreess.Student.MissedClassDetailsActivity;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;
import com.example.tadreess.Student.PackageDetailsActivity;

import java.util.List;

public class MyScheduleAdapter extends RecyclerView.Adapter<MyScheduleAdapter.ViewHolder> {

    Context context;


    List<MyScheduleGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";



    int abc = 0;


    public MyScheduleAdapter(List<MyScheduleGetSet> getDataAdapter, Context context) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public MyScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myschedule_adapter, parent, false);

        MyScheduleAdapter.ViewHolder viewHolder = new MyScheduleAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyScheduleAdapter.ViewHolder Viewholder, final int position) {

        final MyScheduleGetSet dataAdapterOBJ = dataAdapters.get(position);





        Viewholder.txtPackageName.setText( dataAdapterOBJ.getTitle());
        Viewholder.txtTutorName.setText( dataAdapterOBJ.getTutor());
        Viewholder.txtDate.setText( dataAdapterOBJ.getFrom()+" "+"To"+" "+dataAdapterOBJ.getTo());
        Viewholder.txtAvailableSeatts.setText(dataAdapterOBJ.getAvialableSeats());


        Viewholder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.ScheduleId, dataAdapterOBJ.getScheduleID());
                editor.commit();



                context.startActivity(new Intent(context, ClassDetailsActivity.class));

            }
        });

        Viewholder.btnMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.ScheduleId, dataAdapterOBJ.getScheduleID());
                editor.commit();



                context.startActivity(new Intent(context, MissedClassDetailsActivity.class));

            }
        });


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPackageName, txtTutorName, txtDate, txtAvailableSeatts;

        public Button btnComplete,btnMissed;


        public ViewHolder(View itemView) {


            super(itemView);
            txtPackageName = itemView.findViewById(R.id.txtPackageName);
            txtTutorName = itemView.findViewById(R.id.txtTutorName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtAvailableSeatts = itemView.findViewById(R.id.txtAvailableSeatts);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnMissed = itemView.findViewById(R.id.btnMissed);



        }
    }


}
