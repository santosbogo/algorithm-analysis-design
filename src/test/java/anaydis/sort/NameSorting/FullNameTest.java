package anaydis.sort.NameSorting;

import anaydis.sort.BubbleSorter;
import anaydis.sort.SorterType;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

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

        BubbleSorter sorter = new BubbleSorter();
        sorter.sort(new NameComparator(), person);

        Iterator<FullName> iter = person.iterator();

        assertThat(pers3).isEqualTo(iter.next());
        assertThat(pers1).isEqualTo(iter.next());
        assertThat(pers2).isEqualTo(iter.next());
    }
}
