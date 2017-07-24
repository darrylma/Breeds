package com.my.digi.breeds;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Darryl on 12/17/2016.
 */

public class Browse extends Activity implements View.OnClickListener {
    ListView list;
    String[] description, searchDescription;
    Integer[] image, searchImage, searchPosition;
    List<String> searchedDataDescription = new ArrayList<String>();
    List<Integer> searchedDataImage = new ArrayList<Integer>();
    List<Integer> searchDataPostition = new ArrayList<Integer>();
    Database database;
    TextView tvProfile, tvProfileBackground, tvProfileName;
    ImageView ivProfile, ivFriendlinessStars, ivPlayfulnessStars;
    EditText etSearchString;
    Button bClose, bClearSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        String PACKAGE_NAME = getApplicationContext().getPackageName();
        database = new Database(this);
        description = new String[database.getLength()];
        image = new Integer[database.getLength()];
        //searchedDataDescription = new String[database.getLength()];
        //searchedDataImage = new Integer[database.getLength()];
        //searchDataPostition = new Integer[database.getLength()];
        tvProfile = (TextView) findViewById(R.id.tvProfile);
        tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        tvProfileBackground = (TextView) findViewById(R.id.tvProfileBackground);
        ivProfile = (ImageView) findViewById(R.id.ivPreviewProfile);
        ivFriendlinessStars = (ImageView) findViewById(R.id.ivFriendlinessStars);
        ivPlayfulnessStars = (ImageView) findViewById(R.id.ivPlayfulnessStars);
        bClose = (Button) findViewById(R.id.bClose);
        bClearSearch = (Button) findViewById(R.id.bClearSearch);
        etSearchString = (EditText) findViewById(R.id.etSearchString);

        tvProfile.setVisibility(View.INVISIBLE);
        tvProfileName.setVisibility(View.INVISIBLE);
        bClose.setVisibility(View.INVISIBLE);
        ivProfile.setVisibility(View.INVISIBLE);
        ivFriendlinessStars.setVisibility(View.INVISIBLE);
        ivPlayfulnessStars.setVisibility(View.INVISIBLE);
        tvProfileBackground.setVisibility(View.INVISIBLE);

        bClose.setOnClickListener(this);
        bClearSearch.setOnClickListener(this);

        for (int i=0; i < database.getLength(); i++){
            description[i] = database.getBreed(i);
            image[i] = getResources().getIdentifier(PACKAGE_NAME+":drawable/" + database.getImageName(i), null, null);
        }

        CustomList adapter = new CustomList(Browse.this, description, image);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvProfile.setVisibility(View.VISIBLE);
                tvProfileName.setVisibility(View.VISIBLE);
                bClose.setVisibility(View.VISIBLE);
                ivProfile.setVisibility(View.VISIBLE);
                ivFriendlinessStars.setVisibility(View.VISIBLE);
                ivPlayfulnessStars.setVisibility(View.VISIBLE);
                tvProfileBackground.setVisibility(View.VISIBLE);

                String PACKAGE_NAME = getApplicationContext().getPackageName();
                tvProfileName.setText(database.getBreed(position));
                tvProfile.setText(
                        "Breed Category:                        " + database.getCategory(position) + "\n" +
                        "Lifespan:                                    " + database.getLifeSpan(position) + "\n" +
                        "Friendliness: \n" +
                        "Playfulness:");
                ivFriendlinessStars.setImageResource(getResources().getIdentifier(PACKAGE_NAME+":drawable/img_" + database.getFriendliness(position) + "_star", null, null));
                ivPlayfulnessStars.setImageResource(getResources().getIdentifier(PACKAGE_NAME+":drawable/img_" + database.getPlayfulness(position) + "_star", null, null));

                int idImage = getResources().getIdentifier(PACKAGE_NAME+":drawable/" + database.getImageName(position), null, null);
                ivProfile.setImageResource(idImage);
            }
        });

        etSearchString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchedDataDescription.clear();
                searchedDataImage.clear();
                searchDataPostition.clear();
                for (int j = 0, k = 0; j < description.length; j++){
                    if ((description[j].toLowerCase(Locale.getDefault())).contains(etSearchString.getText().toString().toLowerCase())) {
                        searchedDataDescription.add(description[j]);
                        searchedDataImage.add(image[j]);
                        searchDataPostition.add(j);
                        //k++;
                    }
                }

                searchDescription = searchedDataDescription.toArray(new String[searchedDataDescription.size()]);
                searchImage = searchedDataImage.toArray(new Integer[searchedDataImage.size()]);
                searchPosition = searchDataPostition.toArray(new Integer[searchDataPostition.size()]);

                CustomList adapter = new CustomList (Browse.this, searchDescription, searchImage);
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        tvProfile.setVisibility(View.VISIBLE);
                        tvProfileName.setVisibility(View.VISIBLE);
                        bClose.setVisibility(View.VISIBLE);
                        ivProfile.setVisibility(View.VISIBLE);
                        ivFriendlinessStars.setVisibility(View.VISIBLE);
                        ivPlayfulnessStars.setVisibility(View.VISIBLE);
                        tvProfileBackground.setVisibility(View.VISIBLE);

                        String PACKAGE_NAME = getApplicationContext().getPackageName();
                        tvProfileName.setText(database.getBreed(searchPosition[position]));
                        tvProfile.setText(
                                        "Breed Category:                        " + database.getCategory(searchPosition[position]) + "\n" +
                                        "Lifespan:                                    " + database.getLifeSpan(searchPosition[position]) + "\n" +
                                        "Friendliness: \n" +
                                        "Playfulness:");
                        ivFriendlinessStars.setImageResource(getResources().getIdentifier(PACKAGE_NAME+":drawable/img_" + database.getFriendliness(searchPosition[position]) + "_star", null, null));
                        ivPlayfulnessStars.setImageResource(getResources().getIdentifier(PACKAGE_NAME+":drawable/img_" + database.getPlayfulness(searchPosition[position]) + "_star", null, null));

                        int idImage = getResources().getIdentifier(PACKAGE_NAME+":drawable/" + database.getImageName(searchPosition[position]), null, null);
                        ivProfile.setImageResource(idImage);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //Click to close dog profile
            case R.id.bClose:
                tvProfile.setVisibility(View.INVISIBLE);
                tvProfileName.setVisibility(View.INVISIBLE);
                bClose.setVisibility(View.INVISIBLE);
                ivProfile.setVisibility(View.INVISIBLE);
                ivFriendlinessStars.setVisibility(View.INVISIBLE);
                ivPlayfulnessStars.setVisibility(View.INVISIBLE);
                tvProfileBackground.setVisibility(View.INVISIBLE);
                break;

            case R.id.bClearSearch:
                etSearchString.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearchString.getWindowToken(), 0);

                break;
        }
    }

}
