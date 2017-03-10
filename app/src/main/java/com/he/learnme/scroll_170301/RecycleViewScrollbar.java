package com.he.learnme.scroll_170301;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.he.learnme.R;

/**
 * Author lqren on 17/3/1.
 */
public class RecycleViewScrollbar extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scrollbar__170301_recycle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ScrollerBarAdapter(getContext()));
    }



    class ScrollerBarAdapter extends RecyclerView.Adapter<ScrollBarViewHolder> {

        private Context context;

        public ScrollerBarAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ScrollBarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).
                    inflate(R.layout.scrollbar__170301_recycle_item, parent, false);
            return new ScrollBarViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ScrollBarViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 40;
        }
    }


    class ScrollBarViewHolder extends RecyclerView.ViewHolder {

        public ScrollBarViewHolder(View itemView) {
            super(itemView);
        }
    }
}
