package com.final_project.cargo_delivery.web.dto;

/**
 * TypeCargoViewDto
 *
 * @author Mykhailo Hryb
 */
public class TypeCargoViewDto {

    private int id;
    private String name;
    private String imagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "TypeCargoViewDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgPath='" + imagePath + '\'' +
                '}';
    }
}
