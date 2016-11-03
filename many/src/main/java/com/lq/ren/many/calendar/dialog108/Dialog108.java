package com.lq.ren.many.calendar.dialog108;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lq.ren.many.R;

/**
 * Author lqren on 16/10/8.
 */
public class Dialog108 extends Activity {

    private final int MUTI_CHOICE_DIALOG = 1;

    boolean[] selected = new boolean[]{false,false,false,
            false,false,false,false,false,false,false,false,false,false,false,false};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_muti_choice_dialog);

        Button button = (Button) findViewById(R.id.button);
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialog(MUTI_CHOICE_DIALOG);
            }
        };
        button.setOnClickListener(listener);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch(id) {
            case MUTI_CHOICE_DIALOG:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("多选列表对话框");
                builder.setIcon(R.drawable.qq);
                DialogInterface.OnMultiChoiceClickListener mutiListener =
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface,
                                                int which, boolean isChecked) {
                                selected[which] = isChecked;
                            }
                        };
                builder.setMultiChoiceItems(R.array.hobby, selected, mutiListener);
                DialogInterface.OnClickListener btnListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                String selectedStr = "";
                                for(int i=0; i<selected.length; i++) {
                                    if(selected[i] == true) {
                                        selectedStr = selectedStr + " " +
                                                getResources().getStringArray(R.array.hobby)[i];
                                    }
                                }

                                EditText editText = (EditText) findViewById(R.id.editText);
                                editText.setText(selectedStr);
                            }
                        };
                builder.setPositiveButton("确定", btnListener);
                dialog = builder.create();
                break;
        }
        return dialog;
    }
}

