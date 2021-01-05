package com.kostrzewa.cechini.ui.report.photo;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.kostrzewa.cechini.R;
import com.kostrzewa.cechini.model.PhotoDTO;
import com.kostrzewa.cechini.model.PhotoFileDTO;
import com.kostrzewa.cechini.ui.report.data.ReportData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class PhotosFragment extends Fragment {
    int pictureNo;
    Bitmap bitmap1, bitmap2, bitmap3;
    List<PhotoDTO> photosList = new ArrayList<>();

    @BindView(R.id.photo_iv1)
    ImageView imageView1;
    @BindView(R.id.photo_iv2)
    ImageView imageView2;
    @BindView(R.id.photo_iv3)
    ImageView imageView3;

    @OnClick(R.id.photo_btn1)
    public void click1() {
        dispatchTakePictureIntent(1);
    }

    @OnClick(R.id.photo_btn2)
    public void click2() {
        dispatchTakePictureIntent(2);
    }

    @OnClick(R.id.photo_btn3)
    public void click3() {
        dispatchTakePictureIntent(3);
    }

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private int scale = 3;

    Uri image_uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when image was captured from camera

        if (resultCode == RESULT_OK) {
            //set the image captured to our ImageView

            switch (pictureNo) {
                case 1:
                    setPicture(pictureNo - 1, bitmap1, imageView1);
                    break;
                case 2:
                    setPicture(pictureNo - 1, bitmap2, imageView2);
                    break;
                case 3:
                    setPicture(pictureNo - 1, bitmap3, imageView3);
                    break;
            }

        }
    }

    private Bitmap scale (Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
    }

    private Bitmap rotate(float angle, Bitmap bitmap){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        bitmap = rotated;

        return bitmap;
    }

    private void setPicture(int index, Bitmap bitmap, ImageView imageView) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    getActivity().getContentResolver(), image_uri);
            bitmap = scale(bitmap);
            bitmap = rotate(90, bitmap);
            PhotoFileDTO photoFileDTO = new PhotoFileDTO();
            photoFileDTO.setValue(getImage(bitmap));

            PhotoDTO photoDTO = new PhotoDTO();
            photoDTO.setPhotoFileDTO(photoFileDTO);
            if (photosList.size() > index && photosList.get(index) != null) {
                photosList.remove(index);
            }
            photosList.add(photoDTO);
            ReportData.reportDTO.setPhotosList(photosList);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private byte[] getImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }

    private void dispatchTakePictureIntent(int i) {
        this.pictureNo = i;

        //if system os is >= marshmallow, request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                //permission not enabled, request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show popup to request permissions
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                //permission already granted
                openCamera();
            }
        } else {
            //system os < marshmallow
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted
                    openCamera();
                } else {
                    //permission from popup was denied
                    Toast.makeText(getActivity(), "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}