package ir.fanniherfei.tolid;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Person {
    public int index;
    public String name;
    public String nationalCode;
    public String birth;
    public String age;
    public String father;
    public String phoneNumber;
    public String mobileNumber;
    public String address;
    public String eduLevel;
    public String eduPlace;
    public String eduMajor;
    public Sex sex;
    public String about;

    //constructor
    public Person(int index,ArrayList<String> data){
        put(index,data);
    }

    //put info
    public void put(int index,ArrayList<String> data){
        this.index = index;
        if (data.size() != 14)
            return;
        if(data.get(12).isEmpty())
            data.set(12,"0");
        name = data.get(0);
        nationalCode = data.get(2);
        birth = data.get(3);
        age = data.get(4);
        father = data.get(5);
        phoneNumber = data.get(6);
        mobileNumber = data.get(7);
        address = data.get(8);
        eduLevel = data.get(9);
        eduPlace = data.get(10);
        eduMajor = data.get(11);
        this.sex = Sex.values()[Integer.parseInt(data.get(12))];
        about = data.get(13);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());
        if (!birth.isEmpty())
        age = String.valueOf(Integer.parseInt(formatter.format(date)) - (Integer.parseInt(new String(new char[]{birth.charAt(2),birth.charAt(3)})) + 1921));

    }

    //get
    public String get(int index){
        switch (index){
            case 0:
                return name;
            case 2:
                return nationalCode;
            case 3:
                return birth;
            case 4:
                return age;
            case 5:
                return father;
            case 6:
                return phoneNumber;
            case 7:
                return mobileNumber;
            case 8:
                return address;
            case 9:
                return eduLevel;
            case 10:
                return eduPlace;
            case 11:
                return eduMajor;
            case 12:
                return sex.toString();
            case 13:
                return about;
            default:
                return null;
        }
    }

    //is empty
    public boolean isEmpty(){
        if(name == null)
            return true;
        return name.isEmpty();
    }

    // to string
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(sex==Sex.مرد?"آقای":"خانم").append(" ");
        sb.append(name);
        sb.append(" فرزند ").append(father);
        sb.append(" با شماره ملی ").append(nationalCode);
        sb.append(" متولد ").append(birth);
        sb.append(" ( ").append(age).append("سال ) ");
        sb.append("با تلفن ثابت ").append(phoneNumber);
        sb.append(" و شماره همراه ").append(mobileNumber);
        sb.append("، تحصیل کرده ").append(eduMajor);
        sb.append(" در ").append(eduPlace);
        sb.append(" با اخذ مدرک ").append(eduLevel);
        sb.append(" در ").append(address).append(" زندگی می کند ");
        sb.append(" و ").append(about);
        return sb.toString();
    }

    // find by national code
    public static Person findByNationalCode(ArrayList<Person> people,String nationalCode){
        for (Person p : people) {
            if(p.nationalCode.trim().equals(nationalCode.trim()))
                return p;
        }
        return null;
    }

    // print this person
    public void print(){
        Log.i(name,toString());
    }

    //get birth year
    public String birthYear(){
        return new String(new char[]{birth.charAt(0),birth.charAt(1),birth.charAt(2),birth.charAt(3)});
    }

    //get birth year
    public String birthMounth(){
        return new String(new char[]{birth.charAt(4),birth.charAt(5)});
    }

    //get birth year
    public String birthDay(){
        return new String(new char[]{birth.charAt(6),birth.charAt(7)});
    }

    // print all
    public static void printAll(ArrayList<Person> people){
        for (Person p:people) {
            p.print();
        }
    }
}
