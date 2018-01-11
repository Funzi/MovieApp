//package uco_396575.movio2.pv256.fi.muni.cz.movio.api;
//
//import okhttp3.Call;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//
//import static uco_396575.movio2.pv256.fi.muni.cz.movio.api.ApiHelper.buildUrlWithApiKey;
//
//
//public class MovieClientOkHttpImpl extends OkHttpClient implements MovieClient {
//    public MovieClientOkHttpImpl() {
//        initUrl();
//    }
//
//    private void initUrl() {
//    }
//
//    @Override
//    public Call getMostPopular() {
//        return getCallWithSuffix(API_URL + MOST_POPULAR);
//    }
//
//    @Override
//    public Call getBestScifi() {
//        return getCallWithSuffix(API_URL + BEST_SCIFI);
//    }
//
//    @Override
//    public Call getCredits(int movieId) {
//        return getCallWithSuffix(API_URL + "/movie/" + ((Integer) movieId).toString() + "/credits");
//    }
//
//
//    private Call getCallWithSuffix(String url) {
//        return this.newCall(new Request.Builder()
//                .url(buildUrlWithApiKey(url))
//                .build());
//    }
//
//}
