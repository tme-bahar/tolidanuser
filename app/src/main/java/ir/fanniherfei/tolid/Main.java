package ir.fanniherfei.tolid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Main extends Activity {
    View img;
    Bitmap bitmap;
    int height;
    String picturePath;
    boolean moreOpened = false;
    boolean teamOpened = false;
    boolean billOpened = false;
    public static Person user;
    public static List<Team> teams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_page);
        final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = shared.edit();
        Log.i("Name",user.name);
        String[] pass=G.password.split("&");
        String a= dePass(Integer.parseInt(pass[1]));
        int num=((Integer.parseInt(a)-1)*14)+1;
        G.name=shared.getString("people"+ num,"");

        //LOADING PEOPLE
        String[] passs=G.password.split("&");
        String s= dePass(Integer.parseInt(passs[1]));
        G.changenum=Integer.parseInt(shared.getString("peoplenumber", "0"));
        G.importPeopleNumber=Integer.parseInt(s.trim());
        G.last=2;
        CardView cvInfo = findViewById(R.id.cardView2);
        Button more = findViewById(R.id.button);
        ViewGroup.LayoutParams cvinfop = cvInfo.getLayoutParams();
        height = 150;
        more.setOnClickListener(v -> {
            if(!moreOpened){
                more.setText("بستن");
            new CountDownTimer(2000, 5) {

            public void onTick(long millisUntilFinished) {
                if(height >400)
                    return;
                cvinfop.height = cvinfop.height+(int) pxFromDp(Main.this,4);
                height +=4;
                cvInfo.setLayoutParams(cvinfop);
            }
            public void onFinish() {

            }
        }.start();
        }else{
                more.setText("بیشتر");
                new CountDownTimer(2000, 5) {

                    public void onTick(long millisUntilFinished) {
                        if(height <150)
                            return;
                        cvinfop.height = cvinfop.height-(int) pxFromDp(Main.this,4);
                        height -=4;
                        cvInfo.setLayoutParams(cvinfop);
                    }
                    public void onFinish() {}
                }.start();
            }
            moreOpened = !moreOpened;
        });

        //LOADING PERSON
        try{
            final TextView[] et={findViewById(R.id.textView1), findViewById(R.id.textView2), findViewById(R.id.textView3), findViewById(R.id.textView4),findViewById(R.id.textView5), findViewById(R.id.textView6), findViewById(R.id.textView7), findViewById(R.id.textView8), findViewById(R.id.textView9), findViewById(R.id.textView10), findViewById(R.id.textView11), findViewById(R.id.textView12)};
            et[0].setText(user.name);
            et[1].setText(user.nationalCode);
            et[2].setText(user.birthYear()+"/"+user.birthMounth()+"/"+user.birthDay());
            et[3].setText(user.father);
            et[4].setText(user.age);
            et[5].setText(user.phoneNumber);
            et[6].setText(user.mobileNumber);
            et[7].setText(user.address);
            et[8].setText(user.eduLevel);
            et[9].setText(user.eduPlace);
            et[10].setText(user.eduMajor);
            et[11].setText(user.sex.toString());
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        //constraint
        final ConstraintLayout cons = findViewById(R.id.cons);
        G.team=true;


        //photo
        //showing
        img = findViewById(R.id.imageView);
        new LoadImage().execute("http://www.tmebahar.ir/uploads/gallery.php/person_"+ G.importPeopleNumber +".jpg");

        //sending
        CardView cvb = findViewById(R.id.cardView);
        cvb.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 100);
        });


        //team
        final RecyclerView teams = findViewById(R.id.teams);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(Main.this,Main.teams);
        teams.setAdapter(adapter);

        //team list
        teams.setLayoutManager(new LinearLayoutManager(this));
        teams.setAdapter(adapter);

        //opening and closing
        final Button teambtn = findViewById(R.id.button11);
        final View teamBottom = findViewById(R.id.button12);
        final CardView topTeamHolder = findViewById(R.id.topTeamHolder);
        teambtn.setOnClickListener(v -> {
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
        });
        teambtn.performClick();
        teambtn.performClick();
        /*
        //bill
        //team main
        final RecyclerView bills = findViewById(R.id.teams);
        //opening and closing
        final Button billbtn = findViewById(R.id.button13);
        final View billBottom = findViewById(R.id.button112);
        final CardView topBillHolder = findViewById(R.id.topBillHolder);
        billbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(billOpened){
                    billbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(Main.this,R.drawable.arrow_down), null);
                    billBottom.setBackgroundColor(Color.parseColor("#757575"));
                    topBillHolder.setVisibility(View.GONE);
                    bills.setVisibility(View.GONE);
                    billOpened = false;
                }else{
                    billbtn.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(Main.this,R.drawable.arrow_up), null);
                    billBottom.setBackgroundColor(Color.parseColor("#03DAC5"));
                    topBillHolder.setVisibility(View.VISIBLE);
                    bills.setVisibility(View.VISIBLE);
                    billOpened = true;
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);

                    cursor.close();

                    String[] s = at.split(picturePath, '.');
                    try {
                        if (!"jpg".equals(s[s.length - 1])) {
                            throw new Exception("فرمت فایل باید jpg باشد");
                        }
                        String lastName = at.getNameAndType(picturePath);
                        Upload.selectedFilePath = at.rename(picturePath, "person_" + G.importPeopleNumber);
                        startActivity(new Intent(Main.this, Upload.class));
                        Upload.lastName = lastName;
                        finish();
                    } catch (Exception e) {
                        at.ToastExceptionMassage(e);
                    }
                    String[] subtitle = at.split(picturePath, '.');
                    String[] fol = at.split(picturePath, '/');
                    StringBuilder folder = new StringBuilder(fol[0]);
                    for (int i = 1; i < fol.length - 1; i++) {
                        folder.append("/").append(fol[i]);
                    }
                    File selectedFile = new File(picturePath);
                    File newFile = new File(folder + "/" + "project" + "." + subtitle[subtitle.length - 1]);
                    selectedFile.renameTo(newFile);
                    //Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    //generateNoteOnSD(getApplicationContext(), "picture."+subtitle[subtitle.length-1],readFileAsString(picturePath));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    static String dePass(int i){
        String result;
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
            }
        }
    }
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
