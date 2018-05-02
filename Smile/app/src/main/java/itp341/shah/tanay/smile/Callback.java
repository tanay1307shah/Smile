package itp341.shah.tanay.smile;

/**
 * Created by tanay on 4/23/2018.
 */

public interface Callback {


    /**
     * Called when a "sentiment" API request is complete.
     *
     * @param sentiment The sentiment.
     */
    void onSentimentReady(SentimentInfo sentiment);

}