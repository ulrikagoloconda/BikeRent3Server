package Model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.time.LocalDate;

/**
 * Created by Goloconda on 2016-11-29.
 */
public class BikeMock {
    private int bikeID;
    private String brandName;
    private int modelYear;
    private String color;
    private String imagePath;
    private int size;
    private String type;
    private boolean available;
     private int createdByUserID;
    private String imageFileName;
     private BufferedImage bufferedImage;
    //  private FileInputStream fileInputImage;
    //  private LocalDate dayOfReturn;
      private LocalDate dayOfRent;
    //  private ByteArrayInputStream imageStream;

    public BikeMock() {

    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public int getCreatedByUserID() {
        return createdByUserID;
    }

    public void setCreatedByUserID(int createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public LocalDate getDayOfRent() {
        return dayOfRent;
    }

    public void setDayOfRent(LocalDate dayOfRent) {
        this.dayOfRent = dayOfRent;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}