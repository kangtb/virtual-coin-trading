package com.ktb.vct.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class SecurityProviderConfig {
    static {
        // 配置第三方的加密算法
        Security.addProvider(new BouncyCastleProvider());
    }
}
