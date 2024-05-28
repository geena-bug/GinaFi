package com.example.savingsapp;

// Import necessary classes and packages
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WithdrawalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WithdrawalFragment extends Fragment {
    Context context; // Context variable

    public WithdrawalFragment() {
        // Required empty public constructor
    }

    // Static method to create a new instance of WithdrawalFragment with context
    public static WithdrawalFragment newInstance(Context context) {
        WithdrawalFragment fragment = new WithdrawalFragment();
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No specific actions needed on fragment creation for now
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdrawal, container, false);
    }
}
