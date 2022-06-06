package com.example.rent.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rent.R;
import com.example.rent.comment.Comment_items;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<Message_items> messageItemsList;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message_items> messageItemsList){
        this.context = context;
        this.messageItemsList = messageItemsList;
    }

    @Override
    public int getCount() {
        return messageItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItemsList.get(position);
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
            view = inflater.inflate(R.layout.adapter_item_message, null);
        }

        TextView itemName = view.findViewById(R.id.user_name);
        TextView itemLastMessage = view.findViewById(R.id.last_message);


        //int id = this.context.getResources().getIdentifier(itemImg, "drawable", this.context.getPackageName());
        Message_items currentItem = (Message_items) getItem(i);
        itemName.setText(currentItem.getName() + " " + currentItem.getFirstname());
        itemLastMessage.setText(currentItem.getLastMessage() );


        return view;
    }
}
