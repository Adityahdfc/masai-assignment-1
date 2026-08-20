package service;

import exception.PolicyServiceException;

import java.io.FileWriter;
import java.io.IOException;

public class AuditLogger implements AutoCloseable {

    private FileWriter writer;

    public AuditLogger() {
        try {
            writer = new FileWriter("audit.log", true);
        } catch (IOException ex) {
            throw new PolicyServiceException("Failed to open audit.log", ex);
        }
    }

    public void log(String message) {
        try {
            writer.write(message + "\n");
        } catch (IOException e) {
            throw new PolicyServiceException("Failed to write to audit.log", e);
        }
    }

    @Override
    public void close() {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            throw new PolicyServiceException("Failed to close audit.log", e);
        }
    }
}