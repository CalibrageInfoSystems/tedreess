package com.example.tadreess.Student.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tadreess.R;


public class ContactUsFragment extends Fragment {

    RelativeLayout relative;

    EditText edtName,edtEmail,edtPhone,edtMesage;

    Button btnSubmit,btnReset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        relative = view.findViewById(R.id.relative);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtMesage = view.findViewById(R.id.edtMesage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnReset = view.findViewById(R.id.btnReset);


        YoYo.with(Techniques.FadeIn)
                .duration(1100)
                .repeat(0)
                .playOn(view.findViewById(R.id.relative));



    return view;

    }


}
