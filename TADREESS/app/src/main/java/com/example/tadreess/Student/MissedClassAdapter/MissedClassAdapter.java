package com.example.tadreess.Student.MissedClassAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tadreess.R;
import com.example.tadreess.Student.ClassDetailsAdapter.ClassDetailsAdapter;
import com.example.tadreess.Student.ClassDetailsAdapter.ClassDetailsGetSet;

import java.util.List;

public class MissedClassAdapter extends RecyclerView.Adapter<MissedClassAdapter.ViewHolder> {

    Context context;


    List<MissedClassGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";

    String strUserId = "";

    String strUrl = "";


    int abc = 0;


    public MissedClassAdapter(List<MissedClassGetSet> getDataAdapter, Context context) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public MissedClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.missedclass_adapter, parent, false);

        MissedClassAdapter.ViewHolder viewHolder = new MissedClassAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MissedClassAdapter.ViewHolder Viewholder, final int position) {

        final MissedClassGetSet dataAdapterOBJ = dataAdapters.get(position);



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


