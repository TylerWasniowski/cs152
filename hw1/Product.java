import java.util.Arrays;
import java.util.List;

/**
 * Created by rooke on 1/26/2018.
 */
public class Product extends Op<Integer> {
    @SafeVarargs public Product(Expr<Integer>... args) {
        super((numbers) -> {
            int product = 1;
            for (int n : numbers)
                product *= n;

            return product;
        }, args);
    }

}
