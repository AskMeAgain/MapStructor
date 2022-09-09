package io.github.askmeagain.mapstructor.entities;


public class BaseEntity {

  int superBuilderInside;

  public BaseEntity() {
  }

  protected BaseEntity(BaseEntityBuilder<?, ?> b) {
    this.superBuilderInside = b.superBuilderInside;
  }

  public static BaseEntityBuilder<?, ?> builder() {
    return new BaseEntityBuilderImpl();
  }

  public int getSuperBuilderInside() {
    return this.superBuilderInside;
  }

  public void setSuperBuilderInside(int superBuilderInside) {
    this.superBuilderInside = superBuilderInside;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof BaseEntity)) return false;
    final BaseEntity other = (BaseEntity) o;
    if (!other.canEqual((Object) this)) return false;
    if (this.getSuperBuilderInside() != other.getSuperBuilderInside()) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof BaseEntity;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSuperBuilderInside();
    return result;
  }

  public String toString() {
    return "BaseEntity(superBuilderInside=" + this.getSuperBuilderInside() + ")";
  }

  public static abstract class BaseEntityBuilder<C extends BaseEntity, B extends BaseEntityBuilder<C, B>> {
    private int superBuilderInside;

    public B superBuilderInside(int superBuilderInside) {
      this.superBuilderInside = superBuilderInside;
      return self();
    }

    protected abstract B self();

    public abstract C build();

    public String toString() {
      return "BaseEntity.BaseEntityBuilder(superBuilderInside=" + this.superBuilderInside + ")";
    }
  }

  private static final class BaseEntityBuilderImpl extends BaseEntityBuilder<BaseEntity, BaseEntityBuilderImpl> {
    private BaseEntityBuilderImpl() {
    }

    protected BaseEntityBuilderImpl self() {
      return this;
    }

    public BaseEntity build() {
      return new BaseEntity(this);
    }
  }
}
