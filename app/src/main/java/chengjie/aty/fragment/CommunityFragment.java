package chengjie.aty.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chengjie.ui.R;

/**
 * Created by 程杰 on 2017/9/10.
 */

public class CommunityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.community,
                container, false);
        return contactsLayout;
    }
}
