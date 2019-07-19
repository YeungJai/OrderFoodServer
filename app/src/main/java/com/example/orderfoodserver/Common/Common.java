package com.example.orderfoodserver.Common;


import com.example.orderfoodserver.Model.Users;

public class Common {
    public static Users currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final int PICK_IMAGE_REQUEST = 80;

    public static String convertCodeToStatus(String code){
        if (code.equals("0"))
            return "Doing Now";
        else if (code.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }

}
