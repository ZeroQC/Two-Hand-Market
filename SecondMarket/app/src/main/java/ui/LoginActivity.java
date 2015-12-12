package ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.secondmarket.MainActivity;
import com.demo.secondmarket.R;


public class LoginActivity extends Activity {

    public static final String SP_INFOS="SPDATA_Files";
    public static final String Userid="Uerid";
    public static final String Password="Password";
    private static CheckBox cb;
    private static EditText etuser;
    private static EditText etpwd;
    private EditText edname;
    private EditText edpassword;
    private static String user;
    private static String pwd;
    public static SQLiteDatabase db;
    private Button btregister;
    private Button btlogin;

    //添加图片设置的变量
    private Button btn1;
    private final String IMAGE_TYPE = "image/*";
    private final int IMAGE_CODE = 0;
    private Button addPic=null,showPicPath=null;
    private ImageView imgShow=null;
    private TextView imgPath=null;


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);
        //建立或打开数据库
        db= SQLiteDatabase.openOrCreateDatabase(LoginActivity.this.getFilesDir().toString() + "/test.dbs", null);
        createDb();
        etuser = (EditText)findViewById(R.id.editText);
        etpwd = (EditText)findViewById(R.id.editText2);
        cb=(CheckBox)findViewById(R.id.checkBox);
        edname = (EditText) findViewById(R.id.editText);
        edpassword = (EditText) findViewById(R.id.editText2);
        btregister = (Button) findViewById(R.id.register);
        btlogin = (Button) findViewById(R.id.login);

        //添加图片的点击响应事件
        /*btn1=(Button)findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

                getAlbum.setType(IMAGE_TYPE);

                startActivityForResult(getAlbum, IMAGE_CODE);
            }
        });*/
        //设置注册事件监控
        btregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btlogin.setOnClickListener(new LoginListener());

        //从SharePerferences中读取用户的账号和密码
        checkIfRemember();
    }


    protected void onStop(){
        super.onStop();
        if(cb.isChecked()){
            user=etuser.getText().toString().trim();
            pwd=etpwd.getText().toString().trim();
            rememberMe(user,pwd);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }


    //从SharePerferences中读取用户的账号和密码
    public void checkIfRemember(){
        SharedPreferences sp=getSharedPreferences(SP_INFOS,MODE_PRIVATE);
        user=sp.getString(Userid,null);
        pwd=sp.getString(Password,null);
        if(user!=null&&pwd!=null){
            etuser.setText(user);
            etpwd.setText(pwd);
            cb.setChecked(true);
        }
    }
    //将用户的ID和密码存入sharedpreferences
    public void rememberMe(String user,String pwd){
        SharedPreferences sp=getSharedPreferences(SP_INFOS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(Userid,user);
        editor.putString(Password,pwd);
        editor.commit();
    }


    //点击登录按钮的响应时间
    class LoginListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String name = edname.getText().toString();
            String password = edpassword.getText().toString();
            if (name.equals("") || password.equals("")) {
                // 弹出消息框
                new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                        .setMessage("帐号或密码不能空").setPositiveButton("确定", null)
                        .show();
            } else {
                isUserinfo(name, password);
            }
        }


        //判断账号密码是否正确
        public Boolean isUserinfo(String name, String pwd) {
            try{
                String str="select * from tb_user where name=? and password=?";
                Cursor cursor = db.rawQuery(str, new String []{name,pwd});
                if(cursor.getCount()<=0){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                            .setMessage("帐号或密码错误！").setPositiveButton("确定", null)
                            .show();
                    return false;
                }
                else{
                    new AlertDialog.Builder(LoginActivity.this).setTitle("正确")
                            .setMessage("成功登录").setPositiveButton("确定", null)
                            .show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    return true;
                }

            }catch(SQLiteException e){

            }
            return false;
        }

    }
    //建立数据库
    public void createDb() {
        db.execSQL("create table if not exists tb_user( name varchar(30) primary key,password varchar(30))");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


     /*  protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            Log.e("TAG->onresult", "ActivityResult resultCode error");
            return;
        }
        Bitmap bm = null;
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

        ContentResolver resolver = getContentResolver();

        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == IMAGE_CODE) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //显得到bitmap图片
                imgShow.setImageBitmap(bm);


//    这里开始的第二部分，获取图片的路径：

                String[] proj = {MediaStore.Images.Media.DATA};

                //好像是android多媒体数据库的封装接口，具体的看Android文档

                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                //按我个人理解 这个是获得用户选择的图片的索引值

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                //将光标移至开头 ，这个很重要，不小心很容易引起越界

                cursor.moveToFirst();

                //最后根据索引值获取图片路径

                String path = cursor.getString(column_index);
                imgPath.setText(path);
            }catch (IOException e) {

                Log.e("TAG-->Error",e.toString());

            }

        }

    }*/

}

