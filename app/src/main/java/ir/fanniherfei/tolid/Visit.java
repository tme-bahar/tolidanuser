package ir.fanniherfei.tolid;

import ir.fanniherfei.tolid.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Visit extends Activity {
    static String sentext="";
    String picturePath;
    private static final int SELECT_PHOTO = 100;
    ImageView img;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_visit);
        try{
            final SharedPreferences shared = getSharedPreferences("Prefs", MODE_PRIVATE);
            final SharedPreferences.Editor editor = shared.edit();
            TextView tv=(TextView)findViewById(R.id.textView17);tv.setText(String.valueOf(G.importPeopleNumber));
            final TextView sen=(TextView)findViewById(R.id.textView7);
            Button b=(Button)findViewById(R.id.button2);
            //Toast.makeText(getApplicationContext(), String.valueOf(G.changenum),Toast.LENGTH_LONG).show();
            final RadioButton[] rb={(RadioButton)findViewById(R.id.radio0),(RadioButton)findViewById(R.id.radio1)};
            final TextView[] et={(TextView)findViewById(R.id.editText1),(TextView)findViewById(R.id.editText2),(TextView)findViewById(R.id.editText3),(TextView)findViewById(R.id.editText4),(TextView)findViewById(R.id.editText5),(TextView)findViewById(R.id.editText6),(TextView)findViewById(R.id.editText7),(TextView)findViewById(R.id.editText8),(TextView)findViewById(R.id.editText9),(TextView)findViewById(R.id.editText10),(TextView)findViewById(R.id.editText11),(TextView)findViewById(R.id.editText12)};
            if(!shared.getString("peoplenumber","0").equals("0")){
                for(int x=0;x<3;x++){
                    int num=((G.importPeopleNumber-1)*14)+1+x;
                    et[x].setText(shared.getString("people"+String.valueOf(num), ""));
                }
                char[] c=shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+3), "").toCharArray();
                String birth="";
                for(int x=0;x<c.length;x++){birth=birth+String.valueOf(c[x]);
                    if(3==x){birth=birth+"/";}
                    if(5==x){birth=birth+"/";}}
                et[3].setText(birth);
                sen.setText(shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+4), ""));
                for(int x=4;x<11;x++){
                    int num=((G.importPeopleNumber-1)*14)+2+x;
                    et[x].setText(shared.getString("people"+String.valueOf(num), ""));
                }
                if(shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+12),"0").equals("0")){rb[1].setVisibility(View.GONE);}else{rb[0].setVisibility(View.GONE);}
                et[11].setText(shared.getString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+14), ""));
            }
            if(shared.getString("peoplenumber","0").equals(String.valueOf((G.importPeopleNumber-1)))){b.setText("ثبت");}else{b.setText("ویرایش");}
            b.setOnClickListener(new
                                         OnClickListener() {

                                             @Override
                                             public void onClick(View arg0) {
                                                 // TODO Auto-generated method stu
                                                 try{
                                                     try{
                                                         for(int x=0;x<4;x++){
                                                             int num=((G.importPeopleNumber-1)*14)+1+x;
                                                             editor.putString("people"+String.valueOf(num), et[x].getText().toString());
                                                         }
                                                         editor.putString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+4), sen.getText().toString());
                                                         for(int x=4;x<11;x++){
                                                             int num=((G.importPeopleNumber-1)*14)+2+x;
                                                             editor.putString("people"+String.valueOf(num), et[x].getText().toString());
                                                         }
                                                         if(rb[0].isChecked()){editor.putString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+12), "0");}else{editor.putString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+1+12), "1");}
                                                         editor.putString("people"+String.valueOf(((G.importPeopleNumber-1)*14)+14), et[11].getText().toString());
                                                     }catch (Exception e) {
                                                         // TODO: handle exception
                                                         Toast.makeText(getApplicationContext(), "E"+e.toString(), Toast.LENGTH_LONG).show();
                                                     }

                                                     //try{PeopleManager.b.performClick();}catch (Exception e) {}
                                                      try{
                                                         editor.commit();
                                                         G.last=4;
                                                         G.send=true;
                                                         startActivity(new Intent(Visit.this,MainActivity.class));
                                                         finish();}catch(Exception e){Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();}
                                                 }catch (Exception e) {
                                                     at.ToastExceptionMassage(e);
                                                 }

                                             }
                                         });
            Button age=(Button)findViewById(R.id.button1);
            age.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String currentDateandTime = sdf.format(new Date());
                    sen.setText(String.valueOf(CalculateTimeDiffrence(et[3].getText().toString(), currentDateandTime)-622));
                }
            });
            //photo
            //showing
            img = (ImageView)findViewById(R.id.photo);
            new  LoadImage().execute("http://www.tmebahar.ir/uploads/gallery.php/person_"+String.valueOf(G.importPeopleNumber)+".jpg");
            //sending
            Button btpic = (Button) findViewById(R.id.button4);
            btpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);

                }
            });

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
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
                            startActivity(new Intent(Visit.this,Upload.class));
                            Upload.lastName=lastName;
                            finish();
                        }catch (Exception e) {
                            at.ToastExceptionMassage(e);
                        }
					/*String subtitle[]=at.split(picturePath,'.');
					String fol[]=at.split(picturePath,'/');
					String folder=fol[0];
					for (int i = 1; i < fol.length-1; i++) {
						folder+="/"+fol[i];
					}
					File selectedFile = new File(picturePath);
			        File newfile = new File(folder+"/"+"project"+"."+subtitle[subtitle.length-1]);
			        selectedFile.renameTo(newfile);
					Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);*/
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
    public static int CalculateTimeDiffrence(String dateStart, String dateStop) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            // in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            return (int)diffDays/365;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        //ایجاد یک دیالوگ برای بارگذاری تصویر
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(NewPerson.this);
            //pDialog.setMessage("bargozar tasvir....");
            //pDialog.show();
        }
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
                img.setImageBitmap(image);
                //pDialog.dismiss();
            }else{
                //pDialog.dismiss();
                //ایجاد یک توست جهت نمایش دانلود نشدن تصویر
                //Toast.makeText(NewPerson.this, "تصویر مد نظر یافت نشد و یا دسترسی به اینترنت  موجود نیست", Toast.LENGTH_SHORT).show();
                //img.setBackgroundResource(R.drawable.download);
            }
        }
    }
}
