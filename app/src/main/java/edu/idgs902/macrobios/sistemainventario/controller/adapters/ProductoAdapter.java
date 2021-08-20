package edu.idgs902.macrobios.sistemainventario.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolderData> {

    private List<Producto> productos;
    private OnItemListener onItemListener;

    public ProductoAdapter(List<Producto> productos, OnItemListener onItemListener) {
        this.productos = productos;
        this.onItemListener = onItemListener;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element, parent, false);
        return new ViewHolderData(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(ProductoAdapter.ViewHolderData holder, int position) {
        holder.bindData(productos.get(position));
    }

    @Override
    public int getItemCount() {
        if (productos != null)
            return productos.size();
        else
            return 0;
    }

    public void actualizarList(List<Producto> nuevaLista) {
        productos = nuevaLista;
        notifyDataSetChanged();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView list_icono;
        TextView list_no, list_nombre, list_adicional;
        OnItemListener onItemListener;


        public ViewHolderData(View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.list_icono = itemView.findViewById(R.id.list_icono);
            this.list_no = itemView.findViewById(R.id.list_titulo2);
            this.list_nombre = itemView.findViewById(R.id.list_subtitulo);
            this.list_adicional = itemView.findViewById(R.id.list_adicional);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getBindingAdapterPosition());
        }

        public void bindData(Producto producto) {
            list_icono.setImageResource(R.drawable.ic_productos);
            list_no.setText(producto.getNuProducto());
            list_nombre.setText(producto.getNombre());
            list_adicional.setText(producto.getLinea());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
