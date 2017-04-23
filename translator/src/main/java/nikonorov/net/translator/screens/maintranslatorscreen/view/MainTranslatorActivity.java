package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.view.BaseActivity;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenter;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenterImpl;
import nikonorov.net.translator.utils.ThreadUtils;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorActivity extends BaseActivity<MainTranslatorPresenter> implements
        MainTranslatorView,
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private TextView translatedTV;
    private EditText translationField;
    private Spinner langFromSpinner;
    private Spinner langToSpinner;
    private final ArrayList<String> langsFrom = new ArrayList<>();
    private final ArrayList<String> langsTo = new ArrayList<>();
    private ArrayAdapter<String> langsFromAdapter;
    private ArrayAdapter<String> langsToAdapter;
    private ImageView addBookMarkBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainTranslatorPresenterImpl(this);
        translatedTV = (TextView) findViewById(R.id.translated_text);
        translationField = (EditText) findViewById(R.id.translating_text);
        translationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onTranslationTextChanged(translationField.getText().toString());
            }
        });
        langFromSpinner = (Spinner) findViewById(R.id.lang_from_spinner);
        langsFromAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, langsFrom);
        langsFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langFromSpinner.setAdapter(langsFromAdapter);
        langFromSpinner.setOnItemSelectedListener(this);
        langToSpinner = (Spinner) findViewById(R.id.lang_to_spinner);
        langsToAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, langsTo);
        langsToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langToSpinner.setAdapter(langsToAdapter);
        langToSpinner.setOnItemSelectedListener(this);
        addBookMarkBtn = (ImageView) findViewById(R.id.add_bookmark_btn);
        addBookMarkBtn.setOnClickListener(this);
        ((TextView)findViewById(R.id.translation_service_info)).setMovementMethod(LinkMovementMethod.getInstance());
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

    @Override
    public void setLangsFrom(List<String> langsFrom) {
        this.langsFrom.clear();
        this.langsFrom.addAll(langsFrom);
        langsFromAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLangsTo(List<String> langsFrom) {
        this.langsTo.clear();
        this.langsTo.addAll(langsFrom);
        langsToAdapter.notifyDataSetChanged();
    }

    @Override
    public void setActiveBookmarkBtn(final boolean isAlreadyBookmark) {
        ThreadUtils.executeOnMain(new Runnable() {
            @Override
            public void run() {
                @DrawableRes int res;
                if (isAlreadyBookmark) {
                    res = R.drawable.ic_bookmark_selected;
                } else {
                    res = R.drawable.ic_bookmark_notselected;
                }
                Picasso.with(MainTranslatorActivity.this)
                        .load(res)
                        .into(addBookMarkBtn);
                addBookMarkBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideBookMarkBtn() {
        ThreadUtils.executeOnMain(new Runnable() {
            @Override
            public void run() {
                addBookMarkBtn.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_bookmark_btn:
                presenter.onAddBookmarkClick();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lang_from_spinner:
                presenter.onLangFromSelected(position);
                break;
            case R.id.lang_to_spinner:
                presenter.onLangToSelected(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
