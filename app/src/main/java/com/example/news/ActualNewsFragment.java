package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ActualNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresherLayout;
    private NestedScrollView nestedScrollView;
    private ImageButton btnSearch, btnCloseSearchView;
    private TextView tvQuery;
    private EditText etSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actual_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        swipeRefresherLayout = view.findViewById(R.id.swipeRefresherLayout);
        swipeRefresherLayout.setColorSchemeResources(R.color.colorAccent);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        etSearch = view.findViewById(R.id.etSearch);
        tvQuery = view.findViewById(R.id.tvQuery);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnCloseSearchView = view.findViewById(R.id.btnCloseSearchView);
        return view;
    }
}
