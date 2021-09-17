package ir.fanniherfei.tolid;

import java.util.ArrayList;

public class PersonInTeam{
    public String name;
    public String profession;
    public String eduQuota;
    public String ideaQuota;
    public String firstBudgetQuota;
    public String buildHelpQuota;
    public String introduceBudgetManQuota;
    public String managerQuota;
    public String sellingManagerQuota;
    public String budgetQuota;
    public String sumQuotas;

    //constructor
    public PersonInTeam(ArrayList<String> data){
        put(data);
    }

    //put data
    public void put(ArrayList<String> data){
        if(data.size() !=11)
            return;
        name = data.get(0);
        profession = data.get(1);
        eduQuota = data.get(2);
        ideaQuota = data.get(3);
        firstBudgetQuota = data.get(4);
        buildHelpQuota = data.get(5);
        introduceBudgetManQuota = data.get(6);
        managerQuota = data.get(7);
        sellingManagerQuota = data.get(8);
        budgetQuota = data.get(9);
        sumQuotas = data.get(10);
    }

    //get data
    public String get(int index){
        switch (index)
        {
            case 0:
                return name;
            case 1:
                return profession;
            case 2:
                return eduQuota;
            case 3:
                return ideaQuota;
            case 4:
                return firstBudgetQuota;
            case 5:
                return buildHelpQuota;
            case 6:
                return introduceBudgetManQuota;
            case 7:
                return managerQuota;
            case 8:
                return sellingManagerQuota;
            case 9:
                return budgetQuota;
            case 10:
                return sumQuotas;
            default:
                return null;
        }
    }
}