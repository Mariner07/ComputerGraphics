package modernclient.model;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class SampleData {
    public static void fillSampleData(ObservableList<Person> backingList) {
        backingList.add(new Person("Waldo", "Soller", "random notes 1", "male", LocalDate.of(2020, 1, 8), "1.png" ));
        backingList.add(new Person("Herb", "Dinapoli", "random notes 2", "female", LocalDate.of(1998, 3,31), "2.png"));
        backingList.add(new Person("Shawanna", "Goehring", "random notes 3", "m", LocalDate.of(1995, 3,21), "3.png"));

    }

    /*
    Glenn Marti
    Waldo Soller
    Herb Dinapoli
    Shawanna Goehring
    Flossie Slack
    Magdalen Meadors
    Marylou Berube
    Ethan Nieto
    Elli Combes
    Andy Toupin
    Zenia Linwood
    Alan Mckeithan
    Kattie Mellott
    Benito Kearns
    Lloyd Cundiff
    Karleen Westrich
    Jada Perrotta
    Teofila Holbert
    Moira Heart
    Mitsuko Earwood
     */
}
