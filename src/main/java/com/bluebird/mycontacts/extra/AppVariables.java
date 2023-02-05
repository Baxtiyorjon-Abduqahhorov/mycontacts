package com.bluebird.mycontacts.extra;

import org.springframework.beans.factory.annotation.Value;

public class AppVariables {

    public static String PATH = System.getProperty("user.dir").replace("/build/libs", "") + "/src/main/resources/storage/";

    @Value("${server.host}")
    static String serverHost;

    public static String IMAGE_SERVER_URL = serverHost + "/api/info/picture";
}
