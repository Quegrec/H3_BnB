package com.example.rent.conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rent.R;
import com.example.rent.message.Message_items;

import java.util.List;

public class ConversationAdapter extends BaseAdapter {

    private Context context;
    private List<Conversation_items> conversationItemsList;
    private LayoutInflater inflater;

    public ConversationAdapter(Context context, List<Conversation_items> conversationItemsList){
        this.context = context;
        this.conversationItemsList = conversationItemsList;
    }

    @Override
    public int getCount() {
        return conversationItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return conversationItemsList.get(position);
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
            view = inflater.inflate(R.layout.adapter_item_conversation, null);
        }

        TextView itemFrom = view.findViewById(R.id.from);
        TextView itemMessage = view.findViewById(R.id.message);

        Conversation_items currentItem = (Conversation_items) getItem(i);
        itemFrom.setText(currentItem.getFrom());
        itemMessage.setText(currentItem.getText());

        return view;
    }
}
