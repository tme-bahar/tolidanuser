package ir.fanniherfei.tolid;

import ir.fanniherfei.tolid.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    Button b;
    String nationalCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        //introduce
        at.c=getApplicationContext();
        pro = (ProgressBar)findViewById(R.id.progressBar);
        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        b=(Button) findViewById(R.id.button10);
        final FloatingActionButton p=(FloatingActionButton)findViewById(R.id.button2);
        final EditText[] et={(EditText)findViewById(R.id.editText1),(EditText)findViewById(R.id.editText2)};
        final SharedPreferences.Editor editor = shared.edit();
        final CardView maincv = (CardView)findViewById(R.id.card);
        pro.setVisibility(View.GONE);
        et[1].setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE||(actionId == EditorInfo.IME_ACTION_NEXT)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                b.performClick();
                handled = true;
            }
            return handled;
        });
        et[0].setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if ((actionId == EditorInfo.IME_ACTION_DONE)||(actionId == EditorInfo.IME_ACTION_NEXT)) {
                if(et[0].getVisibility() == View.GONE){
                    handled = true;
                    return handled;}
                if(et[0].getText().toString().length() != 10){
                    at.Toast("شماره ملی باید 10 رقم باشد");
                    handled = true;
                    return handled;
                }
                et[1].requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et[1], InputMethodManager.SHOW_IMPLICIT);
                handled = true;
            }
            return handled;
        });
        editor.putBoolean("connected", false);
        editor.apply();
        OnClickListener o= arg0 -> {
            // TODO Auto-generated method stub
            if(!"ورود".equals(b.getText().toString()))
                return;
            if(et[0].getText().toString().length() != 10){
                at.Toast("شماره ملی باید 10 رقم باشد");
                return;
            }
            if(et[1].getText().toString().isEmpty()){
                at.Toast("رمز عبور را پر کنید");
                return;
            }
            nationalCode = et[0].getText().toString();
            G.url2=et[0].getText().toString()+"&"+et[1].getText().toString();
            G.password=G.url2;
            G.url2="a";
            G.recieve=true;
            G.last=1;
            new NetCheck().execute();
        };
        b.setOnClickListener(o);
        p.setOnClickListener(arg0 -> {

            G.changenum=Integer.parseInt(shared.getString("peoplenumber", "0")+1);
            G.importPeopleNumber=Integer.parseInt(shared.getString("peoplenumber", "0"))+1;

            startActivity(new Intent(First.this,NewPerson.class));
        });
    }

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        CountDownTimer cdt;
        /**
         * Gets current device state and checks for working internet connection by trying Google.
         **/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pro.setVisibility(View.VISIBLE);
            b.setText("- - - در حال اجرا - - -");
            b.setBackgroundResource(R.color.c4);
            cdt = new CountDownTimer(500,500) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if(b.getText().toString().equals("ورود"))
                        return;
                    if(b.getText().toString().contains("- - -")){
                        b.setText("در حال اجرا");
                        cdt.start();
                    }else {
                        b.setText("- "+b.getText().toString() + " -");
                        cdt.start();
                    }

                }
            };
            cdt.start();
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
                b.setText("ورود");
                b.setBackgroundResource(R.drawable.but);
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
            final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
            final SharedPreferences.Editor editor = shared.edit();
            DataOprate dp = new DataOprate(page_output);
            int newss=0;
            int nnews=0;
            G.connect=true;
            //G.result=page_output.trim();
            //G.resultText=MainActivity.encode(page_output.trim());
            //if(G.resultText.toString().equals("error")){
            if(dp.hasError){
                pro.setVisibility(View.GONE);
                b.setText("ورود");
                //Toast.makeText(getApplicationContext(), "خطا! \n رمز عبور و یا نام کاربری اشتباه است"+G.resultText, Toast.LENGTH_LONG).show();}else{
                Toast.makeText(getApplicationContext(), "خطا! \n رمز عبور و یا نام کاربری اشتباه است"+dp.getText(), Toast.LENGTH_LONG).show();
                return;
            }
            Main.user = dp.getPerson(nationalCode);
            Main.teams = dp.getTeams(Main.user);
            Main.bill = dp.getBill(Main.user.index);
            Log.i("index",String.valueOf(Main.user.index));
            try{

                for(int x=0;x<Integer.parseInt(shared.getString("peoplenumber", "0"));x++){
                    if(shared.getString("people"+String.valueOf((x*14)+2),"").length()!=0){newss++;}
                }
                //String[] tables=G.resultText.split("©");
                String[] tables=dp.getText().split("©");
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
                    pro.setVisibility(View.GONE);
                    b.setText("ورود");
                    b.setBackgroundResource(R.drawable.but);
                    Toast.makeText(getApplicationContext(),"تیم ها و محصولات دریافت نشدند!",Toast.LENGTH_LONG).show();
                    return;
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
            }catch(Exception e){pro.setVisibility(View.GONE);
                b.setBackgroundResource(R.drawable.but);
                b.setText("ورود");
                Toast.makeText(getApplicationContext(), "نمی توان اطلاعات را خواند!!", Toast.LENGTH_LONG).show();}
                G.recieve=false;
           if(G.connect){
                try{
                    G.connect=false;

                    String[] pass=G.password.split("&");
                    String a=depass(Integer.parseInt(pass[1]));
                    int num=((Integer.parseInt(a)-1)*14)+3;
                    //Toast.makeText(getApplicationContext(),String.valueOf(pass[1].trim())+shared.getString("people"+String.valueOf(3), ",").trim(), Toast.LENGTH_LONG).show();

                    if(shared.getString("people"+String.valueOf(num), "").trim().equals(pass[0].trim())){
                        G.name=shared.getString("people"+String.valueOf(num-1),"");
                        Toast.makeText(getApplicationContext(), "اتصال با موفقیت برقرار شد.", Toast.LENGTH_LONG).show();
                        editor.putBoolean("connected",true);editor.commit();
                        startActivity(new Intent(First.this,Main.class));finish();}else{
                        pro.setVisibility(View.GONE);
                        b.setBackgroundResource(R.drawable.but);
                        b.setText("ورود");
                        Toast.makeText(getApplicationContext(),"نام کاربری یا رمز عبور اشتباه است",Toast.LENGTH_LONG).show();}

                }catch(Exception e){
                    pro.setVisibility(View.GONE);
                    b.setBackgroundResource(R.drawable.but);
                    b.setText("ورود");
                    Toast.makeText(getApplicationContext(), "خطا",Toast.LENGTH_LONG).show();}
            }
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
