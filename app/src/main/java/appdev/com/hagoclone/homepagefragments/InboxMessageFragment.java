package appdev.com.hagoclone.homepagefragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appdev.com.hagoclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxMessageFragment extends Fragment {

    public InboxMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox_message, container, false);
    }
}
