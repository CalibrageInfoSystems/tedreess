package com.example.tadreess;

import android.content.Context;
import android.content.pm.PackageManager;

public class CommonHelper {

    String BASE_WEB_URL = "http://183.82.111.111/Tadreess/trader.html?Cid=123&Token=fg5656546gfhf&Roomid=23&Usertype=test&Firstname=mahesh&Lastname=mallem&Email=mallemmahesh@gmail.com&UniqueUsername=mahesh";


   public static String getBASE_WEB_URL(String Cid, String Token, String RoomID, String UserType, String FirstName, String Lastname, String Email, String UniqueName)
   {
       return "http://183.82.111.111/Tadreess/trader.html?Cid="+Cid+"&Token="+Token+"&Roomid="+RoomID+"&Usertype="+UserType+"&Firstname="+FirstName+"&Lastname="+Lastname+"&Email="+Email+"&UniqueUsername="+UniqueName;
   }

    public static boolean appInstalledOrNot(Context ctx, String uri) {
        PackageManager pm = ctx.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
