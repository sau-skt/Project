package appdev.com.hagoclone.homepagefragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appdev.com.hagoclone.R;


public class GamesAndChatroomFragment extends Fragment {

    private RecyclerView reminderRV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games_and_chatroom,container,false);

        reminderRV = view.findViewById(R.id.reminderrecyclerview);
        return view;
    }
}
