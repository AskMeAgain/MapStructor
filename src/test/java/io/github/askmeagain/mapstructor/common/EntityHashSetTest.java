package io.github.askmeagain.mapstructor.common;

import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityHashSetTest {

  @Test
  void hashSetTest(){
    //Arrange --------------------------------------------------------------------------------
    var set = new HashSet<VariableWithNameEntity>();

    var mock = Mockito.mock(PsiReferenceExpression.class);
    Mockito.when(mock.getQualifiedName()).thenReturn("null");

    var a = VariableWithNameEntity.builder()
        .type(PsiType.BOOLEAN)
        .name(mock)
        .build();

    var b = VariableWithNameEntity.builder()
        .type(PsiType.BOOLEAN)
        .name(mock)
        .build();

    //Act ------------------------------------------------------------------------------------
    set.add(a);
    set.add(b);

    //Assert ---------------------------------------------------------------------------------
    assertThat(a.hashCode()).isEqualTo(b.hashCode());
    assertThat(set).hasSize(1);
  }

}
