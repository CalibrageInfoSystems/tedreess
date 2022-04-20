package com.example.tadreess.Student.OurPackageAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.PackageDetailsActivity;

import java.util.List;

public class OurPackageAdapter extends RecyclerView.Adapter<OurPackageAdapter.ViewHolder> {

    Context context;


    List<OurPackageGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";



    int abc = 0;


    public OurPackageAdapter(List<OurPackageGetSet> getDataAdapter, Context context) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public OurPackageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ourpackage_adapter, parent, false);

        OurPackageAdapter.ViewHolder viewHolder = new OurPackageAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OurPackageAdapter.ViewHolder Viewholder, final int position) {

        final OurPackageGetSet dataAdapterOBJ = dataAdapters.get(position);



        AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
        editor.putString(AppsContants.PackageId, dataAdapterOBJ.getPackageId());
        editor.putString(AppsContants.ScheduleId, dataAdapterOBJ.getScheduleId());
        editor.commit();



        Viewholder.txtPackage.setText((CharSequence) dataAdapterOBJ.getName());
        Viewholder.txtSubject.setText((CharSequence) dataAdapterOBJ.getSubjectName());
        Viewholder.txtTime.setText( dataAdapterOBJ.getDuration()+" "+"mins");
        Viewholder.txtScheduleName.setText(dataAdapterOBJ.getScheduleName());
        Viewholder.txtStatus.setText(dataAdapterOBJ.getScheduleStatus());

        Viewholder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.PackageId, dataAdapterOBJ.getPackageId());
                editor.putString(AppsContants.ScheduleId, dataAdapterOBJ.getScheduleId());
                editor.commit();



                context.startActivity(new Intent(context, PackageDetailsActivity.class));

            }
        });


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPackage, txtSubject, txtTime, txtScheduleName, txtStatus;

        public RelativeLayout relative;


        public ViewHolder(View itemView) {


            super(itemView);
            txtPackage = itemView.findViewById(R.id.txtPackage);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtScheduleName = itemView.findViewById(R.id.txtScheduleName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            relative = itemView.findViewById(R.id.relative);



        }
    }


}

