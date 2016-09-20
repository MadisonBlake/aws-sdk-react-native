package com.amazonaws.reactnative.core;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Work on 9/20/2016.
 */
public class AWSRNCredentialChain extends ReactContextBaseJavaModule {

    private AWSCredentialsProviderChain chain;
    private int numberCredentials;

    public AWSRNCredentialChain(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "AWSRNCredentialChain";
    }

    void ReConstructChain() {

        List<AWSCredentialsProvider> list = new ArrayList<>();
        numberCredentials = 0;

        //check for basic credentials
        final AWSRNBasicCredentials basicCredentials = this.getReactApplicationContext().getNativeModule(AWSRNBasicCredentials.class);
        if (basicCredentials.getCredentialsProvider() != null) {
            list.add(basicCredentials.getCredentialsProvider());
            numberCredentials++;
        }


        //check for cognito credentials
        final AWSRNCognitoCredentials cognitoCredentials = this.getReactApplicationContext().getNativeModule(AWSRNCognitoCredentials.class);
        if (cognitoCredentials.getCredentialsProvider() != null) {
            list.add(cognitoCredentials.getCredentialsProvider());
            numberCredentials++;
        }


        AWSCredentialsProvider[] arr = list.toArray(new AWSCredentialsProvider[0]);

        chain = new AWSCredentialsProviderChain(arr);

    }


    public AWSCredentialsProviderChain getChain() {
        return chain;
    }

    public int NumberSetCredentials() {
        return numberCredentials;
    }
}
