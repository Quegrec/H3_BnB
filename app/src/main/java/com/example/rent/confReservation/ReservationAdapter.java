package com.example.rent.confReservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rent.R;
import com.example.rent.comment.Comment_items;

import java.util.List;

public class ReservationAdapter extends BaseAdapter {

    private Context context;
    private List<Reservation_items> reservationItemsList;
    private LayoutInflater inflater;

    public ReservationAdapter(Context context, List<Reservation_items> reservationItemsList){
        this.context = context;
        this.reservationItemsList = reservationItemsList;
    }

    @Override
    public int getCount() {
        return reservationItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationItemsList.get(position);
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
            view = inflater.inflate(R.layout.adapter_item_reservation, null);
        }

        TextView itemDate_form = view.findViewById(R.id.reservation_date_from);
        TextView itemDate_to = view.findViewById(R.id.reservation_date_to);
        TextView itemUser_name = view.findViewById(R.id.reservation_user_name);
        TextView itemUser_firstname = view.findViewById(R.id.reservation_user_firstname);
        TextView itemLogement_name = view.findViewById(R.id.reservation_logement_name);
        TextView itemPrice = view.findViewById(R.id.reservation_price);


        Reservation_items currentItem = (Reservation_items) getItem(i);
        itemDate_form.setText(currentItem.getDate_from());
        itemDate_to.setText(currentItem.getDate_to());
        itemUser_name.setText(currentItem.getUser_name());
        itemUser_firstname.setText(currentItem.getUser_firstname());
        itemLogement_name.setText(currentItem.getLogement_name());
        itemPrice.setText(currentItem.getPrice());

        return view;
    }
}
