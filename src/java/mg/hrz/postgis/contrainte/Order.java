package mg.hrz.postgis.contrainte;

public enum Order {

    SORT("sort"),
    DIRECTION("dir"),
    ASC("asc"),
    DESC("desc");
    private final String value;

    private Order(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
