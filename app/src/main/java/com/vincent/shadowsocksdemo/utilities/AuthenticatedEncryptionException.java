package com.vincent.shadowsocksdemo.utilities;

/**
 * Thrown during the process of {@link AuthenticatedEncryptionException}
 *
 * @author Patrick Favre-Bulle
 * @since 18.12.2017
 */

class AuthenticatedEncryptionException extends Exception {

    AuthenticatedEncryptionException(String message) {
        super(message);
    }

    AuthenticatedEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
