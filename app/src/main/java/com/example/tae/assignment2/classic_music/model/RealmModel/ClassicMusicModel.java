package com.example.tae.assignment2.classic_music.model.RealmModel;

/**
 * Created by TAE on 11-Feb-18.
 */
import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;

    /**
     * Created by laFerrari on 03/02/2018.
     */

    public class ClassicMusicModel extends RealmObject {

        String mArtistName;
        String mCollectionName;
        String mArtwork;
        String mPreviewURL;
        String mCurrency;
        String mTrackPrice;

        public ClassicMusicModel() {
            super();
        }

        public ClassicMusicModel(String mArtistName, String mCollectionName, String mArtwork, String mPreviewURL, String mCurrency, String mTrackPrice) {
            this.mArtistName = mArtistName;
            this.mCollectionName = mCollectionName;
            this.mArtwork = mArtwork;
            this.mPreviewURL = mPreviewURL;
            this.mCurrency = mCurrency;
            this.mTrackPrice = mTrackPrice;
        }

        public String getmArtistName() {
            return mArtistName;
        }

        public void setmArtistName(String mArtistName) {
            this.mArtistName = mArtistName;
        }

        public String getmCollectionName() {
            return mCollectionName;
        }

        public void setmCollectionName(String mCollectionName) {
            this.mCollectionName = mCollectionName;
        }

        public String getmArtwork() {
            return mArtwork;
        }

        public void setmArtwork(String mArtwork) {
            this.mArtwork = mArtwork;
        }

        public String getmPreviewURL() {
            return mPreviewURL;
        }

        public void setmPreviewURL(String mPreviewURL) {
            this.mPreviewURL = mPreviewURL;
        }

        public String getmCurrency() {
            return mCurrency;
        }

        public void setmCurrency(String mCurrency) {
            this.mCurrency = mCurrency;
        }

        public String getmTrackPrice() {
            return mTrackPrice;
        }

        public void setmTrackPrice(String mTrackPrice) {
            this.mTrackPrice = mTrackPrice;
        }
    }
