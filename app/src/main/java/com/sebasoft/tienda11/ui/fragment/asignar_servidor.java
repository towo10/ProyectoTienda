package com.sebasoft.tienda11.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.sebasoft.tienda11.R;

@SuppressLint("ValidFragment")
public class asignar_servidor extends DialogFragment implements TextView.OnEditorActionListener{

    private EditText mEditText;
    private TextView mTextView;
    private String Servidor;

    public interface nuevocursorlistener{
        void FINALIZA_CUADRO_DIALOGO(String texto);
    }
    @SuppressLint("ValidFragment")
    public asignar_servidor(String servidor){
        Servidor = servidor;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        View view = inflater.inflate(R.layout.asignar_servidor,container);
        mEditText = (EditText) view.findViewById(R.id.et_servidor);
        mTextView = (TextView) view.findViewById(R.id.tv_servidor);

        mEditText.setOnEditorActionListener(this);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("BUSCAR SERVIDOR");
        mEditText.setText(Servidor);
        int position = mEditText.length();
        Editable etext = mEditText.getText();
        Selection.setSelection(etext,position);
        return  view;
    }
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        nuevocursorlistener activity = (nuevocursorlistener) getActivity();
        activity.FINALIZA_CUADRO_DIALOGO(mEditText.getText().toString());
        this.dismiss();
        return true;
    }
}
