package anaydis.sort.nameSorting;

import java.util.Comparator;

public class NameComparator implements Comparator<FullName> {
    @Override
    public int compare(FullName o1, FullName o2) {
        Comparator<String> comp = Comparator.naturalOrder();

        int lastnamecomp = comp.compare(o1.getLastname(), o2.getLastname());

        return lastnamecomp != 0 ? lastnamecomp : comp.compare(o1.getFirstname(), o2.getFirstname());
    }
}
