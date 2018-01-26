import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rooke on 1/26/2018.
 */
public class Op<T> implements Expr<T> {

    private Function<T> function;
    private List<Expr<T>> arguments;

    @SafeVarargs public Op(Function<T> fun, Expr<T>... args) {
        this.function = fun;
        this.arguments = Arrays.asList(args);
    }

    public T value() {
        List<T> values = new ArrayList<>();
        arguments.forEach((arg) -> values.add(arg.value()));

        return function.apply(values);
    }
}
