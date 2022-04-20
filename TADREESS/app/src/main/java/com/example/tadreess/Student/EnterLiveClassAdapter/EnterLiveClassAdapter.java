package com.example.tadreess.Student.EnterLiveClassAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.AppsContants;
import com.example.tadreess.IEnterLiveClassListner;
import com.example.tadreess.R;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageAdapter;
import com.example.tadreess.Student.OurPackageAdapter.OurPackageGetSet;
import com.example.tadreess.Student.WebActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EnterLiveClassAdapter extends RecyclerView.Adapter<EnterLiveClassAdapter.ViewHolder> {

    Context context;


    List<EnterLiveClassGetSet> dataAdapters;
    String fileName = "";
    String fileActualPath = "";
    String strImage = "";

    String strUserId="";

    String strUrl="";


    int abc = 0;


    private IEnterLiveClassListner liveClassListner;

    public EnterLiveClassAdapter(List<EnterLiveClassGetSet> getDataAdapter, Context context,IEnterLiveClassListner liveClassListner) {

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
        this.liveClassListner = liveClassListner;
    }

    @Override
    public EnterLiveClassAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enterliveclass_adapter, parent, false);

        EnterLiveClassAdapter.ViewHolder viewHolder = new EnterLiveClassAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final EnterLiveClassAdapter.ViewHolder Viewholder, final int position) {

        final EnterLiveClassGetSet dataAdapterOBJ = dataAdapters.get(position);

       // WebSettings webSettings = Viewholder.webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);

        AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Viewholder.txtSubject.setText(dataAdapterOBJ.getSubject());
        Viewholder.txtTutorName.setText(dataAdapterOBJ.getTutor());
        Viewholder.txtTime.setText(dataAdapterOBJ.getStartTime()+"_"+dataAdapterOBJ.getEndTime());

        Viewholder.btnEnterClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* AppsContants.sharedpreferences = context.getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                editor.putString(AppsContants.TotalQuestions, dataAdapterOBJ.getIsActive());
                editor.commit();
*/

//                Toast.makeText(context, "On Click", Toast.LENGTH_SHORT).show();
//                context.startActivity(new Intent(context, WebActivity.class));
                liveClassListner.onEnterClick(dataAdapterOBJ);







            }
        });



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSubject, txtTutorName, txtTime;
        public Button btnEnterClass;

       // public WebView webView;

        public ViewHolder(View itemView) {


            super(itemView);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtTutorName = itemView.findViewById(R.id.txtTutorName);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnEnterClass = itemView.findViewById(R.id.btnEnterClass);
            // webView = itemView.findViewById(R.id.webView);



        }
    }

}