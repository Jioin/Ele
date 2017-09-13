package com.example.youjingjing.myelectronicbalance.model;

import android.content.Context;
import android.util.Log;

import com.example.youjingjing.myelectronicbalance.GeneratedProxy;
import com.example.youjingjing.myelectronicbalance.MyAscyncTaskDemo;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.beans.User;
import com.example.youjingjing.myelectronicbalance.javaandroid.DBXException;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTConnection;
import com.example.youjingjing.myelectronicbalance.javaandroid.TDataSet;
import com.example.youjingjing.myelectronicbalance.javaandroid.TJSONObject;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by youjingjing on 2017/7/13.
 */

public class LoginModelIml implements LoginModel{
    private Date mDate;

    String host;
    int port;
    private MyAscyncTaskDemo myAscyncTaskDemo;
    public  void login(Context context, String name, String password,String host,int port,OnLoginResultListener netWorkCallBack) {
         this.host = host;
         this.port = port;
//        myAscyncTaskDemo = new MyAscyncTaskDemo();
//        myAscyncTaskDemo.execute(host,port+"",name,password);


        //查询数据。进行数据匹配。获取数据
        List<User> users = DataSupport.select("name", "password").where("name = ? and password = ?", name,password).find(User.class);
//        List<User> u = DataSupport.where("name = ? ",  name).find(User.class);


        if(users.size()>0){
            User user = users.get(0);
//            String[] stores = array.get(1).split(",");
            //获取门店
            ArrayList<Store> authority = (ArrayList<Store>) user.getAuthority();
            Log.e( "login  ", "" +user.getName()+user.getAuthority().size());

//            DataSupport.select("name").where("name = ?",user1.getStores()[0]).find(Store.class);//null

//            List<PLU> plus1 = DataSupport.where("id = ?", store.getId() + "").find(PLU.class);

//            Cursor cursor = DataSupport.findBySQL("select * from plu where id = ?","select plu_id from store_plu");
////            long ids[] = new long[10];
////            DataSupport.findAll(PLU.class,ids);
//            if(cursor!=null&&cursor.moveToFirst()){
//                do {
//                    int id = cursor.getInt(cursor.getColumnIndex("id"));
//                    String plu_name = cursor.getString(cursor.getColumnIndex("name"));
//                    String plu_price = cursor.getString(cursor.getColumnIndex("price"));
//                    plu.setName(plu_name);
//                    plu.setPrice(plu_price);
//                    plus.add(plu);
//                } while (cursor.moveToNext());
//            }
//            DataSupport.saveAll(plus);
            netWorkCallBack.onSuccess(authority);
        }else{
            netWorkCallBack.onFailure("用户不存在");
        }
    }
    private DSRESTConnection getConnection(String host, int port, String username, String password) {

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

                String userName = tjsonObject.get("UserName").value.toString();
                String authority = tjsonObject.get("UserAuthority").value.toString();

                String store = authority.replaceAll("\\]|\\[", "");
                String username = userName.replaceAll("\\]|\\[", "");

                ds.GetPLU(Integer.parseInt(store),new Date());

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

                List<String> list = new ArrayList<>();


                Log.e("login:", " "+list );


                //连接服务器，创建本地数据库
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy.MM.dd-hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());



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
        }
    }

}
