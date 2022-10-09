package com.example.listview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }

        public int getIntents(){
            return this.intents;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // Nombres
    ArrayList<String> names = new ArrayList<>();
    // Apellidos
    ArrayList<String> surnames = new ArrayList<>();

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo") );
        records.add( new Record(12,"Pepe") );
        records.add( new Record(42,"Laura") );

        names.add(new String("Nerea"));
        names.add("Saray");
        names.add("Jessica");
        names.add("Álvaro");
        names.add("Xavi");
        names.add("Aitor");

        surnames.add("Lopez");
        surnames.add("García");
        surnames.add("Rosa");
        surnames.add("De Miguel");
        surnames.add("Fernandez");
        surnames.add("Mena");

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                return convertView;
            }

        };

        System.out.println(records.toString());

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<5;i++) {

                    int indexName = (int) (Math.random() * names.size());
                    int indexSurname = (int) (Math.random() * surnames.size());
                    int tries = (int) (Math.random() * 50);

                    records.add(new Record(tries, names.get(indexName) + " " + surnames.get(indexSurname)));
                }
                Collections.sort(records, new Comparator<Record>() {
                    @Override
                    public int compare(Record r1, Record r2) {
                        return new Integer(r1.getIntents()).compareTo(new Integer(r2.getIntents()));
                    }
                });
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }
}