package bean;

public class Loc {

    private String cid;
    private String location;
    private String parent_city;
    private String admin_area;
    private String cnty;
    private double lat;
    private double lon;

    public Loc(String cid, String location, String parent_city, String admin_area, String cnty, double lat, double lon) {
        this.cid = cid;
        this.location = location;
        this.parent_city = parent_city;
        this.admin_area = admin_area;
        this.cnty = cnty;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCid() {
        return cid;
    }

    public String getLocation() {
        return location;
    }

    public String getParent_city() {
        return parent_city;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public String getCnty() {
        return cnty;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Loc setCid(String cid) {
        this.cid = cid;
        return this;
    }

    public Loc setLocation(String location) {
        this.location = location;
        return this;
    }

    public Loc setParent_city(String parent_city) {
        this.parent_city = parent_city;
        return this;
    }

    public Loc setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
        return this;
    }

    public Loc setCnty(String cnty) {
        this.cnty = cnty;
        return this;
    }

    public Loc setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public Loc setLon(double lon) {
        this.lon = lon;
        return this;
    }

    @Override
    public String toString() {
        return location + " - " + admin_area;
    }
}
