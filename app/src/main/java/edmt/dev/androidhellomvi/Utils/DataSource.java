package edmt.dev.androidhellomvi.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class DataSource {
    List<String> imageList;

    public DataSource(List<String> imageList) {
        this.imageList = imageList;
    }

    public Observable<String> getImageLinkFromList(int index)
    {
        return Observable.just(imageList.get(index));
    }
}
