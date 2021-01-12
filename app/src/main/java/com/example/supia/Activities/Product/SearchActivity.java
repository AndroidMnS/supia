package com.example.supia.Activities.Product;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supia.Adapter.Product.ProductAdapter;
import com.example.supia.Dto.Product.ProductDto;
import com.example.supia.NetworkTask.Product.NetworkTaskProduct;
import com.example.supia.R;
import com.example.supia.ShareVar.ShareVar;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    final static String TAG = "서치액티비티";


    //field
    String urlAddr = null;
    String urlIp = ShareVar.urlIp;

    ArrayList<ProductDto> product;
    ProductAdapter adapter = null;
    private RecyclerView recyclerView = null;
    private RecyclerView.LayoutManager layoutManager = null;
    //검색을 위한 선언
    EditText etSearch;
    ImageButton ibSearch;
    String stSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //검색 editText, Button---------------------------------
        etSearch = findViewById(R.id.et_search);
        ibSearch = findViewById(R.id.btn_search_searchactivity);
        ibSearch.setOnClickListener(searchClickListener);
        //-----------------------------------------------------

        ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); //사용자에게 사진 사용 권한 받기 (가장중요함)
        recyclerView = findViewById(R.id.rl_product);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        etSearch = findViewById(R.id.et_search);
        ibSearch = findViewById(R.id.btn_search_searchactivity);


        ibSearch.setOnClickListener(searchClickListener);
        connectGetData();
    }



    @Override
    protected void onResume() {
        super.onResume();


//        connectGetData();
        registerForContextMenu(recyclerView);

        Log.v(TAG, "onResume");
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

//                Intent intent = new Intent(SearchActivity.this, ListviewActivity.class);//리스트 클릭시 리스트뷰 넘어가기
//                intent.putExtra("urlIp", urlIp);//ip주소 보내기 ---종찬추가 12/30
//                intent.putExtra("urlAddr", urlAddr);
//                intent.putExtra("addrNo", members.get(position).getAddrNo());
//                intent.putExtra("addrName", members.get(position).getAddrName());
//                intent.putExtra("addrTag", members.get(position).getAddrTag());
//                intent.putExtra("addrTel", members.get(position).getAddrTel());
//                intent.putExtra("addrDetail", members.get(position).getAddrDetail());
//                intent.putExtra("addrAddr", members.get(position).getAddrAddr());
//                intent.putExtra("addrImagePath",members.get(position).getAddrImagePath());
//                startActivity(intent);


            }
        });
    }


    //돋보기 버튼 클릭
    View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            urlAddr = "http://"+urlIp+":8080/test/search.jsp";
            stSearch = etSearch.getText().toString();

            urlAddr = urlAddr + "?productName="+ stSearch +"&productPrice="+ stSearch + "&productBrand="+ stSearch ;


            connectGetData();
        }
    };


    private void connectGetData() {
        try {

            NetworkTaskProduct networkTask = new NetworkTaskProduct(SearchActivity.this, urlAddr,"select");
            Object obj = networkTask.execute().get();
            product = (ArrayList<ProductDto>) obj;


            adapter = new ProductAdapter(SearchActivity.this, R.layout.listlayout, product);
            recyclerView.setAdapter(adapter);



//            adaperClick();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adaperClick() {
        try {
            registerForContextMenu(recyclerView);

            adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

//                    Intent intent = new Intent(SearchActivity.this, ListviewActivity.class);//리스트 클릭시 리스트뷰 넘어가기
//                    intent.putExtra("urlAddr", urlAddr);
//                    intent.putExtra("urlIp", urlIp);
//                    intent.putExtra("addrNo", members.get(position).getAddrNo());
//                    intent.putExtra("addrName", members.get(position).getAddrName());
//                    intent.putExtra("addrTag", members.get(position).getAddrTag());
//                    intent.putExtra("addrTel", members.get(position).getAddrTel());
//                    intent.putExtra("addrDetail", members.get(position).getAddrDetail());
//                    intent.putExtra("addrAddr", members.get(position).getAddrAddr());
//                    intent.putExtra("addrImagePath", members.get(position).getAddrImagePath());
//
//
//                    startActivity(intent);
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    //배경 터치 시 키보드 사라지게
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm;
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


}//------------------------------