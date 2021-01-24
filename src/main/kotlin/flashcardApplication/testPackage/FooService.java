package flashcardApplication.testPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooService {
    final private FooFormatter fooFormatter;

    @Autowired
    public FooService(FooFormatter fooFormatter) {

        this.fooFormatter = fooFormatter;
    }

    void format()
    {
        System.out.println(this.fooFormatter.format());
    }
}
