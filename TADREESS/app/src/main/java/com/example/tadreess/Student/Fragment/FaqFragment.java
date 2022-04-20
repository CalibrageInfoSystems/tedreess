package com.example.tadreess.Student.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.tadreess.R;


public class FaqFragment extends Fragment {

RelativeLayout relativeMain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);

        YoYo.with(Techniques.FadeIn)
                .duration(1100)
                .repeat(0)
                .playOn(view.findViewById(R.id.relativeMain));



        return view;
    }


}
