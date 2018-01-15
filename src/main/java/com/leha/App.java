package com.leha;

import lombok.SneakyThrows;
import java.util.Scanner;

public class App
{
    @SneakyThrows
    public static void main( String[] args ) {
        Provider provider = new Provider();
        Scanner scanner = new Scanner(System.in);
        String inp,base,target,amount;
        while (true){
            System.out.println("Enter base currency or 'q' to quit:");
            if((inp = scanner.nextLine()).equals("q")){
                return;
            }
            base = inp;
            System.out.println("Enter amount of base currency:");
            amount = scanner.nextLine();
            System.out.println("Enter target currency:");
            target = scanner.nextLine();
            System.out.println(provider.sum(base,amount,target));
        }
    }
}

