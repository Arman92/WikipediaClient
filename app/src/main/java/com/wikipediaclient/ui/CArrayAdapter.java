package com.wikipediaclient.ui;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static butterknife.ButterKnife.findById;

public class CArrayAdapter extends ArrayAdapter<CArrayAdapter.AdapterItem> {

    public static class AdapterItem
    {
        public long Id;
        public Object Tag;
        public String Title;

        public AdapterItem(long id, String title)
        {
            this.Id = id;
            this.Title = title;
        }

        public AdapterItem(long id, String title, Object tag)
        {
            this.Id = id;
            this.Title = title;
            this.Tag = tag;
        }
    }

    private List<AdapterItem> list;
    public List<AdapterItem> itemsAll;
    private List<AdapterItem> suggestions;


    private boolean mNotifyOnChange = false;
    private final Object mLock = new Object();

    /**
     * The resource indicating what views to inflate to display the content of this
     * array adapter.
     */
    private int mResource;
    /**
     * The resource indicating what views to inflate to display the content of this
     * array adapter in a drop down widget.
     */
    private int mDropDownResource;


    public CArrayAdapter(Context context, int resource,
                         List<AdapterItem> list){

        super(context, resource, list);
        this.list = list;
        this.itemsAll = (List<AdapterItem>)(((ArrayList<AdapterItem>)list).clone());
        this.suggestions = new ArrayList<>();
        this.mResource = resource;
    }

    public void updateList(List<AdapterItem> newList)
    {
        this.list = newList;
        this.itemsAll = (List<AdapterItem>)(((ArrayList<AdapterItem>)list).clone());
        this.suggestions = new ArrayList<>();
    }

    public List<AdapterItem> getList()
    {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AdapterItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        if (list.size() != 0 || position < list.size())
            return list.get(position).Id;
        else
            return 0;
    }

    private class ViewHolder{
        TextView lblTitle;
        String key;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(mResource, parent, false);

            holder.lblTitle = (TextView)convertView;
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        if (list.size() != 0 && position < list.size())
            holder.lblTitle.setText(list.get(position).Title);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent, true);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent, boolean showSeparator) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        TextView lblTitle;

        if(convertView == null){
            convertView = inflater.inflate(mResource, parent, false);
        }

        lblTitle = (TextView) convertView;

        if (showSeparator)
            convertView.setBackgroundColor(0xFFFFFFFF);
        else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        lblTitle.setText(list.get(position).Title);

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            for (AdapterItem item : itemsAll) {
                if(item.Id == ((AdapterItem)resultValue).Id)
                {
                    return item.Title;
                }
            }


            return "Not found!";
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (AdapterItem item : itemsAll) {
                    if (item.Title.equals(constraint)) {
                        suggestions.addAll(itemsAll);
                        break;
                    }
                    if(item.Title.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsAll;
                filterResults.count = itemsAll.size();
                return filterResults;
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<AdapterItem> filteredList = (ArrayList<AdapterItem>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (AdapterItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (mLock) {
            list.clear();
            itemsAll.clear();
            suggestions.clear();
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    public void addItem(AdapterItem item)
    {
        list.add(item);
        itemsAll.add(item);
    }

    public void add(AdapterItem object) {
        synchronized (mLock) {
            if (list != null) {
                list.add(object);
                itemsAll.add(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }


}