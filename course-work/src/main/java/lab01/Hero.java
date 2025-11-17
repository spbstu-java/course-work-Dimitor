package lab01;

public class Hero {
    private MovementStrategy movementStrategy;
    private String currentLocation;

    public Hero(String name, String startLocation) {
        this.currentLocation = startLocation;
        this.movementStrategy = new WalkingStrategy();
        System.out.println("Герой " + name + " стартует из " + startLocation);
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
        System.out.println("Теперь используется: " + strategy.getStrategyName());
    }

    public void moveTo(String destination) {
        if (currentLocation.equals(destination)) {
            System.out.println("Вы уже находитесь в " + destination);
            return;
        }
        System.out.println("\n--- Перемещение началось ---");
        movementStrategy.move(currentLocation, destination);
        currentLocation = destination;
        System.out.println("Успешно прибыли в " + destination);
        System.out.println("--- Перемещение завершено ---\n");
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public MovementStrategy getCurrentStrategy() {
        return movementStrategy;
    }
}
