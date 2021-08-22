package ir.fanniherfei.tolid;

import android.util.Log;

import java.security.PublicKey;
import java.util.ArrayList;

public class Team {
    public String name;
    public PersonInTeam[] people = new PersonInTeam[5];
    public String about;
    public String schoolQuota;
    public String budgetMan;
    public String getBudgetManQuota;
    public String mainProject;
    public String backUpProject;
    public String reserveProject;
    public String finalProject;

    //constructor
    public Team(ArrayList<String> data){
        put(data);
    }

    //put data
    public boolean put(ArrayList<String> data){
        if(data.size() !=64)
            return false;
        name = data.get(0);
        ArrayList<String>[] array = new ArrayList[5];

        for(int i = 0;i<5;i++)
            array[i] = new ArrayList<>();

        for(int i = 1;i<56;i++)
            array[(i-1)%5].add(data.get(i));

        for (int i = 0; i < 5 ;i++)
            people[i] = new PersonInTeam(array[i]);
        about = data.get(56);
        schoolQuota = data.get(57);
        budgetMan = data.get(58);
        getBudgetManQuota = data.get(59);
        mainProject = data.get(60);
        backUpProject = data.get(61);
        reserveProject = data.get(62);
        finalProject = data.get(63);
        return true;
    }

    //get data
    private String getData(int index){
        switch (index){
            case 0:
                return name;
            case 56:
                return about;
            case 57:
                return schoolQuota;
            case 58:
                return budgetMan;
            case 59:
                return getBudgetManQuota;
            case 60:
                return mainProject;
            case 61:
                return backUpProject;
            case 62:
                return reserveProject;
            case 63:
                return finalProject;
            default:
                return null;
        }
    }

    //get
    public String get(int index){
        if (index>=56 || index == 0)
            return getData(index);
        return people[(index-1)%5].get((index-1)/5);
    }

    //is Empty
    public boolean isEmpty(){
        if(name == null)
            return false;
        return name.isEmpty();
    }

    //to string for print
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tname => ").append(name).append("\n");
        sb.append("\t\taboute => ").append(about).append("\n");
        sb.append("\t\tschool Quota => ").append(schoolQuota).append("\n");
        sb.append("\t\tbudget Man => ").append(budgetMan).append("\n");
        sb.append("\t\tget Budget Man Quota => ").append(getBudgetManQuota).append("\n");
        sb.append("\t\tmain Project => ").append(mainProject).append("\n");
        sb.append("\t\tback Up Project => ").append(backUpProject).append("\n");
        sb.append("\t\treserve Project => ").append(reserveProject).append("\n");
        sb.append("\t\tfinal Project => ").append(finalProject).append("\n");
        sb.append("\t\tin this method, people not mention !").append("\n");
        sb.append("\t finish team info\n");
        return sb.toString();
    }

    //print this obj
    public void print(){
        Log.i("team "+name+" info : \n",toString());
    }

    //print for all
    public static void printAll(ArrayList<Team> data){
        for(Team t : data)
            t.print();
    }
}
