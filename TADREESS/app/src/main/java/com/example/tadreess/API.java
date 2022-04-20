package com.example.tadreess;

public class API {

   // public static String baseUrl = "http://168.187.113.178:813/TadreesService.asmx?";
 //  public static String baseUrl = "http://168.187.113.181:813/TadreesService.svc/";
   // public static String baseUrl = "http://rest.tadreess.com/TadreesService.svc/";
    //public static String baseUrl = "http://183.82.111.111/Electa/TadreesService.svc";
    public static String baseUrl = "http://rest.tadreess.com/TadreesService.web.asmx/";



    public static String Login = baseUrl + "LoginUser?";//done
    public static String Signup = baseUrl + "AddNewUser?";//done
    public static String GetMyClasses = baseUrl + "GetMyClasses?";//url issue
    public static String GetStudentPackages = baseUrl + "GetStudentPackages?";//url issue
    public static String ShowProfile = baseUrl + "GetStudent?";
    public static String GetStages = baseUrl + "GetStages?";
    public static String ShowSubject = baseUrl + "GetAllSubjects?";
    public static String UpdateProfile = baseUrl + "EditProfile?";
    public static String GetMyshedules = baseUrl + "GetMyshedules?";
    public static String TransectionHistory = baseUrl + "Transactionhistory?";
    public static String ChangePassword = baseUrl + "ChangePassword?";//done


}
