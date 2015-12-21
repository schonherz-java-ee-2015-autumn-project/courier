package hu.schonherz.java.training.courier.test;
import org.json.JSONObject;

public class RandomUser {
    private String salt;
    private String gender;
    private String sha256;
    private Integer registered;
    private String cell;
    private String pictureThumbnail;
    private String pictureLarge;
    private String pictureMedium;
    private String ssn;
    private String sha1;
    private String password;
    private String phone;
    private Integer dob;
    private String personLastName;
    private String personTitle;
    private String personFirstName;
    private String location;
    private Integer zip;
    private String city;
    private String street;
    private String state;
    private String email;
    private String username;
    private String md5;

    /**
     * Given a jsob object of user build out all the fields that are possible
     *
     * @param user a JSOBObject of user
     */
    public RandomUser(JSONObject user) {
        setSalt((String) user.get("salt"));
        setGender((String) user.get("gender"));
        setSha256((String) user.get("sha256"));
        setRegistered((Integer) user.get("registered"));
        setCell((String) user.get("cell"));
        setSha1((String) user.get("sha1"));
        setPassword((String) user.get("password"));
        setPhone((String) user.get("phone"));
        setDob((Integer) user.get("dob"));
        setEmail((String) user.get("email"));
        setUsername((String) user.get("username"));
        setMd5((String) user.get("md5"));

        // get picture
        JSONObject picture = (JSONObject) user.get("picture");
        setPictureThumbnail((String) picture.get("thumbnail"));
        setPictureLarge((String) picture.get("large"));
        setPictureMedium((String) picture.get("medium"));

        // get person name
        JSONObject name = (JSONObject) user.get("name");
        setPersonFirstName((String) name.get("first"));
        setPersonTitle((String) name.get("title"));
        setPersonLastName((String) name.get("last"));

        // get address information
        JSONObject location = (JSONObject) user.get("location");
        setStreet((String) location.get("street"));
        setCity((String) location.get("city"));
        setZip((Integer) location.get("zip"));
        setState((String) location.get("state"));

    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

  

    public String getPictureThumbnail() {
        return pictureThumbnail;
    }

    public void setPictureThumbnail(String pictureThumbnail) {
        this.pictureThumbnail = pictureThumbnail;
    }

    public String getPictureLarge() {
        return pictureLarge;
    }

    public void setPictureLarge(String pictureLarge) {
        this.pictureLarge = pictureLarge;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDob() {
        return dob;
    }

    public void setDob(Integer dob) {
        this.dob = dob;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonTitle() {
        return personTitle;
    }

    public void setPersonTitle(String personTitle) {
        this.personTitle = personTitle;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}