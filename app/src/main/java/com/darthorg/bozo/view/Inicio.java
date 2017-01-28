package com.darthorg.bozo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListAdapter;
import com.darthorg.bozo.adapter.UltimaPartidaAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;

import java.util.List;

import static android.view.View.VISIBLE;

public class Inicio extends AppCompatActivity {

    public static final String PREF_CONFIG = "CONFIG_PONTUACAO";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferencias;

    private Toolbar toolbar;

    private ListView listViewPartidas;
    private PartidasListAdapter partidasListAdapter;
    private List<Partida> partidaList;


    private ListView listViewUltimaPartida;
    private UltimaPartidaAdapter ultimaPartidaAdapter;
    private Partida ultimaPartida;


    FloatingActionButton fabCompartilhar, fabDefinicoes, novoMarcador, fabMSalvos, fabInstrucoes;
    CardView cardMSalvos;
    Button cardNovoMarcador;
    LinearLayout ultimo_salvo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        changeStatusBarColor();

        //Toobar do include marcadores salvos
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_marcador));
        setSupportActionBar(toolbar);


        //Botões FAB
        fabCompartilhar = (FloatingActionButton) findViewById(R.id.fabCompartilhar);
        fabDefinicoes = (FloatingActionButton) findViewById(R.id.fabDefinicoes);
        fabMSalvos = (FloatingActionButton) findViewById(R.id.fabMSalvos);
        fabInstrucoes = (FloatingActionButton) findViewById(R.id.fabInstrucoes);

        //PopUp marcadores salvos
        novoMarcador = (FloatingActionButton) findViewById(R.id.novo_marcador);
        cardMSalvos = (CardView) findViewById(R.id.CardMSalvos);
        cardNovoMarcador = (Button) findViewById(R.id.cardBtnNovoMarcador);
        ultimo_salvo = (LinearLayout) findViewById(R.id.ultimo_salvo);

        novoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                startActivity(intent);

            }
        });

        cardNovoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                startActivity(intent);
            }
        });

        fabDefinicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Definicoes.class);
                startActivity(intent);
            }
        });


        fabMSalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardMSalvos.setVisibility(VISIBLE);
            }
        });

        fabCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Compartilhar app
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.textoCompartilharApp) + " " + getString(R.string.url_googleplay));
                startActivity(Intent.createChooser(share, getString(R.string.compartilharApp)));
            }
        });
        fabInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Instrucoes.class);
                startActivity(intent);
            }
        });


        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);
        listViewUltimaPartida = (ListView) findViewById(R.id.list_view_ultima_partida);

        //Aparecer imagem quando a lista estiver vazia
        listViewPartidas.setEmptyView(findViewById(R.id.listVazio));

        PartidaController partidaController = new PartidaController(Inicio.this);
        partidaList = partidaController.buscarPartidas();


        partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, this);

        listViewPartidas.setAdapter(partidasListAdapter);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);
            }
        });


        trazerUltimaPartida();

    }

    @Override
    public void onBackPressed() {

        trazerUltimaPartida();
        if (cardMSalvos.getVisibility() == VISIBLE) {
            cardMSalvos.setVisibility(View.GONE);
        } else {
            finish();
        }

    }

    public void atualizarLista() {
        PartidaController partidaController = new PartidaController(Inicio.this);
        partidaList = partidaController.buscarPartidas();
        partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, this);
        listViewPartidas.setAdapter(partidasListAdapter);

    }

    public void trazerUltimaPartida() {

        PartidaController partidaController = new PartidaController(Inicio.this);
        ultimaPartida = partidaController.buscarUltimaPartida();
        if (ultimaPartida != null) {
            ultimo_salvo.setVisibility(VISIBLE);

            ultimaPartidaAdapter = new UltimaPartidaAdapter(getApplicationContext(), ultimaPartida, this);
            listViewUltimaPartida.setAdapter(ultimaPartidaAdapter);

            listViewUltimaPartida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                    intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                    intent.putExtra("partidaNova", false);
                    startActivity(intent);
                }
            });
        } else {
            ultimo_salvo.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizarLista();

        trazerUltimaPartida();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDark));
        }
    }
}
