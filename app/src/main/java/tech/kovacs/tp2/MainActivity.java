package tech.kovacs.tp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button envoyer = null;
    Button reset = null;
    EditText taille = null;
    EditText poids = null;
    CheckBox commentaire = null;
    RadioGroup group = null;
    TextView result = null;
    private String texteInit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texteInit = getString(R.string.buttonClick);
// On récupère toutes les vues dont on a besoin
        envoyer = (Button)findViewById(R.id.calcul);
        reset = (Button)findViewById(R.id.reset);
        taille = (EditText)findViewById(R.id.taille);
        poids = (EditText)findViewById(R.id.poids);
        commentaire = (CheckBox)findViewById(R.id.commentaire);
        group = (RadioGroup)findViewById(R.id.group);
        result = (TextView)findViewById(R.id.result);


        // On attribue un listener adapté aux vues qui en ont besoin
        envoyer.setOnClickListener(envoyerListener);
        reset.setOnClickListener(resetListener);
        commentaire.setOnClickListener(checkedListener);
        //taille.addTextChangedListener(textWatcher);
        //poids.addTextChangedListener(textWatcher);
        taille.setOnKeyListener(modifListener);
        poids.setOnKeyListener(modifListener);

    }






// exercice 1
    private String interpreteIMC(float imc){
        String msg="";
        if(imc<16.5) msg += getString(R.string.Xlow);
        else if (imc>=16.5 && imc<18.5) msg += getString(R.string.low);
        else if (imc>=18.5 && imc<25) msg += getString(R.string.normal);
        else if (imc>=25 && imc<30) msg += getString(R.string.large);
        else if (imc>=30 && imc<35) msg += getString(R.string.fat);
        else if (imc>=35 && imc<40) msg += getString(R.string.fatter);
        else if (imc>=40 ) msg += getString(R.string.enormous);

     return msg;
    }


    private View.OnClickListener envoyerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = taille.getText().toString();
            // On récupère le poids
            String p = poids.getText().toString();
            float tValue = Float.valueOf(t);

            // Puis on vérifie que la taille est cohérente
            if(tValue <= 0)
                Toast.makeText(MainActivity.this, R.string.positiveHighness, Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if(pValue <= 0)
                    Toast.makeText(MainActivity.this, R.string.positiveWeight, Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (group.getCheckedRadioButtonId() == R.id.radio_centimetre) tValue = tValue / 100;
                    float imc = pValue / (tValue * tValue);
                    String resultat=getString(R.string.yourIMCis) + imc+" . ";
                    if(commentaire.isChecked()) resultat += interpreteIMC(imc);
                    result.setText(resultat);
                }
            }
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            poids.getText().clear();
            taille.getText().clear();
            result.setText(texteInit);
        }
    };

    private View.OnClickListener checkedListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };




            private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}





    };

    // Se lance à chaque fois qu'on appuie sur une touche en étant sur un EditText

    //  on pourrait detecter si un point est écrit et dans ce cas on bascule en mode decimale
    private View.OnKeyListener modifListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // On remet le texte à sa valeur par défaut
            result.setText(texteInit);
            return false;
        }
    };







}
