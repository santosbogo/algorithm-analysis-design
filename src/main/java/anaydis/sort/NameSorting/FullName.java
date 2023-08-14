package anaydis.sort.NameSorting;

import anaydis.sort.BubbleSorter;
import anaydis.sort.SorterType;

import java.util.List;

public class FullName {
    private String firstname;
    private String lastname;

    public FullName(String lastname, String firstname){
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }

}
