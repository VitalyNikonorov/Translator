package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.view.BaseActivity;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenter;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenterImpl;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorActivity extends BaseActivity<MainTranslatorPresenter> implements
        MainTranslatorView,
        View.OnClickListener {

    private TextView translatedTV;
    private EditText translationField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainTranslatorPresenterImpl(this);
        translatedTV = (TextView) findViewById(R.id.translated_text);
        translationField = (EditText) findViewById(R.id.translating_text);
        findViewById(R.id.translate_btn).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_translator_screen;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void showTranslatedResult(String text) {
        translatedTV.setText(text);
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void showError(@StringRes int msgId) {
        showToast(getString(msgId));
    }

    @Override
    public void showRetryError(@StringRes int msgId) {
        new AlertDialog.Builder(this)
                .setMessage(msgId)
                .setTitle(R.string.error_caption)
                .setPositiveButton(R.string.action_retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.onRetryClick();
                        }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .create()
                .show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
