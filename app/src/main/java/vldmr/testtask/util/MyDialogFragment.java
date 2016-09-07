package vldmr.testtask.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import vldmr.testtask.MainActivity;
import vldmr.testtask.ProductActivity;
import vldmr.testtask.database.DaoSessionFactory;
import vldmr.testtask.database.ProductDao;

/**
 * Created by Vladimir on 07.09.2016.
 */
public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        final Long id= getArguments().getLong("id");
        ProductDao dao= DaoSessionFactory.getDaoSesion(getContext()).getProductDao();
        String name=dao.load(id).getName();
        builder.setTitle(name);
        builder.setNeutralButton("Редактировать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getContext(),ProductActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProductDao dao= DaoSessionFactory.getDaoSesion(getContext()).getProductDao();
                dao.delete(dao.load(id));
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        return  builder.create();
    }
}