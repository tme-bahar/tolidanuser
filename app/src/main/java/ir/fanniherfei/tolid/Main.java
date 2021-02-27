package ir.fanniherfei.tolid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ir.fanniherfei.tolid.R;
import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main extends Activity {
    static Button ne;
    View img;
    Bitmap bitmap;
    int hight;
    String picturePath;
    boolean moreOpened = false;
    boolean teamOpened = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_page);
        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        String[] pass=G.password.split("&");
        String a=depass(Integer.parseInt(pass[1]));
        int num=((Integer.parseInt(a)-1)*14)+1;
        G.name=shared.getString("people"+ num,"");
        //LOADING PEOPLE
        String[] passs=G.password.split("&");
        String s=depass(Integer.parseInt(passs[1]));
        G.changenum=Integer.parseInt(shared.getString("peoplenumber", "0"));
        G.importPeopleNumber=Integer.parseInt(s.trim());
        G.last=2;
        CardView cvInfo = findViewById(R.id.cardView2);
        Button more = findViewById(R.id.button);
        ViewGroup.LayoutParams cvinfop = cvInfo.getLayoutParams();
        hight = 150;
        more.setOnClickListener(v -> {
            if(!moreOpened){
            new CountDownTimer(2000, 5) {

            public void onTick(long millisUntilFinished) {
                if(hight>400)
                    return;
                cvinfop.height = cvinfop.height+(int) pxFromDp(Main.this,4);
                hight+=4;
                cvInfo.setLayoutParams(cvinfop);
            }
            public void onFinish() {
                more.setText("بستن");
            }
        }.start();
        }else{
                new CountDownTimer(2000, 5) {

                    public void onTick(long millisUntilFinished) {
                        if(hight<150)
                            return;
                        cvinfop.height = cvinfop.height-(int) pxFromDp(Main.this,4);
                        hight-=4;
                        cvInfo.setLayoutParams(cvinfop);
                    }
                    public void onFinish() {
                        more.setText("بیشتر");
                    }
                }.start();
            }
            moreOpened = !moreOpened;
        });
        //LOADING PERSON
        try{
            final TextView sen= findViewById(R.id.textView5);
            final TextView[] et={findViewById(R.id.textView1), findViewById(R.id.textView2), findViewById(R.id.textView2), findViewById(R.id.textView3), findViewById(R.id.textView4), findViewById(R.id.textView6), findViewById(R.id.textView7), findViewById(R.id.textView8), findViewById(R.id.textView9), findViewById(R.id.textView10), findViewById(R.id.textView11), findViewById(R.id.textView12)};
            if(!shared.getString("peoplenumber","0").equals("0")){
                for(int x=0;x<3;x++){
                    num=((G.importPeopleNumber-1)*14)+1+x;
                    et[x].setText(shared.getString("people"+num, ""));
                }
                //name arrange
                if(et[0].getText().toString().length() >15){
                    et[0].setTextSize(14);
                }
                char[] c=shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+3), "").toCharArray();
                String birth="";
                for(int x=0;x<c.length;x++){birth=birth+String.valueOf(c[x]);
                    if(3==x){birth=birth+"/";}
                    if(5==x){birth=birth+"/";}}
                et[3].setText(birth);
                String year = c[2] + String.valueOf(c[3]);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
                Date date = new Date(System.currentTimeMillis());
                String old = String .valueOf(Integer.parseInt(formatter.format(date))-(Integer.parseInt(year)+1921));
                sen.setText(old);
                for(int x=4;x<11;x++){
                    num=((G.importPeopleNumber-1)*14)+2+x;
                    et[x].setText(shared.getString("people"+String.valueOf(num), ""));
                }
                if(shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+12),"0").equals("0")){et[11].setText("مرد");}else{et[11].setText("زن");}
            }
            //photo
            //showing
            img = findViewById(R.id.imageView);
            new LoadImage().execute("http://www.tmebahar.ir/uploads/gallery.php/person_"+String.valueOf(G.importPeopleNumber)+".jpg");
            //sending
            CardView cvb = findViewById(R.id.cardView);
            cvb.setOnClickListener(view -> {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 100);
            });

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
        //constrant
        final ConstraintLayout cons = findViewById(R.id.cons);
        //team list
        //team list
        ArrayList<String> data = new ArrayList<>();
        data.add("team1");
        data.add("team2");
        data.add("team3");
        final RecyclerView teams = findViewById(R.id.teams);
        teams.setLayoutManager(new LinearLayoutManager(this));
        teams.setAdapter(new MyRecyclerViewAdapter(Main.this,data));
        //opening and closing
        final Button teambtn = findViewById(R.id.button11);
        final View teamBottom = findViewById(R.id.button12);
        final CardView topTeamHolder = findViewById(R.id.topTeamHolder);
        teambtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamOpened){
                    teambtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(Main.this,R.drawable.arrow_down), null);
                    teamBottom.setBackgroundColor(Color.parseColor("#757575"));
                    topTeamHolder.setVisibility(View.GONE);
                    teams.setVisibility(View.GONE);
                    teamOpened = false;
                }else{
                    teambtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(Main.this,R.drawable.arrow_up), null);
                    teamBottom.setBackgroundColor(Color.parseColor("#03DAC5"));
                    topTeamHolder.setVisibility(View.VISIBLE);
                    teams.setVisibility(View.VISIBLE);
                    teamOpened = true;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picturePath = cursor.getString(columnIndex);

                        cursor.close();

                        String[] s=at.split(picturePath,'.');
                        try{
                            if(!"jpg".equals(s[s.length-1])){ throw new Exception("فرمات فایل باید jpg باشد");}
                            String lastName=at.getNameAndType(picturePath);
                            String adress=at.rename(picturePath,"person_"+String.valueOf(G.importPeopleNumber));
                            Upload.selectedFilePath=adress;
                            startActivity(new Intent(Main.this,Upload.class));
                            Upload.lastName=lastName;
                            finish();
                        }catch (Exception e) {
                            at.ToastExceptionMassage(e);
                        }
					String subtitle[]=at.split(picturePath,'.');
					String fol[]=at.split(picturePath,'/');
					String folder=fol[0];
					for (int i = 1; i < fol.length-1; i++) {
						folder+="/"+fol[i];
					}
					File selectedFile = new File(picturePath);
			        File newfile = new File(folder+"/"+"project"+"."+subtitle[subtitle.length-1]);
			        selectedFile.renameTo(newfile);
					Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        //generateNoteOnSD(getApplicationContext(), "picture."+subtitle[subtitle.length-1],readFileAsString(picturePath));
                        //Toast.makeText(getApplicationContext(), folder+"project"+subtitle[subtitle.length-1],Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
                    }

                }}
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
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        //استفاده از bitmap برای دریافت اطلاعات از ادرس و نمایش اون
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap image) {
            if(image != null){
                img.setBackground(new BitmapDrawable(getResources(), image));
            }else{
            }
        }
    }
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
