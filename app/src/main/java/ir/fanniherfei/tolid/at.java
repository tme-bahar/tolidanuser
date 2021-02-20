package ir.fanniherfei.tolid;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class at extends Activity{
	static Context c;
	//Toasting with context
	static void Toast(Context getApplicationContext,String t){
		Toast.makeText(getApplicationContext, t, Toast.LENGTH_LONG).show();
	}
	//Toasting Bog with context
	static void ToastException(Context getApplicationContext,Exception e){
		Toast.makeText(getApplicationContext, e.toString(), Toast.LENGTH_LONG).show();
	}
	//Toasting
	static void Toast(String t){
		Toast.makeText(c, t, Toast.LENGTH_LONG).show();
	}
	//Toasting Bog
	static void ToastException(Exception e){
		Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
	}
	
	//Toasting Bog massage
	static void ToastExceptionMassage(Exception e){
		Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
	}
	//Toasting Bog with context
	static void ToastExceptionMassage(Context getApplicationContext,Exception e){
		Toast.makeText(getApplicationContext, e.getMessage(), Toast.LENGTH_LONG).show();
	}
	
	//Split String
	static String[] split(String s,char c){
		int length=0;
		char[] ca=s.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if(ca[i]==c){length++;}
		}
		String[] result=new String[length+1];
		for (int i = 0; i < result.length; i++) {
			result[i]="";
		}
		int num=0;
		for (int i = 0; i < ca.length; i++) {
			if(ca[i]==c){num++;}else{result[num]+=ca[i];}
		}
		return result;
	}
	
	//Reanaming a file
	static String rename(String filePath,String newName){
		String subtitle[]=split(filePath,'.');
		String fol[]=split(filePath,'/');
		String folder=fol[0];
		for (int i = 1; i < fol.length-1; i++) {
			folder+="/"+fol[i];
		}
		File selectedFile = new File(filePath);
		String result=folder+"/"+newName+"."+subtitle[subtitle.length-1];
        File newfile = new File(result);
        selectedFile.renameTo(newfile);
        return result;
	}
	
	//Renaming a file and type
	static String renameRetype(String filePath,String newName){
		String fol[]=split(filePath,'/');
		String folder=fol[0];
		for (int i = 1; i < fol.length-1; i++) {
			folder+="/"+fol[i];
		}
		File selectedFile = new File(filePath);
		String result=folder+"/"+newName;
        File newfile = new File(result);
        selectedFile.renameTo(newfile);
        return result;
	}
	
	//Getting file name and type
	static String getNameAndType(String filePath){
		String fol[]=split(filePath,'/');
        return fol[fol.length-1];
	}
	
	//Getting file name
	static String getName(String filePath){
		String subtitle[]=split(filePath,'.');
		String fol[]=split(subtitle[subtitle.length-2],'/');
        return fol[fol.length-1];
	}
	
	//Is empty bog
	static void isEmptyBog(EditText et)throws Exception{
		try{
			isEmptyBog(et.getText().toString());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد  خالی را پر کنید");
		}
		
	}
	
	//Is enything edittext bog
	static void isEnythingBog(EditText et)throws Exception{
		String text = et.getText().toString();
		text.replaceAll("\n","");
		try{
			isEmptyBog(text.trim());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد  خالی را پر کنید");
		}
	}
	
	//Is empty string bog
	static void isEmptyBog(String str)throws Exception{
		if(str.equals("")){throw new Exception("متن خالی است");}
	}
	
	//Is enything string bog
	static void isEnythingBog(String str)throws Exception{
		String text = str;
		text.replaceAll("\n","");
		try{
			isEmptyBog(text.trim());
		}catch (Exception e) {
			throw new Exception("متن خالی است");
		}
	}
	
	//Are empty array bog
	static void isEmptyBog(EditText[] et)throws Exception{
		try{
			for(EditText i:et)
			isEmptyBog(i.getText().toString());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد های  خالی را پر کنید");
		}
	}
	
	//Is enything array edittext bog
	static void isEnythingBog(EditText[] et)throws Exception{
		
		try{
			for(EditText i:et){
				String text = i.getText().toString();
				text.replaceAll("\n","");
			isEmptyBog(text.trim());
			}}catch (Exception e) {
			throw new Exception("لطفاً فیلد های  خالی را پر کنید");
		}
	}

	//Is empty textview bog
	static void isEmptyBog(TextView tv)throws Exception{
		try{
			isEmptyBog(tv.getText().toString());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد  خالی را پر کنید");
		}
		
	}
	
	//Is enything textview bog
	static void isEnythingBog(TextView tv)throws Exception{
		String text = tv.getText().toString();
		text.replaceAll("\n","");
		try{
			isEmptyBog(text.trim());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد  خالی را پر کنید");
		}
	}
	
	//Are empty array textview bog
	static void isEmptyBog(TextView[] tv)throws Exception{
		try{
			for(TextView i:tv)
			isEmptyBog(i.getText().toString());
		}catch (Exception e) {
			throw new Exception("لطفاً فیلد های  خالی را پر کنید");
		}
	}
		
	//Is enything array textview bog
	static void isEnythingBog(TextView[] tv)throws Exception{	
		try{
			for(TextView i:tv){
				String text = i.getText().toString();
				text.replaceAll("\n","");
			isEmptyBog(text.trim());
			}}catch (Exception e) {
			throw new Exception("لطفاً فیلد های  خالی را پر کنید");
		}
	}
	
	//Is solar date bog
	static void isSolarDateBog(String date)throws Exception{
		int length = date.length();
		try{date.replaceAll(" ","/");}catch(Exception e){}
		try{date.replaceAll(".","/");}catch(Exception e){}
		String massage = "لطفا تاریخ را صحیح وارد کنید";
		if(length>10 || length < 6){throw new Exception(massage);}
		String[] split=at.split(date,'/');
		if(split.length != 3){throw new Exception(massage);}
		if(split[0].length()!=4 && split[0].length()!=2){throw new Exception(massage);}
		if(split[1].length()!=1 && split[1].length()!=2){throw new Exception(massage);}
		if(split[2].length()!=1 && split[2].length()!=2){throw new Exception(massage);}
		try{
			int num[]={Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2])};
			//mounth
			if(num[1]>12){throw new Exception(massage);}
			//days
			if(num[1] <= 6 && num[2]>31){throw new Exception(massage);}
			if(num[1] <= 12 && num[2] > 30){throw new Exception(massage);}
			//kabiseh
			if(((num[0]+1)%4) != 0 && num[1] == 12 && num[2] == 30){throw new Exception(massage);}
		}catch (Exception e) {
			throw new Exception(massage);
		}
	}
	
	//Is solar date bog return
	static String isSolarDateBog(String date,String formation)throws Exception{
		int length = date.length();
		String result="";
		try{date=replaceAll(date, ' ','/');}catch(Exception e){}
		try{date=replaceAll(date,'.','/');}catch(Exception e){}
		try{date=replaceAll(date,':','/');}catch(Exception e){}
		String massage = "لطفا تاریخ را صحیح وارد کنید";
		if(length>10 || length < 6){throw new Exception(massage);}
		String[] split=split(date,'/');
		if(split.length != 3){throw new Exception(massage);}
		if(split[0].length()!=4 && split[0].length()!=2){throw new Exception(massage);}
		if(split[1].length()!=1 && split[1].length()!=2){throw new Exception(massage);}
		if(split[2].length()!=1 && split[2].length()!=2){throw new Exception(massage);}
		try{
			int num[]={Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2])};
			//mounth
			if(num[1]>12){throw new Exception(massage);}
			//days
			if(num[1] <= 6 && num[2]>31){throw new Exception(massage);}
			if(num[1] <= 12 && num[2] > 30){throw new Exception(massage);}
			//kabiseh
			if(((num[0]+1)%4) != 0 && num[1] == 12 && num[2] == 30){throw new Exception(massage);}
			
			//form return
			char form[] = formation.toCharArray();
			
			String last="";
			for(int x=0;x<form.length;x++){
				last = last + form[x];
				if("yy".contains(last)
						|| "yyyy".contains(last)
						|| "m".contains(last) 
						|| "mm".contains(last)
						|| "d".contains(last)
						|| "dd".contains(last) ){}else{result = result + last ; last = "";}
				String next1="";
				String next2="";
				try{next2=form[x+1]+String.valueOf(form[x+2]);}catch (Exception e) {}
				try{next1=String.valueOf(form[x+1]);}catch (Exception e) {}
				if("yy".equals(last) && !"yy".equals(next2)){result = result + String.valueOf(num[0]%100);last="";}
				
				if("yyyy".equals(last)){String fin = String.valueOf(num[0]);while(fin.length()<4){fin="0"+fin;} result = result +fin;}
				
				
				if("m".equals(last) && !"m".equals(next1)){result = result + String.valueOf(num[1]);last="";}
				
				if("mm".equals(last)){result = result + (num[1] >= 10 ? String.valueOf(num[1]) : "0"+String.valueOf(num[1]));last="";}
				
				
				if("d".equals(last) && !"d".equals(next1)){result = result + String.valueOf(num[2]);last="";}
				
				if("dd".equals(last)){result = result + (num[2] >= 10 ? String.valueOf(num[2]) : "0"+String.valueOf(num[2]));last="";}
			
			}
		}catch (Exception e) {
			throw new Exception(massage);
		}
		return result;
	}
	
	//replace all char
	static String replaceAll(String input, char first,char second){
		String result="";
		char inputChar[] = input.toCharArray();
		for(int x=0; x < inputChar.length;x++){if(inputChar[x] == first){inputChar[x]=second;}result = result + inputChar[x];}
		return result;
	}
	
	//limited char
	static void LimitedCharacterBog(String text,int low,int high)throws Exception{
		if(text.length()<low || text.length()>high){throw new Exception("اندازه ورودی غیر مجاز است");}
	}
	
	//nation code
	static void isNationalCodeBog(String nationalCode)throws Exception{
		String message = "کد ملی نامعتبر است";
		try{
			Integer.parseInt(nationalCode);
			LimitedCharacterBog(nationalCode, 10, 10);
			if(nationalCode.contains("-") || nationalCode.contains(".") || nationalCode.contains("E")){throw new Exception("");}
		}catch (Exception e) {
			throw new Exception(message);
		}
	}
	
	//is mobile (international)
	static void isMobileBog(String number,boolean international)throws Exception{
		String message = "شماره موبایل نامعتبر است";
		char[] num = number.toCharArray();
		if(international){
			if(num.length != 12){throw new Exception(message);}
			String str = "" + num[0] + num [1] + num[2];
			if(!"+98".equals(str)){throw new Exception(message);}
			str = "" + num[3] + num[4] + num[5] + num[6] + num[7] + num[8] + num[9] + num[10] + num[11];
			if(str.contains("-") || str.contains(".") || str.contains("E")){throw new Exception(message);}
			try{Integer.parseInt(str);}catch (Exception e) {throw new Exception(message);}
			}
		else{
			if(num.length != 10){throw new Exception(message);}
			String str ;
			if('0' != num[0]){throw new Exception(message);}
			str = "" + num[1] + num[2] + num[3] + num[4] + num[5] + num[6] + num[7] + num[8] + num[9];
			if(str.contains("-") || str.contains(".") || str.contains("E")){throw new Exception(message);}
			try{Integer.parseInt(str);}catch (Exception e) {throw new Exception(message);}
			}
	}
	
	//is mobile
		static void isMobileBog(String number)throws Exception{
			String message = "شماره موبایل نامعتبر است";
			try {
				isMobileBog(number,true);
			} catch (Exception e) {
				try {
					isMobileBog(number,false);	
				} catch (Exception e2) {
					throw new Exception(message);
				}
			}
		}
		

	//is phone number
		static void isPhoneBog(String number)throws Exception{
			String message = "شماره تلفن نامعتبر است";
			char[] num = number.toCharArray();
			
				if(num.length != 11){throw new Exception(message);}
				String str ;
				if('0' != num[0]){throw new Exception(message);}
				str = "" + num[1] + num[2] + num[3] + num[4] + num[5] + num[6] + num[7] + num[8] + num[9] + + num[10];
				if(str.contains("-") || str.contains(".") || str.contains("E")){throw new Exception(message);}
				try{Integer.parseInt(str);}catch (Exception e) {throw new Exception(message);}
				
		}
}
