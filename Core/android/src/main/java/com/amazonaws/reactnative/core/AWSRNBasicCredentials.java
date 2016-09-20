package com.amazonaws.reactnative.core;

import com.amazonaws.auth.AWSBasicCognitoIdentityProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.IdentityChangedListener;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by Work on 9/20/2016.
 */
public class AWSRNBasicCredentials extends ReactContextBaseJavaModule {
    private static final String REGION = "region";
    private static final String SERVICE_NAME = "AWSBasicCredentials";
    private static final String ACCESS_KEY = "AccessKey";
    private static final String SECRET_KEY = "SecretKey";
    private StaticCredentialsProvider credentialsProvider;


    public AWSRNBasicCredentials(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "AWSRNBasicCredentials";
    }


    @ReactMethod
    public void initWithOptions(final ReadableMap options) throws IllegalArgumentException {
        if (!options.hasKey(ACCESS_KEY) && !options.hasKey(SECRET_KEY) && !options.hasKey(REGION)) {
            throw new IllegalArgumentException("access_key, secret_key, and region not supplied");
        } else {
            credentialsProvider = new StaticCredentialsProvider(new BasicAWSCredentials(options.getString(ACCESS_KEY), options.getString(SECRET_KEY)));

            //Notify the chain that a new credential provider was created
            final AWSRNCredentialChain credentialChain = this.getReactApplicationContext().getNativeModule(AWSRNCredentialChain.class);
            credentialChain.ReConstructChain();
        }


    }


    public StaticCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }
}
