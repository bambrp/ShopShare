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
        TextView ic = findViewById(R.id.itemCost);
        String currentTotal = ic.getText().toString();
        String newChar = ((Button) view).getText().toString();
        if (newChar.equals(".")) {
            if (currentTotal.equals("") || currentTotal.equals("0.00")) {
                ic.setText("0.");
                return;
            }
            if (currentTotal.contains(".")) {
                return;
            }
        } else if (currentTotal.equals("0")) {
                    // If all you have is a zero, it can only be followed
                    // by a decimal point- avoids leading zero.
                    return;
        }
        ic.setText((currentTotal.equals("0.00") ? "" : currentTotal) + newChar);
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
        rtNum = rtNum.add(half ? icNum.divide(new BigDecimal(2),2, RoundingMode.HALF_EVEN) : icNum);
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

    public void clearRunningTotal(View view) {
        TextView rt = findViewById(R.id.runningTotal);
        rt.setText("0.00");
    }
    public void copyToClipboard(View view) {

    }
}