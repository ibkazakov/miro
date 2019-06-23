package widjets.repository;

import java.util.Objects;

public class Widjet {
    private Long id;
    private Double x;
    private Double y;
    private Integer z;

    private Double width;
    private Double height;

    public Widjet(Long id, Double x, Double y, Integer z, Double width, Double height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public void changeParameters(Double x, Double y, Integer z, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public void incrementZ() {
        z++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Widjet)) return false;
        Widjet widjet = (Widjet) o;
        return id.equals(widjet.id) &&
                x.equals(widjet.x) &&
                y.equals(widjet.y) &&
                z.equals(widjet.z) &&
                width.equals(widjet.width) &&
                height.equals(widjet.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, z, width, height);
    }
}
