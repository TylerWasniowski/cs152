import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ExprTest2
{
    private static <T> Const<T> c(T v) { return new Const<T>(v); }

    @Test public void emptyExpr()
    {
        assertEquals(1, (Object) new Product().value());
    }

    @Test public void negativeProduct()
    {
        assertEquals(-30, (Object) new Product(c(-5), c(6)).value());
    }

    @Test public void positiveProductNegativeArgs() {
        assertEquals(50, (Object) new Product(c(-5), c(-5), c(-1), c(-2)).value());
    }
}