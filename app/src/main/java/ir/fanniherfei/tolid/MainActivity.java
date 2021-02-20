package ir.fanniherfei.tolid;

import ir.fanniherfei.tolid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends Activity {
    //static String ss="348626";
    static String[] corocters={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"," ","\n",".","?","!",",","@","&","(",")","-","_",":","«","»","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","$","#","ا","ب","پ","ت","ث","ج","چ","ح","خ","د","ذ","ر","ز","ژ","س","ش","ص","ض","ط","ظ","ع","غ","ف","ق","ک","گ","ل","م","ن","و","ه","ی","آ","©","®","¶"};

    static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        tv=(TextView)findViewById(R.id.textView2);
        tv.setText("");
        G.conn=true;
        new CountDownTimer(50000,1000) {

            @Override
            public void onTick(long arg0) {
                // TODO Auto-generated method stub
                if(G.conn){
                    tv.setText(tv.getText().toString()+".");
                    if(tv.getText().toString().length()==5){tv.setText(".");}}
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

            }
        }.start();
        try{
            if(G.send){
                if(!G .progress){
                    String fin="";
                    int pas=G.importPeopleNumber-1;
                    try{
                        //int im=1;
                        //while(!shared.getString("people"+String.valueOf(im),"").equals("")){im++;}
                        //String[] pass=G.password.split("&");
                        //String a=depass(Integer.parseInt(pass[1]));
                        //pas=im-2;
                    }catch (Exception e) {
                    }
                    fin=fin+shared.getString("people"+String.valueOf((pas*14)+1),"");
                    for(int x=((pas)*14)+1;x<((pas)*14)+14;x++){
                        fin=fin+"®"+shared.getString("people"+String.valueOf(x+1),"");
                    }
                    G.url=fin;
                    at.Toast(fin);
                }
                //Toast.makeText(getApplicationContext(), G.url, Toast.LENGTH_LONG).show();
                String ur="";
                String res="";
                if(G.url2.length()>80 || G.url.length()>80){

                    char[] c=G.url.toCharArray();
                    for(int x=0;x<79;x++){ur=ur+String.valueOf(c[x]);}
                    if(G.url.contains("¶")){res=ur;}else{res="®"+ur;}

                    G.url="¶"+G.url.replaceFirst(ur,"");
                    G.progress=true;
                }else{if(!G.progress){res="®"+G.url;G.progress=false;}else{res=G.url;G.progress=false;}}
                G.url2=res;
                G.send=false;
            }
            new NetCheck().execute();

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(MainActivity.this);
            nDialog.setTitle("در حال بررسی اینترنت");
            nDialog.setMessage("Loading..");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);

            //nDialog.show();
        }
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
        @Override
        protected Boolean doInBackground(String... args){

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new GetData().execute();
            }
            else{
                nDialog.dismiss();
                Toast.makeText(getApplicationContext(), "خطا در برقراری ارتباط", Toast.LENGTH_SHORT).show();
                G.conn=false;
                tv.setText("خطا در برقراری ارتباط!");
            }
        }
    }


    /**
     * Async Task to get data from URL
     **/
    private class GetData extends AsyncTask<String, String, String> {

        private ProgressDialog pDialog;
        private InputStream is = null;

        private String url = "http://fanni.freeasphost.net/default.aspx/010/";
//"http://tmebahar.ir/uploads/data.txt";

        private String page_output = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setTitle("در حال ارتباط با سرور ....");
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            //pDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... args) {

            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.ISO_8859_1), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                page_output = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            return page_output;
        }

        @Override
        protected void onPostExecute(String page_output) {
            pDialog.dismiss();
            at.Toast(page_output);
            if(G.progress){
                G.send=true;startActivity(new Intent(MainActivity.this,MainActivity.class));G.progress=true;finish();}else{
                try {
                    G.connect=true;
                    G.result=page_output.trim();
                    G.resultText=encode(page_output.trim());
                    int newss=0;
                    int nnews=0;
                    if(G.recieve){
                        try{
                            final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = shared.edit();
                            for(int x=0;x<Integer.parseInt(shared.getString("peoplenumber", "0"));x++){
                                if(shared.getString("people"+String.valueOf((x*14)+2),"").length()!=0){newss++;}
                            }
                            String[] tables=G.resultText.split("©");
                            try{
                                String[] projects=tables[1].split("®");
                                try {

                                    char[] proj=tables[1].toCharArray();
                                    int prn=0;
                                    for(int x=0;x<proj.length;x++){if("®".equals(String.valueOf(proj[x]))){prn++;}}
                                    prn=prn/63;
                                    // Toast.makeText(getApplicationContext(), String.valueOf(projects.length),Toast.LENGTH_LONG).show();
                                    editor.putString("projectnumber",String.valueOf(prn));
                                    editor.commit();
                                    for(int x=0;x<projects.length;x++){
                                        editor.putString("projects"+String.valueOf(x+1),projects[x]);
                                    }
                                    String[] incomming=tables[4].split("®");
                                    for(int x=0;x<incomming.length;x++){
                                        editor.putString("incomming"+String.valueOf(x+1),incomming[x]);
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }catch(Exception e){
                                // Toast.makeText(getApplicationContext(),"تیم ها و محصولات دریافت نشدند!",Toast.LENGTH_LONG).show();
                            }
                            try{
                                String[] people=tables[2].split("®");
                                char[] peop=tables[2].toCharArray();
                                int pen=0;
                                for(int x=0;x<peop.length;x++){if("®".equals(String.valueOf(peop[x]))){pen++;}}
                                pen=pen/13;
                                editor.putString("peoplenumber",String.valueOf(pen));
                                for(int x=0;x<people.length;x++){
                                    editor.putString("people"+String.valueOf(x+1),people[x]);
                                }
                                String[] bill=tables[3].split("®");
                                for(int x=0;x<bill.length;x++){
                                    editor.putString("bill"+String.valueOf(x+1),bill[x]);
                                }
                                editor.commit();
                                for(int x=0;x<Integer.parseInt(shared.getString("peoplenumber", "0"));x++){
                                    if(shared.getString("people"+String.valueOf((x*14)+2),"").length()!=0){nnews++;}
                                }
                                if(nnews>newss){G.newnews=true;}
                                //Toast.makeText(getApplicationContext(), String.valueOf(nnews)+"\n"+String.valueOf(newss),Toast.LENGTH_LONG).show();
                            }catch(Exception e){}
                            editor.commit();
                        }catch(Exception e){Toast.makeText(getApplicationContext(), "نمی توان اطلاعات را خواند!!", Toast.LENGTH_LONG).show();}
                        G.recieve=false;

                    }
                    if(G.last==1){startActivity(new Intent(MainActivity.this,First.class));G.last=0;}
                    if(G.last==2){startActivity(new Intent(MainActivity.this,Main.class));G.last=0;}
                    if(G.last==3){startActivity(new Intent(MainActivity.this,NewProject.class));G.last=0;}
                    if(G.last==4){startActivity(new Intent(MainActivity.this,NewPerson.class));G.last=0;}
                    if(G.last==5){startActivity(new Intent(MainActivity.this,Bill.class));G.last=0;}
                    if(G.last==6){startActivity(new Intent(MainActivity.this,IncomingProjectManager.class));G.last=0;}
                    if(G.last==7){startActivity(new Intent(MainActivity.this,IncomingManager.class));G.last=0;}
                    finish();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "خطا! \n"+page_output, Toast.LENGTH_LONG).show();
                }}
            //Toast.makeText(getApplicationContext(), "text:"+page_output, Toast.LENGTH_LONG).show();
        }
    }

    static String decode(String first){
        String result="";
        char[] c=first.toCharArray();
        for(int x=0;x<c.length;x++){
            for(int y=0;y<corocters.length;y++){
                if(String.valueOf(c[x]).equals(corocters[y])){
                    if(y<10){result=result+"00"+String.valueOf(y);}
                    else{if(y<100){result=result+"0"+String.valueOf(y);}
                    else{result=result+String.valueOf(y);}}

                }
            }
        }
        return result;
    }
    static String encode(String first){
        String result="";
        try{
            char[] c=first.toCharArray();
            for(int x=0;x<c.length;x=x+3){
                String text=String.valueOf(c[x])+String.valueOf(c[x+1])+String.valueOf(c[x+2]);
                result=result+corocters[Integer.parseInt(text)];
            }}catch(Exception r){
            //Toast.makeText(getApplicationContext(),first.length(),Toast.LENGTH_LONG).show();
        }
        return result;
    }
    static String depass(int i){
        String result="";
        int a=i-1000;
        int b=a/75;
        double c=Math.pow(b,0.5);
        int d=(int)c;
        result =String.valueOf(d);
        return result;
    }
}
