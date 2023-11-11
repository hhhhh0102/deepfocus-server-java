package io.poten13.deepfocus.global.constants;

public class Constants {
    public static final String NICKNAME_RESPONSE_FORMAT = "text";
    public static final int REQUIRED_NICKNAME_COUNT = 1;
    public static final int MAX_NICKNAME_LENGTH = 6;
    public static final String USER_TOKEN_HEADER_KEY = "X-User-Token";
    public static final String API_KEY = "api_key";
    public static final String OPEN_AI_CHAT_REQUEST_USER_MESSAGE_PATTERN = "Give {0} subtasks of task \"{1}\"";
    public static final String OPEN_AI_CHAT_REQUEST_SYSTEM_MESSAGE_ONE = "Give Korean answer";
    public static final String OPEN_AI_CHAT_REQUEST_SYSTEM_MESSAGE_TWO = "Use only \";\" for separating multiple answer";


    public static final String[] SWAGGER_URL_LIST = {
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/**/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "**/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui.html",
            "/api-docs",
            "/swagger-ui/**",
            "/api-docs/**"
    };
}
