package flashcardApplication.testPackage.testClasses;

import java.util.function.Consumer;

public class ConsumerExtend implements Consumer<Point> {
    @Override
    public void accept(Point point) {
        System.out.println(point.getXCord());
    }
}
