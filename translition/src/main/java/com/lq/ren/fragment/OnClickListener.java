package com.lq.ren.fragment;

/**
 * Listener for kitten click events in the grid of kittens
 *
 * @author bherbst
 */
public interface OnClickListener {
    /**
     * Called when a kitten is clicked
     * @param holder The ViewHolder for the clicked kitten
     * @param position The position in the grid of the kitten that was clicked
     */
    void onKittenClicked(ViewHolder holder, int position);
}
