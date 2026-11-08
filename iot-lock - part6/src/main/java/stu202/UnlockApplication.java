package stu202;

import stu202.controller.UnlockController;
import java.util.Scanner;

public class UnlockApplication {
    public static void main(String[] args){
        UnlockController unlockController=new UnlockController();
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入密码解锁：");
        while(scanner.hasNextLine()){
            String pwd=scanner.nextLine();
            System.out.println(unlockController.unlock(pwd));
            System.out.print("请输入密码解锁：");
        }
    }
}