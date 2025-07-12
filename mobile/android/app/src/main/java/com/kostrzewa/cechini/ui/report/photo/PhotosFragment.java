package com.kostrzewa.cechini.ui.report.photo;

import static android.app.Activity.RESULT_OK;

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
import android.widget.Button;
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

public class PhotosFragment extends Fragment {
    private static final String TAG = "PhotosFragment";
    int pictureNo;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6;
    List<PhotoDTO> photosList = new ArrayList<>();

    private static final int ACTION_TAKE_PHOTO_6 = 6;
    public static final int PICK_IMAGE = 1;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    Button photo_rotate_left_btn1;
    Button photo_rotate_left_btn2;
    Button photo_rotate_left_btn3;
    Button photo_rotate_left_btn4;
    Button photo_rotate_left_btn5;
    Button photo_rotate_left_btn6;


    Button photo_rotate_right_btn1;
    Button photo_rotate_right_btn2;
    Button photo_rotate_right_btn3;
    Button photo_rotate_right_btn4;
    Button photo_rotate_right_btn5;
    Button photo_rotate_right_btn6;


    Button photo_btn1;
    public void click1() {
        dispatchTakePictureIntent(1);
    }

    Button photo_btn2;
    public void click2() {
        dispatchTakePictureIntent(2);
    }

    Button photo_btn3;
    public void click3() {
        dispatchTakePictureIntent(3);
    }

    Button photo_btn4;
    public void click4() {
        dispatchTakePictureIntent(4);
    }

    Button photo_btn5;
    public void click5() {
        dispatchTakePictureIntent(5);
    }

    Button photo_btn6;
    public void click6() {
        dispatchTakePictureIntent(6);
    }

    Button photo_media_btn1;
    public void click1Media() {
        goToMediaStore(1);
    }

    Button photo_media_btn2;
    public void click2Media() {
        goToMediaStore(2);
    }

    Button photo_media_btn3;
    public void click3Media() {
        goToMediaStore(3);
    }
    Button photo_media_btn4;
    public void click4Media() {
        goToMediaStore(4);
    }
    Button photo_media_btn5;
    public void click5Media() {
        goToMediaStore(5);
    }
    Button photo_media_btn6;
    public void click6Media() {
        goToMediaStore(6);
    }

    public void click1RotateLeft() {
        bitmap1 = rotate(1, -90, bitmap1, imageView1);
    }

    public void click1RotateRight() {
        bitmap1 = rotate(1, 90, bitmap1, imageView1);
    }

    public void click2RotateLeft() {
        bitmap2 = rotate(2, -90, bitmap2, imageView2);
    }

    public void click2RotateRight() {
        bitmap2 = rotate(2, 90, bitmap2, imageView2);
    }


    public void click3RotateLeft() {
        bitmap3 = rotate(3, -90, bitmap3, imageView3);
    }

    public void click3RotateRight() {
        bitmap3 = rotate(3, 90, bitmap3, imageView3);
    }


    public void click4RotateLeft() {
        bitmap4 = rotate(3, -90, bitmap4, imageView4);
    }

    public void click4RotateRight() {
        bitmap4 = rotate(3, 90, bitmap4, imageView4);
    }

    public void click5RotateLeft() {
        bitmap5 = rotate(3, -90, bitmap5, imageView5);
    }

    public void click5RotateRight() {
        bitmap5 = rotate(3, 90, bitmap5, imageView5);
    }

    public void click6RotateLeft() {
        bitmap6 = rotate(3, -90, bitmap6, imageView6);
    }

    public void click6RotateRight() {
        bitmap6 = rotate(3, 90, bitmap6, imageView6);
    }


    private void goToMediaStore(int value) {
        pictureNo = value;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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

        if (requestCode == PICK_IMAGE) {
            if (data != null && data.getData() != null) {
                image_uri = data.getData();
                setImage();
            }
        }

        if (resultCode == RESULT_OK) {
            setImage();
        }
    }

    private void setImage() {
        switch (pictureNo) {
            case 1:
                bitmap1 = setPicture(pictureNo, imageView1);
                photo_rotate_left_btn1.setVisibility(View.VISIBLE);
                photo_rotate_right_btn1.setVisibility(View.VISIBLE);
                break;
            case 2:
                bitmap2 = setPicture(pictureNo, imageView2);
                photo_rotate_left_btn2.setVisibility(View.VISIBLE);
                photo_rotate_right_btn2.setVisibility(View.VISIBLE);
                break;
            case 3:
                bitmap3 = setPicture(pictureNo, imageView3);
                photo_rotate_left_btn3.setVisibility(View.VISIBLE);
                photo_rotate_right_btn3.setVisibility(View.VISIBLE);
                break;
            case 4:
                bitmap4 = setPicture(pictureNo, imageView4);
                photo_rotate_left_btn4.setVisibility(View.VISIBLE);
                photo_rotate_right_btn4.setVisibility(View.VISIBLE);
                break;
            case 5:
                bitmap5 = setPicture(pictureNo, imageView5);
                photo_rotate_left_btn5.setVisibility(View.VISIBLE);
                photo_rotate_right_btn5.setVisibility(View.VISIBLE);
                break;
            case 6:
                bitmap6 = setPicture(pictureNo, imageView6);
                photo_rotate_left_btn6.setVisibility(View.VISIBLE);
                photo_rotate_right_btn6.setVisibility(View.VISIBLE);
                break;
        }
    }

    private Bitmap scale(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / scale, bitmap.getHeight() / scale, true);
    }

    private Bitmap rotate(int index, float angle, Bitmap bitmap, ImageView imageView) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        bitmap = rotated;
        imageView.setImageBitmap(bitmap);
        refreshDTO(index, bitmap);
        return bitmap;
    }

    private Bitmap setPicture(int index, ImageView imageView) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    getActivity().getContentResolver(), image_uri);
            bitmap = scale(bitmap);
            refreshDTO(index, bitmap);
            imageView.setImageBitmap(bitmap);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refreshDTO(int index, Bitmap bitmap) {
        PhotoFileDTO photoFileDTO = new PhotoFileDTO();
        photoFileDTO.setValue(getImage(bitmap));

        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setPhotoFileDTO(photoFileDTO);

        if (photosList.size() >= index && photosList.get(index - 1) != null) {
            photosList.remove(index - 1);
        }
        photosList.add(index - 1, photoDTO);
        ReportData.reportDTO.setPhotosList(photosList);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photos, container, false);
        imageView1 = root.findViewById(R.id.photo_iv1);
        imageView2 = root.findViewById(R.id.photo_iv2);
        imageView3 = root.findViewById(R.id.photo_iv3);
        imageView4 = root.findViewById(R.id.photo_iv4);
        imageView5 = root.findViewById(R.id.photo_iv5);
        imageView6 = root.findViewById(R.id.photo_iv6);

        photo_rotate_left_btn1 = root.findViewById(R.id.photo_rotate_left_btn1);
        photo_rotate_left_btn2 = root.findViewById(R.id.photo_rotate_left_btn2);
        photo_rotate_left_btn3 = root.findViewById(R.id.photo_rotate_left_btn3);
        photo_rotate_left_btn4 = root.findViewById(R.id.photo_rotate_left_btn4);
        photo_rotate_left_btn5 = root.findViewById(R.id.photo_rotate_left_btn5);
        photo_rotate_left_btn6 = root.findViewById(R.id.photo_rotate_left_btn6);

        photo_rotate_right_btn1 = root.findViewById(R.id.photo_rotate_right_btn1);
        photo_rotate_right_btn2 = root.findViewById(R.id.photo_rotate_right_btn2);
        photo_rotate_right_btn3 = root.findViewById(R.id.photo_rotate_right_btn3);
        photo_rotate_right_btn4 = root.findViewById(R.id.photo_rotate_right_btn4);
        photo_rotate_right_btn5 = root.findViewById(R.id.photo_rotate_right_btn5);
        photo_rotate_right_btn6 = root.findViewById(R.id.photo_rotate_right_btn6);

        photo_btn1 = root.findViewById(R.id.photo_btn1);
        photo_btn2 = root.findViewById(R.id.photo_btn2);
        photo_btn3 = root.findViewById(R.id.photo_btn3);
        photo_btn4 = root.findViewById(R.id.photo_btn4);
        photo_btn5 = root.findViewById(R.id.photo_btn5);
        photo_btn6 = root.findViewById(R.id.photo_btn6);

        photo_btn1.setOnClickListener(v -> click1());
        photo_btn2.setOnClickListener(v -> click2());
        photo_btn3.setOnClickListener(v -> click3());
        photo_btn4.setOnClickListener(v -> click4());
        photo_btn5.setOnClickListener(v -> click5());
        photo_btn6.setOnClickListener(v -> click6());

        photo_media_btn1 = root.findViewById(R.id.photo_media_btn1);
        photo_media_btn2 = root.findViewById(R.id.photo_media_btn2);
        photo_media_btn3 = root.findViewById(R.id.photo_media_btn3);
        photo_media_btn4 = root.findViewById(R.id.photo_media_btn4);
        photo_media_btn5 = root.findViewById(R.id.photo_media_btn5);
        photo_media_btn6 = root.findViewById(R.id.photo_media_btn6);

        photo_media_btn1.setOnClickListener(v -> click1Media());
        photo_media_btn2.setOnClickListener(v -> click2Media());
        photo_media_btn3.setOnClickListener(v -> click3Media());
        photo_media_btn4.setOnClickListener(v -> click4Media());
        photo_media_btn5.setOnClickListener(v -> click5Media());
        photo_media_btn6.setOnClickListener(v -> click6Media());

        photo_rotate_left_btn1.setOnClickListener(v -> click1RotateLeft());
        photo_rotate_left_btn2.setOnClickListener(v -> click2RotateLeft());
        photo_rotate_left_btn3.setOnClickListener(v -> click3RotateLeft());
        photo_rotate_left_btn4.setOnClickListener(v -> click4RotateLeft());
        photo_rotate_left_btn5.setOnClickListener(v -> click5RotateLeft());
        photo_rotate_left_btn6.setOnClickListener(v -> click6RotateLeft());

        photo_rotate_right_btn1.setOnClickListener(v -> click1RotateRight());
        photo_rotate_right_btn2.setOnClickListener(v -> click2RotateRight());
        photo_rotate_right_btn3.setOnClickListener(v -> click3RotateRight());
        photo_rotate_right_btn4.setOnClickListener(v -> click4RotateRight());
        photo_rotate_right_btn5.setOnClickListener(v -> click5RotateRight());
        photo_rotate_right_btn6.setOnClickListener(v -> click6RotateRight());

        photo_rotate_left_btn1.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn1.setVisibility(View.INVISIBLE);
        photo_rotate_left_btn2.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn2.setVisibility(View.INVISIBLE);
        photo_rotate_left_btn3.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn3.setVisibility(View.INVISIBLE);
        photo_rotate_left_btn4.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn4.setVisibility(View.INVISIBLE);
        photo_rotate_left_btn5.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn5.setVisibility(View.INVISIBLE);
        photo_rotate_left_btn6.setVisibility(View.INVISIBLE);
        photo_rotate_right_btn6.setVisibility(View.INVISIBLE);

        photosList.add(0, new PhotoDTO());
        photosList.add(1, new PhotoDTO());
        photosList.add(2, new PhotoDTO());
        photosList.add(3, new PhotoDTO());
        photosList.add(4, new PhotoDTO());
        photosList.add(5, new PhotoDTO());

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