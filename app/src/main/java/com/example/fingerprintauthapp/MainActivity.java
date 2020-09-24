package com.example.fingerprintauthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accessibilityservice.FingerprintGestureController;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mHeadingLAbel,mParaLabel,mErfolgsText;
    private ImageView mFingerprintImage;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeadingLAbel = findViewById(R.id.headingLabel);
        mParaLabel = findViewById(R.id.paraLabel);
        mFingerprintImage = findViewById(R.id.fingerprintImage);
        mErfolgsText = findViewById(R.id.tv_Erfolgstext);

        mErfolgsText.setVisibility(View.INVISIBLE);

        //     check Android version
        //     check Device has Scanner
        //     check permission to use Fingerprint
        //     Lockscreen screen is secuured with at least one type of lock
        //TODO:     At least one FIngerprint is registered

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()){

                mParaLabel.setText("Scanner not decteted in device");

            }else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED){

                mParaLabel.setText("Permission not granted to use Fingerprint");

            } else if (!keyguardManager.isKeyguardSecure()){

                mParaLabel.setText("Add Lock to your Phone in the Settings");

            }else if (!fingerprintManager.hasEnrolledFingerprints()){

                mParaLabel.setText("you should add at least one fingerprint to your Feature");

            }else{

                mParaLabel.setText("Place your Finger on the Scanner to Acess the Device");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager,null);

            }

        }


    }
}