package bzh.arthur.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String calculEnCours="";//cela correspond à la chaine de caractère du calcul en cours
    boolean last=false;//cette variable permet de savoir si le dernier ajout est un opérateur ou non
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** la méthode clean permet de vider le calcul en cours
     * cela permet de repartir à zero en cas de besoin
     *
     * @param v
     */
    public void clean(View v){
        calculEnCours="";
        TextView tx=findViewById(R.id.calCurr);
        tx.setText(calculEnCours);
        last=false;
    }

    /** la méthode résultat permet de déplacer le résultat du calcul
     * en cours à la partie calculEnCours l'objectif est d'offrir une
     * vue plus agréable pour calculer
     *
     * @param v
     */
    public void resultat(View v){
        TextView tx=findViewById(R.id.calCurr);
        TextView tx2=findViewById(R.id.resCurr);
        tx.setText(tx2.getText());
        calculEnCours=tx2.getText().toString();
        tx2.setText("");
        last=false;
    }

    /** méthode qui se déclenche lorsqu'on presse un bouton
     * chiffre ,elle modifie alors la partie calcul en cours
     * et met à jour la textview resultat courant
     *
     * @param v
     */
    public void add(View v){
        String valeur;
        switch ( v.getId() ) {
            case R.id.un :
                valeur="1";
                break;

            case R.id.deux :
                valeur="2";
                break;

            case R.id.trois :
                valeur="3";
                break;

            case R.id.quatre :
                valeur="4";
                break;

            case R.id.cinq:
                valeur="5";
                break;
            case R.id.six:
                valeur="6";
                break;
            case R.id.sept:
                valeur="7";
                break;
            case R.id.huit:
                valeur="8";
                break;
            case R.id.neuf:
                valeur="9";
                break;
            case R.id.zero:
                valeur="0";
                break;
            default: valeur="";
                break;
        }

        calculEnCours+=valeur;

        TextView tx=findViewById(R.id.calCurr);
        tx.setText(calculEnCours);
        mAjRes(v);
        last=false;
    }

    /** méthode se déclenchant lors de l'activation d'un bouton
     * opérateur ( + * / - ) elle ajoute l'opérateur au calcul actuel
     *
     * @param v
     */
    public void operateur(View v){
        if(last==false){
            String valeur;
            switch ( v.getId() ) {
                case R.id.plus:
                    valeur="+";
                    break;

                case R.id.moins :
                    valeur="-";
                    break;

                case R.id.fois :
                    valeur="*";
                    break;

                case R.id.div :
                    valeur="/";
                    break;

                case R.id.virg:
                    valeur=".";
                    break;
                default: valeur="";
                    break;
            }

            calculEnCours+=valeur;

            TextView tx=findViewById(R.id.calCurr);
            tx.setText(calculEnCours);
            last=true;
        }


    }

    /** méthode de mise à jour du résultat courant
     *
     * @param v
     */
    public void mAjRes(View v){
        TextView txRes=findViewById(R.id.resCurr);
        Log.d("aaa","okok");
        String res=parseCalculPlus(calculEnCours)+"";
        Log.d("aaa",res);
        txRes.setText(res);
    }

    private float parseCalcul(String StrCalcul){
        float resultat= 0;
        String[] mult=StrCalcul.split("\\*");
        if(mult.length>1){
            resultat=parseCalcul(mult[0]);
            for(int i=1;i<mult.length;i++){
                resultat*=Float.parseFloat(mult[i]);
            }
            return resultat;
        }else{
            return Float.parseFloat(StrCalcul);
        }
    }
    private float parseCalculDiv(String StrCalcul){
        float resultat= 0;
        String[] div=StrCalcul.split("/");
        if(div.length>1){
            resultat=parseCalcul(div[0]);
            for(int i=1;i<div.length;i++){
                resultat/=parseCalcul(div[i]);
            }
            return resultat;
        }else{
            return parseCalcul(StrCalcul);
        }
    }

    private float parseCalculMoins(String StrCalcul){
        float resultat= 0;
        String[] moin=StrCalcul.split("\\-");
        if(moin.length>1){
            resultat=parseCalcul(moin[0]);
            for(int i=1;i<moin.length;i++){
                resultat-=parseCalculDiv(moin[i]);
            }
            return resultat;
        }else{
            return parseCalculDiv(StrCalcul);
        }
    }

    private float parseCalculPlus(String StrCalcul){
        float resultat= 0;
        String[] plus=StrCalcul.split("\\+");
        if(plus.length>1){
            resultat=parseCalcul(plus[0]);
            for(int i=1;i<plus.length;i++){
                resultat+=parseCalculMoins(plus[i]);
            }
            return resultat;
        }else{
            return parseCalculMoins(StrCalcul);
        }
    }
}