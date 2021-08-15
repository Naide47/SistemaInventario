package edu.idgs902.macrobios.sistemainventario;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<LiatElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnClienteListener mOnClienteListener;

    public ListAdapter(List<LiatElement> itemList, Context context, OnClienteListener onClienteListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mOnClienteListener = onClienteListener;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view, mOnClienteListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<LiatElement> items) { mData=items; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iconImage;
        TextView nombre, ciudad, saldo;
        OnClienteListener onClienteListener;

        ViewHolder(View itemView, OnClienteListener onClienteListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.nombreView);
            ciudad = itemView.findViewById(R.id.ciudadView);
            saldo = itemView.findViewById(R.id.saldotView);
            this.onClienteListener = onClienteListener;

            itemView.setOnClickListener(this);
        }

        void bindData(final LiatElement item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombre());
            ciudad.setText(item.getCiudad());
            saldo.setText(item.getSaldo());
        }

        @Override
        public void onClick(View v) {
            onClienteListener.onClienteClick(getAdapterPosition());
        }
    }

    public interface OnClienteListener{
        void onClienteClick(int position);
    }

}
