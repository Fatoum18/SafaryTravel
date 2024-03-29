package app.fatoumata.safarytravel.service.dto;


import java.util.ArrayList;

public class CountryOfRegionDto{

    public Name name;
    public boolean independent;
    public Currencies currencies;
    public Idd idd;
    public ArrayList<String> capital;
    public String region;
    public String subregion;
    public Languages languages;
    public ArrayList<Double> latlng;
    public double area;
    public Maps maps;
    public int population;
    public Car car;
    public ArrayList<String> timezones;
    public ArrayList<String> continents;
    public Flags flags;
    public String startOfWeek;


    public class Car{
        public ArrayList<String> signs;
        public String side;
    }

    public class Currencies{

        public EUR eUR;
    }

    public class EUR{
        public String name;
        public String symbol;
    }

    public class Flags{
        public String png;
        public String svg;
        public String alt;
    }

    public class Idd{
        public String root;
        public ArrayList<String> suffixes;
    }

    public class Languages{
        public String ell;
        public String tur;
    }

    public class Maps{
        public String googleMaps;
        public String openStreetMaps;
    }

    public class Name{
        public String common;
        public String official;
    }
}