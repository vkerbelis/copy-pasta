package com.example.vkerbelis.copypasta;

import android.content.ContentResolver;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class UuidCreator {
    private static final String TAG = "Uuid";

    private UuidCreator() {
        // Empty hidden constructor
    }

    @SuppressWarnings("HardwareIds")
    public static String create(TelephonyManager telephonyManager,
                                ContentResolver contentResolver) {
        String result = "";
        String deviceId = telephonyManager.getDeviceId();
        String deviceIdPart1;
        String deviceIdPart2;
        if (deviceId == null) {
            deviceIdPart1 = "2";
            deviceIdPart2 = "2";
        } else {
            deviceIdPart1 = deviceId.substring(0, deviceId.length() / 2);
            deviceIdPart2 = deviceId.substring(deviceId.length() / 2);
        }
        String androidId = Settings.Secure.getString(contentResolver,
                Settings.Secure.ANDROID_ID);
        String androidIdPart1 = androidId.substring(0, androidId.length() / 2);
        String androidIdPart2 = androidId.substring(androidId.length() / 2);
        String finalIdString = androidIdPart1 + deviceIdPart1 + deviceIdPart2 + androidIdPart2;
        result = (androidIdPart1.substring(0, 1) + encodeToMD5(finalIdString)
                + deviceIdPart2.substring(0, 1)).toUpperCase();
        return result;
    }

    private static String encodeToMD5(String source) {
        String result = null;
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(source.getBytes(), 0, source.length());
            result = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException cause) {
            Log.d(TAG, "No algorithm found", cause);
        }
        return result;
    }
}