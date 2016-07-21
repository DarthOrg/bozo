package com.darthorg.bozo.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.view.ListaDePartidas;

import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class PartidasListaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Partida> mPartidasLista;
    Button btnDeletar;

    //Construtor


    public PartidasListaAdapter(Context mContext, List<Partida> mPartidasLista) {
        this.mContext = mContext;
        this.mPartidasLista = mPartidasLista;
    }

    @Override
    public int getCount() {
        return mPartidasLista.size();
    }

    @Override
    public Object getItem(int position) {
        return mPartidasLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final View v = View.inflate(mContext, R.layout.adapter_listview_partidas, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);




        //Botão de Excluir
        btnDeletar = (Button) v.findViewById(R.id.btnDeletarPartida);
        btnDeletar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Você tem certeza que deseja apagar \n( "+mPartidasLista.get(position).getNome()+" ) ?", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.YELLOW)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(mContext, mPartidasLista.get(position).getNome()+" - foi excluido da sua lista", Toast.LENGTH_SHORT).show();
                                PartidaDAO partidaDAO = new PartidaDAO(mContext);
                                partidaDAO.deletarPartida(mPartidasLista.get(position));
                                mPartidasLista.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .show();
            }
        });

        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(mPartidasLista.get(position).getNome());

        v.setTag(mPartidasLista.get(position).getIdPartida());

        return v;
    }

}
