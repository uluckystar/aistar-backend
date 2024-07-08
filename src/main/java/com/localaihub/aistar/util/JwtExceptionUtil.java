package com.localaihub.aistar.util;

import com.localaihub.aistar.enums.JwtError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/6 11:17
 */
public class JwtExceptionUtil {

    public static JwtError extractJwtError(Exception e) {
        if (e instanceof ExpiredJwtException) {
            return JwtError.EXPIRED_TOKEN;
        } else if (e instanceof UnsupportedJwtException) {
            return JwtError.UNSUPPORTED_TOKEN;
        } else if (e instanceof MalformedJwtException) {
            return JwtError.MALFORMED_TOKEN;
        } else if (e instanceof SignatureException) {
            return JwtError.INVALID_SIGNATURE;
        } else if (e instanceof IllegalArgumentException) {
            return JwtError.EMPTY_TOKEN;
        }
        return null;
    }
}
