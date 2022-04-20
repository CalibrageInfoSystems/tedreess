package com.example.tadreess.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.androidnetworking.model.Progress;
import com.bumptech.glide.Glide;
import com.example.tadreess.AppsContants;
import com.example.tadreess.HTTPURL;
import com.example.tadreess.LoginActivity;
import com.example.tadreess.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherProfile extends AppCompatActivity {


    ImageView btnback;

    EditText edtName, edtSpeciality, edtExperience;

    Button btnSubmit;

    String strName = "", strSpeciality = "", strExperience = "";

    String strTeacherId = "", strTeacherName = "";

    String strTeacherSpeciality = "", strTeacherExperience = "";

    CircleImageView profile_image;

    ProgressBar progressBar;


    String strTeacherProfileImage = "";

    File file1;
    String fileName = "";
    String fileActualPath = "";
    String filepath = android.os.Environment.getExternalStorageDirectory() + File.separator + "Uimage" + File.separator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        btnback = findViewById(R.id.btnback);
        edtName = findViewById(R.id.edtName);
        edtSpeciality = findViewById(R.id.edtSpeciality);
        edtExperience = findViewById(R.id.edtExperience);
        btnSubmit = findViewById(R.id.btnSubmit);
        profile_image = findViewById(R.id.profile_image);
        progressBar = findViewById(R.id.progressBar);


        AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
        strTeacherId = AppsContants.sharedpreferences.getString(AppsContants.TeacherId, "");
        strTeacherName = AppsContants.sharedpreferences.getString(AppsContants.TeacherName, "");
        strTeacherSpeciality = AppsContants.sharedpreferences.getString(AppsContants.SubjectSpeciality, "");
        strTeacherExperience = AppsContants.sharedpreferences.getString(AppsContants.Experience, "");
        strTeacherProfileImage = AppsContants.sharedpreferences.getString(AppsContants.TeacherProfileImage, "");

        Log.e("dhgfujfgiutjhfurt", strTeacherProfileImage);
        edtName.setText(strTeacherName);
        edtSpeciality.setText(strTeacherSpeciality);
        edtExperience.setText(strTeacherExperience);
        Glide.with(TeacherProfile.this).load(strTeacherProfileImage).into(profile_image);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strName = edtName.getText().toString().trim();
                strSpeciality = edtSpeciality.getText().toString().trim();
                strExperience = edtExperience.getText().toString().trim();
                Glide.with(TeacherProfile.this).load(strTeacherProfileImage).into(profile_image);

                if (strSpeciality.equals("")) {

                    Toast.makeText(TeacherProfile.this, " Please Enter Subject Speciality", Toast.LENGTH_SHORT).show();
                } else if (strExperience.equals("")) {


                    Toast.makeText(TeacherProfile.this, "Please Enter Work Experience", Toast.LENGTH_SHORT).show();
                } else {

                    ProfileUpdate();
                }


            }
        });

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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
                profile_image.setImageURI(uri);
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
                    profile_image.setImageBitmap(image);
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
                profile_image.setImageBitmap(thumbnail);
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






    @Override
    protected void onResume() {
        super.onResume();
       // ProfileUpdate();
    }




    public void ProfileUpdate() {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("gdfjhgsdjhsgdh", String.valueOf(file1));
        AndroidNetworking.upload(HTTPURL.UpdateProfile)

                .addMultipartParameter("user_id", strTeacherId)
                .addMultipartParameter("subject_specialty", strSpeciality)
                .addMultipartParameter("teaching_experience", strExperience)
                .addMultipartFile("image", file1)

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

                        Log.e("jsdfhsjdfhjhfjshf", response.toString());

                        try {

                            progressBar.setVisibility(View.GONE);
                            String str = response.getString("result");
                            if (str.equals("successfully")) {


                                Log.e("jdfjerkdfj", str.equals("successfully") + "");

                                String strId = response.getString("id");
                                String strName = response.getString("first_name");
                                String strSpeciality = response.getString("subject_specialty");
                                String strExperience = response.getString("teaching_experience");
                                String strImage = response.getString("image");


                                edtName.setText(strName);
                                edtSpeciality.setText(strSpeciality);
                                edtExperience.setText(strExperience);

                                Glide.with(TeacherProfile.this).load(strImage).into(profile_image);
                                Toast.makeText(TeacherProfile.this, response.getString("result"), Toast.LENGTH_SHORT).show();


                                AppsContants.sharedpreferences = getSharedPreferences(AppsContants.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppsContants.sharedpreferences.edit();
                                editor.putString(AppsContants.UserId, response.getString("id"));
                                editor.putString(AppsContants.TeacherName, response.getString("first_name"));
                                editor.putString(AppsContants.SubjectSpeciality, response.getString("subject_specialty"));
                                editor.putString(AppsContants.Experience, response.getString("teaching_experience"));
                                editor.putString(AppsContants.TeacherProfileImage, response.getString("image"));
                                editor.commit();


                                startActivity(new Intent(TeacherProfile.this,TeacherNavigationActivity.class));

                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(TeacherProfile.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("hhhhggggkkk", ex.getMessage());

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("gjhjghjghjh", anError.getMessage());
                    }
                });
    }
}
