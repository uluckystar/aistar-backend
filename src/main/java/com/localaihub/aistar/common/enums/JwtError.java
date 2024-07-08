package com.localaihub.aistar.common.enums;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/6 11:16
 */
public enum JwtError {
    EXPIRED_TOKEN("JWT令牌已过期"),
    UNSUPPORTED_TOKEN("不支持的JWT令牌"),
    MALFORMED_TOKEN("JWT令牌格式错误"),
    INVALID_SIGNATURE("JWT签名验证失败"),
    EMPTY_TOKEN("JWT令牌为空");

    private final String message;

    JwtError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}