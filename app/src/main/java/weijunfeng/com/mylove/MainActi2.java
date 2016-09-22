package weijunfeng.com.mylove;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weijunfeng.com.mylove.Utils.ToastUtil;
import weijunfeng.com.mylove.adapter.SimpleAdapter;
import weijunfeng.com.mylove.bean.WeiXin;
import weijunfeng.com.mylove.network.AbsNextWorkListener;
import weijunfeng.com.mylove.network.BaseNetWork;
import weijunfeng.com.mylove.network.NetworkListener;

public class MainActi2 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.btn)
    Button btn;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private final Runnable mRefreshDone = new Runnable() {

        @Override
        public void run() {
            mSwipeRefreshWidget.setRefreshing(false);
            ToastUtil.getInstance().showMsg("刷新成功");
        }

    };
    private RecyclerView recyclerView;
    private AbsNextWorkListener<WeiXin> networkListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        initView();
    }

    private void initView() {
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
//        mSwipeRefreshWidget.setColorScheme(
//                this.getResources().getColor(R.color.color1),
//                this.getResources().getColor(R.color.color2),
//                this.getResources().getColor(R.color.color3),
//                this.getResources().getColor(R.color.color4));
        mSwipeRefreshWidget.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3,
                R.color.color4);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        BaseNetWork.execute(new NetworkListener<WeiXin>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(final WeiXin weiXin) {
                System.out.println("weiXin = [" + weiXin.getItems().size() + "]");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public Class<WeiXin> getActuClass() {
                return WeiXin.class;
            }
        });
        networkListener = new AbsNextWorkListener<WeiXin>() {

            @Override
            public void onHandleSuccess(WeiXin weiXin) {
                recyclerView.setAdapter(new SimpleAdapter(MainActi2.this, weiXin.getItems()));
                mSwipeRefreshWidget.setNestedScrollingEnabled(true);
                mSwipeRefreshWidget.setRefreshing(false);
            }
        };
        BaseNetWork.execute(networkListener);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration();
//        recyclerView.setAdapter(new simplestringa);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.setNestedScrollingEnabled(false);
        BaseNetWork.execute(networkListener);
//        mSwipeRefreshWidget.removeCallbacks(mRefreshDone);
//        mSwipeRefreshWidget.postDelayed(mRefreshDone, 1000);
    }

    public void onClick(View view) {
        Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
    }

    public void clickBtn(View view) {

    }

    @OnClick(R.id.btn)
    public void onClick() {
        Toast.makeText(this, "btn", Toast.LENGTH_SHORT).show();
    }
}
