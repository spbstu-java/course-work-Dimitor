package lab01;

public interface MovementStrategy {
  /** Выполнить перемещение между двумя точками */
  void move(String from, String to);

  /** Название метода передвижения (для логов) */
  String getStrategyName();
}
