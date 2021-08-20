package edu.idgs902.macrobios.sistemainventario.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Compra;

public class CompraAdapter extends RecyclerView.Adapter<CompraAdapter.ViewHolerData> {

    private List<Compra> compras;
    private OnItemListener onItemListener;

    public CompraAdapter(List<Compra> compras, OnItemListener onItemListener) {
        this.compras = compras;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolerData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        return new ViewHolerData(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraAdapter.ViewHolerData holder, int position) {
        holder.bindData(compras.get(position));
    }

    @Override
    public int getItemCount() {
        if (compras != null)
            return compras.size();
        else
            return 0;
    }

    public void actualizarLista(List<Compra> nuevaLista) {
        compras = nuevaLista;
        notifyDataSetChanged();
    }

    public class ViewHolerData extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView list_icono;
        TextView list_no, list_fecha, list_total;
        OnItemListener onItemListener;

        public ViewHolerData(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.list_icono = itemView.findViewById(R.id.list_icono);
            this.list_no = itemView.findViewById(R.id.list_titulo2);
            this.list_fecha = itemView.findViewById(R.id.list_subtitulo);
            this.list_total = itemView.findViewById(R.id.list_adicional);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        public void bindData(Compra compra) {
            this.list_icono.setImageResource(R.drawable.ic_compras);
            this.list_no.setText(String.valueOf(compra.getNoCompra()));
            this.list_fecha.setText(compra.getFecha());
            this.list_total.setText(String.valueOf(compra.getTotal_compra()));
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getBindingAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
