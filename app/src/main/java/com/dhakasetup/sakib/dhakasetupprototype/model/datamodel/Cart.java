package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<ServiceProp> serviceProps = null;
    List<Service> services = null;

    public Cart() {
        serviceProps = new ArrayList<>();
        services = new ArrayList<>();
    }

    public void addServiceProp(ServiceProp serviceProp){
        serviceProps.add(serviceProp);
    }
    public void addService(Service service){
        services.add(service);
    }

    public ServiceProp getServiceProp(int srvPropID){
        for (ServiceProp serviceProp:serviceProps) {
            if (serviceProp.getId() == srvPropID)
                return serviceProp;
        }
        return null;
    }
    public Service getService(int srvID){
        for (Service service:services) {
            if (Integer.parseInt(service.getSrv_sl()) == srvID)
                return service;
        }
        return null;
    }

    public void removeServiceProp(int srvPropID){
        for (int i=0; i<serviceProps.size(); i++){
            if(serviceProps.get(i).getId() == srvPropID)
                serviceProps.remove(i);
        }
    }
    public void removeService(int srvID){
        for (int i=0; i<services.size(); i++){
            if(Integer.parseInt(services.get(i).getSrv_sl()) == srvID)
                services.remove(i);
        }
    }

    public void updateQty(int srvPropID,int flag){
        for (int i=0; i<serviceProps.size(); i++){
            if(serviceProps.get(i).getId() == srvPropID)
                if (flag == 1)
                    serviceProps.get(i).incCount();
                else
                    serviceProps.get(i).decCount();
        }
    }
    public void updateSrvQty(int srvID,int flag){
        for (int i=0; i<services.size(); i++){
            if(Integer.parseInt(services.get(i).getSrv_sl()) == srvID)
                if (flag == 1)
                    services.get(i).incCount();
                else
                    services.get(i).decCount();
        }
    }

    public int size(){
        return serviceProps.size()+services.size();
    }

    public void clear(){
        serviceProps = new ArrayList<>();
        services = new ArrayList<>();
    }

    public double total(){
        double total = 0;
        for (ServiceProp service: serviceProps) {
            total += service.getCount() * service.getPrice();
        }
        for (Service servic: services) {
            total += servic.getCount() * Double.parseDouble(servic.getSrvPrice());
        }
        return total;
    }

    public List<ServiceProp> getServiceProps(){
        return serviceProps;
    }
    public List<Service> getServices(){
        return services;
    }
}
