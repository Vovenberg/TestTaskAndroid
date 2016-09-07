package vldmr.testtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vldmr.testtask.database.DaoSessionFactory;
import vldmr.testtask.database.Product;
import vldmr.testtask.database.ProductDao;
import vldmr.testtask.util.MyDialogFragment;
import vldmr.testtask.util.RecyclerItemClickListener;
import vldmr.testtask.util.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        /*final ListView listView= (ListView) findViewById(R.id.listView);
        listView.setAdapter(getAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) listView.getItemAtPosition(position);
                Bundle bundle=new Bundle();
                bundle.putLong("id", (Long) map.get("Id"));

                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setArguments(bundle);
                myDialogFragment.show(manager,"dialog");
            }
        });*/
        final RecyclerView rv= (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));
        final List<Product> list=DaoSessionFactory.getDaoSesion(this).getProductDao().loadAll();
        rv.setAdapter(new RecyclerViewAdapter(list,this));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecyclerViewAdapter.PersonViewHolder holder= (RecyclerViewAdapter.PersonViewHolder) rv.findViewHolderForAdapterPosition(position);
                Long l=holder.getId();
                Bundle bundle=new Bundle();
                bundle.putLong("id", l);

                FragmentManager manager = getSupportFragmentManager();
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setArguments(bundle);
                myDialogFragment.show(manager,"dialog");
            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProductActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:{
                Intent intent=new Intent(this,ProductActivity.class);
                startActivity(intent);
            return true;
            }
            default: return super.onOptionsItemSelected(item);
        }
    }

    private SimpleAdapter getAdapter(){
        ProductDao dao= DaoSessionFactory.getDaoSesion(this).getProductDao();
        List<Product> list=dao.loadAll();
        int c=(int) dao.count();
        String[] names= new String[c];
        Integer[] prices= new Integer[c];
        Integer[] amounts= new Integer[c];
        Long[] ids= new Long[c];

        int j=0;
        for (Product p:list){
            names[j]=p.getName();
            prices[j]=p.getPrice();
            amounts[j]=p.getAmount();
            ids[j]=p.getId();
            j++;
        }

        List<Map<String,Object>> list2=new ArrayList<>();
        for (int i=0;i<c;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("Name",names[i]);
            map.put("Price",prices[i]);
            map.put("Amount",amounts[i]);
            map.put("Id",ids[i]);
            list2.add(map);
        }
        String[] from={"Name","Price","Amount"};
        int[] to={R.id.name,R.id.price,R.id.amount};
        return new SimpleAdapter(this,list2,R.layout.item,from,to);
    }
    private void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.products);
        setSupportActionBar(toolbar);
    }


}
