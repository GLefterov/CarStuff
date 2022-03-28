package com.example.cw;

public class classCarModel {
    private int id;
    public String Make;
    public String Model;
    public int Year;
    public int Price;
    public String Longitude;
    public String Latitude;
    public String MOT;
    public String Insurance;
    public String Imagepath;




    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }



    //constructors


    public classCarModel(int id, String make, String model, int year, int price, String longitude, String latitude,String mot, String insurance, String imgPath) {
        this.id = id;
        Make = make;
        Model = model;
        Year = year;
        Price = price;
        Longitude = longitude;
        Latitude = latitude;
        MOT = mot;
        Insurance = insurance;
        Imagepath = imgPath;

    }

    public classCarModel(String carMake, String carModel, int carPrice, int carYear) {

    }

    //to string is necessary for printing ther contents of a class object


    @Override
    public String toString() {
        return " " + id +
                " " + Make +
                " " + Model +
                " " + Year +
                " MOT=" + MOT +
                " Insurance=" + Insurance;
    }

    //getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getMOT(){return MOT;}

    public void setMOT(String mot){MOT = mot;}

    public String getInsurance(){return Insurance;}

    public void setInsurance(String insurance){Insurance = insurance;}

    public String getImagepath(){return Imagepath;}

    public void setImagepath(String imagePath){Imagepath = imagePath;}


}

