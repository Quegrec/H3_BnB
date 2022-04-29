package com.example.rent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BienMaisonAdapter extends BaseAdapter {

    private Context context;
    private List<Biens_items> biensItemsList;
    private LayoutInflater inflater;

    public  BienMaisonAdapter(Context context, List<Biens_items> biensItemsList){
        this.context = context;
        this.biensItemsList = biensItemsList;
    }

    @Override
    public int getCount() {
        return biensItemsList.size();
    }

    @Override
    public Biens_items getItem(int position) {
        return biensItemsList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Pour charger le fichier xml que pour le 1er element et le grarder en memoire
        if (view == null){
            this.inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.adapter_item, null);
        }



        Biens_items currentItem = getItem(i);
        String itemName = currentItem.getName();
        double itemPrix = currentItem.getPrix();
        String itemPrixstr = Double.toString(itemPrix);
        String itemPays = currentItem.getPays();
        String itemNote = currentItem.getNote();
        String itemImg = currentItem.getImg();

        TextView itemNameView = view.findViewById(R.id.biens_name);
        TextView itemNoteView = view.findViewById(R.id.biens_note);
        TextView itemPaysView = view.findViewById(R.id.biens_pays);
        TextView itemPrixView = view.findViewById(R.id.biens_prix);
        ImageView itemImgView = view.findViewById(R.id.logo);

        int id = this.context.getResources().getIdentifier(itemImg, "drawable", this.context.getPackageName());

        itemNameView.setText(itemName);
        itemPaysView.setText(itemPays);
        itemNoteView.setText(itemNote);
        itemImgView.setImageResource(id);
        itemPrixView.setText(itemPrixstr);

        return view;
    }
}
