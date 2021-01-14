package com.aie.bimbelaie.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aie.bimbelaie.R;

import java.util.List;

public class ListViewTentorAdapter extends ArrayAdapter {
    private List<TentorItem> items;
    private Context context;

    public ListViewTentorAdapter(List<TentorItem> playerItemList, Context context) {
        super(context, R.layout.list_item_tentor_card, playerItemList);
        this.items = playerItemList;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item_tentor_card, null, true);
        TextView nama = listViewItem.findViewById(R.id.namalist);
        TextView jenjang = listViewItem.findViewById(R.id.jenjanglist);
        TextView kisaran = listViewItem.findViewById(R.id.kisaranlist);
        TextView kota = listViewItem.findViewById(R.id.kotalist);
        TentorItem item = items.get(position);
        nama.setText(item.getNama());
        jenjang.setText(item.getJenjang());
        kisaran.setText(item.getKisaran());
        kota.setText(item.getKota());
        return listViewItem;
    }
}
