package br.com.accountcontrol.security;

public class SecurityConstants {

    static final long EXPIRATION_TIME = 86400000L;
    static final String SECRET = "accountControl";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
}
