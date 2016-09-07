package vldmr.testtask.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vldmr.testtask.R;
import vldmr.testtask.database.Product;

/**
 * Created by Vladimir on 07.09.2016.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder> {
    List<Product> list;
    Context c;

    public RecyclerViewAdapter(List<Product> list,Context c) {
        this.list = list;
        this.c=c;
    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.price.setText("Цена: "+String.valueOf(list.get(position).getPrice()) );
        holder.amount.setText("Количество: "+String.valueOf(list.get(position).getAmount()));
        holder.id=list.get(position).getId();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        Long id;
        TextView name;
        TextView price;
        TextView amount;
        PersonViewHolder(View itemView) {
            super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.name_r);
            price = (TextView)itemView.findViewById(R.id.price_r);
            amount = (TextView) itemView.findViewById(R.id.amount_r);

        }

        public Long getId() {
            return id;
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
