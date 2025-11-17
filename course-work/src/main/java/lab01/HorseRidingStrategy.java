package lab01;

public class HorseRidingStrategy implements MovementStrategy {
    @Override
    public void move(String from, String to) {
        System.out.println("Едем верхом из " + from + " в " + to);
        System.out.println("   Быстро и элегантно...");
        try {
            Thread.sleep(600);
            System.out.println("   Лошадь прибыла!");
        } catch (InterruptedException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getStrategyName() {
        return "Верхом на лошади";
    }
}
