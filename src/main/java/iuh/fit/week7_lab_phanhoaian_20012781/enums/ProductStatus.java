package iuh.fit.week7_lab_phanhoaian_20012781.enums;

public enum ProductStatus {
    TERMINATED(0),
    ACTIVE(1),
    IN_ACTIVE(2);

    private int value;

    ProductStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
