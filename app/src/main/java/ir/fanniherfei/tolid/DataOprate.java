package ir.fanniherfei.tolid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataOprate {
    final private String[] corocters={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"," ","\n",".","?","!",",","@","&","(",")","-","_",":","«","»","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","$","#","ا","ب","پ","ت","ث","ج","چ","ح","خ","د","ذ","ر","ز","ژ","س","ش","ص","ض","ط","ظ","ع","غ","ف","ق","ک","گ","ل","م","ن","و","ه","ی","آ","©","®","¶"};
    private String wholeText = "";
    private String text = "";
    public boolean hasError ;
    private String[] textTables;
    private ArrayList[] tables = new ArrayList[5];
    private ArrayList<Project> allProjects = new ArrayList<>();
    private ArrayList<Team> allTeams = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<BillObj> Bills = new ArrayList<>();
    private ArrayList<News> News = new ArrayList<>();
    //constructor
    public DataOprate(String wholeText){
        this.wholeText = wholeText;
        String text = wholeToText(wholeText);
        hasError = isError(text);
        if(hasError)
            return;
        ArrayList<String>[] tables = createTables(text);
        if(tables.length <5){
            hasError = true;
            return;}
        exportTeamsAndProjects(tables[1]);
        exportPeopleAndNews(tables[2]);
        exportBill(tables[3]);
        Log.i("bills",String.valueOf(Bills.get(0).nodes.get(0).cast));
    }

    //get person
    public Person getPerson(String nationalCode){
        return Person.findByNationalCode(people,nationalCode);
    }

    //get teams by person
    public List<Team> getTeams(Person person){return getTeams(person.name);}

    //get teams by person
    private List<Team> getTeams(String partnerName){
        List<Team> result = new ArrayList<>();
        for (Team t:allTeams)
            for (PersonInTeam p:t.people)
                if (p.name.equals(partnerName))
                    result.add(t);
        return result;
    }

    //object people and news
    private void exportPeopleAndNews(ArrayList<String> PeopleAndNews){
        ArrayList<Person> people = new ArrayList<>();
        int  counter = 0;
        ArrayList<String> currentObj ;
        for (int i =0;i<PeopleAndNews.size();i++){
            currentObj = new ArrayList<>();
            for (int j =0; (j<14) && (i<PeopleAndNews.size()) ;j++,i++){
                currentObj.add(PeopleAndNews.get(i));
            }
            counter++;
            Person temp = new Person(counter,currentObj);
            if(!temp.isEmpty()) {
                people.add(temp);
            }
            i--;
        }
        this.people = people;
    }

    //object projects and teams
    private void exportTeamsAndProjects(ArrayList<String> teamsAndProjects){
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<String> currentObj = new ArrayList<>();
        for (int i =0;i<teamsAndProjects.size();i++){
            currentObj = new ArrayList<>();
            for (int j =0; (j<64) && (i<teamsAndProjects.size()) ;j++,i++){
                currentObj.add(teamsAndProjects.get(i));
            }
            if(currentObj.get(1).isEmpty())
            {
                Project temp = new Project(currentObj);
                if(!temp.isEmpty()){
                    projects.add(temp);
                }
            }else{
                Team temp = new Team(currentObj);
                if(!temp.isEmpty())
                    teams.add(temp);
            }
            i--;
        }
        allProjects = projects;
        allTeams = teams;
    }

    //object bill
    private void exportBill(ArrayList<String> bills){
        ArrayList<BillObj> result = new ArrayList<>();
        int counter = 0;
        for (int i =0;i<bills.size();i++){
            String[] data = new String[3];
            for (int j =0; (j<3) && (i<bills.size()) ;j++,i++){
                data[j] = bills.get(i);
            }
            counter++;
            if(!data[0].isEmpty())
            {
                String[] projectNodes = data[0].split("¶");
                String[] castNodes = data[1].split("¶");
                String[] dateNodes = data[2].split("¶");
                BillObj bo = new BillObj(counter);
                for (int k = 0;k <castNodes.length;k++) {
                    bo.put(new BillNode(projectNodes[k],castNodes[k],dateNodes[k]));
                }
                result.add(bo);
            }
            i--;
        }
        this.Bills =result;
    }

    //get bill by index
    public BillObj getBill(int index){
        for (BillObj bo:Bills)
            if(bo.index == index)
                return bo;
        return null;
    }
    //print table
    public void printTable(ArrayList<String>[] tables){
        StringBuilder result = new StringBuilder();
        result.append("lengthes : \n");
        for (int i =0;i<tables.length;i++)
            result.append("\t").append("length of subject ").append(i).append(" : ").append(tables[i].size()).append("\n");
        result.append("data : \n");
        Log.d("print table",result.toString());
        result = new StringBuilder();
        for (int i =0;i<tables.length;i++){
            result.append("\t").append("data of subject ").append(i).append(" : ").append("\n");
            for (int j = 0 ; j<tables[i].size();j++){
                result.append("\t\t").append("field ").append(j).append(" => ").append(tables[i].get(j)).append("\n");
                Log.d("print table",result.toString());
                result = new StringBuilder();
            }
        }
        result.append("finish");
        Log.d("print table",result.toString());
    }
    public void printTable(){
        printTable(tables);
    }

    //create tables
    private ArrayList<String>[] createTables(String text){
        String[] taxtTables = text.split("©");
        ArrayList<String> tables[] = new ArrayList[taxtTables.length];
        this.textTables = taxtTables;
        for (int i =0;i<tables.length;i++)
        {
            tables[i] = new ArrayList<>();
            String[] taxtSubjects = taxtTables[i].split("®");
            for(String field : taxtSubjects){
                tables[i].add(field);
            }
        }
        this.tables = tables;
        return tables;
    }

    //encoder method
    private String wholeToText(String wholeText){
        text = encode(wholeText);
        return text;
    }

    //getter
    public String getWholeText(){return  wholeText;}
    public String getText(){return  text;}

    //encoder
    private String encode(String first){
        StringBuilder result=new StringBuilder();
            char[] c=first.toCharArray();
            for(int x=0;x<c.length;x=x+3){
                try {
                    String text = String.valueOf(c[x]) + c[x + 1] + c[x + 2];
                    result.append(corocters[Integer.parseInt(text)]);
                }catch (Exception e){
                    Log.i("probe whith",String.valueOf(x));
                }
            }
        return result.toString();
    }

    //has error
    public boolean isError(String text){
        return text.toLowerCase().equals("error");
    }
}
