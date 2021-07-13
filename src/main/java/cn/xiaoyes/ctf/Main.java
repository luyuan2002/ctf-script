package cn.xiaoyes.ctf;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("请选择要执行的操作 => 1 提交Flag  2 注入木马: ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        AppProvider provider = new AppProvider();
        if (n == 1) {
            System.out.print("请输入你的Token: ");
            String token = input.next();
            provider.saveFlag(token);
        } else if (n == 2) {
            provider.uploadHorse("cc.php", "cc");
        } else {
            System.out.println("输入错误");
        }
    }
}
