package edmt.dev.androidhellomvi.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvi.MviActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edmt.dev.androidhellomvi.Model.MainView;
import edmt.dev.androidhellomvi.R;
import edmt.dev.androidhellomvi.Utils.DataSource;
import io.reactivex.Observable;

public class MainActivity extends MviActivity<MainView,MainPresenter> implements MainView {

    ImageView imageView;
    Button button;
    ProgressBar progressBar;

    List<String> imageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_get_data);
        imageView = (ImageView) findViewById(R.id.recycler_data);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imageList = createListImage();

    }

    private List<String> createListImage() {
        //List of image Link
        return Arrays.asList("https://i.pinimg.com/originals/7a/d1/e3/7ad1e3626c7dbf08e5c0d2c7dd744076.jpg",
                "https://www.mordeo.org/files/uploads/2018/03/Avengers-Infinity-War-2018-950x1689.jpg",
                "https://wallpapers.moviemania.io/phone/movie/299534/89d58e/avengers-endgame-phone-wallpaper.jpg?w=1536&h=2732",
                "https://i.pinimg.com/originals/26/80/70/2680702031e0fd44872b67a56616483e.jpg",
                "https://wallpaperplay.com/walls/full/a/0/7/119622.jpg",
                "http://getwallpapers.com/wallpaper/full/0/7/5/459085.jpg");
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(new DataSource(imageList));
    }

    @Override
    public Observable<Integer> getImageIntent() {
        return RxView.clicks(button).map(click -> getRandomNumberInRange(0,imageList.size()-1));
    }

    private Integer getRandomNumberInRange(int min, int max)   {
        if(min >= max)
            throw  new IllegalArgumentException("max must be greater than min");
        Random r = new Random();
        return r.nextInt((max-min)+1)+min;
    }

    @Override
    public void render(MainViewState viewState) {
        //Here we will process change state to display view
        if(viewState.isLoading)
        {
            //Show progress bar
            progressBar.setVisibility(View.VISIBLE);
            //Hide ImageView
            imageView.setVisibility(View.GONE);
            //Disable button
            button.setEnabled(false);
        }
        else if(viewState.error != null)
        {
            //Hide progress bar
            progressBar.setVisibility(View.GONE);
            //Hide ImageView
            imageView.setVisibility(View.GONE);

            button.setEnabled(true);
            Toast.makeText(this, viewState.error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else if(viewState.isImageViewShow)
        {



            button.setEnabled(true);

            //Load picture
            Picasso.get().load(viewState.imageLink).fetch(new Callback() {
                @Override
                public void onSuccess() {
                    imageView.setAlpha(0f);
                    Picasso.get().load(viewState.imageLink).into(imageView);
                    imageView.animate().setDuration(300).alpha(1f).start(); //Fade animation

                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                    //Show ImageView
                    imageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    }
}
