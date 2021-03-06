package net.swedeheart.shopshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.View.OnLongClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private final static String RT = "RT";
    private final static String IC = "IC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView rt = findViewById(R.id.runningTotal);
        rt.setOnLongClickListener(this.clipboardCopyListener);
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

    public void calcTotal(View view) {
        TextView rt = findViewById(R.id.runningTotal);
        TextView ic = findViewById(R.id.itemCost);
        BigDecimal rtNum = new BigDecimal(rt.getText().toString());
        BigDecimal icNum = new BigDecimal(ic.getText().toString());
        rt.setText(icNum.subtract(rtNum).toString());
    }
    private OnLongClickListener clipboardCopyListener = new OnLongClickListener() {
        public boolean onLongClick(View v) {
            ((ClipboardManager)getSystemService(ClipboardManager.class)).setPrimaryClip(
                    ClipData.newPlainText("Running total", ((TextView)v).getText())
            );
            Toast.makeText(v.getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            return true;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(RT, ((TextView)findViewById(R.id.runningTotal)).getText().toString());
        savedInstanceState.putString(IC, ((TextView)findViewById(R.id.itemCost)).getText().toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        ((TextView)findViewById(R.id.runningTotal)).setText(savedInstanceState.getString(RT));
        ((TextView)findViewById(R.id.itemCost)).setText(savedInstanceState.getString(IC));
    }
}