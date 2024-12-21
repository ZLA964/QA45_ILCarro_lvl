package ilcarro.utils;

public enum Fuel {
    DIESEL("//option[@value='Diesel']"),
    PETROL("//option[@value='Petrol']"),
    HYBRID("//option[@value='Hybrid']"),
    ELECTRIC("//option[@value='Electric']"),
    GAS("//option[@value='Gas']");
    private String locator;



    Fuel(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }
}
