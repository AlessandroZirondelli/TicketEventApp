package com.example.ticketeventapp.model.home.recyclerview.item_diff_callback;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.ticketeventapp.model.mng_events.Event;

import java.util.List;

/**
 * Utility class to compare two List<CardItem>
 *     Android suggests the use of DiffUtil.Callback to improve the performance of the RecyclerView
 *     Each time the list needs to be updated, only the changed items will be reloaded.
 */
public class EventCardItemDiffCallback extends DiffUtil.Callback {

    private final List<Event> oldCardList;
    private final List<Event> newCardList;

    /**
     * Constructor that takes the two lists
     * @param oldList the old list already displayed
     * @param newList the new list to display
     */
    public EventCardItemDiffCallback(List<Event> oldList, List<Event> newList) {
        this.oldCardList = oldList;
        this.newCardList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldCardList.size();
    }

    @Override
    public int getNewListSize() {
        return newCardList.size();
    }

    /**
     *
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return true if the two items are the same
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCardList.get(oldItemPosition) == newCardList.get(
                newItemPosition);
    }

    /**
     *
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return true if the two item have the same content
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Event oldItem = oldCardList.get(oldItemPosition);
        final Event newItem = newCardList.get(newItemPosition);
        return oldItem.getName().equals(newItem.getName())
                && oldItem.getDate().equals(newItem.getDate())
                && oldItem.getTime().equals(newItem.getTime());
    }

    /**
     * When areItemsTheSame(int, int) returns true for two items and
     * areContentsTheSame(int, int) returns false for the two items,
     * this method is called to get a payload about the change.
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return an Object (it could be a bundle) that contains the changes (the only change in this case
     * id the content of the item to handle in the Adapter in the overridden method
     * onBindViewHolder(CryptoViewHolder holder, int position, List<Object> payloads)).
     *
     * It returns null by default.
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}