package com.ltsllc.miranda;

import com.ltsllc.miranda.clientinterface.MirandaException;

public class StartupException extends MirandaException {
    public enum Reasons {
        MissingKeystoreFile
    }

    private Reasons reason;


    public Reasons getReason() {
        return reason;
    }

    public void setReason(Reasons reason) {
        this.reason = reason;
    }

    public StartupException (Reasons reason) {
        this.reason = reason;
    }

    public StartupException (Reasons reason, String message) {
        super(message);

        this.reason = reason;
    }
}
