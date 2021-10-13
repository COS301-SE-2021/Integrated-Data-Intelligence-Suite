package com.Analyse_Service.Analyse_Service.dataclass;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ApplicationModel")
//@Table(name = "ParsedData")
public class ApplicationModel {
    //_ApplicationModel:b1094c1c7df440dbaef478baaa1ed953:8315d243c48840589fd0fc5f8f5425c9

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
