package iuh.fit.week7_lab_phanhoaian_20012781.enums;

public enum EmployeeStatus {
    TERMINATED(0),
    ACTIVE(1),
    IN_ACTIVE(2);

    private final int code;

    private EmployeeStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EmployeeStatus fromCode(int code) {
        for (EmployeeStatus status : EmployeeStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AccountStatus code: " + code);
    }
}
