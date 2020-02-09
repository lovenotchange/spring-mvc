package com.controller;

import com.ianno.AppleController;
import com.ianno.AppleRequestMapping;

/**
 * test class
 * Created by cx on 2020/2/8.
 */
@AppleController
@AppleRequestMapping("/test")
@SuppressWarnings("unused")
public class NanaController {

    @AppleRequestMapping("/nana")
    public String getNana() {
        System.out.println("执行了此方法......");
        return "success";
    }

    @AppleRequestMapping("/huanhuan")
    public  String getHuanhuan(){
        return "error";
    }
}
