/*
 * Copyright (C) 2021 Newlogic Pte. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reactnativevc;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;

@ReactModule(name = VcModule.NAME)
public class VcModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Vc";

    public VcModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public boolean verifySignature(String jwtStr) {
        boolean isValid = false;
        JsonWebSignature jws = new JsonWebSignature();
        try {
            jws.setCompactSerialization(jwtStr);
            List<X509Certificate> certificateChainHeaderValue = jws.getCertificateChainHeaderValue();
            X509Certificate certificate = certificateChainHeaderValue.get(0);
            certificate.checkValidity();
            PublicKey publicKey = certificate.getPublicKey();
            jws.setKey(publicKey);
            isValid = jws.verifySignature();
        } catch (JoseException | CertificateNotYetValidException |
            CertificateExpiredException e) {
        }
        return  isValid;
    }
}
