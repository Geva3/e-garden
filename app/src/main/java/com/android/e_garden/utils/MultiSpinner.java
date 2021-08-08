package com.android.e_garden.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Arrays;
import java.util.List;

public class MultiSpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {
    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private String noneSelectedText;
    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        selected[which] = isChecked;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        StringBuilder spinnerBuffer = new StringBuilder();
        boolean someSelected = false;
        boolean allSelected = true;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
                someSelected = true;
            }
            allSelected &= selected[i];
        }
        String spinnerText;
        if (allSelected) {
            spinnerText = defaultText;
        } else if (someSelected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = noneSelectedText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[0]), selected, this);
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.cancel());
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<String> items, String allText, String noneText,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.noneSelectedText = noneText;
        this.listener = listener;

        // all selected by default
        selected = new boolean[items.size()];
        Arrays.fill(selected, true);

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[] { allText });
        setAdapter(adapter);
    }

    public void setChecked(boolean[] checked) {
        selected = checked;
        onCancel(null);
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected);
    }
}
