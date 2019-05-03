package edmt.dev.androidhellomvi.View;

public class MainViewState {
    boolean isLoading;
    boolean isImageViewShow;
    String imageLink;
    Throwable error;

    public MainViewState(boolean isLoading, boolean isImageViewShow, String imageLink, Throwable error) {
        this.isLoading = isLoading;
        this.isImageViewShow = isImageViewShow;
        this.imageLink = imageLink;
        this.error = error;
    }
}
