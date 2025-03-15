package anaydis.sorting.namesorting;

import anaydis.sorting.InsertionSorter;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class FullNameTest {
    @Test
    public void sorterTest(){
        List<FullName> person = new LinkedList<>();

        FullName pers1 = new FullName("Alonso", "Paula");
        FullName pers2 = new FullName("Diaz", "Diego");
        FullName pers3 = new FullName("Alonso", "Diego");

        person.add(pers1);
        person.add(pers2);
        person.add(pers3);

        InsertionSorter sorter = new InsertionSorter();
        sorter.sort(new NameComparator(), person);

        Iterator<FullName> iter = person.iterator();

        assertThat(pers3).isEqualTo(iter.next());
        assertThat(pers1).isEqualTo(iter.next());
        assertThat(pers2).isEqualTo(iter.next());
    }
}
