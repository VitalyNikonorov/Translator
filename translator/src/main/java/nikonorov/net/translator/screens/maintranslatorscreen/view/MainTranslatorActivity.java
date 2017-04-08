package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nikonorov.net.translator.R;
import nikonorov.net.translator.screens.historyscreen.HistoryActivity;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenter;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenterImpl;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorActivity extends AppCompatActivity implements MainTranslatorView, View.OnClickListener {

    private TextView translatedTV;
    private EditText translationField;
    private MainTranslatorPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainTranslatorPresenterImpl(this);
        setContentView(R.layout.main_translator_screen);
        translatedTV = (TextView) findViewById(R.id.translated_text);
        translationField = (EditText) findViewById(R.id.translating_text);
        findViewById(R.id.translate_btn).setOnClickListener(this);
        findViewById(R.id.mock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainTranslatorActivity.this, HistoryActivity.class));
            }
        });
    }

    @Override
    public void showTranslatedResult(String text) {
        translatedTV.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translate_btn:
                presenter.onTranslateBtnClick(translationField.getText().toString());
                break;
        }
    }
}
