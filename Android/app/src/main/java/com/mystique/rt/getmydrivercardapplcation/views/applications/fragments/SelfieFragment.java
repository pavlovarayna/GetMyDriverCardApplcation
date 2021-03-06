


package com.mystique.rt.getmydrivercardapplcation.views.applications.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mystique.rt.getmydrivercardapplcation.BuildConfig;
import com.mystique.rt.getmydrivercardapplcation.R;
import com.mystique.rt.getmydrivercardapplcation.apputils.RememberAll;
import com.mystique.rt.getmydrivercardapplcation.parsers.bitmap.BitmapParser;
import com.mystique.rt.getmydrivercardapplcation.parsers.bitmap.ByteArrayBitmapParser;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <h1>Selfie Fragment</h1>
 *
 * <b>Description: </b> Fragment uses devise camera to collect personal photo for
 * different user application types, which is organised by Adapters in different activities
 * in slider view.
 *
 *
 * @author  Mystique Team
 * @version 1.0
 * @since   2018-11-12
 */
public class SelfieFragment extends Fragment {


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;

    @BindView(R.id.btn_picturecamera)
    Button selfieButton;


    @BindView(R.id.iv_picture)
    ImageView selfieImageView;

    private RememberAll mRememberAll;
    BitmapParser mSelfieParser;


    public SelfieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selfie, container, false);

        ButterKnife.bind(this, view);

        mSelfieParser = new ByteArrayBitmapParser();
        mRememberAll = RememberAll.getInstance();

        Context context = getActivity();

        PackageManager packageManager = Objects.requireNonNull(context).getPackageManager();

        checkRememberAllForCurrentData();

        // checking if camera exist
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
        }

        // checking if camera is restricted to wark with the app and ask to change restricted permissions
        if (!checkPermissions(context)){
            showPermissionsAlert(context);
        } else {
            Toast.makeText(getActivity(), "The device camera has all necessary permissions.", Toast.LENGTH_SHORT)
                    .show();
        }

        selfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSelfie();
            }
        });

        return view;
    }

    private void checkRememberAllForCurrentData() {
        if (mRememberAll.getSelfiePic().getPicture() != null) {
            Bitmap savedSelfiePic = mSelfieParser.toBitmap(mRememberAll.getSelfiePic().getPicture());
            selfieImageView.setImageBitmap(savedSelfiePic);
        }
    }


    public void makeSelfie() {
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Objects.requireNonNull(getActivity()).startActivityFromFragment(SelfieFragment.this, cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {

                    Bitmap bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if (bmp != null) {

                        //for saving in database
                        byte[] byteSelfie = mSelfieParser.fromBitmap(bmp);
                        mRememberAll.setSelfiePicture(byteSelfie);

                        //for viewing
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    }

                    selfieImageView.setImageBitmap(bmp);
                }
            }
        }catch(Exception e){
            Toast.makeText(this.getActivity(), e+"Getting picture from camera fails. Make picture again!", Toast.LENGTH_LONG).show();

        }
    }

    static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void showPermissionsAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", (dialog, which) -> openSettings(Objects.requireNonNull(getActivity())))
                .setNegativeButton("CANCEL", (dialog, which) -> {
                }).show();
    }

    static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /*if (savedInstanceState != null) {
        super.onViewStateRestored(savedInstanceState);
        byte[] savedPicture = savedInstanceState.getByteArray("saveBundleSelfie");
        selfieImageView.setImageBitmap(mSelfieParser.toBitmap(savedPicture));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putByteArray("saveBundleSelfie", saveBundlePicture);
    }*/
}
