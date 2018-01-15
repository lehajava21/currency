package com.leha;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

public class Provider {

    private static final String propfile = "/application.properties";
    private Properties prop;
    private String baseurl;
    private File logfile;

    public Provider() throws IOException {
        prop = new Properties();
        prop.load(Provider.class.getResourceAsStream(propfile));
        baseurl = prop.getProperty("baseurl");
        logfile = new File(prop.getProperty("logfile"));
        if (!logfile.exists()) {
            logfile.createNewFile();
        }
    }

    public String sum(String base, String amount, String target) throws IOException {
        String sum;
        Double num;
        try {
            num = Double.parseDouble(amount);
        }catch (Exception e){
            sum = "Wrong amount\n";
            FileUtils.write(logfile,sum,true);
            return sum;
        }
        base = base.toUpperCase();
        target = target.toUpperCase();
        String url = baseurl + "latest?symbols=" + target + "," + base + "&base=" + base;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Currency> response = restTemplate.getForEntity(url, Currency.class);
            Currency body = response.getBody();
            try {
                Double res = body.getRates().get(target);
                sum = String.valueOf(num*res);
            }catch (Exception e){
                sum = "Wrong currency\n";
                FileUtils.write(logfile,sum,true);
            }
        } catch (Exception e) {
            String err = e.toString();
            sum = "Error" + err.substring(err.indexOf(":")) + "\n";
            FileUtils.write(logfile,sum,true);
        }
        return sum;
    }

}

@Data
class Currency {
    private String base;
    private LocalDate date;
    private Map<String,Double> rates;
}
