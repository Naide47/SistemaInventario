package edu.idgs902.macrobios.sistemainventario.controller.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import edu.idgs902.macrobios.sistemainventario.model.DetalleVenta;

public class DetalleVentaAdapter extends RecyclerView.Adapter<DetalleVentaAdapter.ViewHolderData> {

    Context context;
    List<DetalleVenta> detallesVenta;
    AccionesLista accionesLista;

    public DetalleVentaAdapter(Context context, List<DetalleVenta> detallesVenta, AccionesLista accionesLista) {
        this.context = context;
        this.detallesVenta = detallesVenta;
        this.accionesLista = accionesLista;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_detalle, parent, false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleVentaAdapter.ViewHolderData holder, int position) {
        DetalleVenta detalleVentaAux = detallesVenta.get(position);
        holder.colocarDatos(detalleVentaAux);
        holder.list_pventa.setEnabled(false);
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
                detalleVentaAux.setCantidad_producto(Integer.parseInt(valor));
                if(detalleVentaAux.getCantidad_producto() < 30){
                    holder.list_pventa.setText(String.valueOf(detalleVentaAux.getProducto().getP_venta_menor()));
                } else {
                    holder.list_pventa.setText(String.valueOf(detalleVentaAux.getProducto().getP_venta_mayor()));
                }
                holder.actualizarImporte(detalleVentaAux);
                accionesLista.calcularTotal(position, valor, 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.list_quitar.setOnClickListener(v -> accionesLista.eliminarElemento(position));
        holder.list_cantidad.setEnabled(detalleVentaAux.isEstado());
        holder.list_quitar.setEnabled(detalleVentaAux.isEstado());
    }

    @Override
    public int getItemCount() {
        return detallesVenta.size();
    }

    public void actualizarList(List<DetalleVenta> nuevaLista) {
        detallesVenta = nuevaLista;
        notifyDataSetChanged();
    }

    public List<DetalleVenta> getLista() {
        return detallesVenta;
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView list_clave, list_desc, list_linea, list_importe ;
        EditText list_cantidad, list_pventa;
        ImageButton list_quitar;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
            list_clave = itemView.findViewById(R.id.list_detalle_clave);
            list_desc = itemView.findViewById(R.id.list_detalle_desc);
            list_linea = itemView.findViewById(R.id.list_detalle_linea);
            list_importe = itemView.findViewById(R.id.list_detalle_importe);
            list_cantidad = itemView.findViewById(R.id.list_detalle_cantidad);
            list_pventa = itemView.findViewById(R.id.list_detalle_precio);
            list_quitar = itemView.findViewById(R.id.list_detalle_quitar);
        }

        public void colocarDatos(DetalleVenta detalleVenta) {
            this.list_clave.setText(detalleVenta.getProducto().getNuProducto());
            this.list_desc.setText(detalleVenta.getProducto().getNombre());
            this.list_linea.setText(detalleVenta.getProducto().getLinea());
            this.list_cantidad.setText(String.valueOf(detalleVenta.getCantidad_producto()));
            this.list_pventa.setText(String.valueOf(detalleVenta.getPrecio_venta()));
        }

        public void actualizarImporte(DetalleVenta detalleVenta) {
            double importe = detalleVenta.getCantidad_producto() * detalleVenta.getPrecio_venta();
            this.list_importe.setText(String.valueOf(importe));
        }
    }

    public interface AccionesLista {
        void calcularTotal(int position, String numero, int tipo);

        void eliminarElemento(int position);
    }
}
