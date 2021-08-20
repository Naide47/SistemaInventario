package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Vendedor;

public class ReporteVendedoresAdapter extends RecyclerView.Adapter<ReporteVendedoresAdapter.ViewHolderData> {

    List<Vendedor> vendedorList;

    public ReporteVendedoresAdapter(List<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_vendedor, parent, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteVendedoresAdapter.ViewHolderData holder, int position) {
        holder.bindData((position + 1), vendedorList.get(position));
    }

    @Override
    public int getItemCount() {
        return vendedorList.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        private TextView list_vend_index, list_vend_nombre, list_vend_calle,
                list_vend_colonia, list_vend_telefono, list_vend_email,
                list_vend_comisiones;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            list_vend_index = itemView.findViewById(R.id.list_vend_index);
            list_vend_nombre = itemView.findViewById(R.id.list_vend_nombre);
            list_vend_calle = itemView.findViewById(R.id.list_vend_calle);
            list_vend_colonia = itemView.findViewById(R.id.list_vend_colonia);
            list_vend_telefono = itemView.findViewById(R.id.list_vend_telefono);
            list_vend_email = itemView.findViewById(R.id.list_vend_email);
            list_vend_comisiones = itemView.findViewById(R.id.list_vend_comisiones);
        }

        public void bindData(int index, Vendedor vendedor) {
            list_vend_index.setText(String.valueOf(index));
            list_vend_nombre.setText(vendedor.getNombre());
            list_vend_calle.setText(vendedor.getCalle());
            list_vend_colonia.setText(vendedor.getColonia());
            list_vend_telefono.setText(vendedor.getTelefono());
            list_vend_email.setText(vendedor.getEmail());
            list_vend_comisiones.setText(String.valueOf(vendedor.getComisiones()));
        }
    }
}
