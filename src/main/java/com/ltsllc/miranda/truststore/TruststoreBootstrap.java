package com.ltsllc.miranda.truststore;


import com.ltsllc.clcl.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class TruststoreBootstrap {
    public static void create(String keystoreFilename, String passwordString, String distinguishedName, String certificateFilename)
            throws GeneralSecurityException, IOException, EncryptionException {
        String password = "";

        if (passwordString != null)
            password = passwordString;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        com.ltsllc.clcl.KeyPair clcKeyPair = new com.ltsllc.clcl.KeyPair(keyPair, distinguishedName);

        new Truststore(clcKeyPair, keystoreFilename, password);

        CertificateSigningRequest certificateSigningRequest =
                clcKeyPair.getPublicKey().createCertificateSigningRequest(clcKeyPair.getPrivateKey());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date aYearFromNow = calendar.getTime();
        Certificate certificate = clcKeyPair.getPrivateKey().sign(certificateSigningRequest, now, aYearFromNow);
        certificate.store(certificateFilename);
    }
}
