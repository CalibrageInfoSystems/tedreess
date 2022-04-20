package com.example.tadreess.Student.ClassDetailsAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.ClassDetailsActivity;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassAdapter;
import com.example.tadreess.Student.EnterLiveClassAdapter.EnterLiveClassGetSet;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleAdapter;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleGetSet;
import com.example.tadreess.Student.MyTransectionAdapter.MyTransectionAdapter;
import com.example.tadreess.Student.MyTransectionAdapter.MyTransectionGetSet;
import com.example.tadreess.Student.WebActivity;

import java.util.List;

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ViewHolder> {

    Context context;


    List<ClassDetailsGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";

    String strUserId = "";

    String strUrl = "";


    int abc = 0;


    public ClassDetailsAdapter(List<ClassDetailsGetSet> getDataAdapter, Context context) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ClassDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classdetails_adapter, parent, false);

        ClassDetailsAdapter.ViewHolder viewHolder = new ClassDetailsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ClassDetailsAdapter.ViewHolder Viewholder, final int position) {

        final ClassDetailsGetSet dataAdapterOBJ = dataAdapters.get(position);


        Viewholder.txtTutorName.setText(dataAdapterOBJ.getTutor());
        Viewholder.txtLoginDate.setText(dataAdapterOBJ.getLoginDate());
        Viewholder.txtLoginTime.setText(dataAdapterOBJ.getLoginTime());


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTutorName, txtLoginDate, txtLoginTime;


        // public WebView webView;

        public ViewHolder(View itemView) {


            super(itemView);
            txtTutorName = itemView.findViewById(R.id.txtTutorName);
            txtLoginDate = itemView.findViewById(R.id.txtLoginDate);
            txtLoginTime = itemView.findViewById(R.id.txtLoginTime);


        }
    }
}


