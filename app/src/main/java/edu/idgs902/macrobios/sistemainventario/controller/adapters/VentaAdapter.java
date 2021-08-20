package edu.idgs902.macrobios.sistemainventario.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.idgs902.macrobios.sistemainventario.R;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.model.Venta;

public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.ViewHolderData> {

    private List<Venta> ventas;
    private OnItemListener onItemListener;

    public VentaAdapter(List<Venta> ventas, OnItemListener onItemListener) {
        this.ventas = ventas;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        return new ViewHolderData(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaAdapter.ViewHolderData holder, int position) {
        holder.bindData(ventas.get(position));
    }

    @Override
    public int getItemCount() {
        if (ventas != null)
            return ventas.size();
        else
            return 0;
    }

    public void actualizarLista(List<Venta> nuevaLista) {
        ventas = nuevaLista;
        notifyDataSetChanged();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView list_icono;
        TextView list_no, list_fecha, list_total;
        OnItemListener onItemListener;

        public ViewHolderData(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.list_icono = itemView.findViewById(R.id.list_icono);
            this.list_no = itemView.findViewById(R.id.list_titulo2);
            this.list_fecha = itemView.findViewById(R.id.list_subtitulo);
            this.list_total = itemView.findViewById(R.id.list_adicional);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        public void bindData(Venta venta) {
            this.list_icono.setImageResource(R.drawable.ic_ventas);
            this.list_no.setText(String.valueOf(venta.getNoVenta()));
            this.list_fecha.setText(venta.getFecha());
            String total = "$" + String.valueOf(venta.getTotal_venta());
            this.list_total.setText(total);
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
