package com.tv.coronavirustracker.service;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/*
* after start of the app an instance of this class due to service annotation is created
 * */
@Service
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    /*
    *
    * execute the method fetchVirusData after the construct of the instance of this service class -> achived with annotation postconstruct
    *
    * */

    @PostConstruct
    @Scheduled (cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        // make a http call using httpclient ->since java 11
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        /// async
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

/*
*   Make use of commons-csv library
*   Reader in = new FileReader("path/to/file.csv");
*
* */

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            String province = record.get("Province/State");
//            String country = record.get("Country/Region");
//            String lat = record.get("lat");
            System.out.println(province);
        }

    }
}
