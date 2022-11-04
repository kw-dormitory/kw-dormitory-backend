package com.kw.kwdn.global.error.exception;

import java.nio.file.AccessDeniedException;

public class JwtAccessDeniedException extends AccessDeniedException {
    public JwtAccessDeniedException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
