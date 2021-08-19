package edu.idgs902.macrobios.sistemainventario.controller.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.model.DetalleCompra;

public class DetalleCompraAdapter extends RecyclerView.Adapter<DetalleCompraAdapter.ViewHolderData> {

    Context context;
    List<DetalleCompra> detallesCompra;
    AccionesLista accionesLista;

    public DetalleCompraAdapter(Context context, List<DetalleCompra> detallesCompra, AccionesLista accionesLista) {
        this.context = context;
        this.detallesCompra = detallesCompra;
        this.accionesLista = accionesLista;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_detalle, parent, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleCompraAdapter.ViewHolderData holder, int position) {
        DetalleCompra detalleCompra = detallesCompra.get(position);
        holder.bindData(detallesCompra.get(position));
        holder.list_cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String valor = s.toString();
                if (valor.equals("")) {
                    valor = "0";
                }
                detallesCompra.get(position).setCantidad_producto(Integer.parseInt(valor));
                holder.actualizarImporte(detalleCompra);
                accionesLista.calcularTotal(position, valor, 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.list_pcosto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String valor = s.toString();
                if (valor.equals("")) {
                    valor = "0";
                }
                detallesCompra.get(position).setPrecio_compra(Double.parseDouble(valor));
                holder.actualizarImporte(detalleCompra);
                accionesLista.calcularTotal(position, valor, 2);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.list_quitar.setOnClickListener(v -> accionesLista.eliminarElemento(position));
        holder.list_cantidad.setEnabled(detalleCompra.isEstado());
        holder.list_pcosto.setEnabled(detalleCompra.isEstado());
        holder.list_quitar.setEnabled(detalleCompra.isEstado());
    }

    @Override
    public int getItemCount() {
        return detallesCompra.size();
    }

    public void actualizarLista(List<DetalleCompra> nuevaLista) {
        detallesCompra = nuevaLista;
        notifyDataSetChanged();
    }

    public List<DetalleCompra> getLista() {
        return detallesCompra;
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        //        TextView item_comp_clave, item_comp_desc, item_comp_linea, item_comp_importe;
//        EditText item_comp_cantidad, item_comp_pcosto;
        TextView list_clave, list_desc, list_linea, list_importe;
        EditText list_cantidad, list_pcosto;
        ImageButton list_quitar;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            list_clave = itemView.findViewById(R.id.list_detalle_clave);
            list_desc = itemView.findViewById(R.id.list_detalle_desc);
            list_linea = itemView.findViewById(R.id.list_detalle_linea);
            list_importe = itemView.findViewById(R.id.list_detalle_importe);
            list_cantidad = itemView.findViewById(R.id.list_detalle_cantidad);
            list_pcosto = itemView.findViewById(R.id.list_detalle_precio);
            list_quitar = itemView.findViewById(R.id.list_detalle_quitar);
        }

        public void bindData(DetalleCompra detalleCompra) {
            this.list_clave.setText(detalleCompra.getProducto().getNuProducto());
            this.list_desc.setText(detalleCompra.getProducto().getNombre());
            this.list_linea.setText(detalleCompra.getProducto().getLinea());
            this.list_cantidad.setText(String.valueOf(detalleCompra.getCantidad_producto()));
            this.list_pcosto.setText(String.valueOf(detalleCompra.getPrecio_compra()));

        }

        public void actualizarImporte(DetalleCompra detallesCompra) {
            double importe = detallesCompra.getCantidad_producto() * detallesCompra.getPrecio_compra();
            this.list_importe.setText(String.valueOf(importe));
        }
    }

    public interface AccionesLista {
        void calcularTotal(int position, String charSeq, int tipo);

        void eliminarElemento(int position);
    }
}
