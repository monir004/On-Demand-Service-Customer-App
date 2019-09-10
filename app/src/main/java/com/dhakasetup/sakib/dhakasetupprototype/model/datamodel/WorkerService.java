package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

public class WorkerService {
    String name,mobile,rating,price,worker_id;

    public WorkerService(String name, String mobile, String rating, String price) {
        this.name = name;
        this.mobile = mobile;
        this.rating = rating;
        this.price = price;
    }

    public WorkerService() {
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
