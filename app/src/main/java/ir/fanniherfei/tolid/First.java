package ir.fanniherfei.tolid;

import ir.fanniherfei.tolid.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class First extends Activity {
    ProgressBar pro;
    int hight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        //introduce
        at.c=getApplicationContext();
        pro = (ProgressBar)findViewById(R.id.progressBar);
        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final Button b=(Button)findViewById(R.id.button1);
        final Button p=(Button)findViewById(R.id.button2);
        final CardView cv = (CardView)findViewById(R.id.cardView);
        final EditText[] et={(EditText)findViewById(R.id.editText1),(EditText)findViewById(R.id.editText2)};
        final SharedPreferences.Editor editor = shared.edit();
        final CardView maincv = (CardView)findViewById(R.id.card);
        pro.setVisibility(View.GONE);
        //animation
        maincv.getLayoutParams().height = (int)pxFromDp(this,210);
        hight = 210;
        et[0].setVisibility(View.GONE);
        et[1].setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        p.setVisibility(View.GONE);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeIn);
        cv.setAnimation(animation);
        animation.start();
        ViewGroup.LayoutParams mcvp = maincv.getLayoutParams();
        cv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et[0].getVisibility() == View.VISIBLE)
                    return;
                    et[0].setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 20) {

                    public void onTick(long millisUntilFinished) {
                        if(hight>290)
                            return;
                        mcvp.height = mcvp.height+(int) pxFromDp(First.this,4);
                        hight+=4;
                        maincv.setLayoutParams(mcvp);
                    }
                    public void onFinish() {
                    }
                }.start();
            }
        });
        et[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et[1].getVisibility() == View.VISIBLE)
                    return;
                if(s.toString().length() != 10)
                    return;
                et[1].setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 20) {

                    public void onTick(long millisUntilFinished) {
                        if(hight>350)
                            return;
                        mcvp.height = mcvp.height+(int) pxFromDp(First.this,4);
                        hight+=4;
                        maincv.setLayoutParams(mcvp);
                    }
                    public void onFinish() {
                    }
                }.start();
            }
        });
        et[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(b.getVisibility() == View.VISIBLE)
                    return;
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.VISIBLE);
                new CountDownTimer(1000, 20) {

                    public void onTick(long millisUntilFinished) {
                        if(hight>400)
                            return;
                        mcvp.height = mcvp.height+(int) pxFromDp(First.this,4);
                        hight+=4;
                        maincv.setLayoutParams(mcvp);
                    }
                    public void onFinish() {
                    }
                }.start();
            }
        });

        editor.putBoolean("connected", false);
        editor.apply();
        OnClickListener o=new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                G.url2=et[0].getText().toString()+"&"+et[1].getText().toString();
                G.password=G.url2;
                G.url2="a";
                G.recieve=true;
                G.last=1;
                new NetCheck().execute();
                //startActivity(new Intent(First.this,MainActivity.class));
                //finish();
            }
        };
        b.setOnClickListener(o);
        p.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                G.changenum=Integer.parseInt(shared.getString("peoplenumber", "0")+1);
                G.importPeopleNumber=Integer.parseInt(shared.getString("peoplenumber", "0"))+1;

                startActivity(new Intent(First.this,NewPerson.class));
            }
        });
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {

        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pro.setVisibility(View.VISIBLE);
        }
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
            if(th){
                new GetData().execute();
            }
            else{
                at.Toast("خطا در برقراری ارتباط");
                G.conn=false;
                pro.setVisibility(View.GONE);
            }
        }
    }


    /**
     * Async Task to get data from URL
     **/
    private class GetData extends AsyncTask<String, String, String> {

        private InputStream is = null;
        private String page_output = "";

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... args) {

            try {
                // defaultHttpClient
                String url = "http://fanni.freeasphost.net/default.aspx/010/";
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
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
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
            at.Toast(page_output);
            final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
            final SharedPreferences.Editor editor = shared.edit();
            G.result=page_output.trim();
            int newss=0;
            int nnews=0;
            G.connect=true;
            G.result=page_output.trim();
            G.resultText=MainActivity.encode(page_output.trim());
            try{

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
                        editor.apply();
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
                     Toast.makeText(getApplicationContext(),"تیم ها و محصولات دریافت نشدند!",Toast.LENGTH_LONG).show();
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
                }catch(Exception e){
                    e.printStackTrace();}
                editor.commit();
            }catch(Exception e){Toast.makeText(getApplicationContext(), "نمی توان اطلاعات را خواند!!", Toast.LENGTH_LONG).show();}
            G.recieve=false;
           if(G.connect){
                try{
                    G.connect=false;
                    if(G.resultText.toString().equals("error")){Toast.makeText(getApplicationContext(), "خطا! \n رمز عبور و یا نام کاربری اشتباه است"+G.resultText, Toast.LENGTH_LONG).show();}else{
                        String[] pass=G.password.split("&");
                        String a=depass(Integer.parseInt(pass[1]));
                        int num=((Integer.parseInt(a)-1)*14)+3;
                        //Toast.makeText(getApplicationContext(),String.valueOf(pass[1].trim())+shared.getString("people"+String.valueOf(3), ",").trim(), Toast.LENGTH_LONG).show();

                        if(shared.getString("people"+String.valueOf(num), "").trim().equals(pass[0].trim())){
                            G.name=shared.getString("people"+String.valueOf(num-1),"");
                            Toast.makeText(getApplicationContext(), "اتصال با موفقیت برقرار شد.", Toast.LENGTH_LONG).show();
                            editor.putBoolean("connected",true);editor.commit();
                            startActivity(new Intent(First.this,Main.class));finish();}else{Toast.makeText(getApplicationContext(),"نام کاربری یا رمز عبور اشتباه است",Toast.LENGTH_LONG).show();}}}catch(Exception e){Toast.makeText(getApplicationContext(), "خطا",Toast.LENGTH_LONG).show();}
            }
            return;
        }
    }

    static String depass(int i){
        String result="";
        double a=i-1000;
        double b=a/75;
        double c=Math.pow(b,0.5);
        result =String.valueOf(c);
        if(c==(int)c){result=String.valueOf((int)c);}
        return result;
    }
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

}
