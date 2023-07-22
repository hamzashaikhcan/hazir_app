package com.enfotrix.hazir.Models;

import java.io.Serializable;

public class ModelMyCar implements Serializable {

    int id;
    String driver_id, car_model, car_make, model_year, car_assembly,  engine_capacity, body_color, between_cities, registration_city, pickup_city, car_no,
            driver_availability, car_mileage, car_rent, description, insured, car_seats, car_type, car_status,  state,car_tranmission;
    String image, image2, image3, image4, image5;
    String phone_no;

    public ModelMyCar(int id, String driver_id, String car_model, String car_make, String model_year, String car_assembly, String engine_capacity, String body_color, String between_cities,
                      String registration_city, String pickup_city, String car_no, String driver_availability, String car_mileage, String car_rent, String description, String insured,
                      String car_seats, String car_type, String car_status, String state, String car_tranmission, String image, String image2, String image3, String image4, String image5) {
        this.id = id;
        this.driver_id = driver_id;
        this.car_model = car_model;
        this.car_make = car_make;
        this.model_year = model_year;
        this.car_assembly = car_assembly;
        this.engine_capacity = engine_capacity;
        this.body_color = body_color;
        this.between_cities = between_cities;
        this.registration_city = registration_city;
        this.pickup_city = pickup_city;
        this.car_no = car_no;
        this.driver_availability = driver_availability;
        this.car_mileage = car_mileage;
        this.car_rent = car_rent;
        this.description = description;
        this.insured = insured;
        this.car_seats = car_seats;
        this.car_type = car_type;
        this.car_status = car_status;
        this.state = state;
        this.car_tranmission = car_tranmission;
        this.image = image;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }


    public ModelMyCar(int id, String driver_id, String car_model, String car_make, String model_year, String car_assembly, String engine_capacity, String body_color, String between_cities,
                      String registration_city, String pickup_city, String car_no, String driver_availability, String car_mileage, String car_rent, String description, String insured,
                      String car_seats, String car_type, String car_status, String state, String car_tranmission, String image, String image2, String image3, String image4, String image5, String phone_no) {
        this.id = id;
        this.driver_id = driver_id;
        this.car_model = car_model;
        this.car_make = car_make;
        this.model_year = model_year;
        this.car_assembly = car_assembly;
        this.engine_capacity = engine_capacity;
        this.body_color = body_color;
        this.between_cities = between_cities;
        this.registration_city = registration_city;
        this.pickup_city = pickup_city;
        this.car_no = car_no;
        this.driver_availability = driver_availability;
        this.car_mileage = car_mileage;
        this.car_rent = car_rent;
        this.description = description;
        this.insured = insured;
        this.car_seats = car_seats;
        this.car_type = car_type;
        this.car_status = car_status;
        this.state = state;
        this.car_tranmission = car_tranmission;
        this.image = image;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.phone_no = phone_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_make() {
        return car_make;
    }

    public void setCar_make(String car_make) {
        this.car_make = car_make;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public String getCar_assembly() {
        return car_assembly;
    }

    public void setCar_assembly(String car_assembly) {
        this.car_assembly = car_assembly;
    }

    public String getEngine_capacity() {
        return engine_capacity;
    }

    public void setEngine_capacity(String engine_capacity) {
        this.engine_capacity = engine_capacity;
    }

    public String getBody_color() {
        return body_color;
    }

    public void setBody_color(String body_color) {
        this.body_color = body_color;
    }

    public String getBetween_cities() {
        return between_cities;
    }

    public void setBetween_cities(String between_cities) {
        this.between_cities = between_cities;
    }

    public String getRegistration_city() {
        return registration_city;
    }

    public void setRegistration_city(String registration_city) {
        this.registration_city = registration_city;
    }

    public String getPickup_city() {
        return pickup_city;
    }

    public void setPickup_city(String pickup_city) {
        this.pickup_city = pickup_city;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getDriver_availability() {
        return driver_availability;
    }

    public void setDriver_availability(String driver_availability) {
        this.driver_availability = driver_availability;
    }

    public String getCar_mileage() {
        return car_mileage;
    }

    public void setCar_mileage(String car_mileage) {
        this.car_mileage = car_mileage;
    }

    public String getCar_rent() {
        return car_rent;
    }

    public void setCar_rent(String car_rent) {
        this.car_rent = car_rent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getCar_seats() {
        return car_seats;
    }

    public void setCar_seats(String car_seats) {
        this.car_seats = car_seats;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCar_tranmission() {
        return car_tranmission;
    }

    public void setCar_tranmission(String car_tranmission) {
        this.car_tranmission = car_tranmission;
    }


    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }
}
