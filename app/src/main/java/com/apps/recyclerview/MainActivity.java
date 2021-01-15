package com.apps.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Default Image
        imageUrl = "https://www.pngitem.com/pimgs/m/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png";

        // toolbar fancy stuff
        getSupportActionBar().setTitle(R.string.toolbar_title);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        addData();
    }

    private void addData() {
        contactList.add(new Contact("Abdullah Azzam, S.Kom", imageUrl, "https://docs.google.com/spreadsheets/d/1up8xhdbdNqofvff0PpetHdzNdxrkcf49uvrAB6bfkLg/edit?usp=sharing"));
        contactList.add(new Contact("Aditya Redha Kusuma, S.Pd.", imageUrl, "https://docs.google.com/spreadsheets/d/13eaddp-sn7bqHwH1PLiI1Ub4uY39bmBZA4U-wjJRP5s/edit?usp=sharing"));
        contactList.add(new Contact("Agam Amintaha, S. Kom", imageUrl, "https://docs.google.com/spreadsheets/d/1qG-StFCjGpP7duNF9v5Au0fCc3KUU7n2I6NIdOcYiTE/edit?usp=sharing"));
        contactList.add(new Contact("Agus Hamdun, Amd. Kom", imageUrl, "https://docs.google.com/spreadsheets/d/1I4osusqPaHuSomU6of19DjskpfG-wMFhlIBCze6_6rg/edit?usp=sharing"));
        contactList.add(new Contact("Ahmad Widodo, S.E", imageUrl, "https://docs.google.com/spreadsheets/d/1VajlfpzkWlfzzxakBwG_GlTgcYfdX2ESY-PNXj-75p8/edit?usp=sharing"));
        contactList.add(new Contact("Aji Suryawan, S.Kom", imageUrl, "https://docs.google.com/spreadsheets/d/1hu1HYU_4Jylu3NuZSRit-1PIC0qafMIvdyY2b8XsWuY/edit?usp=sharing"));
        contactList.add(new Contact("Ali Imron, S.Pd.", imageUrl, "https://docs.google.com/spreadsheets/d/1MKeU8pROUbqhGu11_tFZdDBOFLTyLXFVomHK-U0ONDg/edit?usp=sharing"));
        contactList.add(new Contact("Anjas Syifatul Anam", imageUrl, "https://docs.google.com/spreadsheets/d/1DuAASexjGU8THvkhBU2VcXQiXx93qCzXdV9IM_vV0ao/edit?usp=sharing"));
        contactList.add(new Contact("Arif Agus Kurniawan, S.Kom", imageUrl, "https://docs.google.com/spreadsheets/d/1Vvy_Dye7OjWBRdLJp0cQ1-cXNn2bcBUcpfqaaCFuRYo/edit?usp=sharing"));
        contactList.add(new Contact("Arif Jauhari, S.Pd.", imageUrl, "https://docs.google.com/spreadsheets/d/1aGppBgmfQvf4Qi4HtSGaleO9BHr12_HEQ-0NNNntaNU/edit?usp=sharing"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Contact contact) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(contact.getLink()));
        startActivity(i);
    }
}