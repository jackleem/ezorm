package com.ezorm.model;

/**
 * Created by Li Yu on 2017/6/6.
 */
public class User {
    private Long id;
    private String name;
    private String gender;
    private String nationality;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getNationality(){
        return this.nationality;
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    @Override
    public String toString(){
        return "People: [id="+id
                +", name="+name
                +", gender="+gender
                +", nationality="+nationality
                +"]";
    }
}
