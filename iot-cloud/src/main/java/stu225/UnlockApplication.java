package stu225;

import stu225.controller.UnlockController;

import java.util.Scanner;

public class UnlockApplication {
    public static void main(String[] args){
        UnlockController unlockController=new UnlockController();
        Scanner scanner = new Scanner(System.in);
        String pwd=null;
        System.out.print("请输入密码解锁：");
        while(scanner.hasNextLine()){
            pwd=scanner.nextLine();
            System.out.println(unlockController.unlock(pwd));
            System.out.print("请输入密码解锁：");
        }
    }
}
