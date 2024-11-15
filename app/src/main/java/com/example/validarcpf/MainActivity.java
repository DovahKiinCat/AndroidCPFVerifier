package com.example.validarcpf;

import static android.text.TextUtils.split;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText cpfinput = findViewById(R.id.CPFinput);
        Button validar = findViewById(R.id.ValidarButton);
        TextView resultado = findViewById(R.id.textResultado);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = String.valueOf(cpfinput.getText());

                if (cpf.isEmpty()) {
                    resultado.setText("Por favor, digite um CPF válido.");
                    return;
                }

                String cpfregex = cpf.replaceAll("[^0-9]", "");
                String nums = cpfregex.substring(0, 9);

                if (DigitosIguais(cpfregex)) {
                    resultado.setText("CPF Inválido. Os dígitos não podem ser iguais!");
                    return;
                }

                char numsArray[] = nums.toCharArray();

                int[] multi = {10, 9, 8, 7, 6, 5, 4, 3, 2};
                int sf = 0;
                int[] result = new int[numsArray.length];

                for (int i = 0; i < multi.length; i++){
                    int n = Character.getNumericValue(numsArray[i]);
                    result[i] = n * multi[i];

                    sf += result[i];
                }

                sf = (sf * 10) % 11;

                if (sf == 10 && sf == 11) {
                    sf = 0;
                }

                ArrayList<Character> numList = new ArrayList<>();
                for (char c : numsArray) {
                    numList.add(c);
                }

                numList.add(Character.forDigit(sf, 10));

                int[] multi2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
                int sf2 = 0;
                int[] result2 = new int[numList.toArray().length];

                for (int i = 0; i < multi2.length; i++) {
                    int n = Character.getNumericValue(numList.get(i));
                    result2[i] = n * multi2[i];
                    sf2 += result2[i];
                }

                sf2 = (sf2 * 10) % 11;

                if (sf2 == 10 && sf2 == 11){
                    sf2 = 0;
                }

                String verif = cpfregex.substring(9, 11);
                String somadig = sf + "" + sf2;

                if (verif.equals(somadig)){
                    resultado.setText("CPF Válido!");
                } else{
                    resultado.setText("CPF Inválido!");
                }
            }

            public boolean DigitosIguais(String input) {
                char primeiroDigito = input.charAt(0);

                for (int i = 1; i < input.length(); i++) {
                    if (input.charAt(i) != primeiroDigito) {
                        return false;
                    }
                }
                return true;
            }
        });
    }
}