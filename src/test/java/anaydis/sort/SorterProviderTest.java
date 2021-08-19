package anaydis.sort;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sorter provider test
 */
public class SorterProviderTest {

    /** Return SorterProvider. */
    private SorterProvider createSorterProvider() {
        throw new IllegalStateException("To be implemented!");
    }

    /** Test getAllSorters method to return an iterable containing all implemented sorters. */
    @Test public void testGetAllSorters() {
        final SorterProvider provider = createSorterProvider();
        final Iterable<Sorter> sorters = provider.getAllSorters();
        assertThat(sorters).isNotNull();
        for (final Sorter sorter : sorters) assertThat(sorter).isNotNull();
    }

    /** Test getSorterForType method to return a matching sorter for each sorter type. Sorter instances must match.*/
    @Test public void testGetSorterForType() {
        final SorterProvider provider = createSorterProvider();
        final Iterable<Sorter> sorters = provider.getAllSorters();
        assertThat(sorters).isNotNull();
        for (final Sorter sorter : sorters) {
            final Sorter sorterForType = provider.getSorterForType(sorter.getType());
            // Assert not null
            assertThat(sorterForType).isNotNull();
            // Assert matching sorter type
            assertThat(sorter.getType()).isEqualTo(sorterForType.getType());
            // Assert same instance. Do not create a new XXXSorter on each invocation.
            assertThat(sorter).isEqualTo(sorterForType);
        }
    }
}
