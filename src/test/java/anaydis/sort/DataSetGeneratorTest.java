package anaydis.sort;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Data set generator test
 */
public class DataSetGeneratorTest {

    private DataSetGenerator<String> createStringDataSetGenerator() {
        return new StringDataSetGenerator();
    }

    private DataSetGenerator<Integer> createIntegerDataSetGenerator() {
        return new IntegerDataSetGenerator();
    }

    /** Test create ascending size and sort. */
    @Test public void testCreateAscendingForStringGenerator() {
        final DataSetGenerator<String> stringDataSetGenerator = createStringDataSetGenerator();
        testGeneratorCreateAscending(stringDataSetGenerator, 10);
        testGeneratorCreateAscending(stringDataSetGenerator, 70);
    }

    /** Test create ascending size and sort. */
    @Test public void testCreateAscendingForIntegerGenerator() {
        final DataSetGenerator<Integer> integerDataSetGenerator = createIntegerDataSetGenerator();
        testGeneratorCreateAscending(integerDataSetGenerator, 10);
        testGeneratorCreateAscending(integerDataSetGenerator, 70);
    }

    /** Test create descending size and sort. */
    @Test public void testCreateDescendingForStringGenerator() {
        final DataSetGenerator<String> stringDataSetGenerator = createStringDataSetGenerator();
        testGeneratorCreateDescending(stringDataSetGenerator, 10);
        testGeneratorCreateDescending(stringDataSetGenerator, 70);
    }

    /** Test create descending size and sort. */
    @Test public void testCreateDescendingForIntegerGenerator() {
        final DataSetGenerator<Integer> integerDataSetGenerator = createIntegerDataSetGenerator();
        testGeneratorCreateDescending(integerDataSetGenerator, 10);
        testGeneratorCreateDescending(integerDataSetGenerator, 70);
    }

    /** Test create random size. */
    @Test public void testCreateRandomForStringGenerator() {
        final DataSetGenerator<String> stringDataSetGenerator = createStringDataSetGenerator();
        testGeneratorCreateRandom(stringDataSetGenerator, 10);
        testGeneratorCreateRandom(stringDataSetGenerator, 70);
    }

    /** Test create random size. */
    @Test public void testCreateRandomForIntegerGenerator() {
        final DataSetGenerator<Integer> integerDataSetGenerator = createIntegerDataSetGenerator();
        testGeneratorCreateRandom(integerDataSetGenerator, 10);
        testGeneratorCreateRandom(integerDataSetGenerator, 70);
    }

    private <T extends Comparable<T>> void testGeneratorCreateAscending(DataSetGenerator<T> generator, int size) {
        final List<T> ascending = generator.createAscending(size);
        assertThat(ascending).hasSize(size);
        assertThat(ascending).isSorted();
        assertThat(ascending).isSortedAccordingTo(generator.getComparator());
    }

    private <T extends Comparable<T>> void testGeneratorCreateDescending(DataSetGenerator<T> generator, int size) {
        final List<T> descending = generator.createDescending(size);
        assertThat(descending).hasSize(size);
        assertThat(descending).isSortedAccordingTo(generator.getComparator().reversed());
    }

    private <T extends Comparable<T>> void testGeneratorCreateRandom(DataSetGenerator<T> generator, int size) {
        final List<T> random = generator.createRandom(size);
        assertThat(random).hasSize(size);
    }
}
