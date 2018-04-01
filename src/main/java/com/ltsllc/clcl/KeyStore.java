package com.ltsllc.clcl;

public class KeyStore {
    private java.security.KeyStore keyStore;

    public java.security.KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(java.security.KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    public KeyStore (java.security.KeyStore keyStore) {
        setKeyStore(keyStore);
    }
}
