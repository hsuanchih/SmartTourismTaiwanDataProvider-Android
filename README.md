# Smart Tourism Taiwan Data Provider (Android)
The Smart Tourism Taiwan Data Provider is a library that gives your application access to  
the [Smart Tourism Taiwan Open Data](http://www.hackathon.vztaiwan.com/swagger.html) made available by the [Institute for Information Industry](http://www.iii.org.tw/Default.aspx) .

This Android library uses Square's Retrofit HTTP Client in combination with the GSON serialization library  
under the hood to streamline access to Smart Tourism Taiwan's web services.

## Usage
### Building the library:
* Create a .aar from the "smarttourismtwdataprovider" package
* Add the .aar to your application's build.gradle

### Using the library:
* Getting a reference to the data provider:
```Java

public class MainActivity extends AppCompatActivity {
    .
    .
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Get a reference to the data provider, passing in the context 
         */
        SmartTourismDataProvider dataProvider = SmartTourismDataProvider.getInstance(this);
        .
        .
```
* Accessing Smart Tourism Taiwan Open Data (Example):
```Java
/**
 * Query a list of counties
 */
dataProvider.getCountyList(new SmartTourismDataProvider.Callback<List<County>>() {

    /**
     * Handle response data
     */ 
    @Override
    public void onResponse(Response<List<County>> response) {
    
        if (response.isSuccess()) {
            CountyList countyList = response.body();
            for (County county : countyList) {
                
                /**
                 * Do some stuff...
                 */
                Log.d(TAG, county.id + "\n" + county.name + "\n" + county.region);
            }
        }
    }

    /**
     * Follow-up failure case
     */
    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, t.getCause().toString());
    }
});

```
* Setting the Language Pack (Optional):
```Java
/**
 * Set the language pack to English (US)
 */
dataProvider.setLanguage(SmartTourismAPIContract.SupportedLanguage.en_us);
```


## TODO
#####Intelligent & ACL APIs Pending

## License
#####Licensed under the MIT License.
