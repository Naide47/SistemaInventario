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
    public int getItemCount() { return mData.size(); }

    @Override
    public ListAdapterProveedor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element_proveedor, null);
        return new ListAdapterProveedor.ViewHolder(view, mOnProveedorListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapterProveedor.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElemetProveedor> items) { mData=items; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView iconImage;
        TextView nombre, ciudad, saldo;
        OnProveedorListener onProveedorListener;

        ViewHolder(View itemView, OnProveedorListener onProveedorListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewP);
            nombre = itemView.findViewById(R.id.nombreViewP);
            ciudad = itemView.findViewById(R.id.ciudadViewP);
            saldo = itemView.findViewById(R.id.saldotViewP);
            this.onProveedorListener = onProveedorListener;

            itemView.setOnClickListener(this);
        }

        void bindData(final ListElemetProveedor item) {
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombre());
            ciudad.setText(item.getCiudad());
            saldo.setText(item.getSaldo());
        }

        @Override
        public void onClick(View v) {
            onProveedorListener.onProveedorClick(getAdapterPosition());
        }
    }

    public interface OnProveedorListener{
        void onProveedorClick(int position);
    }

}
