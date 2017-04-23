package nikonorov.net.translator.screens.maintranslatorscreen.presenter;

import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nikonorov.net.translator.R;
import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.mvp.presenter.MVPPresenterImpl;
import nikonorov.net.translator.network.model.TranslationResult;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModel;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;
import nikonorov.net.translator.screens.maintranslatorscreen.view.MainTranslatorView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorPresenterImpl
        extends MVPPresenterImpl<MainTranslatorView>
        implements MainTranslatorPresenter {

    private final int ONE_SECOND_MILLIS = 1000;
    private final int DELAY_IN_SECONDS = 1;

    private final MainTranslatorModel model;
    private Subscription translationSubscription = Subscriptions.empty();
    private Subscription getLangsSubscription = Subscriptions.empty();
    private Subscription translationTextChanges = Subscriptions.empty();
    private final String locale = Locale.getDefault().getLanguage();

    public MainTranslatorPresenterImpl(MainTranslatorView view) {
        this.model = new MainTranslatorModelImpl();
        viewReference = new WeakReference<>(view);
    }

    @Override
    public void onTranslateEvent(String text) {
        prepareSubscription(translationSubscription);
        translationSubscription = model.translate(text).subscribe(new Observer<TranslationResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                model.handleError(e);
                MainTranslatorView view = viewReference.get();
                if (view != null) {
                    view.showError(R.string.internal_error_msg);
                }
                e.printStackTrace();
            }

            @Override
            public void onNext(TranslationResult translationResult) {
                MainTranslatorView view = viewReference.get();
                switch (translationResult.code) {
                    case 200:
                        handleSuccessResult(translationResult);
                        break;
                    case 401:
                    case 402:
                        model.handleError(translationResult);
                        if (view != null) {
                            view.showError(R.string.internal_error_msg);
                        }
                        break;
                    case 404:
                        model.handleError(translationResult);
                        if (view != null) {
                            view.showRetryError(R.string.error_msg_limit_exceeded);
                        }
                        break;
                    case 413:
                        if (view != null) {
                            view.showError(R.string.error_length_limit_exceeded);
                        }
                        break;
                    case 422:
                        if (view != null) {
                            view.showError(R.string.error_cant_translate);
                        }
                        break;
                    case 501:
                        if (view != null) {
                            view.showError(R.string.error_cant_translate);
                        }
                        break;
                    default:
                        model.handleError(translationResult);
                        break;
                }
            }
        });
    }

    private void handleSuccessResult(final TranslationResult translationResult) {
        final MainTranslatorView view = viewReference.get();
        model.getTranslationFromDB(translationResult).subscribe(new Observer<TranslationPair>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TranslationPair translationPair) {
                if (translationPair == null) {
                    model.saveTranslation(translationResult);
                    if (view != null) {
                        view.showTranslatedResult(TextUtils.join(", ", translationResult.text));
                        view.setActiveBookmarkBtn(false);
                    }
                } else {
                    model.setCurrentTranslation(translationPair);
                    if (view != null) {
                        view.showTranslatedResult(TextUtils.join(", ", translationResult.text));
                        view.setActiveBookmarkBtn(translationPair.isBookmark());
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        prepareSubscription(getLangsSubscription);
        getLangsSubscription = model
                .requestLanguages(locale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Language>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Language> languages) {
                model.setLanguages(languages, locale);
                MainTranslatorView view = viewReference.get();
                if (view != null) {
                    view.setLangsFrom(model.getFromLanguages());
                    view.setLangsTo(model.getToLanguages());
                }
            }
        });
    }

    @Override
    public void onStop() {
        prepareSubscription(getLangsSubscription);
        prepareSubscription(translationSubscription);
    }

    @Override
    public void onRetryClick() {
        //TODO retry call
    }

    @Override
    public void onLangFromSelected(int position) {
        model.setLangFrom(position);
    }

    @Override
    public void onLangToSelected(int position) {
        model.setLangTo(position);
    }

    @Override
    public void onAddBookmarkClick() {
        model.addBookmark();
    }

    @Override
    public void onTranslationTextChanged(final String text) {
        if ("".equals(text)) {
            MainTranslatorView view = viewReference.get();
            if (view != null) {
                view.hideBookMarkBtn();
                view.showTranslatedResult("");
            }
        } else {
            prepareSubscription(translationTextChanges);
            translationTextChanges = Observable.interval(ONE_SECOND_MILLIS, TimeUnit.MILLISECONDS)
                    .take(DELAY_IN_SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Long aLong) {
                            onTranslateEvent(text);
                        }
                    });
        }
    }

    protected void prepareSubscription(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
