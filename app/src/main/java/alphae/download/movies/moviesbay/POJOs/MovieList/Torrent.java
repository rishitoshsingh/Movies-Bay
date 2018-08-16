
package alphae.download.movies.moviesbay.POJOs.MovieList;

import com.google.gson.annotations.SerializedName;

public class Torrent {

    @SerializedName("date_uploaded")
    private String mDateUploaded;
    @SerializedName("date_uploaded_unix")
    private Long mDateUploadedUnix;
    @SerializedName("hash")
    private String mHash;
    @SerializedName("peers")
    private Long mPeers;
    @SerializedName("quality")
    private String mQuality;
    @SerializedName("seeds")
    private Long mSeeds;
    @SerializedName("size")
    private String mSize;
    @SerializedName("size_bytes")
    private Long mSizeBytes;
    @SerializedName("url")
    private String mUrl;

    public String getDateUploaded() {
        return mDateUploaded;
    }

    public void setDateUploaded(String dateUploaded) {
        mDateUploaded = dateUploaded;
    }

    public Long getDateUploadedUnix() {
        return mDateUploadedUnix;
    }

    public void setDateUploadedUnix(Long dateUploadedUnix) {
        mDateUploadedUnix = dateUploadedUnix;
    }

    public String getHash() {
        return mHash;
    }

    public void setHash(String hash) {
        mHash = hash;
    }

    public Long getPeers() {
        return mPeers;
    }

    public void setPeers(Long peers) {
        mPeers = peers;
    }

    public String getQuality() {
        return mQuality;
    }

    public void setQuality(String quality) {
        mQuality = quality;
    }

    public Long getSeeds() {
        return mSeeds;
    }

    public void setSeeds(Long seeds) {
        mSeeds = seeds;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public Long getSizeBytes() {
        return mSizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        mSizeBytes = sizeBytes;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
