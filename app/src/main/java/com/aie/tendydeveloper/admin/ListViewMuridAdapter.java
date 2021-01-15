package com.aie.tendydeveloper.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aie.tendydeveloper.R;

import java.util.List;

public class ListViewMuridAdapter extends ArrayAdapter {
    private List<MuridlItem> items;
    private Context context;

    public ListViewMuridAdapter(List<MuridlItem> playerItemList, Context context) {
        super(context, R.layout.list_item_murid_card, playerItemList);
        this.items = playerItemList;
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item_murid_card, null, true);
        TextView nama = listViewItem.findViewById(R.id.namalist);
        TextView jenjang = listViewItem.findViewById(R.id.jenjanglist);
        TextView mapel = listViewItem.findViewById(R.id.mapellist);
        TextView jam = listViewItem.findViewById(R.id.jamlist);
        TextView status = listViewItem.findViewById(R.id.statuslist);
        MuridlItem item = items.get(position);
        nama.setText(item.getNama());
        jenjang.setText(item.getJenjang());
        mapel.setText(item.getMapel());
        jam.setText(item.getHarijam());
        status.setText(item.getStatus());
        return listViewItem;
    }
}
