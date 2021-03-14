package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for {@link primitives.Vector} class.
 */
class VectorTest {

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(3d, 1d, 2d);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(5d, -1.5d, 12d);
        Vector v1v2 = new Vector(8d, -0.5d, 14d);

        // test for a proper result
        assertEquals(v1v2, v1.add(v2), "add() wrong result");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(-3d, -1d, -2d);

        // test for zero vector when adding with the opposite vector
        try {
            v1.add(v3);
            fail("add() addition between the opposite vector doesn't throw an exception");
        } catch(Exception err) {
            // The expected result: zero vector.
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(3d, 1d, 2d);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(5d, -1.5d, 12d);
        Vector v1v2 = new Vector(-2d, 2.5d, -10d);

        // test the result is proper
        assertEquals(v1v2, v1.subtract(v2), "subtract() wrong result");

        // =============== Boundary Values Tests ==================
        // test for a zero vector when subtracting with itself
        try {
            v1.subtract(v1);
            fail("subtract() subtraction of itself doesn't throw an exception.");
        } catch(Exception err) {
            // The expected result: zero vector.
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(3d, 1d, 2d);

        // ============ Equivalence Partitions Tests ==============
        // test for a positive greater than 1 value
        assertTrue(isZero(v1.lengthSquared() * 9d - v1.scale(3d).lengthSquared()), "scale() wrong when giving a value greater than 1");

        // test for a positive smaller than 1 value
        assertTrue(isZero(v1.lengthSquared() * 0.25d - v1.scale(0.5d).lengthSquared()), "scale() wrong result when giving a positive value smaller than 1");

        // test for a negative value
        assertTrue(isZero(v1.lengthSquared() * 6.25d - v1.scale(-2.5d).lengthSquared()), "scale() wrong when giving a negative value");

        // test for a normalization
        assertTrue(isZero(v1.normalized().lengthSquared() - v1.scale(1 / v1.length()).lengthSquared()), "scale() result is not normalized");

        // =============== Boundary Values Tests ==================
        // test when scaling by 0 throws an exception.
        try {
            v1.scale(0d);
            fail("scale() when scaling by 0, doesn't throw an exception");
        } catch(Exception err) {
            // expected result: zero vector.
        }
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
            // expected result: zero vector.
        }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-2, -4, -6);

        // test for proper value
        assertTrue(isZero(v1.dotProduct(v2) + 28d), "dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 3, -2);

        // test in case of orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v3)), "dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);

        // test for a proper result
        assertTrue(isZero(v1.lengthSquared() - 14d), "lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(0, 3, 4);

        // test for a proper result
        assertTrue(isZero(v1.length() - 5d), "length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();

        // ============ Equivalence Partitions Tests ==============
        // test for a proper result.
        assertTrue(isZero(vCopyNormalize.length() - 1), "normalize() result is not a unit vector");

        // =============== Boundary Values Tests ==================
        // test that the returned vector is itself
        assertSame(vCopy, vCopyNormalize, "normalize() function creates a new vector");
    }

    /**
     * Test method for {@link Vector#normalized()}.
     */
    @Test
    void testNormalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();

        // ============ Equivalence Partitions Tests ==============
        // test for a proper result.
        assertTrue(isZero(u.length() - 1), "normalized() result is not a unit vector");

        // =============== Boundary Values Tests ==================
        // test that the returned vector is a new vector
        assertNotSame(u, v, "normalized() function does not create a new vector");
    }
}