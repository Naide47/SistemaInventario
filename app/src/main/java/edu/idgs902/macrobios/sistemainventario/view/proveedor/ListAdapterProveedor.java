package edu.idgs902.macrobios.sistemainventario.view.proveedor;

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

import edu.idgs902.macrobios.sistemainventario.LiatElement;
import edu.idgs902.macrobios.sistemainventario.R;

public class ListAdapterProveedor extends RecyclerView.Adapter<ListAdapterProveedor.ViewHolder> {

    private List<ListElemetProveedor> mData;
    private LayoutInflater mInflater;
    private Context context;
    private ListAdapterProveedor.OnProveedorListener mOnProveedorListener;

    public ListAdapterProveedor(List<ListElemetProveedor> itemList, Context context, OnProveedorListener onProveedorListener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mOnProveedorListener = onProveedorListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapterProveedor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, parent, false);
        return new ListAdapterProveedor.ViewHolder(view, mOnProveedorListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapterProveedor.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElemetProveedor> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        ImageView iconImage;
//        TextView nombre, ciudad, saldo;
        ImageView list_icono;
        TextView list_nombre; //Para el nombre;
        TextView list_ciudad, list_saldo;
        OnProveedorListener onProveedorListener;

        ViewHolder(View itemView, OnProveedorListener onProveedorListener) {
            super(itemView);
            list_icono = itemView.findViewById(R.id.list_icono);
            list_nombre = itemView.findViewById(R.id.list_titulo1);
            list_ciudad = itemView.findViewById(R.id.list_subtitulo);
            list_saldo = itemView.findViewById(R.id.list_adicional);
            this.onProveedorListener = onProveedorListener;

            itemView.setOnClickListener(this);
        }

        void bindData(final ListElemetProveedor item) {
            list_icono.setImageResource(R.drawable.ic_proveedor);
            list_icono.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            list_nombre.setText(item.getNombre());
            list_ciudad.setText(item.getCiudad());
            list_saldo.setText(item.getSaldo());
        }

        @Override
        public void onClick(View v) {
            onProveedorListener.onProveedorClick(getAdapterPosition());
        }
    }

    public interface OnProveedorListener {
        void onProveedorClick(int position);
    }

}
