package com.android.e_garden.viewModels;


        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.widget.Toolbar;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import com.android.e_garden.R;
        import com.google.firebase.auth.FirebaseAuth;

public class TesteListView extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Cebolinha", "Hortelã", "Cebolinha", "Hortelã", "Cebolinha", "Hortelã"};
    String mDescription[] = {"Tempero", "Chá", "Tempero ", "Chá", "Tempero", "Chá"};
    String mRegation[] = {"Regar", "Regado", "Regar ", "Regar", "Regar", "Regar"};
    int images[] = {R.drawable.cebolete, R.drawable.cebolete, R.drawable.cebolete, R.drawable.cebolete, R.drawable.cebolete, R.drawable.cebolete};
    // so our images and other things are set in array

    // now paste some images in drawable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        Toolbar toolbar = findViewById(R.id.toolbar_TestList); //Criando uma variável toolbar, que contém o id da toolbar do activity main
        setSupportActionBar(toolbar); //habilita a toolbar
        setTitle("Meu Jardim"); //Dá o título para a toolbar de Meu Jardim
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Habilita a tecla de return

        listView = findViewById(R.id.listview);
        // now create an adapter class

        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, mRegation, images);
        listView.setAdapter(adapter);
        // there is my mistake...
        // now again check this..

        // now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    startActivity(new Intent(TesteListView.this, Details.class));
                }

            }
        });
        // so item click is done now check list view
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        String rRegation[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], String regation[], int imgs[]) {
            super(c, R.layout.item_list, R.id.tvplantName, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rRegation = regation;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.item_list, parent, false);
            ImageView images = row.findViewById(R.id.ivPlant);
            TextView myTitle = row.findViewById(R.id.tvplantName);
            TextView myDescription = row.findViewById(R.id.tvplantCategory);
            TextView myRegation = row.findViewById(R.id.tvplantStatus);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);
            myRegation.setText(rRegation[position]);




            return row;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Você está deixando seu Jardim")
                .setMessage("Tem certeza que você quer sair?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    finish();
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Você está deixando seu Jardim")
                    .setMessage("Tem certeza que você quer sair?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        mAuth.signOut();
                        this.finish();
                    })
                    .setNegativeButton("Não", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) // Habilita o menu e mostra ele na toolbar
    {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        return true;

    }
}
