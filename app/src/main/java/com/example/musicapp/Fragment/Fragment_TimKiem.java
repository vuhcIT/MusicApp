package com.example.musicapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.SearchAdapter;
import com.example.musicapp.Model.BaiHat;
import com.example.musicapp.R;
import com.example.musicapp.Service.APIService;
import com.example.musicapp.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_TimKiem extends Fragment {
    View view;
    Toolbar toolbarsearch;
    RecyclerView recyclerViewsearch;
    TextView txtsearchnull;

    SearchAdapter searchAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timkiem,container,false);
        toolbarsearch = view.findViewById(R.id.toolbarsearch);
        recyclerViewsearch = view.findViewById(R.id.recyclerviewsearch);
        txtsearchnull = view.findViewById(R.id.textviewsearchkhongduoc);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarsearch);
        toolbarsearch.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoa(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchTuKhoa(String query){
        Dataservice dataservice = APIService.getservice();
        Call<List<BaiHat>> callback = dataservice.GetSearchBaiHat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat =(ArrayList<BaiHat>) response.body();
                if(mangbaihat.size() > 0){
                    searchAdapter = new SearchAdapter(getActivity(),mangbaihat);
                    LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
                    recyclerViewsearch.setLayoutManager(linearLayout);
                    recyclerViewsearch.setAdapter(searchAdapter);
                    txtsearchnull.setVisibility(View.GONE);
                    recyclerViewsearch.setVisibility(View.VISIBLE);
                }else {
                    txtsearchnull.setVisibility(View.VISIBLE);
                    recyclerViewsearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable throwable) {

            }
        });
    }
}
