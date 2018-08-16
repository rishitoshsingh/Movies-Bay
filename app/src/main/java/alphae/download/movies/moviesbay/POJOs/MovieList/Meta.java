
package alphae.download.movies.moviesbay.POJOs.MovieList;

import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("api_version")
    private Long mApiVersion;
    @SerializedName("execution_time")
    private String mExecutionTime;
    @SerializedName("server_time")
    private Long mServerTime;
    @SerializedName("server_timezone")
    private String mServerTimezone;

    public Long getApiVersion() {
        return mApiVersion;
    }

    public void setApiVersion(Long apiVersion) {
        mApiVersion = apiVersion;
    }

    public String getExecutionTime() {
        return mExecutionTime;
    }

    public void setExecutionTime(String executionTime) {
        mExecutionTime = executionTime;
    }

    public Long getServerTime() {
        return mServerTime;
    }

    public void setServerTime(Long serverTime) {
        mServerTime = serverTime;
    }

    public String getServerTimezone() {
        return mServerTimezone;
    }

    public void setServerTimezone(String serverTimezone) {
        mServerTimezone = serverTimezone;
    }

}
