package com.example.pbs.patientseri.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NominatimService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";

    public double[] getCoordinates(String addressLine1, String city, String state, String country, String zipcode) {
        try {
            String fullAddress = String.format("%s, %s, %s, %s, %s",
                    addressLine1, city, state, country, zipcode);

            String url = NOMINATIM_URL + "?format=json&q=" + fullAddress.replace(" ", "+");

            // Send request
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            System.out.println("Nominatim Response: " + response);  // ✅ Useful for debugging

            // Parse JSON Array
            JSONArray array = new JSONArray(response);
            if (array.length() > 0) {
                JSONObject location = array.getJSONObject(0);
                double lat = Double.parseDouble(location.getString("lat"));
                double lon = Double.parseDouble(location.getString("lon"));
                return new double[]{lat, lon};
            }
        } catch (Exception e) {
            System.err.println("Error fetching coordinates: " + e.getMessage());
        }

        // Default fallback
        return new double[]{0.0, 0.0};
    }
}
