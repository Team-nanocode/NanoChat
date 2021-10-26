package com.pdn.eng.cipher.nanochat;

public class CreateUser {

    public boolean checkForNumbers(String str){
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                return true;
            }
        }
        return false;
    }

    public boolean checkForCapitalLetters(String str){
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z'){
                return true;
            }
        }
        return false;
    }

    public boolean checkForSimpleLetters(String str){
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) >= 'a' && str.charAt(i) <= 'z'){
                return true;
            }
        }
        return false;
    }

    public boolean checkForSpecialCharacters(String str){
        for (int i = 0; i < str.length(); i++) {
            if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9') && !(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
                    && !(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')){
                return false;
            }
        }
        return true;
    }

    public boolean checkForLength(String str,int length){
        return str.length() >= length;
    }

    public boolean isValidPassword(String password){
        //must have at least 8 characters
        //must have at least 1 capital letter
        //must have at least 1 simple letter
        //must have at least 1 number
        //must have at least 1 special character
        return checkForLength(password,8)&&checkForNumbers(password)&&checkForCapitalLetters(password)&&
                checkForSimpleLetters(password) &&checkForSpecialCharacters(password);
    }

    public boolean isValidUserName(String userName){
        //first letter should be a letter
        if(userName.charAt(0) < 'A' || userName.charAt(0) > 'z'){
            return false;
        }
        //must have at least 6 characters
        if(!checkForLength(userName,6)){
            return false;
        }
        //then username is valid
        return true;
    }



    public boolean isValidPhoneNumber(String phoneNumber){
        //must have 10 numbers
        if(phoneNumber.length() != 10){
            return false;
        }
        //all characters should be numbers
        for (int i = 0; i < phoneNumber.length(); i++) {
            if(phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9'){
                return false;
            }
        }
        //phone number is valid
        return true;
    }
}


