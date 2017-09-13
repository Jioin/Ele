package com.example.youjingjing.myelectronicbalance;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.beans.User;
import com.example.youjingjing.myelectronicbalance.javaandroid.DBXException;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTConnection;
import com.example.youjingjing.myelectronicbalance.javaandroid.TDataSet;
import com.example.youjingjing.myelectronicbalance.javaandroid.TJSONObject;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by youjingjing on 2017/8/2.
 */

public class MyAscyncTaskDemo extends AsyncTask<String,Integer,Boolean> {
    SparseArray<String> array;
    private MyAscyncTaskDemo myAscyntask;

    public MyAscyncTaskDemo() {
    }

    //    ProgressDialog  progressDialog = new ProgressDialog(context);
    @Override
    protected Boolean doInBackground(String... strings) {
//        getServiceInfo(strings[0],strings[1],strings[2],strings[3]);
        User user1 =new User();
        user1.setName(strings[2]);
        user1.setPassword(strings[3]);

        Store store1 = new Store();
        Store store2 = new Store();
        store1.setName("DO");
        store2.setName("XI");
        //连接服务器，创建本地数据库
//        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String date = sDateFormat.format(d);
        try {
            Date parse = sDateFormat.parse(date);
            Log.e( "doInBackground: ", ""+parse);
            store1.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Store> stores1 = new ArrayList<>();

        PLU plu = new PLU();
        PLU plu1 = new PLU();
        plu.setName("plu1");
        plu.setPrice("12");
        plu1.setName("plu2");
        plu1.setPrice("16");
        List<PLU> plus = new ArrayList<>();

        plus.add(plu);
        plus.add(plu1);

        store1.setPlus(plus);
        stores1.add(store1);
        stores1.add(store2);

        DataSupport.saveAll(plus);
        DataSupport.saveAll(stores1);
        user1.setAuthority(stores1);
        user1.save();
        Log.e("login: ","id-----"+ user1.getId()+user1.getAuthority().size()+stores1.size() );

        publishProgress();
        return true;
    }

    @Override
    protected void onPreExecute() {
        showProgress(true);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progressDialog.setMessage("当前下载进度：" + values[0] + "%");

    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        myAscyntask = null;
        showProgress(false);
        if (success) {
//            finish();
//            Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show();
        } else {
//            mPasswordView.setError(getString(R.string.error_incorrect_password));
//            mPasswordView.requestFocus();
//            Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        myAscyntask = null;
        showProgress(false);
    }

    private void showProgress(boolean b) {
        if (b=true){
//            progressDialog.show();
        }else{
//            progressDialog.dismiss();
        }
    }

    private DSRESTConnection getConnection(String host,int port,String username,String password) {

        DSRESTConnection conn = new DSRESTConnection();

        conn.setHost(host);

        conn.setPort(port);

        conn.setUserName(username);

        conn.setPassword(password);

        return conn;
    }
    public void getServiceInfo(String host,String port,String name,String password){

//                DSRESTConnection conn = getConnection("10.0.11.192",8080,name,password);
                DSRESTConnection conn = getConnection(host,Integer.parseInt(port),name,password);

                GeneratedProxy.TPRT_DS_Interface ds = new GeneratedProxy.TPRT_DS_Interface(conn);

                try {
                    TDataSet tDataSet = ds.GetUserAuthority(name);

                    if(tDataSet.next()) {
                        TJSONObject tjsonObject = tDataSet.asJSONObject();

//                        Log.e("run: ", "" +  tjsonObject.toString());

                         Object userName = tjsonObject.get("UserName").value.getInternalObject();
                        String authority = tjsonObject.get("UserAuthority").value.toString();

                        authority.replaceAll("\\]|\\[","");

//                        JSONTokener jsonParser = new JSONTokener(JsonUtils.serialize(tjsonObject));
//
//                        JSONObject person = (JSONObject) jsonParser.nextValue();
//
//                        JSONArray elements = person.getJSONArray("Elements");
//
//                        array = new SparseArray<>();
//                        for (int i = 0; i < tDataSet.getColumns().size(); i++) {
//                            JSONObject value = elements.getJSONObject(i);
//                            JSONObject value1 = value.getJSONObject("value");
//                            JSONArray elements1 = value1.getJSONArray("Elements");
//                            JSONObject jsonObject = elements1.getJSONObject(0);
//                            String n = jsonObject.get("value").toString();
//                            Log.e( "run: ", "门店号：" +n);
//                            array.put(i,n);
//                        }
                        //创建数据库。添加数据
                        SQLiteDatabase db = Connector.getDatabase();
                        //获取用户信息
                        User user = new User();
                        user.setName(array.get(0));
//                        user.setPassword(array.get());

                        Store store = new Store();
//                        String[] s = new String[]{"1","2","3"};
                        String[] s = array.get(1).split(",");;

//                        List<Store> plus = new ArrayList<>();
//                        user.setAuthority(plus);

                        List<String> list = new ArrayList<>();
                        for (String a: s
                                ) {
                            list.add(a);
                            store.setName(a);
                        }

                        Log.e("login:", " "+list );


                        if(user.isSaved()) {
                            user.updateAll();
                        }else{
                            user.save();
                        }

                        if(store.isSaved()) {
                            store.updateAll();
                        }else{
                            store.save();
                        }



                        //连接服务器，创建本地数据库
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        ds.GetPLU(store.getId(),sDateFormat.parse(store.getTime()));


//                        user.updateAll("password = ? and name = ?", "2","12");

//                        User user1 = new User();
//                        user.setName("");
//                        user.setPassword("");
//                        ContentValues values = new ContentValues();
//                        values.put("password", "12");
////                      DataSupport.updateAll(User.class,values);
//                        DataSupport.updateAll(User.class, values, "password = ? and name = ?", "2","12");

//        DataSupport.delete(User.class,2);
//                        if (user.isSaved()) {
//                            user.delete();
//                        }

//                    Dataset.Builder builder = new Dataset.Builder();
//                    Dataset dataset = builder.build();

                    }else{
                        Log.d("1", "没有数据 " );
                    }
                } catch (DBXException e) {
                    Log.e("1", "连接失败 " );
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
    }

}
