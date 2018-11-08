package com.acme.ecommerce.bookstore.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 7/11/2018.
 */
public class USConstants {

    public static final String US = "US";
    public static final Map<String, String> MAP_OF_STATES = new HashMap<>();
    public static final List<String> US_STATES_CODES = new ArrayList<>();
    public static final List<String> US_STATES_NAMES = new ArrayList<>();

    static {
        MAP_OF_STATES.put("AL", "Alabama");
        MAP_OF_STATES.put("AK", "Alaska");
        MAP_OF_STATES.put("AZ", "Arizona");
        MAP_OF_STATES.put("AR", "Arkansas");
        MAP_OF_STATES.put("CA", "California");
        MAP_OF_STATES.put("CO", "Colorado");
        MAP_OF_STATES.put("CT", "Connecticut");
        MAP_OF_STATES.put("DE", "Delaware");
        MAP_OF_STATES.put("DC", "Dist of Columbia");
        MAP_OF_STATES.put("FL", "Florida");
        MAP_OF_STATES.put("GA", "Georgia");
        MAP_OF_STATES.put("HI", "Hawaii");
        MAP_OF_STATES.put("ID", "Idaho");
        MAP_OF_STATES.put("IL", "Illinois");
        MAP_OF_STATES.put("IN", "Indiana");
        MAP_OF_STATES.put("IA", "Iowa");
        MAP_OF_STATES.put("KS", "Kansas");
        MAP_OF_STATES.put("KY", "Kentucky");
        MAP_OF_STATES.put("LA", "Louisiana");
        MAP_OF_STATES.put("ME", "Maine");
        MAP_OF_STATES.put("MD", "Maryland");
        MAP_OF_STATES.put("MA", "Massachusetts");
        MAP_OF_STATES.put("MI", "Michigan");
        MAP_OF_STATES.put("MN", "Minnesota");
        MAP_OF_STATES.put("MS", "Mississippi");
        MAP_OF_STATES.put("MO", "Missouri");
        MAP_OF_STATES.put("MT", "Montana");
        MAP_OF_STATES.put("NE", "Nebraska");
        MAP_OF_STATES.put("NV", "Nevada");
        MAP_OF_STATES.put("NH", "New Hampshire");
        MAP_OF_STATES.put("NJ", "New Jersey");
        MAP_OF_STATES.put("NM", "New Mexico");
        MAP_OF_STATES.put("NY", "New York");
        MAP_OF_STATES.put("NC", "North Carolina");
        MAP_OF_STATES.put("ND", "North Dakota");
        MAP_OF_STATES.put("OH", "Ohio");
        MAP_OF_STATES.put("OK", "Oklahoma");
        MAP_OF_STATES.put("OR", "Oregon");
        MAP_OF_STATES.put("PA", "Pennsylvania");
        MAP_OF_STATES.put("RI", "Rhode Island");
        MAP_OF_STATES.put("SC", "South Carolina");
        MAP_OF_STATES.put("SD", "South Dakota");
        MAP_OF_STATES.put("TN", "Tennessee");
        MAP_OF_STATES.put("TX", "Texas");
        MAP_OF_STATES.put("UT", "Utah");
        MAP_OF_STATES.put("VT", "Vermont");
        MAP_OF_STATES.put("VA", "Virginia");
        MAP_OF_STATES.put("WA", "Washington");
        MAP_OF_STATES.put("WV", "West Virginia");
        MAP_OF_STATES.put("WI", "Wisconsin");
        MAP_OF_STATES.put("WY", "Wyoming");

        US_STATES_CODES.addAll(MAP_OF_STATES.keySet());
        US_STATES_NAMES.addAll(MAP_OF_STATES.values());
    }

    private USConstants() {
    }
}
