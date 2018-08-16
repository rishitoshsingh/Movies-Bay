
package alphae.download.movies.moviesbay.POJOs.MovieDetails;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("character_name")
    private String mCharacterName;
    @SerializedName("imdb_code")
    private String mImdbCode;
    @SerializedName("name")
    private String mName;
    @SerializedName("url_small_image")
    private String mUrlSmallImage;

    public String getCharacterName() {
        return mCharacterName;
    }

    public void setCharacterName(String characterName) {
        mCharacterName = characterName;
    }

    public String getImdbCode() {
        return mImdbCode;
    }

    public void setImdbCode(String imdbCode) {
        mImdbCode = imdbCode;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrlSmallImage() {
        return mUrlSmallImage;
    }

    public void setUrlSmallImage(String urlSmallImage) {
        mUrlSmallImage = urlSmallImage;
    }

}
