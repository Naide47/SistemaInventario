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
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, parent, false);
        return new ListAdapter.ViewHolder(view, mOnClienteListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<LiatElement> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        ImageView iconImage;
//        TextView nombre, ciudad, saldo;
        ImageView list_icono;
        TextView list_nombre; //Para el nombre;
        TextView list_ciudad, list_saldo;

        OnClienteListener onClienteListener;

        ViewHolder(View itemView, OnClienteListener onClienteListener) {
            super(itemView);
            list_icono = itemView.findViewById(R.id.list_icono);
            list_nombre = itemView.findViewById(R.id.list_titulo1);
            list_ciudad = itemView.findViewById(R.id.list_subtitulo);
            list_saldo = itemView.findViewById(R.id.list_adicional);
            this.onClienteListener = onClienteListener;

            itemView.setOnClickListener(this);
        }

        void bindData(final LiatElement item) {
//            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
//            nombre.setText(item.getNombre());
//            ciudad.setText(item.getCiudad());
//            saldo.setText(item.getSaldo());
            list_icono.setImageResource(R.drawable.ic_cliente);
            list_icono.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            list_nombre.setText(item.getNombre());
            list_ciudad.setText(item.getCiudad());
            list_saldo.setText(item.getSaldo());
        }

        @Override
        public void onClick(View v) {
//            onClienteListener.onClienteClick(getAdapterPosition());
            onClienteListener.onClienteClick(getBindingAdapterPosition());
        }
    }

    public interface OnClienteListener {
        void onClienteClick(int position);
    }

}
