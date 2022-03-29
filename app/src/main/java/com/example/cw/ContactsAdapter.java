package com.example.cw;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    Context context;
    List<classCarModel> contactsList;
    RecyclerView rvPrograms;
    final View.OnClickListener onClickListener = new MyOnClickListener();
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowId;
        TextView rowName;
        TextView rowEmail;
        TextView viewID;
        ImageView imgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowId = itemView.findViewById(R.id.item_id);
            rowName = itemView.findViewById(R.id.item_name);
            rowEmail = itemView.findViewById(R.id.item_email);
            imgView = itemView.findViewById(R.id.imageView);
            viewID = itemView.findViewById(R.id.tv_ID);
        }
    }

    public ContactsAdapter(Context context, List<classCarModel> contactsList, RecyclerView rvPrograms){
        this.context = context;
        this.contactsList = contactsList;
        this.rvPrograms = rvPrograms;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, viewGroup, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder viewHolder, int i) {
        classCarModel contact = contactsList.get(i);
        viewHolder.imgView.setImageURI(Uri.parse(contact.getImagepath()));
        viewHolder.rowId.setText(Integer.toString(contact.getYear()));
        viewHolder.rowName.setText(contact.getMake());
        viewHolder.rowEmail.setText(contact.getModel());
        viewHolder.viewID.setText(Integer.toString(contact.getId()));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvPrograms.getChildLayoutPosition(v);
            String item = contactsList.get(itemPosition).getMake();
            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();
        }
    }
}
