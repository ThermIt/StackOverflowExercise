package stackExchange;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 27.03.16.
 */
public class SearchResults {
    private static final long UNIX_EPOCH_MULTIPLIER = 1000; // difference between Date milliseconds and unix epoch time

    private List<SearchItem> items;
    private boolean hasError = false;
    private boolean hasMore = false;
    private long quotaMax;
    private long quotaRemaining;

    public List<SearchItem> getItems() {
        return items;
    }

    public void setItems(List<SearchItem> items) {
        this.items = items;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public long getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(long quotaMax) {
        this.quotaMax = quotaMax;
    }

    public long getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(long quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

    public static SearchResults deserialize(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        gsonBuilder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json1, typeOfT, context) -> {
            long unixEpochTime = json1.getAsJsonPrimitive().getAsLong();
            return new Date(convertUnixEpochToMilliseconds(unixEpochTime));
        });

        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, SearchResults.class);
    }

    private static long convertUnixEpochToMilliseconds(long unixEpochTime) {
        return unixEpochTime * UNIX_EPOCH_MULTIPLIER;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
}
