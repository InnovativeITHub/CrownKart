package com.innovative.crownkart.api;

import com.innovative.crownkart.config.App;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class ApiHelper {
    private static App app;
    private static ApiHelper apiHelper;

    private ApiHelper(){}

    public static ApiHelper init(App app){
        if(apiHelper==null){
            apiHelper=new ApiHelper();
            apiHelper.initApiService();
            setApp(app);
        }
        return apiHelper;
    }

    public static void setApp(App app) {
        ApiHelper.app = app;
    }

    private void initApiService() {

    }
}
