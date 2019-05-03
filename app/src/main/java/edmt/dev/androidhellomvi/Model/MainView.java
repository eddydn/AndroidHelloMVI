package edmt.dev.androidhellomvi.Model;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import edmt.dev.androidhellomvi.View.MainViewState;
import io.reactivex.Observable;

public interface MainView extends MvpView {
    Observable<Integer> getImageIntent();

    void render(MainViewState viewState);
}
