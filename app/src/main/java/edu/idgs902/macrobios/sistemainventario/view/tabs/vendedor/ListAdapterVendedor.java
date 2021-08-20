package edu.idgs902.macrobios.sistemainventario.view.tabs.vendedor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.view.tabs.VendedorFragment;

public class ListAdapterVendedor extends RecyclerView.Adapter<ListAdapterVendedor.ViewHolder> {

    private List<ListElementVendedor> mData;
    private LayoutInflater mInflater;
    private Context context;
    private ListAdapterVendedor.OnVendedorListener mOnVendedorListener;

    public ListAdapterVendedor(List<ListElementVendedor> itemList, Context context, OnVendedorListener onVendedorListener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.mOnVendedorListener = onVendedorListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapterVendedor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element_vendedor, null);
        return new ListAdapterVendedor.ViewHolder(view, mOnVendedorListener);
    }

    @Override
    public void onBindViewHolder(final ListAdapterVendedor.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElementVendedor> items) { mData=items; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icoImageView;
        TextView comision, nombre, telefono, email;
        OnVendedorListener onVendedorListener;

        public ViewHolder(View itemView, OnVendedorListener onVendedorListener) {
            super(itemView);
            icoImageView = itemView.findViewById(R.id.iconImageViewV);
            nombre = itemView.findViewById(R.id.nombreViewV);
            comision = itemView.findViewById(R.id.comisionViewV);
            telefono = itemView.findViewById(R.id.telefonoViewV);
            email = itemView.findViewById(R.id.emailViewV);

            this.onVendedorListener = onVendedorListener;

            itemView.setOnClickListener(this);
        }

        void bindData(final ListElementVendedor item) {
            nombre.setText(item.getNombre());
            comision.setText(item.getComision());
        }

        @Override
        public void onClick(View view) {
            onVendedorListener.onVendedorClick(getBindingAdapterPosition());
        }
    }

    public interface OnVendedorListener{
        void onVendedorClick(int position);
    }
}
