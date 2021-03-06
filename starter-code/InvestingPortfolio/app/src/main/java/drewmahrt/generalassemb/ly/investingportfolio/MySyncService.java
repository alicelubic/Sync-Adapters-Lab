package drewmahrt.generalassemb.ly.investingportfolio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by owlslubic on 8/22/16.
 */
public class MySyncService extends Service {
    private static final Object SyncAdapterLock = new Object();
    private static SyncAdapter sSyncAdapter=null;

    @Override
    public void onCreate() {
        synchronized (SyncAdapterLock){
            if(sSyncAdapter==null){
                sSyncAdapter = new SyncAdapter(getApplicationContext(),true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
