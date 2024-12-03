package ilcarro_zlv.dto;


import java.util.ArrayList;

public class UserDto {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<String> userData = new ArrayList<>(4);


    public UserDto(String name, String lastName, String email, String password) {
        this.userData.add(this.name = name);
        this.userData.add(this.lastName = lastName);
        this.userData.add(this.email = email);
        this.userData.add(this.password = password);
    }

    public ArrayList<String> getUserData() {
        return userData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.userData.add(this.email = email);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.userData.add(this.password = password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.userData.add(this.name = name);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.userData.add(this.lastName = lastName);
    }


}
