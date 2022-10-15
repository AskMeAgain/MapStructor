package io.github.askmeagain.mapstructor.entities;


public class Output3 extends BaseEntity{

  String superNested1;
  String superNested2;
  String superOuterVariable;

  AnotherObject anotherObject;

  public Output3() {
  }

  protected Output3(Output3Builder<?, ?> b) {
    super(b);
    this.superNested1 = b.superNested1;
    this.superNested2 = b.superNested2;
    this.superOuterVariable = b.superOuterVariable;
    this.anotherObject = b.anotherObject;
  }

  public static Output3Builder<?, ?> builder() {
    return new Output3BuilderImpl();
  }

  public String getSuperNested1() {
    return this.superNested1;
  }

  public String getSuperNested2() {
    return this.superNested2;
  }

  public String getSuperOuterVariable() {
    return this.superOuterVariable;
  }
  public AnotherObject getAnotherObject() {
    return this.anotherObject;
  }

  public void setSuperNested1(String superNested1) {
    this.superNested1 = superNested1;
  }
  public void setAnotherObject(AnotherObject anotherObject) {
    this.anotherObject = anotherObject;
  }

  public void setSuperNested2(String superNested2) {
    this.superNested2 = superNested2;
  }

  public void setSuperOuterVariable(String superOuterVariable) {
    this.superOuterVariable = superOuterVariable;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof Output3)) return false;
    final Output3 other = (Output3) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$superNested1 = this.getSuperNested1();
    final Object other$superNested1 = other.getSuperNested1();
    if (this$superNested1 == null ? other$superNested1 != null : !this$superNested1.equals(other$superNested1))
      return false;
    final Object this$superNested2 = this.getSuperNested2();
    final Object other$superNested2 = other.getSuperNested2();
    if (this$superNested2 == null ? other$superNested2 != null : !this$superNested2.equals(other$superNested2))
      return false;
    final Object this$superOuterVariable = this.getSuperOuterVariable();
    final Object other$superOuterVariable = other.getSuperOuterVariable();
    if (this$superOuterVariable == null ? other$superOuterVariable != null : !this$superOuterVariable.equals(other$superOuterVariable))
      return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Output3;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $superNested1 = this.getSuperNested1();
    result = result * PRIME + ($superNested1 == null ? 43 : $superNested1.hashCode());
    final Object $superNested2 = this.getSuperNested2();
    result = result * PRIME + ($superNested2 == null ? 43 : $superNested2.hashCode());
    final Object $superOuterVariable = this.getSuperOuterVariable();
    result = result * PRIME + ($superOuterVariable == null ? 43 : $superOuterVariable.hashCode());
    return result;
  }

  public static abstract class Output3Builder<C extends Output3, B extends Output3Builder<C, B>> extends BaseEntityBuilder<C, B> {
    private String superNested1;
    private String superNested2;
    private String superOuterVariable;
    private AnotherObject anotherObject;

    public B superNested1(String superNested1) {
      this.superNested1 = superNested1;
      return self();
    }

    public B superNested2(String superNested2) {
      this.superNested2 = superNested2;
      return self();
    }

    public B superOuterVariable(String superOuterVariable) {
      this.superOuterVariable = superOuterVariable;
      return self();
    }

    public B anotherObject(AnotherObject anotherObject) {
      this.anotherObject = anotherObject;
      return self();
    }

    protected abstract B self();

    public abstract C build();

  }

  private static final class Output3BuilderImpl extends Output3Builder<Output3, Output3BuilderImpl> {
    private Output3BuilderImpl() {
    }

    protected Output3BuilderImpl self() {
      return this;
    }

    public Output3 build() {
      return new Output3(this);
    }
  }
}