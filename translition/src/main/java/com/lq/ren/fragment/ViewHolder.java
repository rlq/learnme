package com.lq.ren.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lq.ren.transitions.R;

/**
 * ViewHolder for kitten cells in our grid
 *
 * @author bherbst
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView image;

    public ViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
