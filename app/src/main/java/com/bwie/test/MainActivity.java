package com.bwie.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.bean.MyBean;
import com.bwie.test.okhttputils.OkHttp;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> al;
    private TextView tv;
    private ArrayList<MyBean.MyData> data_al=new ArrayList<MyBean.MyData>();
    private String path="http://m.yunifang.com/yunifang/mobile/goods/getall?random=39986&encode=2092d7eb33e8ea0a7a2113f2d9886c90&category_id=17";
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.HORIZONTAL));

        //设置GridView的适配器
        //MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter();
       // recyclerView.setAdapter(myGridViewAdapter);

        //设置Item添加,删除动画
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration));
        //添加数据
        initData();
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttp.getAsync(path, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                MyBean myBean = gson.fromJson(result, MyBean.class);
                data_al.addAll(myBean.data);
                //设置ListView的适配器
                MyListViewAdatper adapter = new MyListViewAdatper();
                recyclerView.setAdapter(adapter);
            }
        });

    }
   /* //自定义GridView适配器
    class  MyGridViewAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(View.inflate(MainActivity.this, R.layout.lv_item, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            tv.setText(al.get(position));
        }

        @Override
        public int getItemCount() {
            return al.size();
        }
        class  MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv);
            }
        }
    }*/




    //自定义ListView适配器
    class  MyListViewAdatper extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(View.inflate(MainActivity.this, R.layout.lv_item, null));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           tv.setText(data_al.get(position).goods_name);
            BitmapUtils bitmapUtils = new BitmapUtils(MainActivity.this);
            bitmapUtils.display(iv,data_al.get(position).goods_img);

        }
        @Override
        public int getItemCount() {
            return data_al.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            iv = (ImageView) view.findViewById(R.id.iv);
        }
    }

}
