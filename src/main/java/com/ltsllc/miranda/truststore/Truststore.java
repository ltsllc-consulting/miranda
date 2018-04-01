package com.ltsllc.miranda.truststore;

import com.ltsllc.clcl.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class Truststore {
    private KeyStore keyStore;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private boolean loaded;

    public static final String ALIAS = "private";

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public static Truststore create (KeyPair keyPair, String keystoreFilename, String password, String certificateFilename)
            throws GeneralSecurityException, EncryptionException, IOException
    {
        Truststore truststore = new Truststore(keyPair, keystoreFilename, password);

        CertificateSigningRequest certificateSigningRequest =
                keyPair.getPublicKey().createCertificateSigningRequest(keyPair.getPrivateKey());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date aYearFromNow = calendar.getTime();

        Certificate certificate = keyPair.getPrivateKey().sign(certificateSigningRequest, now, aYearFromNow);
        certificate.store(certificateFilename);

        return truststore;
    }

    public Truststore(KeyPair keyPair, String filename, String passwordString)
            throws GeneralSecurityException, EncryptionException, IOException {
        String password = "";

        if (passwordString != null)
            password = passwordString;

        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, "".toCharArray());

        setPrivateKey(keyPair.getPrivateKey());
        setPublicKey(keyPair.getPublicKey());

        CertificateSigningRequest certificateSigningRequest =
                publicKey.createCertificateSigningRequest(keyPair.getPrivateKey());

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date aYearFromNow = calendar.getTime();

        Certificate certificate = privateKey.sign(certificateSigningRequest, now, aYearFromNow);
        X509Certificate[] chain = { certificate.getCertificate() };
        keyStore.setKeyEntry(ALIAS, keyPair.getPrivateKey().getSecurityPrivateKey(), password.toCharArray(), chain);

        setLoaded(true);

        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        keyStore.store(fileOutputStream, password.toCharArray());
        fileOutputStream.close();
    }
}
