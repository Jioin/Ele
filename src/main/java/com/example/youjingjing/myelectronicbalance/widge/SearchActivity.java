package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLURecond;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.view.FlowViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.activity_search_et)
    EditText editText;

    //搜索记录
    ArrayList<PLURecond> name;

//    @BindView(R.id.activity_search_rv)
//    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.activity_research_flowview)
    FlowViewGroup mFlowViewGroup;

    @BindView(R.id.history)
    RelativeLayout history_layout;

    @BindView(R.id.activity_search_listview)
    ListView mListView;

    TextView tv;
    ArrayList<TextView> tvs;
    ArrayList<Integer> plu_ids;
    Store mStore;
    Cursor bySQL;
    EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        showHistory();
    }

    private void initView() {
        name = new ArrayList<PLURecond>();
        tvs = new ArrayList<TextView>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mStore = intent.getParcelableExtra("store");
        }
        plu_ids = new ArrayList<>();
        et_search = (EditText) findViewById(R.id.activity_search_et);
        et_search.setFocusable(true);
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @OnTextChanged(R.id.activity_search_et)
    void OnChange(CharSequence text){
        query(text);
    }


    private void query(CharSequence text) {

        if(text.length()>0) {
            history_layout.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            Cursor plu;
            if(mStore!=null) {
                plu = DataSupport.findBySQL("select plu_id as _id from plu_store where store_id = ?",mStore.getId()+"");

            if (plu != null && plu.moveToFirst()) {
                plu_ids.clear();
                do {
                    int plu_id = plu.getInt(plu.getColumnIndex("_id"));//_id
                    plu_ids.add(plu_id);
                } while (plu.moveToNext());
            }
                if(plu_ids.size()>0){

                    bySQL = DataSupport.findBySQL("select name, id as _id from plu where name like '%" + text.toString() + "%'" +
                            " and _id in (select plu_id as _id from plu_store where store_id = "+mStore.getId()+")");

                }
            }else{
                bySQL = DataSupport.findBySQL("select name, id as _id from plu where name like '%" + text.toString() + "%'");
//                Cursor  p;
//             p = DataSupport.findBySQL("select plu_id as _id from plu_store where store_id = 1");
            }


            // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, bySQL, new String[]{"name"},
                    new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            // 3. 设置适配器
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PLURecond r = new PLURecond();
                    r.setName(((TextView)view).getText().toString());
                    r.save();
                    Intent intent = new Intent(SearchActivity.this,ResultActivity.class);
                    intent.putExtra("plu_name",((TextView)view).getText().toString());
                    startActivity(intent);
                }
            });
            adapter.notifyDataSetChanged();

        }else{
            showHistory();
        }
    }

    private void showHistory() {
        mListView.setVisibility(View.GONE);
        history_layout.setVisibility(View.VISIBLE);
        //清空历史记录
        if(tvs.size()>0){
            //清除子视图
            mFlowViewGroup.removeContent(tvs);
            //清除数组
            tvs.clear();
        }
        //查询、添加历史记录
        name = (ArrayList<PLURecond>) DataSupport.select("name").find(PLURecond.class);
        if(name!=null&&name.size()>0){
            for (int i=0;i<name.size();i++){
                tv= (TextView) LayoutInflater.from(this).inflate(R.layout.item_flow,mFlowViewGroup,false);
                tv.setText(name.get(i).getName());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(SearchActivity.this,ResultActivity.class);
                        intent.putExtra("plu_name",((TextView)v).getText().toString());
                        startActivity(intent);
                    }
                });
                tvs.add(tv);
            }
            mFlowViewGroup.setContent(tvs);
        }else{
            //清除视图
            history_layout.setVisibility(View.GONE);
        }


//        RecondAdapter radapter = new RecondAdapter(this, name, R.layout.recycle_item);
//        radapter.setListener(new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                PLURecond pluRecond = name.get(position);
//                String name = pluRecond.getName();
//                if(name.equals("清除")){
//
//                    mFlowViewGroup.setVisibility(View.GONE);
//                }else{
//                    editText.setText(name);
//                }
//            }
//        });
//        radapter.notifyDataSetChanged();
    }

    @OnClick(R.id.activity_search_clear)
    public void clear(){
        DataSupport.deleteAll(PLURecond.class);
        history_layout.setVisibility(View.GONE);
    }
    @OnClick(R.id.activity_search_iv)
    void onClick(){
        String s = editText.getText().toString();

        query(s);
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivityForResult(intent, 2 );
//        setResult(1,intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode== 2&&resultCode == Activity.RESULT_OK ){
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
