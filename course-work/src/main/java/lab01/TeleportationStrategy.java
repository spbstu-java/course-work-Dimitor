package lab01;

public class TeleportationStrategy implements MovementStrategy {
    @Override
    public void move(String from, String to) {
        System.out.println("Телепортируемся из " + from + " в " + to);
        System.out.println("   *ВЖУХ*");
        System.out.println("   Добрались мгновенно!");
    }

    @Override
    public String getStrategyName() {
        return "Телепортация";
    }
}
