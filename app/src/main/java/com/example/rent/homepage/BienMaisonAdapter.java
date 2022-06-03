package com.example.rent.homepage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rent.R;

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
    public Object getItem(int position) {
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



        Biens_items currentItem = (Biens_items) getItem(i);
        String itemName = currentItem.getName();
        double itemPrix = currentItem.getPrix();
        String itemPrixstr = Double.toString(itemPrix);
        String itemNote = currentItem.getNote();
        String itemImg = currentItem.getImg();
        String itemType = currentItem.getType();
        String itemShortDescription = currentItem.getDescription();
        String itemLocalisation = currentItem.getLocalisation();

        TextView itemNameView = view.findViewById(R.id.biens_name);
        TextView itemNoteView = view.findViewById(R.id.biens_note);
        TextView itemPrixView = view.findViewById(R.id.biens_prix);
        ImageView itemImgView = view.findViewById(R.id.logo);
        TextView itemTypeView = view.findViewById(R.id.biens_type);
        TextView itemShortDescriptionView = view.findViewById(R.id.biens_description);
        TextView itemLocalisationView = view.findViewById(R.id.biens_pays);

        //int id = this.context.getResources().getIdentifier(itemImg, "drawable", this.context.getPackageName());

        //Decode base64 string
        byte[] bytes = Base64.decode(itemImg, Base64.DEFAULT);
        // Initialize bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        itemNameView.setText(itemName);
        itemNoteView.setText(itemNote);
        itemImgView.setImageBitmap(bitmap);
        itemPrixView.setText(itemPrixstr);
        itemTypeView.setText(itemType);
        itemShortDescriptionView.setText(itemShortDescription);
        itemLocalisationView.setText(itemLocalisation);

        return view;
    }
}
