package edu.idgs902.macrobios.sistemainventario.view.tabs.producto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Producto;

public class ReporteProductosAdapter extends RecyclerView.Adapter<ReporteProductosAdapter.ViewHolderData> {

    List<Producto> productos;

    public ReporteProductosAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_producto, parent, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteProductosAdapter.ViewHolderData holder, int position) {
        holder.bindData((position + 1), productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView list_prod_index, list_prod_numero, list_prod_nombre, list_prod_linea,
                list_prod_existencia, list_prod_pcosto, list_prod_promedio,
                list_prod_pmenor, list_prod_pmayor;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            list_prod_index = itemView.findViewById(R.id.list_prod_index);
            list_prod_numero = itemView.findViewById(R.id.list_prod_numero);
            list_prod_nombre = itemView.findViewById(R.id.list_prod_nombre);
            list_prod_linea = itemView.findViewById(R.id.list_prod_linea);
            list_prod_existencia = itemView.findViewById(R.id.list_prod_existencia);
            list_prod_pcosto = itemView.findViewById(R.id.list_prod_pcosto);
            list_prod_promedio = itemView.findViewById(R.id.list_prod_promedio);
            list_prod_pmenor = itemView.findViewById(R.id.list_prod_pmenor);
            list_prod_pmayor = itemView.findViewById(R.id.list_prod_pmayor);
        }

        public void bindData(int index, Producto producto) {
            list_prod_index.setText(String.valueOf(index));
            list_prod_numero.setText(producto.getNuProducto());
            list_prod_nombre.setText(producto.getNombre());
            list_prod_linea.setText(producto.getLinea());
            list_prod_existencia.setText(String.valueOf(producto.getExistencia()));
            list_prod_pcosto.setText(String.valueOf(producto.getP_costo()));
            list_prod_promedio.setText(String.valueOf(producto.getP_promedio()));
            list_prod_pmenor.setText(String.valueOf(producto.getP_venta_menor()));
            list_prod_pmayor.setText(String.valueOf(producto.getP_venta_mayor()));
        }
    }
}
