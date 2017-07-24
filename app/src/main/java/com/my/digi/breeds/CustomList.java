package com.my.digi.breeds;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Darryl on 12/17/2016.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] description;
    private final Integer[] imageId;

    public CustomList(Activity context,
                      String[] description, Integer[] imageId) {
        super(context, R.layout.list_single, description);
        this.context = context;
        this.description = description;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item_txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.item_img);
        txtTitle.setText(description[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
