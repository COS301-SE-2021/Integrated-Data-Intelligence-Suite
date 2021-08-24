package com.Analyse_Service.Analyse_Service.dataclass;

import javax.persistence.*;

@Entity
@Table(name = "AIModel")
public class AIModel {

    @Id
    @Column(name="ID")
    @GeneratedValue
    private Long id;

    @Column(name="type")
    private AIType type;

    @Column(name="accuracy")
    private float accuracy;


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
