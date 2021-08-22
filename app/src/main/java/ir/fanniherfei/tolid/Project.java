package ir.fanniherfei.tolid;

import android.util.Log;

import java.util.ArrayList;

public class Project {
    public String name;
    public String firstSampleCost;
    public String minimumProfit;
    public String minimumSell;
    public String timeToEnd;
    public String about;
    public Quota[] quotas = new Quota[5];

    //constructor
    public Project(ArrayList<String> data){
        put(data);
    }

    //put data
    public boolean put(ArrayList<String> data){
        if(data.size() !=64)
            return false;
        name = data.get(0);
        firstSampleCost = data.get(2);
        minimumProfit = data.get(3);
        minimumSell = data.get(4);
        return true;
    }

    //get data
    public String get(int index){
        switch (index)
        {
            case 0:
                return name;
            case 2:
                return firstSampleCost;
            case 3:
                return minimumProfit;
            case 4:
                return minimumSell;
            default:
                return null;
        }
    }

    //is Empty
    public boolean isEmpty(){
        if(name == null)
            return true;
        return name.isEmpty();
    }

    //quota obj
    class Quota{
        public String profession;
        public String percent;
        public Quota(String profession,String percent){
            this.profession = profession;
            this.percent = percent;
        }
    }

    //to string for print
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\t\tname => ").append(name).append("\n");
        sb.append("\t\tfirst sample cast => ").append(firstSampleCost).append("\n");
        sb.append("\t\tminimum profit => ").append(minimumProfit).append("\n");
        sb.append("\t\tminimum sell => ").append(minimumSell).append("\n");
        sb.append("\t finish project info\n");
        return sb.toString();
    }

    //print this obj
    public void print(){
        Log.i("Project "+name+" info : \n",toString());
    }

    //print for all
    public static void printAll(ArrayList<Project> data){
        for(Project p : data)
            p.print();
    }
}
