package in.gvatreya.communications.utils;

public class Constants {

    public static enum DELIVERY_STATUS {
        IN_TRANSIT("IN_TRANSIT"),
        ERROR("ERROR"),
        COMPLETED("COMPLETED");

        private String columnName;

        DELIVERY_STATUS(final String columnName) {
            this.columnName = columnName;
        }

    }

}
