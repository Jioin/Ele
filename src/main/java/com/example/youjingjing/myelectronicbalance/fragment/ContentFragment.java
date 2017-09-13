package com.example.youjingjing.myelectronicbalance.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.youjingjing.myelectronicbalance.R;
import com.example.youjingjing.myelectronicbalance.adapter.MyAdapter;
import com.example.youjingjing.myelectronicbalance.adapter.RecyclerItemClickListener;
import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.example.youjingjing.myelectronicbalance.beans.Store;
import com.example.youjingjing.myelectronicbalance.popup.CommonPopupWindow;
import com.example.youjingjing.myelectronicbalance.touchhelper.OnStartDragListener;
import com.example.youjingjing.myelectronicbalance.touchhelper.SimpleItemTouchHelperCallback;
import com.example.youjingjing.myelectronicbalance.utils.CommonUtil;
import com.gaoneng.library.AutoScrollBackLayout;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class ContentFragment extends Fragment {
//    @BindView(R.id.activity_main_recycleview)
    RecyclerView mRecyclerView;

//    @BindView(R.id.main_scrollback)
    AutoScrollBackLayout autoScrollBackLayout;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    private MyAscyncTask myAscyncTask = null;
    MyAdapter adapter;
    int mPosition;
    public static final int CHOOSE_PHOTO = 2;

//    @BindView(R.id.main_progressbar)
    ProgressBar progressBar;

    private Unbinder bind;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        return view;
    }

    public MyAscyncTask getInstance( Context context,Store s){
        if(myAscyncTask==null){
           myAscyncTask = new MyAscyncTask(context);
           myAscyncTask.execute(s);
        }
        return myAscyncTask;
    }

    public class MyAscyncTask extends AsyncTask<Store,Integer,List<PLU>> implements CommonPopupWindow.ViewInterface,OnStartDragListener {



        private Context context;
        List<PLU> plu;
        private CommonPopupWindow popupWindow;
        public MyAscyncTask(Context context) {
            this.context = context;
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_content, null, false);
            progressBar = view.findViewById(R.id.main_progressbar);
            autoScrollBackLayout = view.findViewById(R.id.main_scrollback);
            mRecyclerView = view.findViewById(R.id.activity_main_recycleview);
        }
        ItemTouchHelper mItemTouchHelper;

        @Override
        protected List<PLU> doInBackground(Store... strings) {
//            for (int i = 0; i < 100; i++) {
//                if(isCancelled()){
//                    break;
//                }
//           // 模拟进度更新
//                publishProgress(i);
//                try {
//                    Thread.sleep(300);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            plu = strings[0].getPlus();
            Log.e("doInBackground: ", "" + plu);

            return plu;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<PLU> plus) {
            super.onPostExecute(plus);
            myAscyncTask = null;
            showProgress(false);
            if (plus!=null) {
//            finish();
//            Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                mRecyclerView.setLayoutManager(layoutManager);
                adapter = new MyAdapter(context,plus,R.layout.recycleview_item,this);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
                autoScrollBackLayout.bindScrollBack();
                mRecyclerView.setAdapter(adapter);


                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
                mItemTouchHelper  = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(mRecyclerView);

//                mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
//                             @Override
//                             public void onItemClick(View view, int position) {
//                                 // 创建File对象，用于存储拍照后的图片
//                                 File outputImage = new File(context.getExternalCacheDir(), "output_image.jpg");
//                                 try {
//                                     if (outputImage.exists()) {
//                                         outputImage.delete();
//                                     }
//                                     outputImage.createNewFile();
//                                 } catch (IOException e) {
//                                     e.printStackTrace();
//                                 }
//
//                                 if (Build.VERSION.SDK_INT < 24) {
//                                     imageUri = Uri.fromFile(outputImage);
//                                 } else {
//                                     imageUri = FileProvider.getUriForFile(context, "com.example.youjingjing.myelectronicbalance.widge.fileprovider", outputImage);
//                                 }
//                                 // 启动相机程序
//                                 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                                 intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                 startActivityForResult(intent, TAKE_PHOTO);
//                             }
//                         }));

                adapter.setListener(new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (popupWindow != null && popupWindow.isShowing()) return;
                        View upView = LayoutInflater.from(context).inflate(R.layout.popup_up, null);
                        //测量View的宽高
                        CommonUtil.measureWidthAndHeight(upView);
                        popupWindow = new CommonPopupWindow.Builder(context)
                                .setView(R.layout.popup_up)
                                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                                .setAnimationStyle(R.style.AnimUp)
                                .setViewOnclickListener(MyAscyncTask.this)
                                .create();
                        popupWindow.showAtLocation(getView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

                        mPosition = position;

                    }
                });
            } else {
//            mPasswordView.setError(getString(R.string.error_incorrect_password));
//            mPasswordView.requestFocus();
//            Toast.makeText(this, "下载失败", Toast.LENGTH_SHORT).show();
            }

        }
        @Override
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            myAscyncTask = null;
//            showProgress(false);
        }

        private void showProgress(boolean b) {
            if (b==true){
                progressBar.setVisibility(View.VISIBLE);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void getChildView(View view, int layoutResId) {
            //获得PopupWindow布局里的View
            switch (layoutResId) {
                case R.layout.popup_up:
                    Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
                    Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
                    Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                    btn_take_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 创建File对象，用于存储拍照后的图片
                            File outputImage = new File(context.getExternalCacheDir(), "output_image"+mPosition+".jpg");
                            try {
                                if (outputImage.exists()) {
                                    outputImage.delete();
                                }
                                outputImage.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (Build.VERSION.SDK_INT < 24) {
                                imageUri = Uri.fromFile(outputImage);
                            } else {
                                imageUri = FileProvider.getUriForFile(context, "com.example.youjingjing.myelectronicbalance.widge.fileprovider", outputImage);
                            }
                            // 启动相机程序
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                            startActivityForResult(intent, TAKE_PHOTO);
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                    btn_select_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                            } else {
                                openAlbum();
                            }
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                        }
                    });
                    view.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (popupWindow != null) {
                                popupWindow.dismiss();
                            }
                            return true;
                        }
                    });
                    break;
            }
        }
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getContext(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        long id = 0;
                        ContentValues value = new ContentValues();
                        value.put("imageuri",imageUri.toString());

                        DataSupport.update(PLU.class,value,adapter.getData().get(mPosition).getId());
                        adapter.notifyDataSetChanged();
//                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    ContentValues value = new ContentValues();
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                        value.put("imageuri",handleImageOnKitKat(data));

                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                        value.put("imageuri",handleImageOnKitKat(data));
                    }
                    DataSupport.update(PLU.class,value,adapter.getData().get(mPosition).getId());
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
//        displayImage(imagePath); // 根据图片路径显示图片
        return imagePath;
    }

    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
