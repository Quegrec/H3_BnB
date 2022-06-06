package com.example.rent.comment;

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
import com.example.rent.homepage.Biens_items;

import java.util.List;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment_items> commentItemsList;
    private LayoutInflater inflater;

    public CommentAdapter(Context context, List<Comment_items> commentItemsList){
        this.context = context;
        this.commentItemsList = commentItemsList;
    }

    @Override
    public int getCount() {
        return commentItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentItemsList.get(position);
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
            view = inflater.inflate(R.layout.adapter_item_comment, null);
        }

        TextView itemName = view.findViewById(R.id.user_name);
        TextView itemFirstname = view.findViewById(R.id.user_firstname);
        TextView itemComment = view.findViewById(R.id.list_comment);

        Comment_items currentItem = (Comment_items) getItem(i);
        itemName.setText(currentItem.getUser_name());
        itemFirstname.setText(currentItem.getUser_firstname());
        itemComment.setText(currentItem.getComment());

        return view;
    }
}
