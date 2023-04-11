package com.example.rickmorty2;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// The adapter class which
// extends RecyclerView Adapter
public class Adapter
        extends RecyclerView.Adapter<Adapter.MyView> {

    //This is the list of texts
    private List<String> list;
    private int selectedPosition = -1;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            textView = (TextView)view
                    .findViewById(R.id.tvTitle);
            // Set click listener for the button
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the clicked item position
                    int position = getAdapterPosition();
                    // Update the selected position
                    setSelectedPosition(position);
                    // Notify adapter that item has changed to trigger a rebind and update the view
                    notifyDataSetChanged();
                }
            });




        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public Adapter(List<String> horizontalList)
    {
        this.list = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.rv_item,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }

    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {
        holder.textView.setText(list.get(position));

        // Check if the current item is the selected item, and update the UI accordingly
        if (position == getSelectedPosition()) {
            // Set the button color to indicate the selected item
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorSelected));
        } else {
            // Reset the button color for non-selected items
            holder.textView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorNormal));
        }
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }
    // Method to update the selected position
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    // Method to get the selected position
    public int getSelectedPosition() {
        return selectedPosition;
    }
}