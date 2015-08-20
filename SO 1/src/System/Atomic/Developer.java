package System.Atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Developer {

    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String city;
    private final List<Language> programmingLanguages;
    
    public static enum Language {
        C, CLOJURE, GROOVY, JAVA, JAVA_SCRIPT, PYTHON, RUBY, SCALA
    }
    
    public Developer(String firstName, String lastName, String city, Integer age, Language ... languages) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.programmingLanguages = Arrays.asList(languages);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getCity() {
        return city;
    }
    
    public List<Language> getProgrammingLanguages() {
        return Collections.unmodifiableList(programmingLanguages);
    }

    public Integer getAge() {
        return age;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, city, programmingLanguages);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Developer) {
            Developer other = (Developer) obj;
            return Objects.equals(firstName, other.firstName)
                    && Objects.equals(lastName, other.lastName)
                    && Objects.equals(age,  other.age)
                    && Objects.equals(city, other.city)
                    && Objects.equals(programmingLanguages, other.programmingLanguages);
        }

        return false;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + age + ")";
    }
    
    public static class Repository {
        
        public static List<Developer> getSampleList() {
            return new ArrayList<Developer>(Arrays.asList(
                    new Developer("Adam", "Nowak", "Kraków", 24, Language.JAVA, Language.SCALA),
                    new Developer("Beata", "Nowak", "Warszawa", 27, Language.RUBY, Language.JAVA_SCRIPT),
                    new Developer("Jakub", "Mazur", "Katowice", 32, Language.GROOVY, Language.JAVA),
                    new Developer("Marcin", "Wróbel", "Kraków", 35, Language.JAVA, Language.JAVA_SCRIPT),
                    new Developer("Natalia", "WoŸniak", "Katowice", 27, Language.JAVA),
                    new Developer("Hubert", "Krawczyk", "Wroc³aw", 26, Language.C),
                    new Developer("Zenon", "Zaj¹c", "Warszawa", 30, Language.PYTHON)));
        }
        
    }
}