package tm.fantom.newsviewer;

import android.app.Application;

import tm.fantom.newsviewer.deps.AppComponent;
import tm.fantom.newsviewer.deps.DBModule;
import tm.fantom.newsviewer.deps.DaggerAppComponent;
import tm.fantom.newsviewer.deps.NetModule;

/**
 * Created by fantom on 22-May-17.
 */

public class AppController extends Application {

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .netModule(new NetModule(this))
                .dBModule(new DBModule(this))
                .build();
    }

}
