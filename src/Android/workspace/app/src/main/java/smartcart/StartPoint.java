package smartcart;

import android.app.Application;
import android.content.Intent;

import smartcart.activities.MainActivity;

/**
 * Created by admin on 25.03.2017.
 */

public class StartPoint extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
