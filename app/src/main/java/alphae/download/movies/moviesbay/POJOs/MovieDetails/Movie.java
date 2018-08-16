
package alphae.download.movies.moviesbay.POJOs.MovieDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("background_image")
    private String mBackgroundImage;
    @SerializedName("background_image_original")
    private String mBackgroundImageOriginal;
    @SerializedName("cast")
    private List<Cast> mCast;
    @SerializedName("date_uploaded")
    private String mDateUploaded;
    @SerializedName("date_uploaded_unix")
    private Long mDateUploadedUnix;
    @SerializedName("description_full")
    private String mDescriptionFull;
    @SerializedName("description_intro")
    private String mDescriptionIntro;
    @SerializedName("download_count")
    private Long mDownloadCount;
    @SerializedName("genres")
    private List<String> mGenres;
    @SerializedName("id")
    private Long mId;
    @SerializedName("imdb_code")
    private String mImdbCode;
    @SerializedName("language")
    private String mLanguage;
    @SerializedName("large_cover_image")
    private String mLargeCoverImage;
    @SerializedName("large_screenshot_image1")
    private String mLargeScreenshotImage1;
    @SerializedName("large_screenshot_image2")
    private String mLargeScreenshotImage2;
    @SerializedName("large_screenshot_image3")
    private String mLargeScreenshotImage3;
    @SerializedName("like_count")
    private Long mLikeCount;
    @SerializedName("medium_cover_image")
    private String mMediumCoverImage;
    @SerializedName("medium_screenshot_image1")
    private String mMediumScreenshotImage1;
    @SerializedName("medium_screenshot_image2")
    private String mMediumScreenshotImage2;
    @SerializedName("medium_screenshot_image3")
    private String mMediumScreenshotImage3;
    @SerializedName("mpa_rating")
    private String mMpaRating;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("runtime")
    private Long mRuntime;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("small_cover_image")
    private String mSmallCoverImage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("title_english")
    private String mTitleEnglish;
    @SerializedName("title_long")
    private String mTitleLong;
    @SerializedName("torrents")
    private List<Torrent> mTorrents;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("year")
    private Long mYear;
    @SerializedName("yt_trailer_code")
    private String mYtTrailerCode;

    public String getBackgroundImage() {
        return mBackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        mBackgroundImage = backgroundImage;
    }

    public String getBackgroundImageOriginal() {
        return mBackgroundImageOriginal;
    }

    public void setBackgroundImageOriginal(String backgroundImageOriginal) {
        mBackgroundImageOriginal = backgroundImageOriginal;
    }

    public List<Cast> getCast() {
        return mCast;
    }

    public void setCast(List<Cast> cast) {
        mCast = cast;
    }

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

    public String getDescriptionFull() {
        return mDescriptionFull;
    }

    public void setDescriptionFull(String descriptionFull) {
        mDescriptionFull = descriptionFull;
    }

    public String getDescriptionIntro() {
        return mDescriptionIntro;
    }

    public void setDescriptionIntro(String descriptionIntro) {
        mDescriptionIntro = descriptionIntro;
    }

    public Long getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        mDownloadCount = downloadCount;
    }

    public List<String> getGenres() {
        return mGenres;
    }

    public void setGenres(List<String> genres) {
        mGenres = genres;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImdbCode() {
        return mImdbCode;
    }

    public void setImdbCode(String imdbCode) {
        mImdbCode = imdbCode;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getLargeCoverImage() {
        return mLargeCoverImage;
    }

    public void setLargeCoverImage(String largeCoverImage) {
        mLargeCoverImage = largeCoverImage;
    }

    public String getLargeScreenshotImage1() {
        return mLargeScreenshotImage1;
    }

    public void setLargeScreenshotImage1(String largeScreenshotImage1) {
        mLargeScreenshotImage1 = largeScreenshotImage1;
    }

    public String getLargeScreenshotImage2() {
        return mLargeScreenshotImage2;
    }

    public void setLargeScreenshotImage2(String largeScreenshotImage2) {
        mLargeScreenshotImage2 = largeScreenshotImage2;
    }

    public String getLargeScreenshotImage3() {
        return mLargeScreenshotImage3;
    }

    public void setLargeScreenshotImage3(String largeScreenshotImage3) {
        mLargeScreenshotImage3 = largeScreenshotImage3;
    }

    public Long getLikeCount() {
        return mLikeCount;
    }

    public void setLikeCount(Long likeCount) {
        mLikeCount = likeCount;
    }

    public String getMediumCoverImage() {
        return mMediumCoverImage;
    }

    public void setMediumCoverImage(String mediumCoverImage) {
        mMediumCoverImage = mediumCoverImage;
    }

    public String getMediumScreenshotImage1() {
        return mMediumScreenshotImage1;
    }

    public void setMediumScreenshotImage1(String mediumScreenshotImage1) {
        mMediumScreenshotImage1 = mediumScreenshotImage1;
    }

    public String getMediumScreenshotImage2() {
        return mMediumScreenshotImage2;
    }

    public void setMediumScreenshotImage2(String mediumScreenshotImage2) {
        mMediumScreenshotImage2 = mediumScreenshotImage2;
    }

    public String getMediumScreenshotImage3() {
        return mMediumScreenshotImage3;
    }

    public void setMediumScreenshotImage3(String mediumScreenshotImage3) {
        mMediumScreenshotImage3 = mediumScreenshotImage3;
    }

    public String getMpaRating() {
        return mMpaRating;
    }

    public void setMpaRating(String mpaRating) {
        mMpaRating = mpaRating;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public Long getRuntime() {
        return mRuntime;
    }

    public void setRuntime(Long runtime) {
        mRuntime = runtime;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getSmallCoverImage() {
        return mSmallCoverImage;
    }

    public void setSmallCoverImage(String smallCoverImage) {
        mSmallCoverImage = smallCoverImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleEnglish() {
        return mTitleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        mTitleEnglish = titleEnglish;
    }

    public String getTitleLong() {
        return mTitleLong;
    }

    public void setTitleLong(String titleLong) {
        mTitleLong = titleLong;
    }

    public List<Torrent> getTorrents() {
        return mTorrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        mTorrents = torrents;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Long getYear() {
        return mYear;
    }

    public void setYear(Long year) {
        mYear = year;
    }

    public String getYtTrailerCode() {
        return mYtTrailerCode;
    }

    public void setYtTrailerCode(String ytTrailerCode) {
        mYtTrailerCode = ytTrailerCode;
    }

}
