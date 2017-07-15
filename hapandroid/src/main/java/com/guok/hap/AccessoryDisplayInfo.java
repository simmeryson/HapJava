package com.guok.hap;

/**
 *
 * @author guok
 */

public class AccessoryDisplayInfo {
    private String label;
    private String manufacturer;
    private String model;
    private String serialNumber;
    private String firmwareRevision;

    public AccessoryDisplayInfo() {
    }

    /**
     *
     * @param label             label for the bridge. This will show in iOS during pairing.
     * @param manufacturer      manufacturer of the bridge. This information is exposed to iOS for
     *                          unknown purposes.
     * @param model             model of the bridge. This is also exposed to iOS for unknown purposes.
     * @param serialNumber      serial number of the bridge. Also exposed. Purposes also unknown.
     * @param firmwareRevision
     */
    public AccessoryDisplayInfo(String label,
                                String manufacturer,
                                String model,
                                String serialNumber,
                                String firmwareRevision) {
        this.label = label;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
        this.firmwareRevision = firmwareRevision;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirmwareRevision() {
        return firmwareRevision;
    }

    public void setFirmwareRevision(String firmwareRevision) {
        this.firmwareRevision = firmwareRevision;
    }

    @Override
    public String toString() {
        return "AccessoryDisplayInfo{" +
                "label='" + label + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", firmwareRevision='" + firmwareRevision + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessoryDisplayInfo that = (AccessoryDisplayInfo) o;

        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null)
            return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null)
            return false;
        return firmwareRevision != null ? firmwareRevision.equals(that.firmwareRevision) : that.firmwareRevision == null;

    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (firmwareRevision != null ? firmwareRevision.hashCode() : 0);
        return result;
    }
}
