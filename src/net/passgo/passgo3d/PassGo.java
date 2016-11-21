package net.passgo.passgo3d;


import android.util.Log;

public class PassGo {

    private final String TAG="PassGo";
    public String pwd;
    int k,l,k2,k3,l2,l3,n;
    /** Creates a new instance of passwd */
    public PassGo(String pwd) {
        this.pwd=pwd;
    }

    public String readablecode() {
        String  code="";
        for (int n=0; n<pwd.length(); n+=6){
            k=(int)pwd.charAt(n)-48; l=(int)pwd.charAt(n+1)-48;
            k2=(int)pwd.charAt(n+2)-48; l2=(int)pwd.charAt(n+3)-48;
            k3=(int)pwd.charAt(n+4)-48; l3=(int)pwd.charAt(n+5)-48;
            if (k==0 && l==0 && k2==0 && l2==0 && k3==0 && l3==0){
                code=code+" | ";
            }else {
                code = code + "(" +k+l +"," + k2+l2 + "," + k3+l3 + ")";
            }
        }
//        Log.i(TAG,"readable is "+code);
        return code;
    }

    public int length() {
        int length=0;
        for (n=0; n<pwd.length(); n+=6) {
            k=(int)pwd.charAt(n)-48; l=(int)pwd.charAt(n+1)-48;
            k2=(int)pwd.charAt(n+2)-48; l2=(int)pwd.charAt(n+3)-48;
            k3=(int)pwd.charAt(n+4)-48; l3=(int)pwd.charAt(n+5)-48;
            if(!(k==0 && l==0 && k2==0 && l2==0 && k3==0 && l3==0)) length++;
        }
        return length;
    }

//    public float longestStrokeLength() {
//        float strokeLength=0, longestStrokeLength=0;
//        for (n=0; n<pwd.length(); n+=2) {
//            k=(int)pwd.charAt(n)-48; l=(int)pwd.charAt(n+1)-48;
//            if(k!=0) strokeLength++;
//            else if(l==0) {
//                if (strokeLength>longestStrokeLength) longestStrokeLength=strokeLength;
//                strokeLength=0;
//            }
//        }
//        return longestStrokeLength;
//    }

    public int strokeCount() {
        int strokeCount=0;
        for (n=0; n<pwd.length(); n+=6) {
            k=(int)pwd.charAt(n)-48; l=(int)pwd.charAt(n+1)-48;
            k2=(int)pwd.charAt(n+2)-48; l2=(int)pwd.charAt(n+3)-48;
            k3=(int)pwd.charAt(n+4)-48; l3=(int)pwd.charAt(n+5)-48;
            if(k==0 && l==0 && k2==0 && l2==0 && k3==0 && l3==0) strokeCount++;
        }
        return strokeCount;
    }

//    public float dotCount() {
//        float dotCount=0;
//        if (pwd.length()==0) return 0;
//        if ((int)pwd.charAt(0)-48!=0 && (int)pwd.charAt(2)-48==0) {
//            dotCount++;
//        };
//        for (n=2; n<pwd.length()-3; n+=2){
//            if ((int)pwd.charAt(n)-48!=0) {
//                if ((int)pwd.charAt(n-2)-48==0 && (int)pwd.charAt(n+2)-48==0) {
//                    dotCount++;
//                }
//            }
//        }
//        return dotCount;
//    }

//
//    public int startfromsorc() {
//
//        if (pwd.length()==0) return 1;
//        else{
//            k=(int)pwd.charAt(0)-48; l=(int)pwd.charAt(1)-48;
//
//            if(k!=0) {
//                if ((k==1 && l==1)|(k==1 && l==9)|(k==9 && l==1)|(k==9 && l==9)|(k==3 && l==3)|(k==3 && l==7)
//                        |(k==5 && l==5)|(k==7 && l==3)|(k==7 && l==7)) return 1;
//                else return 0;
//            } else {
//                k=(int)pwd.charAt(2)-48; l=(int)pwd.charAt(3)-48;
//                if ((k==1 && l==1)|(k==1 && l==9)|(k==9 && l==1)|(k==9 && l==9)|(k==3 && l==3)|(k==3 && l==7)
//                        |(k==5 && l==5)|(k==7 && l==3)|(k==7 && l==7)) return 1;
//                else return 0;
//            }
//        }
//    }


//    public int endbysorc() {
//
//        k=(int)pwd.charAt(pwd.length()-4)-48; l=(int)pwd.charAt(pwd.length()-3)-48;
//
//        if ((k==1 && l==1)|(k==1 && l==9)|(k==9 && l==1)|(k==9 && l==9)|(k==3 && l==3)|(k==3 && l==7)
//                |(k==5 && l==5)|(k==7 && l==3)|(k==7 && l==7)) return 1;
//        else return 0;
//
//    }
}


