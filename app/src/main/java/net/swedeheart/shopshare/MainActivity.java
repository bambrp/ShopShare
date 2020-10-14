package net.swedeheart.shopshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleNumber(View view) {
        //TODO Prevent leading zero, more than one decimal point, more than two decimal places
        TextView ic = findViewById(R.id.itemCost);
        String currentTotal = ic.getText().toString();
        ic.setText((currentTotal.equals("0.00") ? "" : currentTotal) + ((Button) view).getText().toString());
    }

    public void addFull(View view) {
        add(false);
    }

    public void addHalf(View view) {
        add(true);
    }

    private void add(boolean half) {
        TextView rt = findViewById(R.id.runningTotal);
        TextView ic = findViewById(R.id.itemCost);
        BigDecimal rtNum = new BigDecimal(rt.getText().toString());
        BigDecimal icNum = new BigDecimal(ic.getText().toString());
        rtNum = rtNum.add(half ? icNum.divide(new BigDecimal(2)) : icNum);
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumFractionDigits(2);
        rt.setText(nf.format(rtNum));
        ic.setText("0.00");
    }

    public void deleteChar(View view) {
        TextView ic = findViewById(R.id.itemCost);
        CharSequence icText = ic.getText();
        if (ic.length() > 0) {
            ic.setText(icText.subSequence(0, icText.length() - 1));
        }
    }
}