package com.example.rent.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rent.R;
import com.example.rent.comment.Comment_items;

import java.util.List;

public class MoneyAdapter extends BaseAdapter {

    private Context context;
    private List<Money_items> moneyItemsList;
    private LayoutInflater inflater;

    public MoneyAdapter(Context context, List<Money_items> moneyItemsList){
        this.context = context;
        this.moneyItemsList = moneyItemsList;
    }

    @Override
    public int getCount() {
        return moneyItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return moneyItemsList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Pour charger le fichier xml que pour le 1er element et le garder en memoire
        if (view == null){
            this.inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.adapter_item_money, null);
        }

        TextView itemName = view.findViewById(R.id.from);
        TextView itemPrice = view.findViewById(R.id.price);

        Money_items currentItem = (Money_items) getItem(i);
        itemName.setText(currentItem.getName());
        itemPrice.setText(currentItem.getPrice());

        return view;
    }
}
