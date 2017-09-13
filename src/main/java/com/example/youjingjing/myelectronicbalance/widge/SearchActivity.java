package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.PLURecond;
import com.example.youjingjing.myelectronicbalance.view.FlowViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.activity_search_et)
    EditText editText;

    ArrayList<PLURecond> name;

//    @BindView(R.id.activity_search_rv)
//    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FlowViewGroup mFlowViewGroup;
    private String[] mTexts = new String[]{"BatteryView.txt", "为自定义View",
            " 参考attrs.xml", " 定义自定义View属性", " 参考fragment_04.xml",
            " 使用自定义view，并传入属性值", " 两张图片为资源", "一张为view背景（白圈）",
            "一张为一个圆形图片", "用于遮盖XFerMode","形成圆形波浪效果"};

    TextView tv;
    ArrayList<TextView> a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = new ArrayList<>();

        initView();
        a = new ArrayList<>();
    }
    private void initView() {
        mFlowViewGroup = (FlowViewGroup) findViewById(R.id.activity_research_flowview);
    }



    @OnTextChanged(R.id.activity_search_et)
    void OnChange(CharSequence text){
        query(text);
    }


    private void query(CharSequence text) {

        if(a.size()>0){
            mFlowViewGroup.removeContent(a);
            a.clear();
        }
        if(text.length()>0) {
//            mRecyclerView.setVisibility(View.VISIBLE);
            mFlowViewGroup.setVisibility(View.VISIBLE);

            name = (ArrayList<PLURecond>) DataSupport.select("name").where("name like ?", "%" + text.toString() + "%").find(PLURecond.class);
            if(name!=null&&name.size()>0){
//                PLURecond p = new PLURecond();
//                p.setName("清除");
//                name.add(p);
//                RecondAdapter radapter = new RecondAdapter(this, name, R.layout.recycle_item);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//                layoutManager.setOrientation(OrientationHelper.VERTICAL);
//                mRecyclerView.setLayoutManager(layoutManager);
//                mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//                mRecyclerView.setAdapter(radapter);
//                radapter.setListener(new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        PLURecond pluRecond = name.get(position);
//                        String name = pluRecond.getName();
//                        if(name.equals("清除")){
//                            DataSupport.deleteAll(PLURecond.class);
//                            mFlowViewGroup.setVisibility(View.GONE);
//                        }else{
//                            editText.setText(name);
//                        }
//                    }
//                });
//                radapter.notifyDataSetChanged();

                for (int i=0;i<name.size();i++){
                    tv= (TextView) LayoutInflater.from(this).inflate(R.layout.item_flow,mFlowViewGroup,false);
                    tv.setText(name.get(i).getName());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(SearchActivity.this,""+((TextView)v).getText(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SearchActivity.this,ResultActivity.class);
                            intent.putParcelableArrayListExtra("plu_name",name);
                            startActivity(intent);
                        }
                    });
                    a.add(tv);
                }
                    mFlowViewGroup.setContent(a);


            }else{
                mFlowViewGroup.setVisibility(View.GONE);
            }

//            Cursor bySQL = DataSupport.findBySQL("select * from plurecond where name like  '%" + text.toString() + "%' ");
//            // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
//            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, bySQL, new String[]{"name"},
//                    new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//            // 3. 设置适配器
//            mListView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();

        }else{
//            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.activity_search_iv)
    void onClick(){
        String s = editText.getText().toString();
        List<PLU> name = DataSupport.select("name").where("name like ?", "%" + s + "%").find(PLU.class);
        if(name.size()>0&&name!=null){
            for (PLU p: name
                 ) {
                PLURecond r = new PLURecond();
                r.setName(p.getName());
                r.save();
            }
        }
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
