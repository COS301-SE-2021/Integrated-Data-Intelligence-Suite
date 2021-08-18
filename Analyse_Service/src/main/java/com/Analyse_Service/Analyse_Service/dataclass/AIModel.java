package com.Analyse_Service.Analyse_Service.dataclass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "AIModel")
@Table(name = "AI Model")
public class AIModel {

    @Id
    @GeneratedValue(generator = "")
    private Long id;

    private AIType type;

    private float accuracy;


    public void setId(Long id){
        this.id = id;
;    }

    public void setType(AIType type){
        this.type = type;
    }

    public void setAccuracy(float accuracy){
        this.accuracy = accuracy;
    }


    public Long getId(){
        return this.id;
    }

    public AIType getType(){
        return this.type;
    }

    public float getAccuracy(){
        return this.accuracy;
    }
}
