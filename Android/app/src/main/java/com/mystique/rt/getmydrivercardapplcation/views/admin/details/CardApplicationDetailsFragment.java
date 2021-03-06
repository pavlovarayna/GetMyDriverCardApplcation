package com.mystique.rt.getmydrivercardapplcation.views.admin.details;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mystique.rt.getmydrivercardapplcation.R;
import com.mystique.rt.getmydrivercardapplcation.apputils.Constants;
import com.mystique.rt.getmydrivercardapplcation.apputils.email.SendMail;
import com.mystique.rt.getmydrivercardapplcation.models.CardApplicationForm;
import com.mystique.rt.getmydrivercardapplcation.models.Driver;
import com.mystique.rt.getmydrivercardapplcation.models.Picture;
import com.mystique.rt.getmydrivercardapplcation.parsers.bitmap.BitmapParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <h1>CardApplicationDetailsFragment</h1>
 *
 * <b>Description: </b> This fragment represents the visualisation of the details of
 * the selected application form. It is the View of the MVP.
 *
 * @author  Mystique Team
 * @version 1.0
 * @since   2018-11-12
 */
public class CardApplicationDetailsFragment extends Fragment implements CardApplicationDetailsContracts.View{



    private String statusChangeItem = "";

    private CardApplicationDetailsContracts.Presenter mPresenter;


    @Inject
    BitmapParser mPictureParser;

    @BindView(R.id.status_spinner)
    MaterialSpinner mStatusSpinner;

    @BindView(R.id.btn_change_status)
    Button mChangeStatusButton;


    @BindView(R.id.tv_cardappformid_details)
    TextView mIDTextView;

    @BindView(R.id.tv_dateofsubmission_details)
    TextView mSubmissionTextView;

    @BindView(R.id.tv_status_details)
    TextView mStatusTextView;

    @BindView(R.id.tv_type_details)
    TextView mTypeTextView;

    @BindView(R.id.tv_firstname_details)
    TextView mFirstNameTextView;

    @BindView(R.id.tv_lastname_details)
    TextView mLastNameTextView;

    @BindView(R.id.tv_personal_number_details)
    TextView mPersonalNumberTextView;

    @BindView(R.id.tv_date_of_birth_details)
    TextView mDateOfBirthNumberTextView;

    @BindView(R.id.tv_address_details)
    TextView mAddressTextView;

    @BindView(R.id.tv_phone_number_details)
    TextView mPhoneTextView;

    @BindView(R.id.tv_email_details)
    TextView mEmailTextView;

    /*@BindView(R.id.iv_driverselfie_details)
    ImageView mSelfieImageView;

    @BindView(R.id.iv_drivinglicense_details)
    ImageView mDrivingPicImageView;*/

    @BindView(R.id.pv_driverselfie_details)
    PhotoView mSelfiePhotoView;

    @BindView(R.id.pv_drivinglicense_details)
    PhotoView mDrivingLicensePhotoView;

    @BindView(R.id.tv_license_number_details)
    TextView mDrivingNumberTextView;

    @BindView(R.id.tv_license_coutry_details)
    TextView mDrivingCountryTextView;

    @BindView(R.id.layout_loose_theft_details)
    LinearLayout mLooseTheftLayout;

    @BindView(R.id.tv_placeofevent_details)
    TextView mPlaseOfEventTextView;

    @BindView(R.id.tv_dateofevent_details)
    TextView mDareOfEventTextView;

    /*@BindView(R.id.iv_oldcard_picture_details)
    ImageView mOldCardPicImageView;*/

    @BindView(R.id.layout_oldcard_details)
    LinearLayout mOldCardDetailsLayout;

    @BindView(R.id.pv_oldcard_picture_details)
    PhotoView mOldCardPicPhotoView;


    @BindView(R.id.tv_oldcard_country_details)
    TextView mOldCardCountryTextView;

    @BindView(R.id.tv_oldcard_authority_details)
    TextView mOldCardAuthorityTextView;

    @BindView(R.id.tv_oldcard_number_details)
    TextView mOldCardNumberTextView;

    @BindView(R.id.tv_oldcard_date_of_expiry_details)
    TextView mOldCardDateOfExpieryTextView;

    @BindView(R.id.layout_newinfo_details)
    LinearLayout mNewInfoDetailsLayout;

    @BindView(R.id.tv_renewal_reason_details)
    TextView mRenewalReasonTextView;

    @BindView(R.id.tv_new_address_details)
    TextView mNewAddressTextView;

    @BindView(R.id.tv_new_firstname_details)
    TextView mNewFirstNameTextView;

    @BindView(R.id.tv_new_lastname_details)
    TextView mNewLastNameTextView;

    /*@BindView(R.id.iv_newselfie_picture_details)
    ImageView mNewSelfieImageView;*/


    @BindView(R.id.pv_newselfie_picture_details)
    PhotoView mNewSelfiePhotoView;



    @BindView(R.id.tv_details_details)
    TextView mDetailsTextView;

    @BindView(R.id.tv_status_checkcode_details)
    TextView mCheckCodeTextView;

    @BindView(R.id.tv_office_details)
    TextView mReceivingOfficeTextView;

    /*@BindView(R.id.iv_signature_details)
    ImageView mSignatureImageView;*/

    @BindView(R.id.pv_signature_details)
    PhotoView mSignaturePhotoView;

    private Picture mDrivingLicensePhoto;
    private Picture mSelfie;
    private Picture mNewSelfie;
    private int mDriverId;
    private String mLastSetId;

    @Inject
    public CardApplicationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_application_details, container, false);

        ButterKnife.bind(this, view);

        mStatusSpinner.setItems(Constants.STATUS_FIELDS);
        mStatusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Status changed to " + item, Snackbar.LENGTH_SHORT).show();
                statusChangeItem = item;
            }
        });

        mChangeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateCardApplicationForm(statusChangeItem);
                mPresenter.sendMail(getContext(), String.valueOf(mEmailTextView.getText()), statusChangeItem, String.valueOf(mReceivingOfficeTextView.getText()));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
        mPresenter.loadCardApplicationForm();
    }

    @Override
    public void showCardApplicationFormDetails(CardApplicationForm form) {
        mIDTextView.setText(String.valueOf(form.getCardApplicationFormId()));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateOfSubmission = df.format(form.getDateOfSubmission());
        mSubmissionTextView.setText(dateOfSubmission);

        mStatusTextView.setText(form.getStatus());
        mTypeTextView.setText(form.getType());
        mFirstNameTextView.setText(form.getDriver().getFirstName());
        mLastNameTextView.setText(form.getDriver().getLastName());
        mPersonalNumberTextView.setText(form.getDriver().getPersonalNumber());

        String dateOfBirth = df.format(form.getDriver().getDateOfBirth());
        mDateOfBirthNumberTextView.setText(dateOfBirth);

        mAddressTextView.setText(form.getDriver().getAddress());
        mPhoneTextView.setText(form.getDriver().getPhoneNumber());
        mEmailTextView.setText(form.getDriver().getEmail());

        mSelfiePhotoView.setImageBitmap(mPictureParser.toBitmap(form.getDriver().getSelfie().getPicture()));
        mSelfie = form.getDriver().getSelfie();

        mDrivingLicensePhotoView.setImageBitmap(mPictureParser.toBitmap(form.getDriver().getDrivingPic().getPicture()));
        mDrivingLicensePhoto = form.getDriver().getDrivingPic();
        //mSelfieImageView.setImageBitmap(mPictureParser.toBitmap(form.getDriver().getDrivingPic().getPicture()));
        //mDrivingPicImageView.setImageBitmap(mPictureParser.toBitmap(form.getDriver().getDrivingPic().getPicture()));

        mDrivingNumberTextView.setText(form.getDrivingLicenseNumber());
        mDrivingCountryTextView.setText(form.getDrivingLicenseCountry());
        mPlaseOfEventTextView.setText(form.getPlaceOfEvent());

        if(form.getDateOfEvent() != null){
            mLooseTheftLayout.setVisibility(View.VISIBLE);
            mDareOfEventTextView.setVisibility(View.VISIBLE);
            String dateOfEvent = df.format(form.getDateOfEvent());
            mDareOfEventTextView.setText(dateOfEvent);
        }

        if(form.getOldCardPicture() != null){
            mOldCardDetailsLayout.setVisibility(View.VISIBLE);
        mOldCardPicPhotoView.setImageBitmap(mPictureParser.toBitmap(form.getOldCardPicture().getPicture()));}
        /*mOldCardPicImageView.setImageBitmap(mPictureParser.toBitmap(form.getOldCardPicture().getPicture()));*/

        if(form.getOldCardCountry()!= null){
        mOldCardCountryTextView.setText(form.getOldCardCountry());}

        if(form.getOldCardAuthority() != null){
        mOldCardAuthorityTextView.setText(form.getOldCardAuthority());}

        if(form.getOldCardNumber() != null){
        mOldCardNumberTextView.setText(form.getOldCardNumber());}

        if(form.getOldCardDateOfExpiry() != null){
        String dateOfExpiry = df.format(form.getOldCardDateOfExpiry());
        mOldCardDateOfExpieryTextView.setText(dateOfExpiry);}

        if(form.getRenewalReason() != null){
        mNewInfoDetailsLayout.setVisibility(View.VISIBLE);
        mRenewalReasonTextView.setText(form.getRenewalReason());}

        if(form.getNewAddress() != null){
        mNewAddressTextView.setText(form.getNewAddress());}

        if(form.getNewFirstName() != null){
        mNewFirstNameTextView.setText(form.getNewFirstName());}

        if(form.getNewLastName() != null){
        mNewLastNameTextView.setText(form.getNewLastName());}

        if(form.getNewSelfie() != null){
        mNewSelfiePhotoView.setImageBitmap(mPictureParser.toBitmap(form.getNewSelfie().getPicture()));
        mNewSelfie = form.getNewSelfie();}
        /*mNewSelfieImageView.setImageBitmap(mPictureParser.toBitmap(form.getNewSelfie().getPicture()));*/

        mDetailsTextView.setText(form.getDetails());
        mCheckCodeTextView.setText(form.getStatusCheckCode());
        mReceivingOfficeTextView.setText(form.getReceivingOffice());

        mSignaturePhotoView.setImageBitmap(mPictureParser.toBitmap(form.getSignaturePicture().getPicture()));
        /*mSignatureImageView.setImageBitmap(mPictureParser.toBitmap(form.getSignaturePicture().getPicture()));*/

        mDriverId = form.getDriver().getDriverId();
        mLastSetId = form.getDriver().getLastSetID();
    }

    @Override
    public void setPresenter(CardApplicationDetailsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading(Driver driver) {
        Toast.makeText(getContext(), "Information updated!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showMessageApplicationStatusChange() {
        Toast.makeText(getActivity(), "Application status has been changed.", Toast.LENGTH_SHORT)
                .show();

        if (statusChangeItem.equals(Constants.STATUS_FIELDS[1])) {
            assembleUpdatedDriver();
        }
    }

    private void assembleUpdatedDriver() {
        String applicationType = mTypeTextView.getText().toString();
        if (applicationType.equals(Constants.APP_TYPE_REPLACEMENT_PERSON)) {
            Driver updatedDriver = new Driver();
            updatedDriver.setPersonalNumber(mPersonalNumberTextView.getText().toString());
            updatedDriver.setFirstName(mFirstNameTextView.getText().toString());
            updatedDriver.setLastName(mLastNameTextView.getText().toString());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = null;
            try {
                dateOfBirth = df.parse(mDateOfBirthNumberTextView.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            updatedDriver.setDateOfBirth(dateOfBirth);
            updatedDriver.setAddress(mAddressTextView.getText().toString());
            updatedDriver.setPhoneNumber(mPhoneTextView.getText().toString());
            updatedDriver.setEmail(mEmailTextView.getText().toString());
            updatedDriver.setSelfie(mSelfie);
            updatedDriver.setDrivingPic(mDrivingLicensePhoto);
            updatedDriver.setDriverId(mDriverId);
            updatedDriver.setLastSetID(mLastSetId);

            if (mRenewalReasonTextView.getText().equals(Constants.RENEWAL_REASON_NAME)) {
                updatedDriver.setFirstName(mNewFirstNameTextView.getText().toString());
                updatedDriver.setLastName(mNewLastNameTextView.getText().toString());
            } else if (mRenewalReasonTextView.getText().equals(Constants.RENEWAL_REASON_ADDRESS)) {
                updatedDriver.setAddress(mNewAddressTextView.getText().toString());
            } else {
                updatedDriver.setSelfie(mNewSelfie);
            }

            mPresenter.updateDriver(updatedDriver);
        }
    }
}
