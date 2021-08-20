package edu.idgs902.macrobios.sistemainventario.view.tabs.externos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.Externo;

public class ReporteExternosAdapter extends RecyclerView.Adapter<ReporteExternosAdapter.ViewHolderData> {

    private List<Externo> externos;

    public ReporteExternosAdapter(List<Externo> externos) {
        this.externos = externos;
    }

    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_externos, parent, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteExternosAdapter.ViewHolderData holder, int position) {
        holder.bindData((position + 1), externos.get(position));
    }

    @Override
    public int getItemCount() {
        return externos.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        private TextView list_ext_index, list_ext_numero, list_ext_nombre, list_ext_calle,
                list_ext_colonia, list_ext_ciudad, list_ext_rfc,
                list_ext_telefono, list_ext_email, list_ext_saldo;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            list_ext_index = itemView.findViewById(R.id.list_ext_index);
            list_ext_numero = itemView.findViewById(R.id.list_ext_numero);
            list_ext_nombre = itemView.findViewById(R.id.list_ext_nombre);
            list_ext_calle = itemView.findViewById(R.id.list_ext_calle);
            list_ext_colonia = itemView.findViewById(R.id.list_ext_colonia);
            list_ext_ciudad = itemView.findViewById(R.id.list_ext_ciudad);
            list_ext_rfc = itemView.findViewById(R.id.list_ext_rfc);
            list_ext_telefono = itemView.findViewById(R.id.list_ext_telefono);
            list_ext_email = itemView.findViewById(R.id.list_ext_email);
            list_ext_saldo = itemView.findViewById(R.id.list_ext_saldo);
        }

        public void bindData(int position, Externo externo) {
            list_ext_index.setText(String.valueOf(position));
            list_ext_numero.setText(String.valueOf(externo.getNoExterno()));
            list_ext_nombre.setText(externo.getNombre());
            list_ext_calle.setText(externo.getCalle());
            list_ext_colonia.setText(externo.getColonia());
            list_ext_ciudad.setText(externo.getCiudad());
            list_ext_rfc.setText(externo.getRfc());
            list_ext_telefono.setText(externo.getTelefono());
            list_ext_email.setText(externo.getEmail());
            DecimalFormat df = new DecimalFormat("#0.00");
            String cadena = "$" + df.format(externo.getSaldo());
            list_ext_saldo.setText(cadena);
        }
    }
}
