package drewmahrt.generalassemb.ly.investingportfolio;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by owlslubic on 8/22/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private ContentResolver mContentResolver;


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        ArrayList<String> symbols = extras.getStringArrayList("stock_symbols");
        String symbol = "";

        if(symbols!=null&&!symbols.isEmpty()){
            symbol = symbols.get(0);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://dev.markitondemand.com/Api/v2/Quote/json?symbol="+symbol)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            StockSymbol stockSymbol = gson.fromJson(response.body().string(),StockSymbol.class);
            ContentValues values = new ContentValues();
            values.put(StockPortfolioContract.Stocks.COLUMN_PRICE,stockSymbol.getLastPrice());
            mContentResolver.update(StockPortfolioContract.Stocks.CONTENT_URI,
                    values,
                    StockPortfolioContract.Stocks.COLUMN_STOCK_SYMBOL+ " = ?",
                    new String[]{stockSymbol.getSymbol()});

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
