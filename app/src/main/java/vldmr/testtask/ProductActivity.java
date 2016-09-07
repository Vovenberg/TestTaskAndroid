package vldmr.testtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vldmr.testtask.database.DaoSessionFactory;
import vldmr.testtask.database.Product;
import vldmr.testtask.database.ProductDao;

/**
 * Created by Vladimir on 07.09.2016.
 */
public class ProductActivity extends AppCompatActivity {
    EditText name;
    EditText price;
    EditText amount;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initToolbar();
        name= (EditText) findViewById(R.id.name_edit);
        price= (EditText) findViewById(R.id.price_edit);
        amount= (EditText) findViewById(R.id.amount_edit);
        Button b= (Button) findViewById(R.id.button);
        final Long id=getIntent().getLongExtra("id",1);
        if (id==1) {
            setTitle(R.string.add);    //для добавления

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name1=name.getText().toString();
                        String price1=price.getText().toString();
                        String amount1=amount.getText().toString();
                        if (isValidInt(price1)&&isValidInt(amount1)&&!name1.equals("")) {
                            Product product = new Product(new Random().nextLong(),name1 , Integer.valueOf(price1), Integer.valueOf(amount1));

                            ProductDao dao = DaoSessionFactory.getDaoSesion(getApplicationContext()).getProductDao();
                            dao.insert(product);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    R.string.eror,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
        else {                                          //для редактирования
            b.setText(R.string.submit);
            setTitle(R.string.edit);
            ProductDao dao = DaoSessionFactory.getDaoSesion(getApplicationContext()).getProductDao();
            final Product product=dao.load(id);
            name.setText(product.getName());
            price.setText(String.valueOf(product.getPrice()));
            amount.setText(String.valueOf(product.getAmount()));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String price1=price.getText().toString();
                    String amount1=amount.getText().toString();
                    if (isValidInt(price1)&&isValidInt(amount1)) {
                        ProductDao dao = DaoSessionFactory.getDaoSesion(getApplicationContext()).getProductDao();
                        Product product1=new Product(id,name.getText().toString(),Integer.valueOf(price.getText().toString()),Integer.valueOf(amount.getText().toString()));
                        dao.update(product1);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                R.string.eror,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private boolean isValidInt(String price1) {
        String PATTERN = "[0-9]+";

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(price1);
        return matcher.matches();
    }

    public void initToolbar(){
        toolbar=(Toolbar)findViewById(R.id.toolbar_pr);

        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
