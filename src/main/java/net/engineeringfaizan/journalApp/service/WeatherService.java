package net.engineeringfaizan.journalApp.service;

import net.engineeringfaizan.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@Service
public class WeatherService {

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    public String apiKey;


    @Autowired
    private RedisTemplate<String , String> redisTemplate;


    public String getWeather(String city){

        String cachekey = "weather:" + city;
        String cachedWeather = redisTemplate.opsForValue().get(cachekey);

        if (cachedWeather != null){
            return cachedWeather;
        }
        try{
            String url = UriComponentsBuilder.fromHttpUrl(appCache.appCache.get("weather-api"))
                    .queryParam("city" , city)
                    .queryParam("key" , apiKey)
                    .toUriString();
            String weatherData = restTemplate.getForObject(url , String.class);
            //store the data
            redisTemplate.opsForValue().set(cachekey, weatherData);

            return weatherData;

        }catch (RestClientException e){
            return "Failed to fetch weather data :" +e.getMessage();
        }

    }
}
