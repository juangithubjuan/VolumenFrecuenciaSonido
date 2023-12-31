package com.example.volumenfrecuenciasonido;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int sonido1Id = 0;
    private int sonido2Id = 0;
    private SoundPool sonidoPool;
    private TextView texto = null;
    private SeekBar barra1;
    private SeekBar barra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.textview1);
        barra1 = findViewById(R.id.seekBar1);
        barra2 = findViewById(R.id.seekBar2);
        Button botonSonido1 = findViewById(R.id.botonSonido1);
        Button botonSonido2 = findViewById(R.id.botonSonido2);

        AudioAttributes audioAttributes	= new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        builder.setAudioAttributes(audioAttributes);
        sonidoPool = builder.build();
        texto.setText("Sonido Build");
        sonido1Id = sonidoPool.load(this, R.raw.ambient_occlusion, 1);
        sonido2Id = sonidoPool.load(this, R.raw.music, 1);
        texto.append("\nSonido Load");
        // Por aquí no funciona
        if (sonido1Id != 0) {
            sonidoPool.play(sonido1Id, 0.8f, 0.8f, 0, 0, 1);
            texto.append("\nSonido1 Play");
        } else {
            texto.append("\nSonido1 No Play");
        }
        if (sonido2Id != 0) {
            sonidoPool.play(sonido2Id, 1, 1, 0, 0, 1);
            texto.append("\nSonido2 Play");
        } else {
            texto.append("\nSonido2 No Play");
        }
        botonSonido1.setOnClickListener(view -> {
            sonidoPool.play(sonido1Id, 1, 1, 0, 0, 1);
        });
        botonSonido2.setOnClickListener(view -> {
            sonidoPool.play(sonido2Id, 1, 1, 0, 0, 1);
        });
        barra1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                float volumen = (float) progressChangedValue / 100;
                sonidoPool.play(sonido1Id, volumen, volumen, 0, 0, 1);
                texto.append("\nVolumen1: " + progressChangedValue);
            }
        });
        barra2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                float volumen = (float) progressChangedValue / 100;
                sonidoPool.play(sonido2Id, volumen, volumen, 0, 0, 1);
                texto.append("\nVolumen2: " + progressChangedValue);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sonidoPool != null) sonidoPool.release();
        sonidoPool = null;
    }
}