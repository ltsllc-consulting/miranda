package com.ltsllc.clcl;

import com.ltsllc.clcl.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.Date;

public class BootstrapKeystore {
    public BootstrapKeystore () {
    }

    public static com.ltsllc.clcl.KeyStore create (String filename, PrivateKey CA, String password) throws GeneralSecurityException, EncryptionException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = new PublicKey(keyPair.getPublic());
        PrivateKey privateKey = new PrivateKey(keyPair.getPrivate());
        CertificateSigningRequest certificateSigningRequest = publicKey.createCertificateSigningRequest(privateKey);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date oneYearFromToday = calendar.getTime();
        Certificate certificate = CA.sign(certificateSigningRequest, today, oneYearFromToday);
        KeyStore keyStore = KeyStore.getInstance("RSA");
        java.security.cert.Certificate[] chain = { certificate.getCertificate() };
        keyStore.setKeyEntry("public", publicKey.getSecurityPublicKey(), password.toCharArray(), chain);
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        keyStore.store(fileOutputStream, password.toCharArray());

        return new com.ltsllc.clcl.KeyStore(keyStore);
    }
}
