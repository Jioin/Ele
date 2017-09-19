package com.example.youjingjing.myelectronicbalance.widge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.Spinner.SpinerPopWindow;
import com.example.youjingjing.myelectronicbalance.adapter.AbstractSpinerAdapter;
import com.example.youjingjing.myelectronicbalance.adapter.CustemSpinerAdapter;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.fragment.ContentFragment;
import com.example.youjingjing.myelectronicbalance.presenter.MainPresenter;
import com.example.youjingjing.myelectronicbalance.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainView,MainPresenter> implements MainView {

    TextView textView_user;

    List<String> datas;

    private View mRootView;
    private TextView mTView;

    private AbstractSpinerAdapter mAdapter;

    ArrayList<Store> mStores;

    @BindView(R.id.spinner_image)
    ImageView imageView_spinner;

    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        autoLogin();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            mStores = intent.getParcelableArrayListExtra("stores");
//            textView_user.setText(name);
        }

//    // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.storeArray);
        datas = new ArrayList<>();
        for (int i = 0; i < mItems.length; i++) {
//            CustemObject object = new CustemObject();
//            object.data = names[i];
            datas.add(mItems[i]);
        }

//自定义spinner
        if(mStores!=null) {
            setupViews();
        }

    }

    @OnClick(R.id.main_search)
    public void Search() {
        Intent intent = new Intent(this,SearchActivity.class);
        if(mStores!=null) {
            if(store!=null){
                intent.putExtra("store", store);
            }
        }
        startActivity(intent);
    }


    private void setupViews() {
        mRootView = findViewById(R.id.rootView);
        mTView = (TextView) findViewById(R.id.tv_value);

        mAdapter = new CustemSpinerAdapter(this);
        mAdapter.refreshData(mStores, 0);

        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);

        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                setHero(pos);
//                if (myAscyncTask == null) {
                store = mStores.get(pos);
                List<PLU> plus = store.getPlus();

                FragmentTransaction replace = getSupportFragmentManager().beginTransaction().replace(R.id.content, new ContentFragment(MainActivity.this, plus));
                replace.commit();
//                    myAscyncTask = new MyAscyncTask(MainActivity.this);
//                    myAscyncTask.execute(mStores.get(pos));
//                }
//                Toast.makeText(MainActivity.this,"自定义点击了:"+mStores.get(pos),Toast.LENGTH_SHORT).show();
            }
        });
        mSpinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTextImage(R.drawable.unfold);
            }
        });
    }

    private void setHero(int pos) {
        if (pos >= 0 && pos <= mStores.size()) {
            String value = mStores.get(pos).getName();
            mTView.setText(value.toString());
        }
    }

    /**
     * 给TextView右边设置图片
     *
     * @param resId
     */
    private void setTextImage(int resId) {
//      Drawable drawable = getResources().getDrawable(resId);
//      drawable.setBounds(0, 0, 20,20);// 必须设置图片大小，否则不显示
//      mTView.setCompoundDrawables(null, null, drawable, null);
        imageView_spinner.setImageResource(resId);
    }

    @OnClick(R.id.rootView)
    public void onclick() {
        showSpinWindow();
    }

    //显示list view
    private SpinerPopWindow mSpinerPopWindow;

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(mRootView.getWidth());
        mSpinerPopWindow.showAsDropDown(mRootView);
        setTextImage(R.drawable.packup);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showUserInfo() {
        String name = readData("name");
//      textView_user.setText(name);
    }

    @Override
    public void autoLogin() {
        if (readBoolean("AUTO_ISCHECK")) {
            showUserInfo();
        }
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}