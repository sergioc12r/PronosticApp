package sergioapps.pronosticapp.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergioapps.pronosticapp.R;

/**
 * Created by drago on 15/02/2018.
 */

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {



    ArrayList<Atributos> listaCompleta;

    public AdapterDatos(ArrayList<Atributos> listaCompleta) {
        this.listaCompleta = listaCompleta;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdays,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.dia.setText(listaCompleta.get(position).getDia());
        holder.temperatura.setText(listaCompleta.get(position).getTemp());
        holder.logo.setImageResource(listaCompleta.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return listaCompleta.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView dia,temperatura;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.imDay);
            dia = (TextView)itemView.findViewById(R.id.txtday);
            temperatura = (TextView)itemView.findViewById(R.id.txtTemp);
        }
    }
}
