package com.codemonk.introtopopularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.codemonk.introtopopularmovies.api.tmdb.rest.models.TMDBModel;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MovieGridArrayAdapter movieGridArrayAdapter;
    private ArrayList<TMDBModel> tmdbModels = new ArrayList<TMDBModel>();
    private String tmdbModelsKey = "tmdbModels";

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(tmdbModelsKey)) {
            tmdbModels = (ArrayList<TMDBModel>) savedInstanceState.get(tmdbModelsKey);
        } else {
            tmdbModels = new ArrayList<TMDBModel>();
        }

        movieGridArrayAdapter = new MovieGridArrayAdapter(getActivity(), tmdbModels);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mainfragment, menu);

        String currentSortOrder = getSortOrder();

        MenuItem menuItem = getMenuItemFromName(menu, currentSortOrder);

        onOptionsItemSelected(menuItem);
    }

    private MenuItem getMenuItemFromName(Menu menu, String aString) {
        String packageName = getContext().getPackageName();

        int resId = getResources().getIdentifier(aString, "id", packageName);

        MenuItem test = menu.findItem(resId);

        return test;
    }

    private String getNameFromMenuItem(MenuItem menuItem) {
        String packageName = getContext().getPackageName();

        String resourceEntryName = getResources().getResourceEntryName(menuItem.getItemId());

        return resourceEntryName;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        // Get sort order from menu item.
        String sortOrder = getNameFromMenuItem(menuItem);

        // Set sort order in preferences.
        setSortOrder(sortOrder);

//        getActivity().setTitle(menuItem.getTitle());

        // Call service with the new sort order.
        new MovieDBApiTask(movieGridArrayAdapter).execute(sortOrder);

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // I can't seem to decide between persisting the data or not.
        // The guy who said to use that design pattern of "retaining" the fragment seemed pretty sure.
        // I guess I"m worried it will conflict with what udacity is teaching.
        // Would I have to persist the movieGridArrayAdapter?
        // Or perhaps just the array itself?
        outState.putParcelableArrayList(tmdbModelsKey, tmdbModels);
        super.onSaveInstanceState(outState);
    }

    private String getSortOrder() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_sort_order_key), getString(R.string.sort_order_default));

        return sortOrder;
    }

    private void setSortOrder(String sortOrder) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putString(getString(R.string.pref_sort_order_key), sortOrder);
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_main, container, false);
        gridView.setAdapter(movieGridArrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TMDBModel tmdbModel = movieGridArrayAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(TMDBModel.class.getSimpleName(), tmdbModel);

                startActivity(intent);
            }
        });

        return gridView;
    }
}