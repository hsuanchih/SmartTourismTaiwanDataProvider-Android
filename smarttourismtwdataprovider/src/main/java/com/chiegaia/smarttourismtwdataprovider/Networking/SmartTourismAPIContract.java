package com.chiegaia.smarttourismtwdataprovider.Networking;

/**
 * Created by Hsuan-Chih Chuang on 12/12/15.
 */
public class SmartTourismAPIContract {

    public enum SupportedLanguage {

        zh_tw,
        en_us,
        ja_jp
    }

    public enum APIType {

        PoiAPI,
        IntelligentAPI,
        AclAPI
    }

    public enum ResultType {

        CountyList       (APIType.PoiAPI),
        Theme            (APIType.PoiAPI),
        ThemeList        (APIType.PoiAPI),
        Activity         (APIType.PoiAPI),
        ActivityList     (APIType.PoiAPI),
        Tour             (APIType.PoiAPI),
        TourList         (APIType.PoiAPI),
        Attraction       (APIType.PoiAPI),
        AttractionList   (APIType.PoiAPI),
        FoodAndDrink     (APIType.PoiAPI),
        FoodAndDrinkList (APIType.PoiAPI);

        private APIType apiType;

        ResultType(APIType apiType) {
            this.apiType = apiType;
        }

        String getMethodName() {
            return "get" + this.toString();
        }

        public APIType getAPIType() {
            return this.apiType;
        }
    }

    public enum ResourceURI {

        findTopTen       (),
        findByCounty     (),
        findByTheme      (),
        findByCriteria   (),
        findByNearBy     (),
        findByRank       (),
        findByDate       (),
        findByAttraction (),
        none             ("");


        String resourceURI = null;

        ResourceURI() {}

        ResourceURI(String resourceURI) {
            this.resourceURI = resourceURI;
        }

        public String stringValue() {
            return this.resourceURI == null ? this.toString() : this.resourceURI;
        }
    }

}
