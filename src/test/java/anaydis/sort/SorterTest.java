package anaydis.sort;

/**
 * Sorter tests should subclass this abstract implementation
 */
abstract class SorterTest extends AbstractSorterTest {

    @Override protected DataSetGenerator<String> createStringDataSetGenerator() {
        return new StringDataSetGenerator();
    }

    @Override protected DataSetGenerator<Integer> createIntegerDataSetGenerator() {
        throw new IllegalStateException("To be implemented!");
    }

    @Override protected SorterProvider getSorterProvider() {
        throw new IllegalStateException("To be implemented!");
    }
}
