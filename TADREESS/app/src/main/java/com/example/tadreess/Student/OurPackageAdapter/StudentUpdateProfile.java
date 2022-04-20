package com.example.tadreess.Student.OurPackageAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillValue;
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
import com.example.tadreess.AppsContants;
import com.example.tadreess.R;
import com.example.tadreess.Student.EnterLiveClass;
import com.example.tadreess.Student.StudentProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.view.autofill.AutofillValue.*;

public class StudentUpdateProfile extends AppCompatActivity {
    EditText edtFullName, edtLastName, edtEmail, edtDob, edtNationality, edtDescription;

    String strFullName = "", strLastName = "", strEmail = "", strDob = "", strNationality = "", strDescription = "";


    EditText edtSchoolName, edtFatherPhone, edtPhone, edtFatherEmail, edtFatherName;

    String strSchoolName = "", strFatherPhone, strPhone = "", strFatherEmail = "", strFatherName = "";

    RadioGroup radioGroup;
    RadioButton radioMale, radioFemale;

    ImageView btnback;

    String strGender="";

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

    ImageView imgProfile;

    LinearLayout linearFirst, linearSecond;

    String strUserType = "";
    Button btnRegister, btnContnue2;

    String strStageId = "", strStageName = "", strSubjectId = "", strSubjectName = "";

    String strUserId="",stringGender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_profile);



        radioGroup = findViewById(R.id.radioGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        btnback = findViewById(R.id.btnback);
        imgProfile = findViewById(R.id.imgProfile);

        //cristinaalberd1803

        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strUserId = AppsContants.sharedpreferences.getString(AppsContants.UserId, "");

        Log.e("sdjfshdjkfsfs",strUserId);

        edtFullName = findViewById(R.id.edtFullName);
        edtLastName = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDob = findViewById(R.id.edtDob);
        edtNationality = findViewById(R.id.edtNationality);
        edtDescription = findViewById(R.id.edtDescription);
        spinStage = findViewById(R.id.spinStage);
        spinSubject = findViewById(R.id.spinSubject);
        btnRegister = findViewById(R.id.btnRegister);
        linearFirst = findViewById(R.id.linearFirst);
        linearSecond = findViewById(R.id.linearSecond);


        edtSchoolName = findViewById(R.id.edtSchoolName);
        edtFatherPhone = findViewById(R.id.edtFatherPhone);
        edtPhone = findViewById(R.id.edtPhone);
        edtFatherEmail = findViewById(R.id.edtFatherEmail);
        btnContnue2 = findViewById(R.id.btnContnue2);
        edtFatherName = findViewById(R.id.edtFatherName);
        progressBar = findViewById(R.id.progressBar);
        ShowStage();
        ShowSubject();
        ShowProfile();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

                Toast.makeText(StudentUpdateProfile.this, "Admin[position]", Toast.LENGTH_SHORT).show();

            }
        });
        spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                strSubjectId = arrayListSubjectId.get(position);
                strSubjectName = arrayListStageName.get(position);


                Log.e("jdgfjdgjhdgfjh",strSubjectId);
                Log.e("jdgfjdgjhdgfjh",strSubjectName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(StudentUpdateProfile.this, "Admin[position]", Toast.LENGTH_SHORT).show();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.VISIBLE);

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
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                    Uri selectedImage = getImageUri(this, image);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception" + e.getMessage(), "");
                }

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
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
        Cursor cursor = managedQuery(uri, projection, null, null, null);
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
        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetStudent?userid="+strUserId+"&cultureid=1")
                // AndroidNetworking.post(HTTPURL.Login)
                .addHeaders("Content-Type","application/json")
                .addHeaders("Accept","application/json")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("jgjdgretuiydfjh",response.toString());

                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult=response.getString("Result");
                                JSONArray jsonArray= new JSONArray(strResult);

                                for (int i = 0; i <jsonArray.length() ; i++) {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String strAddress=jsonObject.getString("Address");
                                    String strDob=jsonObject.getString("Dob");
                                    String strEmail=jsonObject.getString("Email");
                                    String strFatherMail=jsonObject.getString("FathersMail");
                                    String strFatherName=jsonObject.getString("FathersName");
                                    String strFatherPhone=jsonObject.getString("FathersPhone");
                                    String strFullname=jsonObject.getString("FullName");
                                     strGender=jsonObject.getString("Gender");
                                    String strLastName=jsonObject.getString("LastName");
                                    String strNationality=jsonObject.getString("Nationality");
                                    String strPhone=jsonObject.getString("Phone");
                                    String strPhoto=jsonObject.getString("Photo");
                                    String strSchoolName=jsonObject.getString("SchoolName");
                                    String strSirName=jsonObject.getString("SirName");
                                    String strStage=jsonObject.getString("Stage");
                                    String strSubject=jsonObject.getString("Subject");
                                    String strUserId=jsonObject.getString("UserId");




                                    if (strGender.equals("1")){

                                        radioMale.setChecked(true);

                                    }
                                    else{

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










                                Toast.makeText(StudentUpdateProfile.this, "success" + str, Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(EnterLiveClass.this, EnterSecretKey.class));




                            } else {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(StudentUpdateProfile.this, "" + str, Toast.LENGTH_SHORT).show();

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



    private void ShowStage() {

        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetStages?cultureid=1")
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

                                    ArrayAdapter aa = new ArrayAdapter(StudentUpdateProfile.this, android.R.layout.simple_spinner_item, arrayListStageName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinStage.setAdapter(aa);

                                } else {


                                    Toast.makeText(StudentUpdateProfile.this, "Unsuccess", Toast.LENGTH_SHORT).show();
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

    private void ShowSubject() {

        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/GetAllSubjects?cultureid=1")
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

                                    ArrayAdapter aa = new ArrayAdapter(StudentUpdateProfile.this, android.R.layout.simple_spinner_item, arraListSubjectName);
                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    //Setting the ArrayAdapter data on the Spinner
                                    spinSubject.setAdapter(aa);

                                } else {


                                    Toast.makeText(StudentUpdateProfile.this, "Unsuccess", Toast.LENGTH_SHORT).show();
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


    private void UpdateProfile() {
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jObj = new JSONObject();

        try {

            jObj.put("FathersPhone", strFatherPhone);
            jObj.put("Subject", strSubjectId);
            jObj.put("SirName", strFullName);
            jObj.put("Email", strEmail);
            jObj.put("Photo", "photo");
            jObj.put("Nationality", strNationality);
            jObj.put("FathersMail", strFatherEmail);
            jObj.put("UserId", strUserId);
            jObj.put("FathersName", strFatherName);
            jObj.put("Phone", strPhone);
            jObj.put("FullName", strFullName);
            jObj.put("Address", "mp nagar");
            jObj.put("Stage", strStageId);
            jObj.put("Gender", strGender);
            jObj.put("SchoolName", strSchoolName);
            jObj.put("LastName", strLastName);
            jObj.put("Dob", strDob);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.e("sdfshdfhsdf",jObj.toString());


        AndroidNetworking.post("http://168.187.113.181:813/TadreesService.svc/EditProfile")
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jObj)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Log.e("wetwetwefsdvv",response.toString());
                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("Status");
                            if (str.equals("1")) {
                                // progress.setVisibility(View.GONE);


                                String strResult=response.getString("Result");
                                JSONArray jsonArray= new JSONArray(strResult);

                                for (int i = 0; i <jsonArray.length() ; i++) {

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String strStageId=jsonObject.getString("StageId");
                                    String strUserId=jsonObject.getString("UserId");
                                    String strStatus=jsonObject.getString("Userstatus");


                                }

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
                                edtFatherPhone.setText(strFatherPhone);
                                edtFullName.setText(strFullName);
                                edtEmail.setText(strEmail);
                                edtNationality.setText(strNationality);
                                edtFatherEmail.setText(strFatherEmail);
                                edtFatherName.setText(strFatherName);
                                edtPhone.setText(strPhone);
                               spinStage.setSelection(Integer.parseInt(strStageId));
                                spinSubject.setSelection(Integer.parseInt(strSubjectId));
                                edtSchoolName.setText(strSchoolName);
                                edtFullName.setText(strLastName);
                                edtDob.setText(strDob);

                                Toast.makeText(StudentUpdateProfile.this, "Success", Toast.LENGTH_SHORT).show();

                                // startActivity(new Intent(StudentProfile.this, StudentProfile.class));


                            } else {

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(StudentUpdateProfile.this, "" + str, Toast.LENGTH_SHORT).show();

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














}



