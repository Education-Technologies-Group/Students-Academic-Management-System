package models.user;

class UserModel {
    private int id;
    private String hashed_password;
    private String full_name;
    private String email;
    private String phone_number;

    public UserModel(int id, String password, String full_name, String email, String phone_number) {
        this.id = id;
        this.hashed_password = password;
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHashedPassword() {
        return hashed_password;
    }

    public void setHashedPassword(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }
}
