package com.example.tadreess.Student.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tadreess.API;
import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.NavigationActivity;
import com.example.tadreess.Student.OurPackageAdapter.StudentUpdateProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class UpdateProfileFragment extends Fragment {
    public static final String TAG = UpdateProfileFragment.class.getSimpleName();
    EditText edtFullName, edtLastName, edtEmail, edtDob, edtNationality, edtDescription;

    String strFullName = "", strLastName = "", strEmail = "", strDob = "", strNationality = "", strDescription = "",strFullname ;


    EditText edtSchoolName, edtFatherPhone, edtPhone, edtFatherEmail, edtFatherName;

    String strSchoolName = "", strFatherPhone, strPhone = "", strFatherEmail = "", strFatherName = "";

    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;

    ImageView btnback;

    String strGender = "";

    String strNationalityyy="";

    File file1;
    String fileName = "";
    String fileActualPath = "";
    String filepath = android.os.Environment.getExternalStorageDirectory() + File.separator + "Uimage" + File.separator;


    List<String> arrayListStageId;
    List<String> arrayListStageName;


    List<String> arrayListSubjectId;
    List<String> arraListSubjectName;

    ProgressBar progressBar;

    Spinner spinStage, spinSubject;

    String strAddress="",strSirName="";

    ImageView imgProfile;

    LinearLayout linearFirst, linearSecond;

    String strUserType = "";
    Button btnRegister, btnContnue2;

    String strStageId = "", strStageName = "", strSubjectId = "", strSubjectName = "";

    String strUserId = "", stringGender = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioMale = view.findViewById(R.id.radioMale);
        radioFemale = view.findViewById(R.id.radioFemale);
        btnback = view.findViewById(R.id.btnback);
        imgProfile = view.findViewById(R.id.imgProfile);

        //cristinaalberd1803

        AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("sdjfshdjkfsfs", strUserId);

        edtFullName = view.findViewById(R.id.edtFullName);
        edtLastName = view.findViewById(R.id.edtLastName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtDob = view.findViewById(R.id.edtDob);
        edtNationality = view.findViewById(R.id.edtNationality);
        edtDescription = view.findViewById(R.id.edtDescription);
        spinStage = view.findViewById(R.id.spinStage);
        spinSubject = view.findViewById(R.id.spinSubject);
        btnRegister = view.findViewById(R.id.btnRegister);
        linearFirst = view.findViewById(R.id.linearFirst);
        linearSecond = view.findViewById(R.id.linearSecond);


        edtSchoolName = view.findViewById(R.id.edtSchoolName);
        edtFatherPhone = view.findViewById(R.id.edtFatherPhone);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtFatherEmail = view.findViewById(R.id.edtFatherEmail);
        btnContnue2 = view.findViewById(R.id.btnContnue2);
        edtFatherName = view.findViewById(R.id.edtFatherName);
        progressBar = view.findViewById(R.id.progressBar);

        new ShowStage().execute(API.GetStages+"cultureid=1");
        new ShowSubject().execute(API.ShowSubject+"cultureid=1");
       new ShowProfile().execute(API.ShowProfile + "userid=" + strUserId + "&cultureid=1");

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        btnContnue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strFullName = edtFullName.getText().toString().trim();
                strLastName = edtLastName.getText().toString().trim();
                strEmail = edtEmail.getText().toString().trim();
                strDob = edtDob.getText().toString().trim();
                strNationality = edtNationality.getText().toString().trim();
                strDescription = edtDescription.getText().toString().trim();


                strSchoolName = edtSchoolName.getText().toString().trim();
                strFatherName = edtFatherName.getText().toString().trim();
                strFatherPhone = edtFatherPhone.getText().toString().trim();
                strPhone = edtPhone.getText().toString().trim();
                strFatherEmail = edtFatherEmail.getText().toString().trim();

                UpdateProfile();


            }
        });


        spinStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strStageId = arrayListStageId.get(i);
                strStageName = arrayListStageName.get(i);


                Log.e("gfjhfgjhdg", strStageId);
                Log.e("gfjhfgjhdg", strStageName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(getActivity(), "Admin[position]", Toast.LENGTH_SHORT).show();

            }
        });
        spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                strSubjectId = arrayListSubjectId.get(position);
                strSubjectName = arrayListStageName.get(position);


                Log.e("jdgfjdgjhdgfjh", strSubjectId);
                Log.e("jdgfjdgjhdgfjh", strSubjectName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Admin[position]", Toast.LENGTH_SHORT).show();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.VISIBLE);

               // UpdateProfile();

                // Profile();

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected


                if (radioMale.isChecked()) {

                    strUserType = "Male";


                } else if (radioFemale.isChecked()) {
                    strUserType = "Female";


                } else {

                }


            }
        });

        return view;


    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileName = System.currentTimeMillis() + ".jpg";
                    File style = new File(filepath);
                    if (!style.exists()) {
                        style.mkdir();
                    }
                    File f = new File(filepath, fileName);
                    fileActualPath = f.getAbsolutePath();
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                imgProfile.setImageURI(uri);
                String image_up = getPath(uri);

                Bitmap image = (Bitmap) data.getExtras().get("data");
                fileName = "DriverImage" + System.currentTimeMillis() + ".jpg";
                File style = new File(filepath);
                if (!style.exists()) {
                    style.mkdir();
                }
                file1 = new File(filepath, fileName);
                Log.e("fghhghghghg" + file1, "fghhghghghg");
                fileActualPath = file1.getAbsolutePath();
                FileOutputStream fo;
                System.out.println("datatata******** " + " Come " + image);

                try {
                    fo = new FileOutputStream(file1);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    int height = image.getHeight();
                    int width = image.getWidth();
                    imgProfile.setImageBitmap(image);
                    Uri selectedImage = getImageUri(getActivity(), image);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception" + e.getMessage(), "");
                }

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                // mDetials123.add(picturePath);
                file1 = new File(picturePath);
                Log.e("imageFile", file1 + "");
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                imgProfile.setImageBitmap(thumbnail);
            }
        }
    }

    private String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public void ShowProfile() {
        progressBar.setVisibility(View.VISIBLE);
        //  AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/LoginUser?Username="+strEmail+"&password="+strPassword+"&cultureid=1")
        AndroidNetworking.post(API.ShowProfile+"userid=" + strUserId + "&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("jgjdgretuiydfjh", response.toString());

                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strAddress = jsonObject.getString("Address");
                                    String strDob = jsonObject.getString("Dob");
                                    String strEmail = jsonObject.getString("Email");
                                    String strFatherMail = jsonObject.getString("FathersMail");
                                    String strFatherName = jsonObject.getString("FathersName");
                                    String strFatherPhone = jsonObject.getString("FathersPhone");
                                    String strFullname = jsonObject.getString("FullName");
                                    strGender = jsonObject.getString("Gender");
                                    String strLastName = jsonObject.getString("LastName");
                                    String strNationality = jsonObject.getString("Nationality");
                                    String strPhone = jsonObject.getString("Phone");
                                    String strPhoto = jsonObject.getString("Photo");
                                    String strSchoolName = jsonObject.getString("SchoolName");
                                    String strSirName = jsonObject.getString("SirName");
                                    String strStage = jsonObject.getString("Stage");
                                    String strSubject = jsonObject.getString("Subject");
                                    String strUserId = jsonObject.getString("UserId");


                                    if (strGender.equals("1")) {

                                        radioMale.setChecked(true);

                                    } else {

                                        radioFemale.setChecked(true);
                                    }


                                    edtDob.setText(strDob);
                                    edtEmail.setText(strEmail);
                                    edtFatherEmail.setText(strFatherMail);
                                    edtFatherName.setText(strFatherName);
                                    edtFatherPhone.setText(strFatherPhone);
                                    edtFullName.setText(strFullname);
                                    edtLastName.setText(strLastName);
                                    edtNationality.setText(strNationality);
                                    edtPhone.setText(strPhone);
                                    edtSchoolName.setText(strSchoolName);









                                  /*  txtSubject.setText(strSubject);
                                    txtTutorName.setText(strTutor);
                                    txtStudentName.setText(strName);
                                    txtTime.setText(strStartTime+"-"+strEndTime);*/

                                }


                               // Toast.makeText(getActivity(), "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                progressBar.setVisibility(View.GONE);

                               // Toast.makeText(getActivity(), "" + str, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("hjsdfahfasdjhfasd", anError.toString());

                    }

                });
    }


    public void ShowStage() {

        AndroidNetworking.post(API.GetStages+"cultureid=1")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("myresponse", response.toString());
                        arrayListStageId = new ArrayList<>();
                        arrayListStageName = new ArrayList<>();
                        try {


                            if (response.has("Status")) {
                                if (response.getString("Status").equals("1")) {

                                    String strResult = response.getString("Result");
                                    JSONArray jsonArray = new JSONArray(strResult);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String strStageId = jsonObject.getString("SatgeId");
                                        String strStageName = jsonObject.getString("Name");


                                        arrayListStageName.add(strStageName);
                                        arrayListStageId.add(strStageId);

                                    }

                                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrayListStageName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinStage.setAdapter(aa);

                                } else {


                                   // Toast.makeText(getActivity(), "Unsuccess", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {

                            // Toast.makeText(TeacherRegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            Log.e("dhgfhjdgfjhdgf", e.getMessage());


                        }


                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });

    }

    public void ShowSubject() {

        AndroidNetworking.post(API.ShowSubject+"cultureid=1")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("myresponse", response.toString());
                        arrayListSubjectId = new ArrayList<>();
                        arraListSubjectName = new ArrayList<>();
                        try {


                            if (response.has("Status")) {
                                if (response.getString("Status").equals("1")) {

                                    String strResult = response.getString("Result");
                                    JSONArray jsonArray = new JSONArray(strResult);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String strSubjectName = jsonObject.getString("Subject");
                                        String strSubjectId = jsonObject.getString("SubjectId");


                                        arraListSubjectName.add(strSubjectName);
                                        arrayListSubjectId.add(strSubjectId);

                                    }

                                    ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arraListSubjectName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinSubject.setAdapter(aa);

                                } else {


                                   // Toast.makeText(getActivity(), "Unsuccess", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {

                            // Toast.makeText(TeacherRegisterActivity.this, "" + response, Toast.LENGTH_SHORT).show();

                            Log.e("dhgfhjdgfjhdgf", e.getMessage());


                        }


                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        //UpdateProfile();
    }


    public void UpdateProfile() {
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jObj = new JSONObject();

        try {


            jObj.put("FathersPhone", strFatherPhone);
            jObj.put("Subject", strSubjectId);
            jObj.put("SirName", strSirName);
            jObj.put("Email", strEmail);
            jObj.put("Photo", "photo");
            jObj.put("Nationality", strNationality);
            jObj.put("FathersMail", strFatherEmail);
            jObj.put("UserId", strUserId);
            jObj.put("FathersName", strFatherName);
            jObj.put("Phone", strPhone);
            jObj.put("FullName", strFullName);
            jObj.put("Address", strAddress);
            jObj.put("Stage", strStageId);
            jObj.put("Gender", strGender);
            jObj.put("SchoolName", strSchoolName);
            jObj.put("LastName", strLastName);
            jObj.put("Dob", strDob);


            AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
            editor.putString(AppsContants.Nationality, strNationality);
            editor.commit();


            Log.e("jdfgdfjkghjfghjfgh", strNationality);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("sdfshdfhsdf", jObj.toString());


       // AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/EditProfile?")
        AndroidNetworking.post(API.UpdateProfile)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jObj)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.e("wetwetwefsdvv", response.toString());
                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strStageId = jsonObject.getString("StageId");
                                    String strUserId = jsonObject.getString("UserId");
                                    String strStatus = jsonObject.getString("Userstatus");


                                }

                                if (strGender.equals("1")){

                                    radioMale.setChecked(true);

                                }
                                else{

                                    radioFemale.setChecked(true);
                                }


                                AppsContants.sharedpreferences = getActivity().getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                 strNationalityyy = AppsContants.sharedpreferences.getString(AppsContants.Nationality, "");


                                edtFatherPhone.setText(strFatherPhone);
                                edtFullName.setText(strFullName);
                                edtEmail.setText(strEmail);
                                edtNationality.setText(strNationality);
                                edtFatherEmail.setText(strFatherEmail);
                                edtFatherName.setText(strFatherName);
                                edtPhone.setText(strPhone);

                                //spinStage.setAutofillHints(strStageId);

                                Log.e("fghhjdgfjhdgfjdsghf", strNationality);


                                 spinStage.setSelection(Integer.parseInt((strStageId)));
                                spinSubject.setSelection(Integer.parseInt(strSubjectId));
                                edtSchoolName.setText(strSchoolName);
                                edtLastName.setText(strLastName);
                                edtDob.setText(strDob);

                                radioMale.setChecked(Boolean.parseBoolean(strGender));
                                radioFemale.setChecked(Boolean.parseBoolean(strGender));

                                Toast.makeText(getActivity(), "Successfully Update", Toast.LENGTH_SHORT).show();

                                // startActivity(new Intent(StudentProfile.this, StudentProfile.class));


                            } else {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Update failed" , Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }


                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("dhgfhjdgfjhdgf", error.getMessage());

                    }
                });
    }

    private class ShowProfile extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "ShowProfile API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "ShowProfile API URL:" + APIURL);
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(APIURL)).openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                final StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                // some XML String previously created

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String Status = "";
            String xmlString = s;
            Log.i(TAG, "On ShowSubject Responce :" + xmlString);
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput( new StringReader(xmlString) );
                String name=xpp.getName();// pass input whatever xml you have
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = xpp.getName();
                    switch (eventType) {

                        case XmlPullParser.TEXT:
                            name = xpp.getText();
                            Log.d(TAG,"Start tag "+xpp.getName());
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("Dob")) {
                                Log.d(TAG,"user_id==322==="+name);
                                strDob =name;

                                // builder.append("TAG1 = " + name);
                            } else if (tagname.equalsIgnoreCase("Email")) {
                                strEmail =name;
                                //builder.append("\nTAG2 = " + name);
                            }
                            else if (tagname.equalsIgnoreCase("FullName")) {
                                strFullname = name;
                            }
                            else if (tagname.equalsIgnoreCase("FathersName")) {
                                strFatherName = name;
                            }
                            else if (tagname.equalsIgnoreCase("FathersPhone")) {
                                strFatherPhone = name;
                            }
                            else if (tagname.equalsIgnoreCase("LastName")) {
                                strLastName = name;
                            } else if (tagname.equalsIgnoreCase("Nationality")) {
                                strNationality = name;
                            }
                            else if (tagname.equalsIgnoreCase("Phone")) {
                                strPhone = name;
                            }
                            else if (tagname.equalsIgnoreCase("SchoolName")) {
                                strSchoolName = name;
                            }
                            else if (tagname.equalsIgnoreCase("Gender")) {
                                strGender = name;
                            }


                            break;

                        default:
                            break;
                    }
                    eventType = xpp.next();
                }


                Log.d(TAG,"Start tag 325"+xpp.getName());
                Log.d(TAG,"End document");

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (strGender.equals("1")) {

                radioMale.setChecked(true);

            } else {

                radioFemale.setChecked(true);
            }


            edtDob.setText(strDob);
            edtEmail.setText(strEmail);
            // edtFatherEmail.setText(strFatherMail);
            edtFatherName.setText(strFatherName);
            edtFatherPhone.setText(strFatherPhone);
            edtFullName.setText(strFullname);
            edtLastName.setText(strLastName);
            edtNationality.setText(strNationality);
            edtPhone.setText(strPhone);
            edtSchoolName.setText(strSchoolName);



        }
    }





    private class ShowSubject extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "ShowSubject API Call");
        }

        @Override
        protected String doInBackground(String... strings) {
            String APIURL = strings[0];
            Log.i(TAG, "ShowSubject API URL:" + APIURL);
            BufferedReader br = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(APIURL)).openConnection();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;
                final StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }


                // some XML String previously created

                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String Status = "";


            String xmlString = s;
            Log.i(TAG, "On ShowSubject Responce :" + xmlString);
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput( new StringReader(xmlString) );
                String name=xpp.getName();// pass input whatever xml you have
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = xpp.getName();
                    switch (eventType) {

                        case XmlPullParser.TEXT:
                            name = xpp.getText();
                            Log.d(TAG,"Start tag "+xpp.getName());
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("Dob")) {
                                Log.d(TAG,"user_id==322==="+name);
                                strDob =name;

                                // builder.append("TAG1 = " + name);
                            } else if (tagname.equalsIgnoreCase("Email")) {
                                strEmail =name;
                                //builder.append("\nTAG2 = " + name);
                            }
                            else if (tagname.equalsIgnoreCase("FullName")) {
                                strFullname = name;
                            }
                                else if (tagname.equalsIgnoreCase("FathersName")) {
                                strFatherName = name;
                            }
                            else if (tagname.equalsIgnoreCase("FathersPhone")) {
                                strFatherPhone = name;
                            }
                            else if (tagname.equalsIgnoreCase("LastName")) {
                                strLastName = name;
                            } else if (tagname.equalsIgnoreCase("Nationality")) {
                                strNationality = name;
                            }
                            else if (tagname.equalsIgnoreCase("Phone")) {
                                strPhone = name;
                            }
                            else if (tagname.equalsIgnoreCase("SchoolName")) {
                                strSchoolName = name;
                            }
                            else if (tagname.equalsIgnoreCase("Gender")) {
                                strGender = name;
                            }


                            break;

                        default:
                            break;
                    }
                    eventType = xpp.next();
                }


                Log.d(TAG,"Start tag 325"+xpp.getName());
                Log.d(TAG,"End document");

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (strGender.equals("1")) {

                radioMale.setChecked(true);

            } else {

                radioFemale.setChecked(true);
            }


            edtDob.setText(strDob);
            edtEmail.setText(strEmail);
           // edtFatherEmail.setText(strFatherMail);
            edtFatherName.setText(strFatherName);
            edtFatherPhone.setText(strFatherPhone);
            edtFullName.setText(strFullname);
            edtLastName.setText(strLastName);
            edtNationality.setText(strNationality);
            edtPhone.setText(strPhone);
            edtSchoolName.setText(strSchoolName);



        }
}

    private class ShowStage extends AsyncTask<String,String,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i(TAG, "ShowStage API Call");
            }

            @Override
            protected String doInBackground(String... strings) {
                String APIURL = strings[0];
                Log.i(TAG, "ShowStage API URL:" + APIURL);
                BufferedReader br = null;
                try {
                    HttpURLConnection conn = (HttpURLConnection) (new URL(APIURL)).openConnection();
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line;
                    final StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }


                    // some XML String previously created

                    return sb.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    try {
                        if (br != null) br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String Status = "";


                String xmlString = s;
                Log.i(TAG, "On ShowStage Responce :" + xmlString);

            }
    }
    }


  /*  public void UpdateImage() {

        JSONObject jObj = new JSONObject();

        try {


            jObj.put("UserId", strUserId);
            jObj.put("FileName", file1);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.upload("http://168.187.113.181:813/TadreesService.svc/InsertImage?")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                    }
                })
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("dfhkdhfdksjfhjsdfh", response.toString());

                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult = response.getString("Result");
                                JSONArray jsonArray = new JSONArray(strResult);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String strAddress = jsonObject.getString("userid");


                                }


                                Toast.makeText(getActivity(), "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));


                            } else {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getActivity(), "" + str, Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception ex) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("hjsdfahfasdjhfasd", ex.getMessage());

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("gjhjghjghjh", anError.getMessage());

                    }
                });*/



