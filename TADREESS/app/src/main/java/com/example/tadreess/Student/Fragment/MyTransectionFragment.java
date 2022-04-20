package com.example.tadreess.Student.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.EnterSecretKey;
import com.example.tadreess.R;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleAdapter;
import com.example.tadreess.Student.MyScheduleAdapter.MyScheduleGetSet;
import com.example.tadreess.Student.MyTransectionAdapter.MyTransectionAdapter;
import com.example.tadreess.Student.MyTransectionAdapter.MyTransectionGetSet;
import com.example.tadreess.Student.PaymentConfirmationActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyTransectionFragment extends Fragment {
    RecyclerView recyclerview1;
    List<MyTransectionGetSet> arrayList;
    MyTransectionAdapter adapter;
    ProgressBar pBar;

    String strStudentId="";

    Button btnRecharge;


    TextView txtMyBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_transection, container, false);


        pBar = view.findViewById(R.id.pBar);
        recyclerview1 = view.findViewById(R.id.recyclerview1);
        txtMyBalance = view.findViewById(R.id.txtMyBalance);
        btnRecharge = view.findViewById(R.id.btnRecharge);

        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dailog_transection);
                // dialog.setTitle("Title...");


                Button btnCoupon= (Button) dialog.findViewById(R.id.btnCoupon);
                Button btnknet= (Button) dialog.findViewById(R.id.btnknet);

                btnCoupon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), EnterSecretKey.class));
                    }
                });
                btnknet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), PaymentConfirmationActivity.class));
                    }
                });




                dialog.show();

            }
        });


        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strStudentId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("djfgueryjhdfewr",strStudentId);

        MySchedule() ;
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new GridLayoutManager(getActivity(), 1));



        return view;




    }

    public void MySchedule() {
        pBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.TransectionHistory+"userid=" + strStudentId + "")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("fghjghjkfghjfg", response.toString());

                        try {

                            pBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);


                                arrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {






                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    String strMyBalance = jsonObject.getString("Totalamount");

                                    txtMyBalance.setText("My Bal:"+" "+strMyBalance);







                                    MyTransectionGetSet GetSet = new MyTransectionGetSet();
                                    GetSet.setAmount(jsonObject.getString("Amount"));
                                    GetSet.setDatetime(jsonObject.getString("Datetime"));
                                    GetSet.setTotalamount(jsonObject.getString("Totalamount"));
                                    GetSet.setTransactionId(jsonObject.getString("TransactionId"));
                                    GetSet.setTransactiontype(jsonObject.getString("Transactiontype"));
                                    GetSet.setUserId(jsonObject.getString("UserId"));

                                    arrayList.add(GetSet);


                                }

                                adapter = new MyTransectionAdapter(arrayList, getActivity());
                                recyclerview1.setAdapter(adapter);
                                pBar.setVisibility(View.GONE);
                                Log.e("tracing", arrayList.size() + "");


                                Toast.makeText(getActivity(), "Transection history", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                pBar.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "you don,t have any transection history", Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            pBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }


}
