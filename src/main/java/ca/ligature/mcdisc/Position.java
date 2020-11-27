package ca.ligature.mcdisc;

import java.util.Objects;

public class Position {
  public final int x;
  public final int y;
  public final int z;

  public Position(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Position position = (Position) o;
    return x == position.x &&
        y == position.y &&
        z == position.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }
}
